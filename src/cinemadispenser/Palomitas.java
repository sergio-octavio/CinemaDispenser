package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import javax.naming.CommunicationException;

/**
 *
 * @author octavio
 */
public class Palomitas {

    public Tipo tipo;

    public Palomitas(Tipo tipo) {
        this.tipo = tipo;
    }

    public enum Tipo {
        SMALL(3),
        MEDIUM(6),
        BIG(8);

        public int coste;

        Tipo(int coste) {
            this.coste = coste;
        }
    }
}
