package AI;

import Game.Board;

public class Evaluator {

    public int evaluateBoard(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(board.getPiece(j + 1, i + 1));
            }
        }
        return 1;
    }
}
