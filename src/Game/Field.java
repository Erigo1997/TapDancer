package Game;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;

import java.awt.*;

public class Field {

    private int i, j;
    private Piece piece;
    public COLOR fieldColor;


    public Piece getPiece() {
        return piece;

    }



    public void setPiece(Piece piece, int value) {
        this.piece = piece;
        piece.value = value;
    }

    public Field(COLOR color, int i, int j) {
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
