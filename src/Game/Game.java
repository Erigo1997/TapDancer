package Game;

import AI.AIMain;
import AI.Evaluator;
import Enumerators.COLOR;
import Enumerators.PIECETYPE;

import java.util.Scanner;

public class Game {

    Board board;
    Scanner input;
    COLOR computerColour;
    AIMain tapDancer;

    public void playGame() {
        // Initialize the game.
        board = new Board();
        input = new Scanner(System.in);
        board.setStandardBoard();

        System.out.println("Which colour are you? (W/B)");
        String colour = input.next();
        if (colour.equals("W"))
            computerColour = COLOR.BLACK;
        else if (colour.equals("B"))
            computerColour = COLOR.WHITE;
        else {
            System.out.println("You gotta pick a colour, smartass.");
            System.exit(1);
        }

        // Initialize tapdancer.
        tapDancer = new AIMain(computerColour);

        COLOR currentColor = COLOR.WHITE;
        Move otherMove;
        while(true) {
            // Check turn.
            System.out.println(board.toString());
            if (computerColour == currentColor) {
                System.out.println("TapDancer is now making a move.");
                otherMove = tapDancer.makeMove(board);
                board.playMove(otherMove);
            } else {
                Move move = askPlayerMove();
                board.playMove(move);
            }

            currentColor = (currentColor == COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE;
        }

    }

    private Move askPlayerMove() {
        int x1;
        int y1;
        int x2;
        int y2;
        System.out.println("Input your move (From x, y, To x, y): ");
        x1 = input.nextInt();
        y1 = input.nextInt();
        x2 = input.nextInt();
        y2 = input.nextInt();
        return new Move(x1, y1, x2, y2, board.getPiece(x1, y1), board.getPiece(x2, y2), false);
    }

    // TODO: Remove test main.
    public static void main(String[] args) {
        Game game = new Game();
        game.testGame();
    }

    // For testing with odd board stances.
    public void testGame() {
        // Initialize the game.
        input = new Scanner(System.in);
        board = new Board();
        input = new Scanner(System.in);
        board.setPiece(1, 2, new Piece(PIECETYPE.PAWN, COLOR.WHITE));
        board.setPiece(2, 1, new Piece(PIECETYPE.ROOK, COLOR.WHITE));
        board.setPiece(1, 3, new Piece(PIECETYPE.BISHOP, COLOR.WHITE));
        board.setPiece(2, 3, new Piece(PIECETYPE.PAWN, COLOR.WHITE));
        board.setPiece(3, 2, new Piece(PIECETYPE.PAWN, COLOR.WHITE));
        board.setPiece(4, 2, new Piece(PIECETYPE.KING, COLOR.WHITE));
        board.setPiece(5, 3, new Piece(PIECETYPE.PAWN, COLOR.WHITE));
        board.setPiece(6, 3, new Piece(PIECETYPE.QUEEN, COLOR.WHITE));
        board.setPiece(6, 1, new Piece(PIECETYPE.BISHOP, COLOR.WHITE));

        board.setPiece(1, 5, new Piece(PIECETYPE.QUEEN, COLOR.BLACK));

        computerColour = COLOR.WHITE;
        // Initialize tapdancer.
        tapDancer = new AIMain(computerColour);

        // Initialize tapdancer.
        AIMain tapDancer2 = new AIMain(COLOR.BLACK);

        System.out.println(board.toString());

        input.next();

        System.out.println("TapDancer is now making a move.");
        Move otherMove = tapDancer.makeMove(board);
        board.playMove(otherMove);

        System.out.println(board.toString());

        input.next();

        System.out.println("TapDancer2 is now making a move.");
        otherMove = tapDancer2.makeMove(board);
        board.playMove(otherMove);

        System.out.println(board.toString());

    }
}
