package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 * Gestiona la operación de selección inicial del idioma. Cuando esta clase
 * termina su operación deja guardada en la clase Multiplex el resultado. Como
 * todas las operaciones tienen acceso a dicha variable pueden usarla para
 * informar al sistema de traducción del idioma elegido.
 *
 * @author octavio
 */
public class IdiomSelection extends Operation {

    public IdiomSelection(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        super(dispenser, multiplex);
    }

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public void doOperation() {
        borrarOpciones();
        dispenser.setTitle("Por favor, seleccione el idioma");
        dispenser.setOption(0, "ESPAÑOL");
        dispenser.setOption(1, "ENGLISH");
        dispenser.setOption(2, "EUSKARA");
        dispenser.setOption(3, "CATALÁ");
        dispenser.setOption(4, null);
        dispenser.setOption(5, null);

        char opcion = dispenser.waitEvent(30);
        switch (opcion) {
            case 'A':
                this.multiplex.setIdiom("spanish");
                break;

            case 'B':
                this.multiplex.setIdiom("english");
                break;

            case 'C':
                this.multiplex.setIdiom("euskera");
                break;

            case 'D':
                this.multiplex.setIdiom("catalan");
                break;
            case '1':
                doOperation();
                break;
        }
    }
}
