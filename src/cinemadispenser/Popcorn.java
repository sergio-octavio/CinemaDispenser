package cinemadispenser;

import java.io.IOException;
import sienens.CinemaTicketDispenser;

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
        dispenser.setTitle("¿COMPRAR PALOMITAS?");
        dispenser.setOption(0, "PALOMITAS");
        dispenser.setOption(1, "SEGUIR COMPRANDO SIN PALOMITAS");
        dispenser.setOption(2, "CANCELAR PEDIDO");
        while (true) {

            char opcion = dispenser.waitEvent(30);
            switch (opcion) {
                // opcion de CARTELERA    
                case 'A':
                    if (opcion == 'A') {
                        if (mode == 0) {

                            dispenser.setTitle("PALOMITAS");
                            dispenser.setDescription("LAS PALOMITAS SE ESTÁN HACIENDO, COMPRE UNAS ENTRADAS PORQUE ESTO VA PARA LARGO... "
                                    + "NOS HEMOS QUEDADO SIN PALOMITAS Y EL COMPAÑERO LAS HA IDO A COPRAR AL CHINO DE ABAJO");
                            //comprar palomitas
                            //si lo piden para el examen
                            //HACER EL ALGORITMO DE COMPRA DE PALOMITAS
                        }
                    }
                // opcion de PALOMITAS
//                case 'B':
//                    if (opcion == 'B') {
//                        if (mode == 0) {
//                            Popcorn popcorn = new Popcorn(dispenser, multiplex);
//                            popcorn.doOperation();
//                        }
//                    }
                    
                    
                // SEGUIR COMPRANDO SIN PALOMITAS
                case 'B':
                    if (opcion == 'B') {
                        if (mode == 0) {
                            mainMenu.doOperation();
                        }
                    }

                    //CANCELAR PEDIDO
                case 'C':
                    if (opcion == 'C') {
                        if (mode == 0) {
                            mainMenu.doOperation();
                        }
                    }
            }
        }

    }
}
