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

    public void doOperation() throws IOException, CommunicationException {

        MovieTicketSale movieTicketSale = new MovieTicketSale(dispenser, multiplex);
        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);

        int mode = 0;
        while (true) {

            borrarOpciones();
            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("URJC CINEMA - BIENVENIDO"));
            dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CARTELERA"));
            dispenser.setOption(1, "SNACKS");
            dispenser.setOption(2, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CAMBIAR IDIOMA"));
            

            char opcion = dispenser.waitEvent(30);
            switch (opcion) {
                // opcion de CARTELERA    
                case 'A':
                    if (opcion == 'A') {
                        if (mode == 0) {
                            movieTicketSale.doOperation();
                        }
                    }
                    
                    case 'B':
                    if (opcion == 'B') {
                        if (mode == 0) {

                            Snacks snacks = new Snacks(dispenser, multiplex);
                            snacks.doOperation();
                        }
                    }
                    
                    
//                 opcion de PALOMITAS
//                case 'B':
//                    if (opcion == 'B') {
//                        if (mode == 0) {
//
//                            CompraPalomitas compraPalomitas = new CompraPalomitas(dispenser, multiplex);
//                            compraPalomitas.doOperation();
//                        }
//                    }
                case 'C':
                    if (opcion == 'C'){
                        if (mode == 0){
                            ComprarBebidas comprarBebidas = new ComprarBebidas(dispenser, multiplex);
                            comprarBebidas.doOperation();
                        }
                    }
                // opcion de CAMBIO DE IDIOMA
                case 'D':
                    if (opcion == 'D') {
                        if (mode == 0) {
                            idiomSelection.doOperation();
                        }
                    }
                case 'E':
                    if (opcion == 'E'){
                        if (mode == 0) {
                            multiplex.start();
                        }
                    }
                //SEGURIDAD POR SI SE INSERTA LA TARJE NO HACER NADA 
                case '1':
                    if (opcion == '1') {
                        doOperation();
                    }

            }   
        }
    }
}
