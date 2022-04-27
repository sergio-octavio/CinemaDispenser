package cinemadispenser;

/**
 *
 * @author octavio
 */
class Seat implements Cloneable {

    private int row;
    private int col;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Seat{" + "row=" + row + ", col=" + col + '}';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 20 * hash + this.row;
        hash = 83 * hash + this.col;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        Seat object = (Seat) obj;
        if ((object.row == this.row) && (object.col == this.col)) {
            return true;
        }
        return false;
    }
}
