package Game;

import Enumerators.COLOR;

public class Field {

    private Piece piece;
    public COLOR fieldColor;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Field(COLOR color) {
        piece = null;
        fieldColor = color;
    }

    public String toString() {
        if (piece == null) {
            if (fieldColor == COLOR.WHITE)
                return "☐";
            else
                return "☒";
        } else {
            return piece.toString();
        }
    }

}
