package Test;

import AI.AIMain;
import AI.Evaluator;
import Enumerators.COLOR;
import Enumerators.PIECETYPE;
import Game.Board;
import Game.Piece;

import java.util.Scanner;

public class TestEval {

    public static void main(String[] args) {
        TestEval test = new TestEval();
        test.testGame();
    }

    // For testing with odd board stances.
    public void testGame() {
        // Initialize the game.
        Scanner input = new Scanner(System.in);
        Board board = new Board();
        board.setStandardBoard();
        board.setPiece(5, 2, null);
        board.setPiece(5, 4, new Piece(PIECETYPE.PAWN, COLOR.WHITE));
        board.setPiece(5, 7, null);
        board.setPiece(5, 5, new Piece(PIECETYPE.PAWN, COLOR.BLACK));

        Evaluator eval = new Evaluator(COLOR.WHITE);
        System.out.println(board);
        System.out.println("Eval: " + eval.evaluateBoard(board, 0));

    }
}
