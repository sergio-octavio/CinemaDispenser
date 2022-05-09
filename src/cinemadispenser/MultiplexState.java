package cinemadispenser;

import cinemadispenser.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.String;
import java.util.Scanner;

/**
 * Agrupa el estado del multicines. Al ser esta clase serializable, se podría
 * guardar y recuperar todo el estado del multicine de una manera sencilla.
 *
 * @author octavio
 */
public class MultiplexState implements Serializable{

    private List<Theater> theaterList = new ArrayList<>();

    public List<Theater> getTheatreList() {
        return theaterList;
    }

    public int getNumberOfTheaters() {
        return theaterList.size();
    }

    private String getInfo(String info, String regex) {
//        System.out.println("info " + info);
        String ret = info.split(regex)[1].trim();
//        System.out.println(ret);
        return ret;
    }

    /**
     * @param loadMoviesAndSessions() este método se encarga de la carga de las
     * peliculas y salas en los ficheros correspondientes.
     *
     * @throws IOException
     */
    public void loadMoviesAndSessions() throws IOException {
        /**
         * @param pathnamesFilm directorio donde se encuentra los ficheros de la
         * distribucion de las salas que queremos leer.
         */
        String[] pathnamesFilm;
        File f = new File("./Movies/");
        pathnamesFilm = f.list();

        /**
         * @param pathNameTheater directorio donde se encuentra los ficheros de
         * las peliculas que queremos leer.
         */
        String[] pathNameTheater;
        File fTheatre = new File("./Theaters/");
        pathNameTheater = fTheatre.list();

        /**
         * @param pathname directorio donde se encuentra los ficheros que
         * queremos leer
         */
        // For each pathname in the pathnamesFilm array
        for (String pathname : pathnamesFilm) {
            Path classPathMovie = Paths.get("./Movies/" + pathname);
            List<String> readingMovies = Files.readAllLines(classPathMovie);

            String fileTheather = null;
            for (String pathNameTheaterAux : pathNameTheater) {
                if (pathNameTheaterAux.contains(getInfo(readingMovies.get(0), "Theatre: "))) {
                    fileTheather = pathNameTheaterAux;
                }
            }

            Theater theater = new Theater(
                    Integer.parseInt(getInfo(readingMovies.get(0), "Theatre: ")),
                    Integer.parseInt(getInfo(readingMovies.get(12), "Price: ").split(" ")[0]),
                    new HashSet<>(),
                    new Film(
                            getInfo(readingMovies.get(2), "Title: "),
                            getInfo(readingMovies.get(10), "Poster: "),
                            Integer.parseInt(getInfo(readingMovies.get(6), "Duration: ")),
                            readingMovies.get(4)
                    ));

            ArrayList<Session> listSession = new ArrayList<>();
            for (String listSessionString : getInfo(readingMovies.get(8), "Sessions: ").split(" ")) {

                String[] sessionCut = listSessionString.split(":");
                Pair<Integer, Integer> session = new Pair<>(
                        Integer.parseInt(sessionCut[0]),
                        Integer.parseInt(sessionCut[1])
                );
                listSession.add(new Session(session, new HashSet<Seat>()));
            }

            theater.addSessions(listSession);
            theater.loadSeats(fileTheather);
            theaterList.add(theater);
        }
    }

    /**
     * CARGA DE LOS SOCIOS DEL CINE A TRAVES DEL FICHERO DE TEXTO LLAMADO
     * Socios/Descuentos.txt
     *
     */
    /**
     * @param pathNameSocios directorio donde se encuentra los ficheros de los
     * socios del cine.
     */
    public void loadpartners() throws IOException {
        
        FileReader fr = new FileReader("./Socios/Descuentos.txt");
        BufferedReader bf = new BufferedReader(fr);
     
       
        InputStream ins = new FileInputStream("./Socios/Descuentos.txt");
        Scanner obj = new Scanner(ins);
        String socio1 = obj.nextLine();
        String socio2 = obj.nextLine();
        String socio3 = obj.nextLine();
        String socio4 = obj.nextLine();
        
//        
//        System.out.println("socio1   " + socio1);
//        System.out.println("socio2   " + socio2);
//        System.out.println("socio3   " + socio3);
//        System.out.println("socio4   " + socio4);
       
    }
}
