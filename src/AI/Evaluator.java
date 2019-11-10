package AI;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;
import Game.Board;
import Game.Piece;

public class Evaluator {

    public static int[] pawnRow = {0, 0, -1, 0, 2, 14, 30, 0};
    public static int[] pawnLine = {-2, 0, 3, 4, 5, 1, -2, -2};

    // If we are black, we need to flip some values around.
    public void flipBoard() {
        pawnRow = new int[] {0, 30, 14, 2, 0, -1, 0, 0};
    }

    // Return evaluation. Add up value from own pieces, remove value for opponent pieces.
    public int evaluateBoard(Board board, COLOR myColor) {
        int evalSum = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                // First we sum up pieces for a total value. This is the most basic evaluation function.
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    switch(piece.type) {
                        case KING:
                            if (piece.color ==  myColor)
                                evalSum += 10000; // TODO: Add -100 per 'depths in' we are.
                            else
                                evalSum -= 10000;
                            break;
                        case QUEEN:
                            if (piece.color ==  myColor)
                                evalSum += 900; // TODO: Add covered fields.
                            else
                                evalSum -= 900;
                            break;
                        case ROOK:
                            if (piece.color ==  myColor)
                                evalSum += 500; // TODO: Add covered fields.
                            else
                                evalSum -= 500;
                            break;
                        case BISHOP:
                            if (piece.color ==  myColor)
                                evalSum += 300; // TODO: Add covered fields.
                            else
                                evalSum -= 300;
                            break;
                        case KNIGHT:
                            if (piece.color ==  myColor)
                                evalSum += 300; // TODO: Add covered fields.
                            else
                                evalSum -= 300;
                            break;
                        case PAWN:
                            int pawnValue;
                            pawnValue = 100 + pawnRow[y] + (pawnLine[x] * (y/2));
                            if (piece.color ==  myColor)
                                evalSum += pawnValue; // TODO: Add covered fields.
                            else
                                evalSum -= pawnValue;
                            break;
                        default:
                            break;
                    }
                    // TODO: Check for double-pawns
                    // TODO: Check for fortresses
                    // TODO: Check for threatened pieces
                    // TODO: Check for endgame
                    // TODO: Check for chess-mate-finale
                }
            }
        }
        return 1;
    }
}
