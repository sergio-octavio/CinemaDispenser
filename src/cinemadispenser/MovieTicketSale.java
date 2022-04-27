package cinemadispenser;

import java.io.IOException;
import java.util.List;
import sienens.CinemaTicketDispenser;
import cinemadispenser.Theater;
import cinemadispenser.Film;
import java.lang.String;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public void doOperation() throws IOException {

        while (true) {
            Theater theater = selectTheatre();
            Session session = selectSession(theater);
            ArrayList<Seat> seat = selectSeats(theater, session);
        }

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
        Session optionSessiopn = theater.getSession().get(optionNum);
        return optionSessiopn;
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

            } else if (c == 'B') { //aceptar
                borrarOpciones();
                dispenser.setOption(0, "LA OPCION HA SIDO SELECCIONADA");
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
//                    dispenser.markSeat(selectedSeat.getCol()+ 1, selectedSeat.getRow() + 1, 2);
                    session.unocupiesSeat(row, col);
                    buyedSeats.remove(selectedSeat);
                } else if (buyedSeats.size() == 4) {
                    dispenser.setTitle("EL MAXIMO ES DE 4 BUTACAS");
                }
            }
        }
        return buyedSeats;
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

    private boolean performPayment(Theater theater, Session session, ArrayList<Seat> seatsBuyed) {
        boolean paymentCompleted = false;
        if (seatsBuyed != null) {
            int totalPrice = computePrice(theater, seatsBuyed);
            PerformPayment.doOperation payment = new PerformPayment.doOperation(theater, session, seatsBuyed, totalPrice);
        }
        return paymentCompleted;
    }

    private int computePrice(Theater theater, ArrayList<Seat> seatsBuyed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
