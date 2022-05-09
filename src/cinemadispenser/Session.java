package cinemadispenser;

import java.util.Set;
/**
 *
 * @author octavio
 */
public class Session implements Serializable {

    private Pair<Integer, Integer> hour;
    private Set<Seat> occupiedSeatSet;

    public Session(Pair<Integer, Integer> hour, Set<Seat> occupiedSeatSet) {
        this.hour = hour;
        this.occupiedSeatSet = occupiedSeatSet;
    }

    public Pair<Integer, Integer> getHour() {
        return hour;
    }

    public Set<Seat> getOccupiedSeatSet() {
        return occupiedSeatSet;
    }

    public boolean isOccupiesSeat(int row, int col) {
        Seat seat = new Seat(row, col);
        
        return occupiedSeatSet.contains(seat);
    }
   

    public void unocupiesSeat(int row, int col) {
        Seat seat = new Seat(row, col);
        occupiedSeatSet.remove(seat);
    }
    
    @Override
    public String toString() {
        return hour + ", occupiedSeatSet=" + occupiedSeatSet;
    } 

      public boolean isOccupied(int row, int col){
        //indicar si es alsiento es ocupado o no
        Seat seat = new Seat(row, col);
        
        return occupiedSeatSet.contains(seat);
        
    }

    void occupeSeat(byte row, byte col) {
        Seat seat = new Seat(row, col);
        occupiedSeatSet.add(seat);
       
    }
}
