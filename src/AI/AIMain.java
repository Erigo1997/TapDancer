package AI;

import Enumerators.COLOR;
import Game.Board;
import Game.Move;
import Game.Piece;

import java.util.ArrayList;
import java.util.List;

public class AIMain {

    final int maxDepth = 6; // How many moves to search into.
    Board board;
    COLOR myColor;

    // Constructor with color.
    public AIMain(COLOR color) {
        myColor = color;
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

        return null;
    }


    private void search(State state) {

        // Let's update the stattracker on the latest news.
        StatTracker.getInstance().iterations++;
        StatTracker.getInstance().depthIterations[state.depth]++;

        // TODO: Can we check a win condition here? If so, return it.
        // --------- Win Condition

        // TODO: Check any moves left. Probably not gonna be relevant for some time.
        // ----------- Check if there are any moves left. If not, let's go back. -----------

        // ----------- Search all moves with alpha beta pruning -------------
        State newState;
        // Check that depth hasn't exceeded max depth.
        if (state.depth < maxDepth) {
            // Double for loop for the entire board.
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    // If the field contains a piece whose turn it is
                    if (board.getPiece(x, y) != null && board.getPiece(x, y).color == state.turnColor) {
                        // System.out.println("TapDancer found a: " + board.getPiece(x, y) + " with colour " + board.getPiece(x, y).color);
                        // Then let's search all valid moves.
                        // TODO: We need some bloody pruning up in this *****
                        List<Move> moves = getMoves(board.getPiece(x, y), state.turnColor, x, y);
                        for (Move move: moves) {
                            newState = new State();
                            // TODO: Reconsider - do states really need to contain moves?
                            newState.move = move;
                            newState.depth = state.depth + 1;
                            // System.out.println(move);
                            // Set the colour to opposite.
                            if (state.turnColor == COLOR.WHITE)
                                newState.turnColor = COLOR.BLACK;
                            else
                                newState.turnColor = COLOR.WHITE;
                            board.playMove(move);
                            search(newState);
                            board.reverseMove(move); // Make sure the board is reverted.
                        }
                    }
                }
            }
        }
        // TODO: Evaluate the state here. Test evaluation for speed. Depth 6 took about 10 seconds with no evaluation - or pruning! Depth 7 never seems to finish.


    }

    public boolean checkExists(int x, int y) {
        try {
            board.getPiece(x, y);
            return true;
        } catch (NullPointerException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // Returns a list of available moves for a piece.
    public List<Move> getMoves(Piece piece, COLOR turnColor, int x, int y) {
        int x2, y2;
        List<Move> output = new ArrayList<Move>();
        switch (piece.type) {
            // For a pawn.
            case PAWN:
                // If the pawn is white. Inverted moves (downward) if the pawn is black
                if (turnColor == COLOR.WHITE) {
                    // Check if it can simply move forward.
                    x2 = x;
                    y2 = y + 1;
                    if (checkExists(x2, y2) && board.getPiece(x2, y2) == null) {
                        output.add(new Move(x, y, x2, y2, piece, null, false));
                        // Check if it can move forward twice.
                        y2 = y + 2;
                        if (checkExists(x2, y) && board.getPiece(x2, y2) == null) {
                            output.add(new Move(x, y, x2, y2, piece, null, false));
                        }
                    }
                    // Let's check if we can beat down anyone. First up and to the left.
                    x2 = x - 1;
                    y2 = y + 1;
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.WHITE) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false));
                        }
                    }

                    x2 = x + 1;
                    y2 = y + 1;
                    // Now up and to the right.
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.WHITE) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false));
                        }
                    }
                    // ELSE for black pawns.
                } else {
                    // Check if it can simply move forward.
                    x2 = x;
                    y2 = y - 1;
                    if (checkExists(x2, y2) && board.getPiece(x2, y2) == null) {
                        output.add(new Move(x, y, x2, y2, piece, null, false));
                        // Check if it can move forward twice.
                        y2 = y - 2;
                        if (checkExists(x2, y) && board.getPiece(x2, y2) == null) {
                            output.add(new Move(x, y, x2, y2, piece, null, false));
                        }
                    }
                    // Let's check if we can beat down anyone. First down and to the left.
                    x2 = x - 1;
                    y2 = y - 1;
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.WHITE) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false));
                        }
                    }

                    x2 = x + 1;
                    y2 = y - 1;
                    // Now down and to the right.
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.WHITE) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false));
                        }
                    }
                }
                break;
            case KING:
                x2 = x;
                y2 = y;

                // We check all nine fields around the king. We start at upwards and go with the clock.
                if (checkExists(x, y + 1)) scanField(piece, turnColor, x, y, x, y + 1, output);
                if (checkExists(x + 1, y + 1)) scanField(piece, turnColor, x, y, x + 1, y + 1, output);
                if (checkExists(x + 1, y)) scanField(piece, turnColor, x, y, x + 1, y, output);
                if (checkExists(x + 1, y - 1)) scanField(piece, turnColor, x, y, x + 1, y - 1, output);
                if (checkExists(x, y - 1)) scanField(piece, turnColor, x, y, x, y - 1, output);
                if (checkExists(x - 1, y - 1)) scanField(piece, turnColor, x, y, x - 1, y - 1, output);
                if (checkExists(x - 1, y)) scanField(piece, turnColor, x, y, x - 1, y, output);
                if (checkExists(x - 1, y + 1)) scanField(piece, turnColor, x, y, x - 1, y + 1, output);
                break;
            case KNIGHT:
                // We check in a windmill from the upwards-right movement and around the clock. Just trust me.

                // Up-Right
                x2 = x;
                y2 = y;
                x2 += 1;
                y2 += 2;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Right-Up
                x2 = x;
                y2 = y;
                x2 += 2;
                y2 += 1;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Right-Down
                x2 = x;
                y2 = y;
                x2 += 2;
                y2 -= 1;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Down-Right
                x2 = x;
                y2 = y;
                x2 += 1;
                y2 -= 2;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Down-Left
                x2 = x;
                y2 = y;
                x2 -= 1;
                y2 -= 2;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Left-Down
                x2 = x;
                y2 = y;
                x2 -= 2;
                y2 -= 1;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Left-Up
                x2 = x;
                y2 = y;
                x2 -= 2;
                y2 += 1;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);

                // Up-Left
                x2 = x;
                y2 = y;
                x2 -= 1;
                y2 += 2;
                if (checkExists(x2, y2)) scanField(piece, turnColor, x, y, x2, y2, output);
                break;
            case QUEEN:
                // --- An amalgamation of both rooks and bishops.
                // Let's check the four diagonal directions.
                // Up-Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, ++y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Up-Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, ++y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Down-Left
                x2 = x;
                y2 = y;
                while (checkExists(--x2, --y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Down-Right
                x2 = x;
                y2 = y;
                while (checkExists(--x2, --y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Let's check the four cardinal directions.
                // Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Up.
                x2 = x;
                y2 = y;
                while (checkExists(x2, ++y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Down
                x2 = x;
                y2 = y;
                while (checkExists(x2, --y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                break;
            case BISHOP:
                // Let's check the four diagonal directions.
                // Up-Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, ++y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Up-Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, ++y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Down-Left
                x2 = x;
                y2 = y;
                while (checkExists(--x2, --y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Down-Right
                x2 = x;
                y2 = y;
                while (checkExists(--x2, --y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                break;
            case ROOK:
                // Let's check the four cardinal directions.
                // Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Up.
                x2 = x;
                y2 = y;
                while (checkExists(x2, ++y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                // Down
                x2 = x;
                y2 = y;
                while (checkExists(x2, --y2)) {
                    if (scanField(piece, turnColor, x, y, x2, y2, output)) break;
                }
                break;
        }
        return output;
    }

    // Adds moves to the output unless the spot is occupied by allies.
    private boolean scanField(Piece piece, COLOR turnColor, int x, int y, int x2, int y2, List<Move> output) {
        // If the spot is empty, that's a move.
        if (board.getPiece(x2, y2) == null) {
            output.add(new Move(x, y, x2, y2, piece, null, false));
        } else {
            // If the spot is occupied by an enemy, that's a move.
            if (board.getPiece(x2, y2).color != turnColor) {
                output.add(new Move(x, y, x2, y2, piece, board.getPiece(x2, y2), false));
                return true;
            } else {
                // Otherwise it ain't no move.
                return true;
            }
        }
        return false;
    }

}
