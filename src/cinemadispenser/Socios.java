package cinemadispenser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author s.octavio.2018
 */
public class Socios extends Operation {

    private List<Socios> socios;

    public List<Socios> getSocios() {
        return socios;
    }

    @Override
    public String toString() {
        return "Socios{" + '}';
    }

    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    public void setSocios(List<Socios> socios) {
        this.socios = socios;
    }

    public Multiplex getMultiplex() {
        return multiplex;
    }
    
    public void doOperation(){
        ArrayList<String> comprobacion = comprobarTarjeta(socios);
       
    }

    private boolean comprobarTarjeta(ArrayList<String> socios) throws FileNotFoundException {
        ArrayList<String> partners = loadPartners();
        boolean esSocio = false;
        boolean exit = true;
        while (exit) {

            for (int j = 0; j < socios.size(); j++) {

                String numeroSocio;
                numeroSocio = socios.get(j);
                long numberCredirCard = dispenser.getCardNumber();
                String creditCardString = Long.toString(numberCredirCard);

                int caracteres = 4;
                String numeroSeparado = separar(creditCardString, caracteres);
                System.out.println(numeroSeparado);

                if (numeroSocio.equalsIgnoreCase(numeroSeparado)) {
                    exit = false;
                    esSocio = true;
                    break;
                } else {
                    esSocio = false;
                }
            }
        }

        return esSocio;

    }

    private ArrayList<String> loadPartners() throws FileNotFoundException {
        File archivo = new File("./Socios/Descuentos.txt");
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> listPartner = new ArrayList<String>();
        try {
            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] textoSeparado = linea.split("Socio:");
                listPartner.add(textoSeparado[1]);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace(System.out);
            }
        }
        return listPartner;
    }

    private String separar(String numero, int numeroCaracteres) {
        StringBuilder stringBuilderAuxiliar = new StringBuilder("");
        int i = 0;
        for (Character caracter : numero.toCharArray()) {
            if (i++ == numeroCaracteres) {
                stringBuilderAuxiliar.append(" ");
                i = 1;
            }
            stringBuilderAuxiliar.append(caracter.toString());
        }
        return stringBuilderAuxiliar.toString();
    }

}
