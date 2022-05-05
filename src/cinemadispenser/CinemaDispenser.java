package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.naming.CommunicationException;

/**
 *
 * @author octavio
 */
public class CinemaDispenser {

    /**
     * Este método se encarga de iniciar la ejecución del programa
     * Éste es el método principal del proyecto que contiene el main
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, CommunicationException {
        Multiplex multiplex = new Multiplex();
        multiplex.start();
        
    }
    
}
