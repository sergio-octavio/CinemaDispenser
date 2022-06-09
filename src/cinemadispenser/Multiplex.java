package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;

/**
 * Clase principal del sistema de venta de entradas. Almacena el idioma, crea
 * los objetos URJCBankServer y CinemaTicketDispenser y desarrolla el bucle
 * principal de la aplicación.
 *
 * @author octavio
 *
 */
public class Multiplex {

    private String Idiom;

    public String getIdiom() {
        return Idiom;
    }

    public void setIdiom(String Idiom) {
        this.Idiom = Idiom;
    }

//Método encargado de iniciar el programa.
    
    public void start() throws FileNotFoundException, IOException, CommunicationException {
        CinemaTicketDispenser dispenser = new CinemaTicketDispenser();
        Multiplex multiplex = new Multiplex();
        
        //METODO PARA SABER SI ES UN NUEVO DIA O NO
        MovieTicketSale movieTicketSale = new MovieTicketSale(dispenser, multiplex);
        movieTicketSale.newDay(multiplex);
        //METODO PARA SELECCIONAR EL IDIOMA
        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);
        idiomSelection.doOperation();
        //Menu principal del programa que ve el usuario
        MainMenu mainMenu = new MainMenu(dispenser, idiomSelection.getMultiplex());
        mainMenu.doOperation();  //presenta la pantalla de bienvenida
    }

}
