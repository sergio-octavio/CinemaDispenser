package cinemadispenser;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import sienens.CinemaTicketDispenser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.naming.CommunicationException;

/**
 * Gestiona la venta de un conjunto de entradas a un cliente.
 *
 * @author octavio
 *
 */
public class MovieTicketSale extends Operation {

    public MovieTicketSale(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, CommunicationException {
        super(dispenser, multiplex);
        state = new MultiplexState();
        state.loadMoviesAndSessions();
    }

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

    private String optionMenuSeats;
    private Boolean isNewDayState;

    public void doOperation() throws IOException, CommunicationException {

        //newDay(multiplex);
        Theater theater = selectTheatre();
        Session session = selectSession(theater);
        ArrayList<Seat> seat = selectSeats(theater, session);

        if (optionMenuSeats == "CANCEL") {
            MainMenu mainMenu = new MainMenu(dispenser, multiplex);
            mainMenu.doOperation();
        } else if (seat.size() > 0) {  //si se ha seleccionado al menos una entrada...
            double totalPrice = computePrice(theater, seat);
            PerformPayment performPayment = new PerformPayment(dispenser, multiplex, (int) totalPrice);
            String mensaje = (seat.size() + " entradas para " + theater.getFilm().getName() + "." + "\n" + "Precio total: " + totalPrice + "€");

            double precioFinal;
            boolean esSocio;
            if (performPayment.comprobarEsSocio(mensaje)) {
                esSocio = true;
                precioFinal = (totalPrice - (totalPrice * 0.3));
            } else {
                precioFinal = totalPrice;
                esSocio = false;
            }
            borrarOpciones();
            mensaje = (seat.size() + " entradas para " + theater.getFilm().getName() + "." + "\n" + "Precio total: " + precioFinal);
            dispenser.setDescription(mensaje);
            serializeMultiplexstate(); //guarda el proceso SERIALIZABLE
            printTicket(theater, seat, session, precioFinal, esSocio);

            dispenser.print(printTicket(theater, seat, session, precioFinal, esSocio));
            dispenser.setTitle("RECOJA LA TARJETA DE CRÉDITO");
            dispenser.expelCreditCard(30);
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
                optionMenuSeats = "CANCEL";
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

    private void presentSeats(Theater theater, Session session) throws IOException {
        int maxRows = theater.getMaxRows();
        int maxCols = theater.getMaxCols();
        Set<Seat> seatSet = theater.getSeatSet();
        dispenser.setTheaterMode(maxRows, maxCols);
        session.getOccupiedSeatSet();
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {

                if (seatSet.contains(new Seat(row, col))) {
                    if (session.isOccupied(row + 1, col + 1)) { //CORREGIDO! se le suma a 1 row y col para que cuadre bien al marcarlo
                        dispenser.markSeat(row + 1, col + 1, 1); //OCCUPIED SEAT
                    } else {
                        dispenser.markSeat(row + 1, col + 1, 2); //UNOCCUPIED SEAT
                    }
                } else {
                    dispenser.markSeat(row + 1, col + 1, 0);  //NOT A SEAT (empty space for corridors)
                }
            }
        }
        dispenser.setOption(0, "CANCELAR");
        dispenser.setOption(1, "ACEPTAR");
    }

    /**
     * @Description: return full price of the seats
     * @param theater
     * @param seatsBuyed
     * @return totalPrice
     */
    private int computePrice(Theater theater, ArrayList<Seat> seatsBuyed) throws FileNotFoundException {
        int totalPrice = 0;
        for (int i = 0; i < seatsBuyed.size(); i++) {
            totalPrice = totalPrice + theater.getPrice();
        }
        return totalPrice;
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

    /**
     * Description: Method to print ticket
     *
     * @param theater
     * @param seat
     * @param session
     */
    private List<String> printTicket(Theater theater, ArrayList<Seat> seat, Session session, double precioFinal, boolean esSocio) throws FileNotFoundException {

        List<String> text = new ArrayList<>();

        text.add("   Entrada para " + theater.getFilm().getName());
        text.add("   ===================");
        text.add("   Sala " + theater.getNumber());
        text.add("   Hora " + session.getHour());
        int countSeat = 0;
        int countRow = 0;
        for (int i = 0; i < seat.size(); i++) {
            countSeat = seat.get(i).getCol();
            countRow = seat.get(i).getRow();
            text.add("   Fila " + countRow + " - Butaca " + countSeat + " - Precio: " + theater.getPrice() + "€");
        }
        int totalPrice = computePrice(theater, seat);
        text.add("   Precio " + totalPrice + "€");
        Socios socios = new Socios();

        if (esSocio) {
            text.add("   Precio de SOCIO " + precioFinal + "€");
        }
        return text;
    }

    public void serializeMultiplexstate() {
        //en este metodo tendremos que meter lo que queramos que se guqarde a disco 
        try {
            String ficheroSerializable = "./ficheros/serializable.ser";
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroSerializable));
            salida.writeObject(state);
            salida.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean newDay(Multiplex multiplex) throws IOException, CommunicationException {
        borrarOpciones();
        dispenser.setTitle("¿ES UN NUEVO DIA?");
        dispenser.setOption(0, "SÍ");
        dispenser.setOption(1, "NO");
        char option = dispenser.waitEvent(30);

        if (option == 'A') {

            state = new MultiplexState();
            state.loadMoviesAndSessions();
            //loadPartners();
            isNewDayState = true;
        } else if (option == 'B') {
            isNewDayState = false;
        }
        return isNewDayState;
    }

}
