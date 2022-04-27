package cinemadispenser;

/**
 *
 * @author octavio
 */
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
