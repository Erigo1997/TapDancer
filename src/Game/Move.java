package Game;

public class Move {
    int fromX, fromY;
    int toX, toY;
    Piece subject, target;
    boolean special;

    public Move(int fromX, int fromY, int toX, int toY, Piece subject, Piece target, boolean special) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.subject = subject;
        this.target = target;
        this.special = special;
    }

    public String toString() {
        return subject + " moved from " + fromY + "," + fromX + " to " + toY + "," + toX;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public int getFromX() { return fromX;}

    public int getFromY() { return fromY;}
}
