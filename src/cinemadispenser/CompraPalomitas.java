package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import java.io.FileNotFoundException;

/**
 *
 * @author octavio
 */
class CompraPalomitas extends Operation {

    private ArrayList<Palomitas> cestaPalomitas = new ArrayList<>();

    public CompraPalomitas(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, CommunicationException {
        super(dispenser, multiplex);
    }

    public void doOperation() throws IOException, CommunicationException {

        comprarPalomitas(cestaPalomitas);
        cantidadPalomitas(cestaPalomitas);
        Integer precioTotal = precioPalomitas(cestaPalomitas);

        borrarOpciones();
        dispenser.setTitle("¿SEGUIR COMPRANDO PALOMITAS?");
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

    private void comprarPalomitas(ArrayList<Palomitas> cesta) throws IOException, CommunicationException {

        borrarOpciones();
        dispenser.setTitle("PALOMITAS:  ");
        dispenser.setOption(0, "PALOMITAS PEQUEÑAS");
        dispenser.setOption(1, "PALOMITAS MEDIANAS");
        dispenser.setOption(2, "PALOMITAS GRANDES");
        dispenser.setOption(3, "CANCELAR");
        dispenser.setDescription("PRECIOS: " + "\n"
                + "----------" + "\n"
                + "PALOMITAS PEQUEÑAS: " + Palomitas.Tipo.SMALL.coste + "€" + "\n"
                + "PALOMITAS MEDIANAS: " + Palomitas.Tipo.MEDIUM.coste + "€" + "\n"
                + "PALOMITAS GRANDES: " + Palomitas.Tipo.BIG.coste + "€");
        char option = dispenser.waitEvent(30);

        switch (option) {
            case 'A':
                cesta.add(new Palomitas(Palomitas.Tipo.SMALL));
                break;
            case 'B':
                cesta.add(new Palomitas(Palomitas.Tipo.MEDIUM));
                break;
            case 'C':
                cesta.add(new Palomitas(Palomitas.Tipo.BIG));
                break;
            case 'D':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
        }
    }

    private void cantidadPalomitas(ArrayList<Palomitas> cesta) {

        int last = cesta.size() - 1;

        Palomitas ultimaAdd = cesta.get(last);

        borrarOpciones();
        dispenser.setTitle("¿CUÁNTAS PALOMITAS QUIERES?");
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

    private int precioPalomitas(ArrayList<Palomitas> cesta) {

        int precioTotal = 0;

        for (Palomitas palomitas : cesta) {
            precioTotal += palomitas.tipo.coste;
        }

        return precioTotal;
    }

    private List<String> printTicket(Integer precioTotal, double precioFinal, boolean esSocio) throws FileNotFoundException {
        List<String> text = new ArrayList<>();

        text.add("   PALOMITAS: "); //NOI18N
        text.add("   ==================="); //NOI18N

        for (int i = 0; i < cestaPalomitas.size(); i++) {
            if (cestaPalomitas.get(i).tipo.equals(Palomitas.Tipo.SMALL)) {
                text.add("Pequeñas:" + " " + Palomitas.Tipo.SMALL.coste + "€");
            } else if (cestaPalomitas.get(i).tipo.equals(Palomitas.Tipo.MEDIUM)) {
                text.add("Medianas:" + " " + Palomitas.Tipo.SMALL.coste + "€");
            } else if (cestaPalomitas.get(i).tipo.equals(Palomitas.Tipo.BIG)) {
                text.add("Grandes:" + " " + Palomitas.Tipo.BIG.coste + "€");
            }
        }
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
        textStrinbBuilder.append("   PALOMITAS: " + "\n"); //NOI18N
        textStrinbBuilder.append("   ===================" + "\n"); //NOI18N

        for (int i = 0; i < cestaPalomitas.size(); i++) {
            if (cestaPalomitas.get(i).tipo.equals(Palomitas.Tipo.SMALL)) {
                textStrinbBuilder.append("- Pequeñas:" + " " + Palomitas.Tipo.SMALL.coste + "€" + "\n");
            } else if (cestaPalomitas.get(i).tipo.equals(Palomitas.Tipo.MEDIUM)) {
                textStrinbBuilder.append("- Medianas:" + " " + Palomitas.Tipo.SMALL.coste + "€" + "\n");
            } else if (cestaPalomitas.get(i).tipo.equals(Palomitas.Tipo.BIG)) {
                textStrinbBuilder.append("- Grandes:" + " " + Palomitas.Tipo.BIG.coste + "€" + "\n");
            }
        }

        textStrinbBuilder.append("Precio TOTAL: " + precioTotal + "€" + "\n");

        Socios socios = new Socios();
        if (esSocio) {
            textStrinbBuilder.append("   Precio de SOCIO " + precioFinal + "€" + "\n"); //NOI18N
        }

        String singleString = textStrinbBuilder.toString();
        return singleString;

    }
}
