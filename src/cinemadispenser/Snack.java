package cinemadispenser;

/**
 *
 * @author octavio
 */
public class Snack {

    public Tipo tipo;

    public Snack(Tipo tipo) {
        this.tipo = tipo;
    }
/**
 * METODO DONDE SE ESPECIFICA EL PRECIO DE CADA TIPO DE PALOMITAS
 * SI POR ALGUN CASUAL ESTOS PRECIOS CAMBIAN, SOLO TENDRIAMOS QUE 
 * MODIFICAR EL VALOR DE LOS ENUMERADOR.
 */
    public enum Tipo {
        SMALL(3),
        MEDIUM(6),
        BIG(8), 
        COCACOLA(3), 
        NESTEA(3),
        FANTA(3),
        AGUA(3);

        public int coste;

        Tipo(int coste) {
            this.coste = coste;
        }
    }
}
