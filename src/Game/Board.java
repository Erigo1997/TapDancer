package Game;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;

public class Board {
    private Field[][] board;
    public int[][] pawnFieldValueBlack;
    public int[][] pawnFieldValueWhite;


    // Build a board.
    public Board() {
        board = new Field[8][8];
        pawnFieldValueBlack = new int[8][8];
        pawnFieldValueWhite = new int[8][8];
        setPawnFieldValue();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Black/White logic.
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    board[i][j] = new Field(COLOR.BLACK, i, j);
                } else {
                    board[i][j] = new Field(COLOR.WHITE, i, j);
                }
            }
        }
    }

    // Conversion from 1-8 to 0-7, simplifying piece placement.
    public void setPiece(int x, int y, Piece piece) {
        int value = piece.value;

        switch(piece.type) {
            case KING:
                break;
            case PAWN:
                if(piece.color == COLOR.WHITE){
                    value = pawnFieldValueWhite[y-1][x-1];
                }
                else{
                    value = pawnFieldValueBlack[y-1][x-1];
                }
                break;
            case ROOK:
                value = getRookProtected(x-1, y-1);
                break;
            case QUEEN:

            case BISHOP:
            case KNIGHT:

            default:
        }
        board[x - 1][y - 1].setPiece(piece, value);
    }

    public int getRookProtected(int x, int y){
        int protectCounter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(board[i][j].getPiece());
                if(i == y){
                    if(board[i][j].getPiece() != null)
                        protectCounter++;
                }

            }
        }
        return protectCounter;
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
        for (int i = board.length - 1; i > -1; i--) {
            for (int j = 0; j < board[i].length; j++) {
                output += board[j][i].toString();
            }
            output += "\n";
        }
        return output;
    }

    public void setPawnFieldValue() {


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 7) {
                    pawnFieldValueBlack[i][j] = 0;
                    pawnFieldValueWhite[i][j] = 0;
                }
            }
        }

        pawnFieldValueWhite[1][0] = -2;
        pawnFieldValueWhite[1][1] = 0;
        pawnFieldValueWhite[1][2] = 3;
        pawnFieldValueWhite[1][3] = 4;
        pawnFieldValueWhite[1][4] = 5;
        pawnFieldValueWhite[1][5] = 1;
        pawnFieldValueWhite[1][6] = -2;
        pawnFieldValueWhite[1][7] = -2;
        pawnFieldValueWhite[2][0] = -4;
        pawnFieldValueWhite[2][1] = -1;
        pawnFieldValueWhite[2][2] = 4;
        pawnFieldValueWhite[2][3] = 5;
        pawnFieldValueWhite[2][4] = 7;
        pawnFieldValueWhite[2][5] = 1;
        pawnFieldValueWhite[2][6] = -4;
        pawnFieldValueWhite[2][7] = -4;
        pawnFieldValueWhite[3][0] = -4;
        pawnFieldValueWhite[3][1] = 0;
        pawnFieldValueWhite[3][2] = 6;
        pawnFieldValueWhite[3][3] = 8;
        pawnFieldValueWhite[3][4] = 10;
        pawnFieldValueWhite[3][5] = 2;
        pawnFieldValueWhite[3][6] = -4;
        pawnFieldValueWhite[3][7] = -4;
        pawnFieldValueWhite[4][0] = -3;
        pawnFieldValueWhite[4][1] = 2;
        pawnFieldValueWhite[4][2] = 10;
        pawnFieldValueWhite[4][3] = 12;
        pawnFieldValueWhite[4][4] = 15;
        pawnFieldValueWhite[4][5] = 5;
        pawnFieldValueWhite[4][6] = -3;
        pawnFieldValueWhite[4][7] = -2;
        pawnFieldValueWhite[5][0] = 8;
        pawnFieldValueWhite[5][1] = 14;
        pawnFieldValueWhite[5][2] = 23;
        pawnFieldValueWhite[5][3] = 26;
        pawnFieldValueWhite[5][4] = 29;
        pawnFieldValueWhite[5][5] = 17;
        pawnFieldValueWhite[5][6] = 8;
        pawnFieldValueWhite[5][7] = 8;
        pawnFieldValueWhite[6][0] = 23;
        pawnFieldValueWhite[6][1] = 30;
        pawnFieldValueWhite[6][2] = 42;
        pawnFieldValueWhite[6][3] = 44;
        pawnFieldValueWhite[6][4] = 48;
        pawnFieldValueWhite[6][5] = 34;
        pawnFieldValueWhite[6][6] = 23;
        pawnFieldValueWhite[6][7] = 23;

        pawnFieldValueBlack[6][7] = -2;
        pawnFieldValueBlack[6][6] = 0;
        pawnFieldValueBlack[6][5] = 3;
        pawnFieldValueBlack[6][4] = 4;
        pawnFieldValueBlack[6][3] = 5;
        pawnFieldValueBlack[6][2] = 1;
        pawnFieldValueBlack[6][1] = -2;
        pawnFieldValueBlack[6][0] = -2;
        pawnFieldValueBlack[5][7] = -4;
        pawnFieldValueBlack[5][6] = -1;
        pawnFieldValueBlack[5][5] = 4;
        pawnFieldValueBlack[5][4] = 5;
        pawnFieldValueBlack[5][3] = 7;
        pawnFieldValueBlack[5][2] = 1;
        pawnFieldValueBlack[5][1] = -4;
        pawnFieldValueBlack[5][0] = -4;
        pawnFieldValueBlack[4][7] = -4;
        pawnFieldValueBlack[4][6] = 0;
        pawnFieldValueBlack[4][5] = 6;
        pawnFieldValueBlack[4][4] = 8;
        pawnFieldValueBlack[4][3] = 10;
        pawnFieldValueBlack[4][2] = 2;
        pawnFieldValueBlack[4][1] = -4;
        pawnFieldValueBlack[4][0] = -4;
        pawnFieldValueBlack[3][7] = -3;
        pawnFieldValueBlack[3][6] = 2;
        pawnFieldValueBlack[3][5] = 10;
        pawnFieldValueBlack[3][4] = 12;
        pawnFieldValueBlack[3][3] = 15;
        pawnFieldValueBlack[3][2] = 5;
        pawnFieldValueBlack[3][1] = -3;
        pawnFieldValueBlack[3][0] = -2;
        pawnFieldValueBlack[2][7] = 8;
        pawnFieldValueBlack[2][6] = 14;
        pawnFieldValueBlack[2][5] = 23;
        pawnFieldValueBlack[2][4] = 26;
        pawnFieldValueBlack[2][3] = 29;
        pawnFieldValueBlack[2][2] = 17;
        pawnFieldValueBlack[2][1] = 8;
        pawnFieldValueBlack[2][0] = 8;
        pawnFieldValueBlack[1][7] = 23;
        pawnFieldValueBlack[1][6] = 30;
        pawnFieldValueBlack[1][5] = 42;
        pawnFieldValueBlack[1][4] = 44;
        pawnFieldValueBlack[1][3] = 48;
        pawnFieldValueBlack[1][2] = 34;
        pawnFieldValueBlack[1][1] = 23;
        pawnFieldValueBlack[1][0] = 23;
    }
}
