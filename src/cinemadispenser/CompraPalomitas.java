package cinemadispenser;

import java.io.IOException;
import java.util.ArrayList;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;

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

        PerformPayment performPayment = new PerformPayment(dispenser, multiplex, precioTotal);

    }

    private void comprarPalomitas(ArrayList<Palomitas> cesta) {

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
}
