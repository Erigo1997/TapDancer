package Game;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;

public class Board {
    private Field[][] board;

    // Build a board.
    public Board() {
        board = new Field[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Black/White fields logic.
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    board[i][j] = new Field(COLOR.BLACK);
                } else {
                    board[i][j] = new Field(COLOR.WHITE);
                }
            }
        }
    }

    public void playMove(Move move){
        setPiece(move.fromX, move.fromY, null);
        setPiece(move.toX, move.toY, move.subject);
        if (move.subject.type == PIECETYPE.PAWN) {
            if (move.subject.color == COLOR.WHITE) {
                if (move.toY == 8) {
                    // TODO: Ask player what type of conversion they want. We assume queen.
                    setPiece(move.toX, move.toY, new Piece(PIECETYPE.QUEEN, move.subject.color));
                }
            } else {
                if (move.toY == 1) {
                    setPiece(move.toX, move.toY, new Piece(PIECETYPE.QUEEN, move.subject.color));
                }
            }
        }
    }

    public void reverseMove(Move move){
        if (move.target != null)
            setPiece(move.toX, move.toY, move.target);
        else
            setPiece(move.toX, move.toY, null);
        setPiece(move.fromX, move.fromY, move.subject);
        /*
        if (move.subject.type == PIECETYPE.PAWN) {
            if (move.subject.color == COLOR.WHITE) {
                if (move.toY == 8) {
                    setPiece(move.fromX, move.fromY, new Piece(PIECETYPE.PAWN, COLOR.WHITE));
                }
            } else {
                if (move.toY == 1) {
                    setPiece(move.fromX, move.fromY, new Piece(PIECETYPE.PAWN, COLOR.BLACK));
                }
            }
        }

         */

    }

    // Conversion from 1-8 to 0-7, simplifying piece placement.
    public void setPiece(int x, int y, Piece piece) {
        board[x - 1][y - 1].setPiece(piece);
    }

    public Piece getPiece(int x, int y) {
        return board[x - 1][y - 1].getPiece();
    }

    // Sets up an initial board state.
    public void setStandardBoard() {
        int y = 1;
        COLOR col = COLOR.WHITE;
        setPiece(1, y, new Piece(PIECETYPE.ROOK, col));
        setPiece(8, y, new Piece(PIECETYPE.ROOK, col));
        setPiece(2, y, new Piece(PIECETYPE.KNIGHT, col));
        setPiece(7, y, new Piece(PIECETYPE.KNIGHT, col));
        setPiece(3, y, new Piece(PIECETYPE.BISHOP, col));
        setPiece(6, y, new Piece(PIECETYPE.BISHOP, col));
        setPiece(4, y, new Piece(PIECETYPE.QUEEN, col));
        setPiece(5, y, new Piece(PIECETYPE.KING, col));

        for (int i = 1; i <= 8; i++) {
            setPiece(i, y + 1, new Piece(PIECETYPE.PAWN, col));
        }

        y = 8;
        col = COLOR.BLACK;
        setPiece(1, y, new Piece(PIECETYPE.ROOK, col));
        setPiece(8, y, new Piece(PIECETYPE.ROOK, col));
        setPiece(2, y, new Piece(PIECETYPE.KNIGHT, col));
        setPiece(7, y, new Piece(PIECETYPE.KNIGHT, col));
        setPiece(3, y, new Piece(PIECETYPE.BISHOP, col));
        setPiece(6, y, new Piece(PIECETYPE.BISHOP, col));
        setPiece(4, y, new Piece(PIECETYPE.QUEEN, col));
        setPiece(5, y, new Piece(PIECETYPE.KING, col));

        for (int i = 1; i <= 8; i++) {
            setPiece(i, y - 1, new Piece(PIECETYPE.PAWN, col));
        }
    }


    // Silly logic to print out the board correctly.
    public String toString() {
        String output = "";
        for (int i = board.length - 1; i >=  0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                output += board[j][i].toString();
            }
            output += "\n";
        }
        return output;
    }
}
