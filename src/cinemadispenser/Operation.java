package cinemadispenser;

import sienens.CinemaTicketDispenser;

/**
 * Clase de la que heredan todas las operaciones que se puedan realizar con el
 * dispensador. Gestiona el acceso a URJCBankServer y CinemaTicketDispenser. 
 * Así, todas las clases que deriven de ella podrán usarlos. Además define dos
 * operaciones abstractas que deberán implementar todos los herederos: doOperation y getTitle.
 * 
 * @author octavio
 */
public class Operation {

    CinemaTicketDispenser dispenser;
    Multiplex multiplex;

    public Operation(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        this.dispenser = dispenser;
        this.multiplex = multiplex;
    }

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }

    public void setDispenser(CinemaTicketDispenser dispenser) {
        this.dispenser = dispenser;
    }

    public void setMultiplex(Multiplex multiplex) {
        this.multiplex = multiplex;
    }
/**
 * @param borrarOpciones()  metodo para borrar la pantalla
 */
    protected void borrarOpciones() {
        dispenser.setTitle(" ");
        dispenser.setOption(0, null);
        dispenser.setOption(1, null);
        dispenser.setOption(2, null);
        dispenser.setOption(3, null);
        dispenser.setOption(4, null);
        dispenser.setOption(5, null);
        dispenser.setDescription("");
        dispenser.setImage(null);
        dispenser.setMenuMode();
    }
}
