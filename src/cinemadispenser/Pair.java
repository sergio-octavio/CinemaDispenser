package cinemadispenser;

/**
 *
 * @author octavio
 */

//Clase encargada de seperar las horas de los minutos
public class Pair<T, M> {

    private T hour;
    private M minute;

    public Pair(T hour, M minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public T getHour() {
        return hour;
    }

    public M getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }
    
}
