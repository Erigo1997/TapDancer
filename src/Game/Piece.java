package Game;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;

public class Piece {

    public PIECETYPE type;
    public COLOR color;

    public Piece(PIECETYPE type, COLOR color) {
        this.type = type;
        this.color = color;
    }

    // Print out the piece in question.
    public String toString() {
        switch(type) {
            case KING:
                if (color == COLOR.WHITE)
                    return "♔";
                else
                    return "♚";
            case PAWN:
                if (color == COLOR.WHITE)
                    return "♙";
                else
                    return "♟";
            case ROOK:
                if (color == COLOR.WHITE)
                    return "♖";
                else
                    return "♜";
            case QUEEN:
                if (color == COLOR.WHITE)
                    return "♕";
                else
                    return "♛";
            case BISHOP:
                if (color == COLOR.WHITE)
                    return "♗";
                else
                    return "♝";
            case KNIGHT:
                if (color == COLOR.WHITE)
                    return "♘";
                else
                    return "♞";
        }
        // If we reach this, then we're f-ed.
        return null;
    }

}
