package Game;

import AI.AIMain;
import Enumerators.COLOR;
import Enumerators.PIECETYPE;

import java.util.InputMismatchException;
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
        System.out.println(board.toString());
        while(true) {
            // Check turn.
            if (computerColour == currentColor) {
                computerTurn();
            } else {
                playerTurn();
            }

            currentColor = (currentColor == COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE;
        }

    }

    private void computerTurn() {
        board.isComputerTurn = true;
        Move otherMove;
        System.out.println("TapDancer is now making a move.");
        otherMove = tapDancer.makeMove(board);
        board.playMove(otherMove);
        System.out.println(otherMove);
        System.out.println(board.toString());
    }


    private void playerTurn() {
        board.isComputerTurn = false;
        Move move = askPlayerMove();
        board.playMove(move);
        System.out.println(board.toString());
        askPlayerNext(move);
    }

    private void askPlayerNext(Move lastMove) {
        System.out.println("Next? (go/reverse/extra/depth)");
        try {
            String response = input.next();
            if (response.equals("go")) {
                return;
            }
            if (response.equals("depth")) {
                System.out.println("Which depth are we changing to, captain?");
                int newDepth = input.nextInt();
                tapDancer.maxDepth = newDepth;
                System.out.println("Changing depth to " + newDepth);
                askPlayerNext(lastMove);
            }
            if (response.equals("reverse")) {
                board.reverseMove(lastMove);
                System.out.println(board.toString());
                askPlayerNext(null);
                return;
            }
            if (response.equals("extra")) {
                Move move = askPlayerMove();
                board.playMove(move);
                System.out.println(board.toString());
                askPlayerNext(move);
                return;
            }
            askPlayerNext(lastMove);
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch exception. Try again.");
            askPlayerNext(lastMove);
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
        return new Move(x1, y1, x2, y2, board.getPiece(x1, y1), board.getPiece(x2, y2), false, 33);
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

    }
}
