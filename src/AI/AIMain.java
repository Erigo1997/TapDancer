package AI;

import Enumerators.COLOR;
import Game.Board;
import Game.Move;
import Game.Piece;

import java.util.ArrayList;
import java.util.List;

public class AIMain {

    static final int maxDepth = 2; // How many moves to search into.
    private Board board;
    private COLOR myColor;
    private Move returnMove; // Set in Search in larger scope for practical reasons. Is the move we would like to return.
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

        // TODO: We need to return a move somehow. Otherwise this is kind of silly.
        search(firstState);

        // Let's see how that went!
        System.out.println("TapDancer searched a whopping " + StatTracker.getInstance().iterations + " different states.");
        System.out.println("TapDancer searched on following depths: ");
        for (int i = 0; i < StatTracker.getInstance().depthIterations.length; i++) {
            System.out.println("Depth[" + i + "]: " + StatTracker.getInstance().depthIterations[i]);
        }
        StatTracker.getInstance().compare();
        System.out.println("Compared to no pruning/eval, this was: ");
        for (int i = 0; i < StatTracker.getInstance().differenceIterations.length; i++) {
            System.out.println("Depth[" + i + "]: " + StatTracker.getInstance().differenceIterations[i] + "% more effective.");
        }

        return returnMove;
    }


    private float search(State state) {

        // Let's update the StatTracker on the latest news.
        StatTracker.getInstance().iterations++;
        StatTracker.getInstance().depthIterations[state.depth]++;

        // TODO: Can we check a win condition here? If so, return it.
        // --------- Win Condition

        // TODO: Check any moves left. Probably not gonna be relevant for some time.
        // ----------- Check if there are any moves left. If not, let's go back. -----------

        // ----------- Initiliaze our auxiliary variables instead of doing it in code ---------
        float value;
        State newState;
        boolean isMax = state.turnColor == myColor; // Are we Max-searching or Min-searching? Self is Maxsearching.
        // ----------- Search all moves with alpha beta pruning -------------
        // Check that depth hasn't exceeded max depth.
        if (state.depth < maxDepth) {
            // Double for loop for the entire board.
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    // If the field contains a piece whose turn it is
                    if (board.getPiece(x, y) != null && board.getPiece(x, y).color == state.turnColor) {
                        // Then let's search all valid moves.
                        List<Move> moves = generator.getMoves(board, board.getPiece(x, y), x, y);
                        for (Move move: moves) {
                            // If alpha is larger than beta, return.
                            // TODO: Can we add some stat-tracking here?
                            if (state.alpha > state.beta) {
                                if (isMax) {
                                    return state.alpha;
                                } else {
                                    return state.beta;
                                }
                            }
                            newState = new State();
                            // TODO: Reconsider - do states really need to contain moves?
                            newState.depth = state.depth + 1;
                            newState.alpha = state.alpha;
                            newState.beta = state.beta;
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
                                if (value > state.alpha) {
                                    state.alpha = value;
                                    if (state.depth == 0) {
                                        returnMove = move;
                                    }
                                }
                            } else {
                                if (value < state.beta) {
                                    state.beta = value;
                                }
                            }
                        }
                    }
                }
            }
        }
        // TODO: Evaluate the state here. Test evaluation for speed. Depth 6 took about 10 seconds with no evaluation - or pruning! Depth 7 never seems to finish.
        return evaluator.evaluateBoard(board, state.depth);
    }

}
