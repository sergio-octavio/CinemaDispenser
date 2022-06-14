package cinemadispenser;

import java.io.IOException;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;

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

    /**
     * 
     * @throws IOException
     * @throws CommunicationException 
     * Muestra el menú principal del programa. Cartelera, snacks, cambiar idioma.
     */
    public void doOperation() throws IOException, CommunicationException {

        MovieTicketSale movieTicketSale = new MovieTicketSale(dispenser, multiplex);
        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);

        int mode = 0;
        while (true) {

            borrarOpciones();
            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("URJC CINEMA - BIENVENIDO")); //NOI18N
            dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CARTELERA")); //NOI18N
            dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("SNACKS"));
            dispenser.setOption(2, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CAMBIAR IDIOMA")); //NOI18N

            char opcion = dispenser.waitEvent(30);
            switch (opcion) {
                // opcion de CARTELERA    
                case 'A':
                    if (opcion == 'A') {
                        if (mode == 0) {
                            movieTicketSale.doOperation();
                        }
                    }
                    //OPCION SNAKS
                case 'B':
                    if (opcion == 'B') {
                        if (mode == 0) {
                            Snacks snacks = new Snacks(dispenser, multiplex);
                            snacks.doOperation();
                        }
                    }

                
                // opcion de CAMBIO DE IDIOMA
                case 'C':
                    if (opcion == 'C') {
                        if (mode == 0) {
                            idiomSelection.doOperation();
                        }
                    }
                case 'E':
                    if (opcion == 'E') {
                        if (mode == 0) {
                            multiplex.start();
                        }
                    }
                //SEGURIDAD POR SI SE INSERTA LA TARJETA: NO HACER NADA 
                case '1':
                    if (opcion == '1') {
                        doOperation(); //si se mete la tarjeta vuelve al mismo menu
                    }

            }
        }
    }
}
