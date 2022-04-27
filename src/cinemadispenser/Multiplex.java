package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 * Clase principal del sistema de venta de entradas. Almacena el idioma, crea los 
 * objetos URJCBankServer y CinemaTicketDispenser y desarrolla el bucle principal de la
 * aplicación.
 * 
 * @author octavio
 * 
 */
class Multiplex {

    private String Idiom; 
    
    public String getIdiom() {
        return Idiom;
    }
    public void setIdiom(String Idiom) {
        this.Idiom = Idiom;
    }

    void start() throws FileNotFoundException, IOException {
        CinemaTicketDispenser dispenser = new CinemaTicketDispenser();
        Multiplex multiplex = new Multiplex();
        
        IdiomSelection idiomSelection = new IdiomSelection(dispenser, multiplex);
        idiomSelection.doOperation();  
        
        MainMenu mainMenu = new MainMenu(dispenser, idiomSelection.getMultiplex());
        mainMenu.doOperation();  //presenta la pantalla de bienvenida
    }
}