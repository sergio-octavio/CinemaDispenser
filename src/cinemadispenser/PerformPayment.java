package cinemadispenser;

import java.util.ArrayList;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author octavio
 */
public class PerformPayment extends Operation {

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    void doOperation() {

        borrarOpciones();
        dispenser.setTitle("INSERTE LA TARJETA");
        dispenser.waitEvent(30);

    }

    public PerformPayment(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        super(dispenser, multiplex);
    }

    static class doOperation {

        public doOperation(Theater theater, Session session, ArrayList<Seat> seatsBuyed, int totalPrice) {
        }
    }

}
