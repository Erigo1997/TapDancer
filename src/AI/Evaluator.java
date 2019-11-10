package AI;

import Enumerators.COLOR;
import Game.Board;
import Game.Field;
import Game.Move;
import Game.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Evaluator {
    private Field[][] board;
    private Piece tempPiece;
    public int[][] pawnFieldValueBlack;
    public int[][] pawnFieldValueWhite;
    private int[][] valueBoard;
    private MoveGenerator generator;
    private COLOR color;
    private Board entireBoard;

    public Evaluator(){
        pawnFieldValueBlack = new int[8][8];
        pawnFieldValueWhite = new int[8][8];
        valueBoard = new int[8][8];
        setPawnFieldValue();
    }

    // Return evaluation. Add up value from own pieces, remove value for opponent pieces.
    public int evaluateBoard(Board boardLoad, MoveGenerator generator, COLOR turnColor) {
        board = boardLoad.getBoard();
        entireBoard = boardLoad;
        this.color = turnColor;
        this.generator = generator;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                tempPiece = boardLoad.getPiece(x, y);
                if(tempPiece == null)
                    continue;
                //System.out.println(board[i][j].getPiece());
                valueBoard[x-1][y-1] = evaluatePiece(tempPiece, x, y);
                System.out.println(valueBoard[x-1][y-1]);
            }
        }
        return 1;
    }

    private int evaluatePiece(Piece piece, int x, int y) {
        List<Move> moves = generator.getMoves(entireBoard, piece, color, x, y);
        //System.out.println("Move " + piece.type + ": " + moves.size() + " moves: " +  moves.toString());

        switch(piece.type) {
            case KING:
                //subtract moves to mate
                return 10000;
            case PAWN:
                if(piece.color == COLOR.WHITE){
                    return pawnFieldValueWhite[y-1][x-1];
                }
                else{
                    return pawnFieldValueBlack[y-1][x-1];
                }
            case ROOK:
                return 500 + 2 * moves.size();
            case QUEEN:
                return 900 + 1 * moves.size();
            case BISHOP:
                return 300 + 2 * moves.size();
            case KNIGHT:
                //distances from the 4 centers
                List<Integer> distances = new ArrayList<>();
                int distance = (int)Math.sqrt(Math.pow(4-x, 2) + Math.pow(4-y, 2));
                int distance2 = (int)Math.sqrt(Math.pow(4-x, 2) + Math.pow(5-y, 2));
                int distance3 = (int)Math.sqrt(Math.pow(5-x, 2) + Math.pow(4-y, 2));
                int distance4 = (int)Math.sqrt(Math.pow(5-x, 2) + Math.pow(5-y, 2));
                distances.add(distance);
                distances.add(distance2);
                distances.add(distance3);
                distances.add(distance4);
                //return lowest distance
                //System.out.print(300 + 3 * distances.indexOf(Collections.min(distances)));
                return 300 + 3 * distances.indexOf(Collections.min(distances));
            default:
                break;
        }
        return 0;
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

