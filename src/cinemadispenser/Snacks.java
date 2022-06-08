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
class Snacks extends Operation {

    private ArrayList<Snack> cesta = new ArrayList<>();

    public Snacks(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, CommunicationException {
        super(dispenser, multiplex);
    }

    public void doOperation() throws IOException, CommunicationException {
        menuSnack();
//        comprarPalomitas(cesta);
//        cantidadPalomitas(cesta);
        Integer precioTotal = precioSnacks(cesta);

        borrarOpciones();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("¿SEGUIR COMPRANDO?"));
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("SEGUIR COMPRANDO"));
        dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PAGAR"));
        char option = dispenser.waitEvent(30);

        if (option == 'A') {
            doOperation();
        } else if (option == 'B') {
            paymentSnacks(precioTotal);
           
        }
    }

    private void comprarPalomitas(ArrayList<Snack> cesta) throws IOException, CommunicationException {

        borrarOpciones();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PALOMITAS:  "));
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PALOMITAS PEQUEÑAS"));
        dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PALOMITAS MEDIANAS"));
        dispenser.setOption(2, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PALOMITAS GRANDES"));
        dispenser.setOption(3, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CANCELAR"));
        dispenser.setDescription("PRECIOS: " + "\n"
                + "----------" + "\n"
                + "PALOMITAS PEQUEÑAS: " + Snack.Tipo.SMALL.coste + "€" + "\n"
                + "PALOMITAS MEDIANAS: " + Snack.Tipo.MEDIUM.coste + "€" + "\n"
                + "PALOMITAS GRANDES: " + Snack.Tipo.BIG.coste + "€");
        char option = dispenser.waitEvent(30);

        switch (option) {
            case 'A':
                cesta.add(new Snack(Snack.Tipo.SMALL));
                break;
            case 'B':
                cesta.add(new Snack(Snack.Tipo.MEDIUM));
                break;
            case 'C':
                cesta.add(new Snack(Snack.Tipo.BIG));
                break;
            case 'D':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
        }
        cantidad(cesta);
    }

    private void cantidad(ArrayList<Snack> cesta) {

        int last = cesta.size() - 1;

        Snack ultimaAdd = cesta.get(last);

        borrarOpciones();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("¿CUÁNTAS QUIERES?"));
        dispenser.setOption(0, "1");
        dispenser.setOption(1, "2");
        dispenser.setOption(2, "3");
        dispenser.setOption(3, "4");
        dispenser.setOption(4, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CANCELAR"));

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

    private int precioSnacks(ArrayList<Snack> cesta) {

        int precioTotal = 0;

        for (Snack snack : cesta) {
            precioTotal += snack.tipo.coste;
        }

        return precioTotal;
    }

    private List<String> printTicket(Integer precioTotal, double precioFinal, boolean esSocio) throws FileNotFoundException {
        List<String> text = new ArrayList<>();

        text.add("   PALOMITAS: "); //NOI18N
        text.add("   ==================="); //NOI18N

        for (int i = 0; i < cesta.size(); i++) {
            if (cesta.get(i).tipo.equals(Snack.Tipo.SMALL)) {
                text.add("Palomitas Pequeñas:" + " " + Snack.Tipo.SMALL.coste + "€");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.MEDIUM)) {
                text.add("Palomitas Medianas:" + " " + Snack.Tipo.MEDIUM.coste + "€");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.BIG)) {
                text.add("Palomitas Grandes:" + " " + Snack.Tipo.BIG.coste + "€");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.COCACOLA)) {
                text.add("Cocacola:" + " " + Snack.Tipo.COCACOLA.coste + "€");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.FANTA)) {
                text.add("Fanta:" + " " + Snack.Tipo.FANTA.coste + "€");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.NESTEA)) {
                text.add("Nestea:" + " " + Snack.Tipo.NESTEA.coste + "€");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.AGUA)) {
                text.add("Agua:" + " " + Snack.Tipo.AGUA.coste + "€");
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

    private String mensaje(Integer precioTotal, double precioFinal, boolean esSocio) throws FileNotFoundException {
        StringBuilder textStrinbBuilder = new StringBuilder();
        textStrinbBuilder.append("   SNACKS: " + "\n"); //NOI18N
        textStrinbBuilder.append("   ===================" + "\n"); //NOI18N

        for (int i = 0; i < cesta.size(); i++) {
            if (cesta.get(i).tipo.equals(Snack.Tipo.SMALL)) {
                textStrinbBuilder.append("- Palomitas Pequeñas:" + " " + Snack.Tipo.SMALL.coste + "€" + "\n");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.MEDIUM)) {
                textStrinbBuilder.append("- Palomitas Medianas:" + " " + Snack.Tipo.MEDIUM.coste + "€" + "\n");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.BIG)) {
                textStrinbBuilder.append("- Palomitas Grandes:" + " " + Snack.Tipo.BIG.coste + "€" + "\n");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.COCACOLA)) {
                textStrinbBuilder.append("- CocaCola:" + " " + Snack.Tipo.COCACOLA.coste + "€" + "\n");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.FANTA)) {
                textStrinbBuilder.append("- Fanta:" + " " + Snack.Tipo.FANTA.coste + "€" + "\n");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.NESTEA)) {
                textStrinbBuilder.append("- Nestea:" + " " + Snack.Tipo.NESTEA.coste + "€" + "\n");
            } else if (cesta.get(i).tipo.equals(Snack.Tipo.AGUA)) {
                textStrinbBuilder.append("- Agua:" + " " + Snack.Tipo.AGUA.coste + "€" + "\n");
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

    private void menuSnack() throws IOException, CommunicationException {
        borrarOpciones();
        dispenser.setTitle("SNACKS: ");
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PALOMITAS"));
        dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("BEBIDAS"));
        dispenser.setOption(3, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("PAGAR"));
        dispenser.setOption(4, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CANCELAR"));
        char option = dispenser.waitEvent(30);

        if (option == 'A') {
            comprarPalomitas(cesta);
        } if (option == 'B'){
            comprarBebidas(cesta);
        }if (option == 'D'){
            if(!cesta.isEmpty()){
                paymentSnacks(precioSnacks(cesta));
            } else if (cesta.isEmpty()){
                menuSnack();
            }
        } if (option == 'E'){
            MainMenu mainMenu = new MainMenu(dispenser, multiplex);
            mainMenu.doOperation();
        }
        
    }

    private void comprarBebidas(ArrayList<Snack> cesta) throws IOException, CommunicationException {
       
        borrarOpciones();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("BEBIDAS:  "));
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("COCACOLA"));
        dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("FANTA NARANJA"));
        dispenser.setOption(2, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("NESTEA"));
        dispenser.setOption(3, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("AGUA"));
        dispenser.setOption(4, java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("CANCELAR"));
        dispenser.setDescription("PRECIOS: " + "\n"
                + "----------" + "\n"
                + "TODAS LAS BEBIDAS CUESTAN 3€ " );
        char option = dispenser.waitEvent(30);

        switch (option) {
            case 'A':
                cesta.add(new Snack(Snack.Tipo.COCACOLA));
                break;
            case 'B':
                cesta.add(new Snack(Snack.Tipo.FANTA));
                break;
            case 'C':
                cesta.add(new Snack(Snack.Tipo.NESTEA));
                break;
                
            case 'D':
                cesta.add(new Snack(Snack.Tipo.AGUA));
                break;
            case 'E':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
        }
        cantidad(cesta);
    }

    private void paymentSnacks(Integer precioTotal) throws IOException, CommunicationException {
        PerformPayment performPayment = new PerformPayment(dispenser, multiplex, precioTotal);
            String mensaje = (mensaje(precioTotal, precioTotal, false));
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
            mensaje = (mensaje(precioTotal, precioFinal, esSocio));
            dispenser.setDescription(mensaje);

            printTicket(precioTotal, precioFinal, esSocio);

            dispenser.print(printTicket(precioTotal, precioFinal, esSocio));
            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + this.multiplex.getIdiom()).getString("RECOJA LA TARJETA DE CRÉDITO"));
            dispenser.expelCreditCard(30);
    }
}
