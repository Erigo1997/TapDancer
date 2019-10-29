package AI;

import Enumerators.COLOR;
import Game.Board;

public class Evaluator {

    // Return evaluation. Add up value from own pieces, remove value for opponent pieces.
    public int evaluateBoard(Board board, COLOR myColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(board.getPiece(j + 1, i + 1));
            }
        }
        return 1;
    }
}
