package AI;

import Enumerators.COLOR;
import Game.Board;
import Game.Move;

import java.util.List;

public class AIMain {

    public static int maxDepth = 8; // How many moves to search into.
    public int currentMaxDepth;
    private long maxSearchTime = 4; // How many seconds we allow the process to take at most.
    private Board board;
    private COLOR myColor;
    private Move returnMove; // Set in Search in larger scope for practical reasons. Is the move we would like to return.
    private Move bestMove; // Works in conjuction with Returnmove. This is a temporary best move for current max depth.
    private MoveGenerator generator;
    private Evaluator evaluator;

    // Constructor with color.
    public AIMain(COLOR color) {
        myColor = color;
        generator = new MoveGenerator();
        evaluator = new Evaluator(color);
    }

    // Making a move.
    public Move makeMove(Board board) {

        // Time to tap on some fools.
        this.board = board;
        State firstState = new State();
        firstState.alpha = -999999;
        firstState.beta = 999999;
        firstState.depth = 0;
        firstState.turnColor = myColor;

        // Let's reset the stattracker.
        StatTracker.getInstance().iterations = 0;
        StatTracker.getInstance().resetDepthIterations();

        StatTracker.getInstance().resetTime();

        currentMaxDepth = 1;
        while (currentMaxDepth <= maxDepth) {
            System.out.println("Searching with depth: " + currentMaxDepth);
            float searchResult = search(firstState);
            if (searchResult == -999999 || searchResult == 999999) {
                break;
            } else {
                returnMove = bestMove;
            }
            System.out.println("Best move for depth " + currentMaxDepth + " is " + bestMove);
            currentMaxDepth++;
        }


        // Let's see how that went!
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

        return returnMove;
    }

    private float search(State state) {
        boolean isMax = state.turnColor == myColor; // Are we Max-searching or Min-searching? Self is Maxsearching.
        if (StatTracker.getInstance().getTime() >= maxSearchTime) {
            System.out.println("Braking search.");
            if (isMax) {
                return -999999;
            } else {
                return 999999;
            }
        }

        // ----------- Check if there's time for more searching. Else it's time to go home. -----------



        // Let's update the StatTracker on the latest news.
        StatTracker.getInstance().iterations++;
        StatTracker.getInstance().depthIterations[state.depth]++;

        // TODO: Can we check a win condition here? If so, return it.
        // --------- Win Condition

        // TODO: Check any moves left. Probably not gonna be relevant for some time.
        // ----------- Check if there are any moves left. If not, let's go back. -----------

        // ----------- Initialize our auxiliary variables instead of doing it in code ---------
        float value;
        State newState;
        float searchValue;
        // ----------- Search all moves with alpha beta pruning -------------
        // Check that depth hasn't exceeded max depth.
        if (state.depth >= currentMaxDepth) {
            // TODO: Evaluate the state here. Test evaluation for speed. Depth 6 took about 10 seconds with no evaluation - or pruning! Depth 7 never seems to finish.
            return evaluator.evaluateBoard(board, state.depth);
        }

        if (isMax) {
            value = -999999;
        } else {
            value = 999999;
        }

        // Double for loop for the entire board.
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    // If the field contains a piece whose turn it is
                    if (board.getPiece(x, y) != null && board.getPiece(x, y).color == state.turnColor) {
                        // Then let's search all valid moves.
                        List<Move> moves = generator.getMoves(board, board.getPiece(x, y), x, y);
                        for (Move move : moves) {
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
                            searchValue = search(newState);
                            if (isMax) {
                                value = maximize(value, searchValue);
                            } else {
                                value = minimize(value, searchValue);
                            }
                            board.reverseMove(move); // Make sure the board is reverted.
                            if (isMax) {
                                if (state.depth == 0 && value > state.alpha) {
                                    bestMove = move;
                                }
                                state.alpha = maximize(value, state.alpha);
                            } else {
                                state.beta = minimize(value, state.beta);
                            }

                            if (state.alpha >= state.beta) {
                                return value;
                            }
                        }
                    }
                }
            }
            return value;
    }


        private float maximize(float a, float b) {
            return Math.max(a, b);
        }

        private float minimize(float a, float b) {
            return Math.min(a, b);
        }

}

