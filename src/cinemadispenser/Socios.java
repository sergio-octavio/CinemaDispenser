/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemadispenser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author s.octavio.2018
 */
class Socios {

    private List<Session> socios;

    public Socios getSocio() {
        return (Socios) socios;

    }

    @Override
    public String toString() {
        return "Socios{" + '}';
    }

    public void loadPartners() {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("./Socios/Descuentos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;

            String separador = null;
            ArrayList<Socios> listPartners = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                linea = String.valueOf(listPartners.size());
                Integer partner = null;

                for (int i = 0; i < listPartners.size(); i++) {
                    separador.split("Socio:");
                    listPartners.add((Socios) socios);
                }
            }

            System.out.println(socios);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
