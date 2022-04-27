package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 * 
 * Gestiona el menú principal de la aplicación. Además, es la clase que contiene
 * al resto de operaciones.
 *    
 * @author octavio
 *  
 */
public class MainMenu extends Operation {

    public MainMenu(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        super(dispenser, multiplex);
    }

    void doOperation() throws FileNotFoundException, IOException {

        MovieTicketSale movieTicketSale = new MovieTicketSale(dispenser, multiplex);
        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);
        
        int mode = 0;
        while (true) {
            
            borrarOpciones();

            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("URJC CINEMA - BIENVENIDO"));
            dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CARTELERA"));
            dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PALOMITAS"));
            dispenser.setOption(2, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CAMBIAR IDIOMA"));

            char opcion = dispenser.waitEvent(30);
            switch (opcion) {
                // opcion de CARTELERA    
                case 'A':
                    if (opcion == '1') {
                        dispenser.retainCreditCard(false);
                    } else if (opcion == 'A') {
                        if (mode == 0) {
                            movieTicketSale.doOperation();
                  
                        }
                    }
                // opcion de PALOMITAS
                case 'B':
                    if (opcion == '1') {
                        dispenser.retainCreditCard(false);
                    } else if (opcion == 'B') {
                        if (mode == 0) {
                            Popcorn popcorn = new Popcorn(dispenser, multiplex);
                            popcorn.doOperation();
                        }
                    }
                    
                 // opcion de CAMBIO DE IDIOMA
                case 'C':
                    if (opcion == '1') {
                        dispenser.retainCreditCard(false);
                    } else if (opcion == 'C') {
                        if (mode == 0) {
                            idiomSelection.doOperation();
                        }
                    }
            }
        }
    }
}
