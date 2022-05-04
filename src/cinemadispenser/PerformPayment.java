package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author octavio
 */
public class PerformPayment extends MovieTicketSale {

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public void doOperation(Theater theater, Session session, ArrayList<Seat> seatsBuyed) {

        borrarOpciones();
    }

    public PerformPayment(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException {
        super(dispenser, multiplex);
    }

}
