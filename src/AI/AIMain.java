package AI;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;
import Game.Board;
import Game.Move;
import Game.Piece;

import java.util.PriorityQueue;

public class AIMain {

    public static int maxDepth = 5; // How many moves to search into.
    public int currentMaxDepth;
    private static long maxSearchTime = 15; // How many seconds we allow the process to take at most.
    private Board board;
    private COLOR myColor;
    private Move returnMove; // Set in Search in larger scope for practical reasons. Is the move we would like to return.
    private Move bestMove; // Works in conjunction with Returnmove. This is a temporary best move for current max depth.
    private MoveGenerator generator;
    public Evaluator evaluator;

    // Constructor with color.
    public AIMain(COLOR color) {
        myColor = color;
        generator = new MoveGenerator();
        evaluator = new Evaluator(color);
    }

    // Making a move.
    public Move makeMove(Board board) {

        // Set Bestmove to null to reset.
        bestMove = null;

        // Let's reset the stattracker.
        StatTracker.getInstance().iterations = 0;
        StatTracker.getInstance().resetDepthIterations();

        StatTracker.getInstance().resetTime();

        currentMaxDepth = 1;
        while (currentMaxDepth <= maxDepth) {
            // Time to tap on some fools.
            this.board = board;
            State firstState = new State();
            firstState.alpha = -999999;
            firstState.beta = 999999;
            firstState.depth = 0;
            firstState.turnColor = myColor;

            System.out.println("Searching with depth: " + currentMaxDepth);
            float searchResult = search(firstState);
            if (searchResult == -999999 || searchResult == 999999) {
                break;
            } else {
                returnMove = bestMove;
            }
            System.out.println("Best move for depth " + currentMaxDepth + " is " + bestMove);
            bestMove.moveValue = 500;
            currentMaxDepth++;
        }


        // Let's see how that went!
        printStatistics();

        if (returnMove.subject.type == PIECETYPE.KING) {
            generator.castleLegalLeft = false;
            generator.castleLegalRight = false;
        }
        if (myColor == COLOR.WHITE) {
            if (returnMove.fromY == 1 && returnMove.fromX == 1) {
                generator.castleLegalLeft = false;
            }
            if (returnMove.fromY == 1 && returnMove.fromX == 8) {
                generator.castleLegalRight = false;
            }
        } else {
            if (returnMove.fromY == 8 && returnMove.fromX == 1) {
                generator.castleLegalLeft = false;
            }
            if (returnMove.fromY == 8 && returnMove.fromX == 8) {
                generator.castleLegalRight = false;
            }
        }

        return returnMove;
    }

    private void printStatistics() {
        System.out.println("TapDancer searched on following depths: ");
        for (int i = 0; i < StatTracker.getInstance().depthIterations.length; i++) {
            System.out.println("Depth[" + i + "]: " + StatTracker.getInstance().depthIterations[i]);
        }
        StatTracker.getInstance().compare();
        System.out.println("Compared to no pruning/eval, this was: ");
        for (int i = 0; i < StatTracker.getInstance().differenceIterations.length; i++) {
            System.out.println("Depth[" + i + "]: " + StatTracker.getInstance().differenceIterations[i] + "% more effective.");
        }
        System.out.println("TapDancer searched a whopping " + StatTracker.getInstance().iterations + " different states.");
        System.out.println("Searching took: " + StatTracker.getInstance().getTime() + " seconds.");
    }

    private float search(State state) {
        boolean isMax = state.turnColor == myColor; // Are we Max-searching or Min-searching? Self is Maxsearching.
        // ----------- Check if there's time for more searching. Else it's time to go home. -----------
        if (StatTracker.getInstance().getTime() >= maxSearchTime) {
            System.out.println("Braking search.");
            if (isMax) {
                return -999999;
            } else {
                return 999999;
            }
        }



        // Let's update the StatTracker on the latest news.
        StatTracker.getInstance().iterations++;
        StatTracker.getInstance().depthIterations[state.depth]++;


        // --------- Termination State
        // We can check if a state is a termination state (It should stop searching) if isKingDead in Board is True.
        // This flag is true when a king has been eliminated.
        /*
        if (board.isKingDead) {
            return evaluator.evaluateBoard(board, state.depth);
        }
         */

        // TODO: Check any moves left. Probably not gonna be relevant for some time.
        // ----------- Check if there are any moves left. If not, let's go back. -----------

        // ----------- Initialize our auxiliary variables instead of doing it in loop ---------
        float value;
        State newState;
        Float searchValue;
        Piece selectedPiece;
        // ----------- Search all moves with alpha beta pruning -------------
        // Check that depth hasn't exceeded max depth.
        if (state.depth > currentMaxDepth) {
            return evaluator.evaluateBoard(board, state.depth);
        }

        // Search previously best move first if we are in depth 0.
        /* TODO: Check if this really works as intended.
        if (state.depth == 0 && bestMove != null) {
            searchValue = analyzeMove(state, isMax, bestMove);
            if (searchValue != null) return searchValue;
        }
         */

        // Double for loop for the entire board.
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    // If the field contains a piece whose turn it is
                    selectedPiece = board.getPiece(x, y);
                    if (selectedPiece != null && selectedPiece.color == state.turnColor) {
                        // Then let's search all valid moves.
                        PriorityQueue<Move> moves = generator.getMoves(board, selectedPiece, x, y);
                        for (Move move : moves) {
                            searchValue = analyzeMove(state, isMax, move);
                            if (searchValue != null) return searchValue;
                        }
                    }
                }
            }
        if (isMax) {
            return state.alpha;
        } else {
            return state.beta;
        }
      
    }

    private Float analyzeMove(State state, boolean isMax, Move move) {
        State newState;
        float value;
        if (state.alpha >= state.beta) {
            if (isMax) {
                return state.alpha;
            } else {
                return state.beta;
            }
        }

        newState = new State();
        newState.depth = state.depth + 1;
        newState.alpha = state.alpha;
        newState.beta = state.beta;
        newState.move = move;
        // System.out.println(move);
        // Set the colour to opposite.
        if (state.turnColor == COLOR.WHITE)
            newState.turnColor = COLOR.BLACK;
        else
            newState.turnColor = COLOR.WHITE;
        // Let's alpha-beta-value-prune.
        board.playMove(move);
        value = search(newState);
        board.reverseMove(move); // Make sure the board is reverted.

        if (isMax) {
            if (state.depth == 0 && value > state.alpha) {
                bestMove = move;
            }
            state.alpha = Math.max(value, state.alpha);
        } else {
            state.beta = Math.min(value, state.beta);
        }
        return null;
    }

}

