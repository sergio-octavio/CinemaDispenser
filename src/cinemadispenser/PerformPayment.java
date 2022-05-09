package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author octavio
 */
public class PerformPayment extends MovieTicketSale {

    PerformPayment(CinemaTicketDispenser dispenser, Multiplex multiplex, int totalPrice) throws IOException, CommunicationException {
        super(dispenser, multiplex);
        this.totalPrice = totalPrice;

    }

    private int totalPrice;

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public void doOperation(String mensaje) throws CommunicationException, IOException {

        borrarOpciones();
        dispenser.setTitle("INSERTE LA TARJETA DE CRÉDITO");
        dispenser.setDescription(mensaje);
        dispenser.setOption(4, "CANCELAR");
        boolean exit = false;
        while (!exit) {
            char option = dispenser.waitEvent(30);

            if (option == '1') { //si se inserta la tarjeta de credito
                exit = true;
                UrjcBankServer urjcBankServer = new UrjcBankServer();
                urjcBankServer.comunicationAvaiable();
                urjcBankServer.doOperation(dispenser.getCardNumber(), totalPrice);
                dispenser.expelCreditCard(30);
                break;
            } else if (option == 'E') { //boton de cancelar
                exit = true;
                MovieTicketSale movieTicketSale = new MovieTicketSale(dispenser, multiplex);
                movieTicketSale.doOperation();
            }
        }
    }
}
