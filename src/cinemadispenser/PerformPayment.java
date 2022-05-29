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
    
    public boolean comprobarEsSocio(String mensaje) throws CommunicationException, IOException {
        
        borrarOpciones();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("INSERTE LA TARJETA DE CRÉDITO"));
        dispenser.setDescription(mensaje);
        dispenser.setOption(4, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CANCELAR"));
        boolean exit = false;
        boolean esSocioFinal = false;
        while (!exit) {
            char option = dispenser.waitEvent(30);
            
            if (option == '1') { //si se inserta la tarjeta de credito
                exit = true;
                UrjcBankServer urjcBankServer = new UrjcBankServer();
                
                //comprobacion de la comunicacion con el banco 
                
                if (urjcBankServer.comunicationAvaiable() == true) {
                    urjcBankServer.doOperation(dispenser.getCardNumber(), totalPrice);
                    dispenser.expelCreditCard(30);
                    Socios socios = new Socios();
                    boolean esSocio = socios.comprobarTarjeta(dispenser.getCardNumber());
                    double precioFinal = 0;
                    if (esSocio) {
                        esSocioFinal = true;
                        return esSocioFinal;
                    } else {
                        esSocioFinal = false;
                        return esSocioFinal;
                    }
                    //si la comunicacion falla, salta el mensaje.
                } else {
                    dispenser.setTitle("HA OCURRIDO UN PROBLEMA");
                    dispenser.setDescription("LA COMUNICACION CON EL BANCO HA FALLADO, RECOJA LA TARJETA. DISCULPE LAS MOLESTIAS");
                    dispenser.expelCreditCard(30);
                    MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                    mainMenu.doOperation();
                    
                }
//                urjcBankServer.comunicationAvaiable();
                
            } else if (option == 'E') { //boton de cancelar
                exit = true;
                MovieTicketSale movieTicketSale = new MovieTicketSale(dispenser, multiplex);
                movieTicketSale.doOperation();
            }
        }
        return esSocioFinal;
    }
}
