package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author octavio
 */
public class Theater implements Serializable {

    private int number;
    private int price;
    private Set<Seat> seatSet;
    private Film film;
    private List<Session> session;
    private int maxCols;
    private int maxRows;

    public Theater(int number, int price, Set<Seat> seatSet, Film film) {
        this.number = number;
        this.price = price;
        this.seatSet = seatSet;
        this.film = film;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public Set<Seat> getSeatSet() {
        return seatSet;
    }

    public Film getFilm() {
        return film;
    }

    public void addSessions(List<Session> session) {
        this.session = session;
    }

    public List<Session> getSession() {
        return session;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public int getMaxCols() {
        return maxCols;
    }
/**
 * Carga de los asientos para los respectivos teatros
 * @param fileTheatre
 * @throws FileNotFoundException
 * @throws IOException 
 */
    public void loadSeats(String fileTheatre) throws FileNotFoundException, IOException {

        FileReader theaterFile = new FileReader("./Theaters/" + fileTheatre);
        int row = 0;
        int col = 0;
        int caract = theaterFile.read();
        while (caract != -1) {
            if ((char) caract == '*') {
                Seat s = new Seat(row, col);
                seatSet.add(s);
            } 
            if ((char) caract == '\n') {
                if (maxCols < col) {
                    maxCols = col;
                }
                col = 0;
                row++;
            } else {
                col++;
            }
            caract = theaterFile.read();
        }
            maxRows = row;
    }

    @Override
    public String toString() {
        return "Theater{" + "number=" + number + ", price=" + price + ", seatSet=" + seatSet + ", film=" + film + '}';
    }

}
