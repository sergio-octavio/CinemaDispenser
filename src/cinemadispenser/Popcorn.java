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
public class Popcorn extends Operation {

    public Popcorn(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, CommunicationException {
        super(dispenser, multiplex);
    }

    private String sizePopcorn;
    private Integer countPopcorn;
    private Double pricePopcorn;
    private ArrayList<String> popcorn;

    public void doOperationPalomitas() throws IOException, CommunicationException {
        sizePopcorn = tipoDePalomitas();
        countPopcorn = countPopcorn(sizePopcorn);
        
        borrarOpciones();
        dispenser.setTitle("¿QUIERES SEGUIR COMPRANDO PALOMITAS?");
        dispenser.setOption(0, "SÍ");
        dispenser.setOption(1, "NO");

        char option = dispenser.waitEvent(30);
        if (option == 'A') {
            tipoDePalomitas();
        } else if ((option == 'B') && (!sizePopcorn.isEmpty())) {
            //CALCULAMOS LA CANTIDAD TOTAL DE PALOMITAS
            double precioFinal = calculoPalomitas(sizePopcorn, countPopcorn);
            //IMPRIMIR TICKET   
            PerformPayment performPayment = new PerformPayment(dispenser, multiplex, (int) precioFinal);
            
            double comprobacionPrecioSocio;
            boolean esSocio;
            if (performPayment.comprobarEsSocio(sizePopcorn)){
                esSocio = true;
                comprobacionPrecioSocio = (precioFinal - (precioFinal * 0.3));
            } else {
                comprobacionPrecioSocio = precioFinal;
                esSocio = false;
            }
            borrarOpciones();
            printTicket(sizePopcorn, countPopcorn, pricePopcorn, comprobacionPrecioSocio, esSocio);

        } else { //por si falla 
            dispenser.setTitle("SE HA PRODUCIDO UN PROBLEMA");
            dispenser.setDescription("INTENTELO MAS TARDE" +"\n"+
                    "DISCULPA LAS MOLESTIAS");
            dispenser.waitEvent(10);
            //NOS DIRIGIMOS AL MENU PRINCIPAL
            MainMenu mainMenu = new MainMenu(dispenser, multiplex);
            mainMenu.doOperation();
        }

    }

    private String tipoDePalomitas() throws IOException, CommunicationException {
        borrarOpciones();
        dispenser.setTitle("¿COMPRAR PALOMITAS?");
        dispenser.setOption(0, "PALOMITAS PEQUEÑAS");
        dispenser.setOption(1, "PALOMITAS MEDIANAS");
        dispenser.setOption(2, "PALOMITAS GRANDES");
        dispenser.setOption(3, "CANCELAR");
        dispenser.setDescription("PRECIOS: " + "\n"
                + "----------" + "\n"
                + "PALOMITAS PEQUEÑAS: 3€" + "\n"
                + "PALOMITAS MEDIANAS: 6€" + "\n"
                + "PALOMITAS GRANDES: 8€");

        char option = dispenser.waitEvent(30);

        switch (option) {
            case 'A':
                sizePopcorn = "SMALL";
                break;
            case 'B':
                sizePopcorn = "MEDIUM";
                break;
            case 'C':
                sizePopcorn = "LARGE";
                break;
            case 'D':
                MainMenu mainMenu = new MainMenu(dispenser, multiplex);
                mainMenu.doOperation();
                break;
            case '1':
                doOperationPalomitas();
                break;
        }
        return sizePopcorn;
    }

    private Integer countPopcorn(String sizePopcorn1) throws IOException, CommunicationException {

        borrarOpciones();
        dispenser.setTitle("¿CUÁNTAS PALOMITAS QUIERES?");
        dispenser.setOption(0, "1");
        dispenser.setOption(1, "2");
        dispenser.setOption(2, "3");
        dispenser.setOption(3, "4");
        dispenser.setOption(4, "CANCELAR");

        char option = dispenser.waitEvent(30);
        ArrayList<String> tipoPalomitas = new ArrayList<>();
        switch (option) {
            case 'A':
                countPopcorn = 1;

                break;
            case 'B':
                countPopcorn = 2;
                break;
            case 'C':
                countPopcorn = 3;
                break;
            case 'D':
                countPopcorn = 4;
                break;
            case 'E':
                tipoDePalomitas();
            case '1':
                countPopcorn(sizePopcorn1);
                break;
        }
        return countPopcorn;
    }

    private double calculoPalomitas(String sizePopcorn, Integer countPopcorn) {

        double precioFinal = 0;
        for (String palomitas : popcorn){
            if (sizePopcorn.equals("SMALL")) {
                pricePopcorn = 3.0;
                precioFinal = countPopcorn * pricePopcorn;
            } else if (sizePopcorn.equals("MEDIUM")) {
                pricePopcorn = 6.0;
                precioFinal = countPopcorn * pricePopcorn;
            } else if (sizePopcorn.equals("LARGE")) {
                pricePopcorn = 8.0;
                precioFinal = countPopcorn * pricePopcorn;
            }
        }
            
        return precioFinal;
    }

    private void printTicket(String sizePopcorn1, Integer countPopcorn1, Double pricePopcorn1, double comprobacionPrecioSocio, boolean esSocio) {
    
        List<String> text = new ArrayList<>();

        text.add("   PALOMITAS" ); //NOI18N
        text.add("   ==================="); //NOI18N
        text.add(countPopcorn"   Sala " + theater.getNumber()); //NOI18N
        text.add("   Hora " + session.getHour()); //NOI18N
        int countSeat = 0;
        int countRow = 0;
        for (int i = 0; i < seat.size(); i++) {
            countSeat = seat.get(i).getCol();
            countRow = seat.get(i).getRow();
            text.add("   Fila " + countRow + " - Butaca " + countSeat + " - Precio: " + theater.getPrice() + "€"); //NOI18N
        }
        int totalPrice = computePrice(theater, seat);
        text.add("   Precio " + totalPrice + "€"); //NOI18N
        Socios socios = new Socios();

        if (esSocio) {
            text.add("   Precio de SOCIO " + precioFinal + "€"); //NOI18N
        }
        return text;
    }}

}
