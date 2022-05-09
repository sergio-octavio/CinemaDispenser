package cinemadispenser;

import java.io.IOException;
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

    void doOperation() throws IOException, CommunicationException {
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

        popcorn = 0;
        char opcion = dispenser.waitEvent(30);
        switch (opcion) {
            // opcion de CARTELERA    
            case 'A':
                dispenser.setDescription("AUN NO TENEMOS MAQUINA PARA AHCER PALOMITAS, PERO EN UN FUTURO TENDREMOS PALOMITAS");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 1;
                break;

            case 'B':
                dispenser.setDescription("EL CAMION NO HA LLEGADO AUN Y NUESTRO COMPAÑERO A IDO AL CHINO A POR ELLAS");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 2;
                break;

            case 'C':
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

    private Integer cantidadPalomitas(int cantidadPalomitas) throws IOException, CommunicationException {
        dispenser.setTitle("¿CUÁNTAS BOLSAS QUIERE?");
        dispenser.setOption(0, "1 PEQUEÑA");
        dispenser.setOption(1, "2 PEQUEÑA");
        dispenser.setOption(2, "3 PEQUEÑA");
        dispenser.setOption(3, "¿QUIERE OTRO TAMAÑO?  ");
        dispenser.setOption(4, "ATRÁS");
        char opcion = dispenser.waitEvent(30);
        if (opcion == 'A') {
            cantidadPalomitas = 1;
        }
        int popcorn = 0;
        switch (opcion) {
            // opcion de CARTELERA  
            case 'A':
                dispenser.setDescription("AUN NO TENEMOS MAQUINA PARA AHCER PALOMITAS, PERO EN UN FUTURO TENDREMOS PALOMITAS");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 1;
                break;

            case 'B':
                dispenser.setDescription("EL CAMION NO HA LLEGADO AUN Y NUESTRO COMPAÑERO A IDO AL CHINO A POR ELLAS");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 2;
                break;

            case 'C':
                dispenser.setDescription("en el chino tampoco tienen palomitas, te va a tocar ver la pelicula sin palomitas");
                dispenser.setImage("C:\\Users\\octavio\\Documents\\SERGIO\\UNIVERSIDAD\\POO\\PRACTICA\\CinemaDispenser\\src\\cinemadispenser\\ficheros\\popcorn.jpg");
                popcorn = 3;
                break;
            case 'D':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
                break;
        }
        return null;
    }
}
