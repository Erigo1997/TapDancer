package Game;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;

public class Piece {

    public PIECETYPE type;
    public COLOR color;
    public int value;

    public Piece(PIECETYPE type, COLOR color) {
        this.type = type;
        this.color = color;
        value = setValue();
    }

    public void setPieceValue(int amount){
        value = amount;
    }

    private int setValue(){
        switch(type) {
            case KING:
                return 10000;
            case PAWN:
                return 100;
            case ROOK:
                return 500;
            case QUEEN:
                return 900;
            case BISHOP:
            case KNIGHT:
                return 300;
            default:
                return 0;
        }
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
