package Game;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;

public class Piece {

    public PIECETYPE type;
    public COLOR color;
    public int moveCounter;

    public Piece(PIECETYPE type, COLOR color) {
        this.type = type;
        this.color = color;
    }

    public void moveCounter () {
        moveCounter++;
    }
    // Print out the piece in question.
    public String toString() {
        switch(type) {
            case KING:
                if (color == COLOR.BLACK)
                    return "♔";
                else
                    return "♚";
            case PAWN:
                if (color == COLOR.BLACK)
                    return "♙";
                else
                    return "♟";
            case ROOK:
                if (color == COLOR.BLACK)
                    return "♖";
                else
                    return "♜";
            case QUEEN:
                if (color == COLOR.BLACK)
                    return "♕";
                else
                    return "♛";
            case BISHOP:
                if (color == COLOR.BLACK)
                    return "♗";
                else
                    return "♝";
            case KNIGHT:
                if (color == COLOR.BLACK)
                    return "♘";
                else
                    return "♞";
        }
        // If we reach this, then we're f-ed.
        return null;
    }

}
