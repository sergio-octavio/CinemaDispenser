package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author octavio
 */
public class Popcorn extends Operation {

    public Popcorn(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        super(dispenser, multiplex);
    }

    void doOperation() throws IOException {

        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);
        MainMenu mainMenu = new MainMenu(dispenser, idiomSelection.getMultiplex());

        int mode = 0;
        borrarOpciones();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("AÚN SE ESTÁN HACIENDO"));
        dispenser.setDescription(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("LAS PALOMITAS ESTARÁN DISPONIBLES MÁS ADELANTE. DISCULPE LAS MOLESTIAS"));
        dispenser.setOption(4, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("VOLVER"));
        while (true) {

            char c = dispenser.waitEvent(30);
            if (c == '1') {
                dispenser.retainCreditCard(false);
            } else if (c == 'E') {
                if (mode == 0) {
                    mainMenu.doOperation();
                }
            }
        }
    }

}
