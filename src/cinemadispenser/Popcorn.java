package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author octavio
 */
public class Popcorn extends Operation {

    public Popcorn(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        super(dispenser, multiplex);
       
    }

    public void doOperation() throws IOException, CommunicationException {
        int tipoPopcorn = 0;
        
        tiposPalomitas(tipoPopcorn);
        cantidadPalomitas(tipoPopcorn);

    }

    private Integer tiposPalomitas(int popcorn) throws IOException, CommunicationException {
        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);
        MainMenu mainMenu = new MainMenu(dispenser, idiomSelection.getMultiplex());

        int mode = 0;
        borrarOpciones();
        dispenser.setTitle("¿COMPRAR PALOMITAS?");
        dispenser.setOption(0, "PALOMITAS PEQUEÑAS");
        dispenser.setOption(1, "PALOMITAS MEDIANAS");
        dispenser.setOption(2, "PALOMITAS GRANDES");
        dispenser.setOption(3, "CANCELAR");
        dispenser.setDescription("PRECIOS: " + "\n"
                + "----------" + "\n"
                + "PALOMITAS PEQUEÑAS: 3€" + "\n"
                + "PALOMITAS MEDIANAS: 6€" + "\n"
                + "PALOMITAS GRANDES: 8€");

        popcorn = 0;
        double precioPalomitas = 0;

        char opcion = dispenser.waitEvent(30);
        switch (opcion) {
            // opcion de CARTELERA    
            case 'A':
                precioPalomitas = 3.0;
                dispenser.setDescription("AUN NO TENEMOS MAQUINA PARA AHCER PALOMITAS, PERO EN UN FUTURO TENDREMOS PALOMITAS");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                dispenser.waitEvent(20);
                popcorn = 1;
                break;

            case 'B':
                precioPalomitas = 6.0;
                dispenser.setDescription("EL CAMION NO HA LLEGADO AUN Y NUESTRO COMPAÑERO A IDO AL CHINO A POR ELLAS");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 2;
                break;

            case 'C':
                precioPalomitas = 8.0;
                dispenser.setDescription("en el chino tampoco tienen palomitas, te va a tocar ver la pelicula sin palomitas");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 3;
                break;
            case 'D':
                mainMenu.doOperation();
                break;
        }
        return tiposPalomitas(popcorn);

    }

  
    private Integer cantidadPalomitas(int tipoPopcorn) throws IOException, CommunicationException {
        dispenser.setTitle("¿CUÁNTAS BOLSAS QUIERE?");
        dispenser.setOption(0, "1");
        dispenser.setOption(1, "2");
        dispenser.setOption(2, "3");
        dispenser.setOption(3, "¿QUIERE OTRO TAMAÑO?  ");
        dispenser.setOption(4, "ATRÁS");
        char option = dispenser.waitEvent(30);
       
        int bolsasPalomitas = 0;
        switch (option) {
           
            case 'A':
                bolsasPalomitas = 1;
                break;

            case 'B':
               bolsasPalomitas = 2;
                break;

            case 'C':
               bolsasPalomitas = 3;
                break;
            case 'D':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
                break;
        }
        return null;
    }
}
