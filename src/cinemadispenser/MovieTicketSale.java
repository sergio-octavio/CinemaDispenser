package cinemadispenser;

import java.io.IOException;
import sienens.CinemaTicketDispenser;
import java.util.ArrayList;
import java.util.Set;
import javax.naming.CommunicationException;
import urjc.UrjcBankServer;

/**
 * Gestiona la venta de un conjunto de entradas a un cliente.
 *
 * @author octavio
 *
 */
public class MovieTicketSale extends Operation {

    private MultiplexState state;

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public MultiplexState getState() {
        return state;
    }

    public Multiplex getTitle() {
        return multiplex;
    }

    public void doOperation() throws IOException, CommunicationException {

        while (true) {
            Theater theater = selectTheatre();
            Session session = selectSession(theater);
            ArrayList<Seat> seat = selectSeats(theater, session);
            performPayment(theater, session, seat);

            //SI SE PIDE COMPRAR PALOMITAS DESCOMENTAR ESTAS DOS LINEAS
//            Popcorn popcorn = new Popcorn(dispenser, multiplex); 
//            popcorn.doOperation();
        }

    }

    public MovieTicketSale(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException {
        super(dispenser, multiplex);
        state = new MultiplexState();

//        if (esun nuevo dia){
//        state = new MultiplexState();
//    } else{
//            state = desserializeMultiplzState(); 
//            }
        state.loadMoviesAndSessions();
        state.loadpartners();

    }

    private Theater selectTheatre() {
        borrarOpciones();
        //imprimimos las peliculas por pantalla
        dispenser.setTitle("PELÍCULAS");
        int peliculasInt = 0;

        //imprime las peliculas disponibles en el dispensador
        for (Theater sala : state.getTheatreList()) {
            dispenser.setOption(peliculasInt++, sala.getFilm().getName());
        }
        char option = dispenser.waitEvent(30);
        int optionNum = convertiraNumero(option);
        Theater optionTheatre = state.getTheatreList().get(optionNum);
        return optionTheatre;
    }

    private Session selectSession(Theater theater) {

        borrarOpciones();
        dispenser.setTitle("Seleccione sesión");
        dispenser.setImage("./Poster/" + theater.getFilm().getPoster());
        dispenser.setDescription(theater.getFilm().getDescription());
        int sessions = 0;
        for (Session session : theater.getSession()) {
            dispenser.setOption(sessions++, session.getHour().toString());
        }
        char option = dispenser.waitEvent(30);
        int optionNum = convertiraNumero(option);
        Session optionSession = theater.getSession().get(optionNum);
        return optionSession;
    }

    /**
     * Mark a seat in the screen. the seat screen in composed by a matrix of 8
     * rows and 15 cols. param para markSeat (no seat), 1 (OCCUPIED), 2 (FREE),
     * 3 (SELECTED).
     *
     * @param theater
     * @param session
     * @return
     * @throws IOException
     */
    private ArrayList<Seat> selectSeats(Theater theater, Session session) throws IOException {

        MainMenu mainMenu = new MainMenu(dispenser, multiplex);

        borrarOpciones();
        boolean exit = false;
        dispenser.setTitle("Seleccione butacas");
        presentSeats(theater, session);
        ArrayList<Seat> buyedSeats = new ArrayList<>();

        while (!exit) {
            char c = dispenser.waitEvent(30);
            if (c == 'A') { //cancelar
                exit = true;
                buyedSeats = null;
                // COMPROBAR QUE AL SELECCIONAR EL BOTON DE ACEPTAR TIENE AL MENOS UNA BUTACA SELECCIONADA
            } else if (c == 'B') {
                exit = true;

            } else if (c != 0) {

                byte col = (byte) (c & 0xFF);
                byte row = (byte) ((c & 0xFF00) >> 8);
                Seat selectedSeat = new Seat(row, col);
                if (!session.isOccupiesSeat(row, col) && (buyedSeats.size() != 4)) {
                    session.occupeSeat(row, col);
                    dispenser.setTitle("Fila: " + row + " Asiento: " + col);
                    dispenser.markSeat(row, col, 3);
                    buyedSeats.add(selectedSeat);
                } else if (buyedSeats.contains(selectedSeat)) {

                    dispenser.markSeat(row, col, 2);
                    session.unocupiesSeat(row, col);
                    buyedSeats.remove(selectedSeat);
                } else if (buyedSeats.size() == 4) {
                    dispenser.setTitle("EL MAXIMO ES DE 4 BUTACAS");
                }
            }
        }

        return buyedSeats;
    }

    private boolean performPayment(Theater theater, Session session, ArrayList<Seat> seatsBuyed) throws IOException, CommunicationException {
        boolean paymentCompleted = false;

        if (!seatsBuyed.isEmpty()) { //SI SE SELECCIONA AL MENOS UNA BUTACA 
            int totalPrice = computePrice(theater, seatsBuyed);

            borrarOpciones();
            dispenser.setTitle("INSERTE LA TARJETA DE CRÉDITO");
            dispenser.setImage("./Poster/" + theater.getFilm().getPoster());
            dispenser.setDescription(seatsBuyed.size() + " entradas para " + theater.getFilm().getName() + "." + "\n"
                    + "Precio total: " + totalPrice + "€");
            dispenser.setOption(4, "CANCELAR");
            dispenser.setOption(5, "ACEPTAR");
            char option = dispenser.waitEvent(30);

            //MIRAR ESTE METODO PORQUE PUEDE QUE TODO ESTO ESTE DENTRO DE LA CLASE DE PerformPayment.doOperation(); 
            boolean exit = false;

            while (!exit) {
                char c = dispenser.waitEvent(30);
                
                if (option == '1') { //si se inserta la tarjeta de credito
                    exit = true;
                    UrjcBankServer urjcBankServer = new UrjcBankServer();
                    urjcBankServer.comunicationAvaiable();
                    dispenser.expelCreditCard(totalPrice);
                    
                }
                if (c == 'E') { //boton de cancelar
                    exit = true;
                    selectSeats(theater, session);// COMPROBAR QUE AL SELECCIONAR EL BOTON DE ACEPTAR TIENE AL MENOS UNA BUTACA SELECCIONADA
                } else if (c == 'F') { //boton de aceptar
                    exit = true;
                }
            }
        }
        return paymentCompleted;
    }

    private void presentSeats(Theater theater, Session session) throws IOException {
        int maxRows = theater.getMaxRows();
        int maxCols = theater.getMaxCols();
        Set<Seat> seatSet = theater.getSeatSet();
        dispenser.setTheaterMode(maxRows, maxCols);
        for (int row = 0; row < maxRows; row++) {
            for (int colum = 0; colum < maxCols; colum++) {
                if (seatSet.contains(new Seat(row, colum))) {
                    if (session.isOccupied(row, colum)) {
                        dispenser.markSeat(row + 1, colum + 1, 1);
                    } else {
                        dispenser.markSeat(row + 1, colum + 1, 2);
                    }
                } else {
                    dispenser.markSeat(row + 1, colum + 1, 0);
                }
            }
        }
        dispenser.setOption(0, "CANCELAR");
        dispenser.setOption(1, "ACEPTAR");
    }

    private Seat getSeatSelect(char option) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * @Description: return full price of the seats
     * @param theater
     * @param seatsBuyed
     * @return totalPrice
     */
    private int computePrice(Theater theater, ArrayList<Seat> seatsBuyed) {
        int totalPrice = 0;
        for (int i = 0; i < seatsBuyed.size(); i++) {
            totalPrice = totalPrice + theater.getPrice();
        }
        return totalPrice;

    }

    public void serializableMultiplexState() {

    }

    private int convertiraNumero(char opcion) {

        switch (opcion) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            default:
                return -1;
        }
    }
}
