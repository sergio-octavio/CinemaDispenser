package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author octavio
 */
public class ComprarBebidas extends Operation {

    public ComprarBebidas(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, CommunicationException {
        super(dispenser, multiplex);
    }

    private ArrayList<Bebidas> cestaBebidas = new ArrayList<>();

    public void doOperation() throws IOException, CommunicationException {

        comprarBebidas(cestaBebidas);
        cantidadBebidas(cestaBebidas);
        Integer precioTotal = precioBebidas(cestaBebidas);

        borrarOpciones();
        dispenser.setTitle("¿SEGUIR COMPRANDO?");
        dispenser.setOption(0, "SÍ");
        dispenser.setOption(1, "NO");
        char option = dispenser.waitEvent(30);

        if (option == 'A') {
            doOperation();
        } else if (option == 'B') {
            PerformPayment performPayment = new PerformPayment(dispenser, multiplex, precioTotal);
            String mensaje = (mensajePalomitas(precioTotal, precioTotal, false));
            double precioFinal;
            boolean esSocio;
            if (performPayment.comprobarEsSocio(mensaje)) {
                esSocio = true;
                precioFinal = (precioTotal - (precioTotal * 0.3));
                //redondeamos el resultado y restringomos a dos decimales
                precioFinal = Math.round(precioFinal * 100.0) / 100.0;
            } else {
                precioFinal = precioTotal;
                //redondeamos el resultado y restringomos a dos decimales
                precioFinal = Math.round(precioFinal * 100.0) / 100.0;
                esSocio = false;
            }

            borrarOpciones();
            mensaje = (mensajePalomitas(precioTotal, precioFinal, esSocio));
            dispenser.setDescription(mensaje);

            printTicket(precioTotal, precioFinal, esSocio);

            dispenser.print(printTicket(precioTotal, precioFinal, esSocio));
            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("RECOJA LA TARJETA DE CRÉDITO"));
            dispenser.expelCreditCard(30);
        }
    }

    private void comprarBebidas(ArrayList<Bebidas> cesta) throws IOException, CommunicationException {

        borrarOpciones();
        dispenser.setTitle("BEBIDAS:  ");
        dispenser.setOption(0, "AGUA");
        dispenser.setOption(1, "COCACOLA");
        dispenser.setOption(2, "FANTA NARANJA");
        dispenser.setOption(3, "CANCELAR");
        dispenser.setDescription("PRECIOS: " + "\n"
                + "----------" + "\n"
                + "AGUA: " + Bebidas.Tipo.Agua.coste + "€" + "\n"
                + "COCACOLA: " + Bebidas.Tipo.CocaCola.coste + "€" + "\n"
                + "FANTA NARANJA: " + Bebidas.Tipo.FantaNaranja.coste + "€");
        char option = dispenser.waitEvent(30);

        switch (option) {
            case 'A':
                cesta.add(new Bebidas(Bebidas.Tipo.Agua));
                break;
            case 'B':
                cesta.add(new Bebidas(Bebidas.Tipo.CocaCola));
                break;
            case 'C':
                cesta.add(new Bebidas(Bebidas.Tipo.FantaNaranja));
                break;
            case 'D':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
        }
    }

    private void cantidadBebidas(ArrayList<Bebidas> cesta) {

        int last = cesta.size() - 1;

        Bebidas ultimaAdd = cesta.get(last);

        borrarOpciones();
        dispenser.setTitle("¿CUÁNTAS QUIERES?");
        dispenser.setOption(0, "1");
        dispenser.setOption(1, "2");
        dispenser.setOption(2, "3");
        dispenser.setOption(3, "4");
        dispenser.setOption(4, "CANCELAR");

        char option = dispenser.waitEvent(30);
        switch (option) {
            case 'A':
                break;
            case 'B':
                cesta.add(ultimaAdd);
                break;
            case 'C':
                cesta.add(ultimaAdd);
                cesta.add(ultimaAdd);
                break;
            case 'D':
                cesta.add(ultimaAdd);
                cesta.add(ultimaAdd);
                cesta.add(ultimaAdd);
                break;
        }
    }

    private int precioBebidas(ArrayList<Bebidas> cesta) {

        int precioTotal = 0;

        for (Bebidas bebidas : cesta) {
            precioTotal += bebidas.tipo.coste;
        }

        return precioTotal;
    }

    private List<String> printTicket(Integer precioTotal, double precioFinal, boolean esSocio) throws FileNotFoundException {
        List<String> text = new ArrayList<>();

        text.add("   BEBIDAS: "); //NOI18N
        text.add("   ==================="); //NOI18N

        for (int i = 0; i < cestaBebidas.size(); i++) {
            if (cestaBebidas.get(i).tipo.equals(Bebidas.Tipo.Agua)) {
                text.add("Agua:" + " " + Bebidas.Tipo.Agua.coste + "€");
            } else if (cestaBebidas.get(i).tipo.equals(Bebidas.Tipo.CocaCola)) {
                text.add("CocaCola:" + " " + Bebidas.Tipo.CocaCola.coste + "€");
            } else if (cestaBebidas.get(i).tipo.equals(Bebidas.Tipo.FantaNaranja)) {
                text.add("FantaNaranja:" + " " + Bebidas.Tipo.FantaNaranja.coste + "€");
            }
        }
        text.add("   ===================" + "\n"); //NOI18N
        text.add("Precio TOTAL: " + precioTotal + "€");
        Socios socios = new Socios();
        if (esSocio) {
            text.add("-----------------");
            text.add("");
            text.add("Precio de SOCIO " + precioFinal + "€"); //NOI18N
        }
        return text;
    }

    private String mensajePalomitas(Integer precioTotal, double precioFinal, boolean esSocio) throws FileNotFoundException {
        StringBuilder textStrinbBuilder = new StringBuilder();
        textStrinbBuilder.append("   BEBIDAS: " + "\n"); //NOI18N
        textStrinbBuilder.append("   ===================" + "\n"); //NOI18N

        for (int i = 0; i < cestaBebidas.size(); i++) {
            if (cestaBebidas.get(i).tipo.equals(Bebidas.Tipo.Agua)) {
                textStrinbBuilder.append("- Agua:" + " " + Bebidas.Tipo.Agua.coste + "€" + "\n");
            } else if (cestaBebidas.get(i).tipo.equals(Bebidas.Tipo.CocaCola)) {
                textStrinbBuilder.append("- Coca~Cola:" + " " + Bebidas.Tipo.CocaCola.coste + "€" + "\n");
            } else if (cestaBebidas.get(i).tipo.equals(Bebidas.Tipo.FantaNaranja)) {
                textStrinbBuilder.append("- FantaNaranja:" + " " + Bebidas.Tipo.FantaNaranja.coste + "€" + "\n");
            }
        }
        textStrinbBuilder.append("   ===================" + "\n"); //NOI18N
        textStrinbBuilder.append("Precio TOTAL: " + precioTotal + "€" + "\n");

        Socios socios = new Socios();
        if (esSocio) {
            textStrinbBuilder.append("   Precio de SOCIO " + precioFinal + "€" + "\n"); //NOI18N
        }
        String singleString = textStrinbBuilder.toString();
        return singleString;

    }
}
