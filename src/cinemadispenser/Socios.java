package cinemadispenser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author s.octavio.2018
 */
public class Socios {

    public Socios() throws FileNotFoundException {
        partners = loadPartners();
    }

    private ArrayList<String> partners;

    public boolean comprobarTarjeta(long numeroTarjeta) throws FileNotFoundException {
        String numeroTarjetaEnCaracteres = convertir(numeroTarjeta);
        for (String socio : partners) {
            if (socio.equals(numeroTarjetaEnCaracteres)) {
                return true;
            }
        }
        return false;

    }
/**
 * 
 * @return
 * @throws FileNotFoundException 
 * Carga los socios del fichero propocionar en la ruta "./Socios/Descuentos.txt" y los guarda en una lista.
 */
    private ArrayList<String> loadPartners() throws FileNotFoundException {
        File archivo = new File("./Socios/Descuentos.txt");
        FileReader fileReader = new FileReader(archivo);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> listPartner = new ArrayList<String>();
        try {
            // Lectura del fichero
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {

                String[] textoSeparado = linea.split("Socio:");
                listPartner.add(textoSeparado[1]);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace(System.out);
            }
        }
        return listPartner;
    }

/**
 * 
 * @param numero
 * @param numeroCaracteres
 * @return número de socio separado por pares de 4
 */
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

/**
 * 
 * @param numeroTarjeta
 * @return recogemos el número de la tarjeta
 */
    private String convertir(long numeroTarjeta) {
       
       String aux = Long.toString(numeroTarjeta);
     
       String primero = (String) aux.subSequence(0, 4);
       String segundo = (String) aux.subSequence(4, 8);
       String tercero = (String) aux.subSequence(8, 12);
       String cuarto = (String) aux.subSequence(12, 16);
       
               return primero + " " + segundo + " " +tercero + " " +cuarto;
    }

}
