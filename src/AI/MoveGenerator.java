package AI;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;
import Game.Board;
import Game.Move;
import Game.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MoveGenerator {

    private Board board;
    public boolean castleLegalLeft = true;
    public boolean castleLegalRight = true;

    public boolean checkExists(int x, int y) {
        try {
            board.getPiece(x, y);
            return true;
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // Adds moves to the output unless the spot is occupied by allies.
    private boolean scanField(Piece piece, int x, int y, int x2, int y2, PriorityQueue<Move> output) {
        // TODO: Get rid of moves that don't protect the king.
        // If the spot is empty, that's a move.
        if (board.getPiece(x2, y2) == null) {
            output.add(new Move(x, y, x2, y2, piece, null, false, 10));
        } else {
            // If the spot is occupied by an enemy, that's a move.
            if (board.getPiece(x2, y2).color != piece.color) {
                output.add(new Move(x, y, x2, y2, piece, board.getPiece(x2, y2), false, 50));
                return true;
            } else {
                // Otherwise it ain't no move.
                return true;
            }
        }
        return false;
    }

    // Returns a list of available moves for a piece. Remember to set the board.
    public PriorityQueue<Move> getMoves(Board newBoard, Piece piece, int x, int y) {
        this.board = newBoard;
        int x2, y2;
        PriorityQueue<Move> output = new PriorityQueue<>();
        switch (piece.type) {
            // For a pawn.
            case PAWN:
                // If the pawn is white. Inverted moves (downward) if the pawn is black
                if (piece.color == COLOR.WHITE) {
                    // Check if it can simply move forward.
                    x2 = x;
                    y2 = y + 1;
                    if (checkExists(x2, y2) && board.getPiece(x2, y2) == null) {
                        output.add(new Move(x, y, x2, y2, piece, null, false, 10));
                        // Check if it can move forward twice.
                        y2 = y + 2;
                        if (y == 2 && checkExists(x2, y2) && board.getPiece(x2, y2) == null) {
                            output.add(new Move(x, y, x2, y2, piece, null, false, 10));
                        }
                    }
                    // Let's check if we can beat down anyone. First up and to the left.
                    x2 = x - 1;
                    y2 = y + 1;
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.BLACK) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false, 50));
                        }
                    }

                    x2 = x + 1;
                    y2 = y + 1;
                    // Now up and to the right.
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.BLACK) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false, 50));
                        }
                    }
                    // ELSE for black pawns.
                } else {
                    // Check if it can simply move forward.
                    x2 = x;
                    y2 = y - 1;
                    if (checkExists(x2, y2) && board.getPiece(x2, y2) == null) {
                        output.add(new Move(x, y, x2, y2, piece, null, false, 10));
                        // Check if it can move forward twice.
                        y2 = y - 2;
                        if (y == 7 && checkExists(x2, y2) && board.getPiece(x2, y2) == null) {
                            output.add(new Move(x, y, x2, y2, piece, null, false, 10));
                        }
                    }
                    // Let's check if we can beat down anyone. First down and to the left.
                    x2 = x - 1;
                    y2 = y - 1;
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.WHITE) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false, 50));
                        }
                    }

                    x2 = x + 1;
                    y2 = y - 1;
                    // Now down and to the right.
                    if (checkExists(x2, y2)) {
                        if (board.getPiece(x2, y2) != null && board.getPiece(x2, y2).color == COLOR.WHITE) {
                            output.add(new Move(x, y , x2, y2, piece, board.getPiece(x2, y2), false, 50));
                        }
                    }
                }
                break;
            case KING:
                // ~~~~~~~~~~~~~~~~~ Check Castling ~~~~~~~~~~
                y2 = 1;
                // Check for white first.
                if (y == y2 && piece.color == COLOR.WHITE && x == 5) {
                    if (board.getPiece(8, y2) != null && board.getPiece(8, y2).type == PIECETYPE.ROOK && board.getPiece(8, y2).color == piece.color) {
                        if (board.getPiece(6, y2) == null && board.getPiece(7, y2) == null) {
                            // output.add(new Move(5, y2, 8, y2, piece, null, true, 100));
                        }
                    }
                    if (board.getPiece(1, y2) != null && board.getPiece(1, y2).type == PIECETYPE.ROOK && board.getPiece(1, y2).color == piece.color) {
                        if (board.getPiece(4, y2) == null && board.getPiece(3, y2) == null && board.getPiece(2, y2) == null) {
                            // output.add(new Move(5, y2, 1, y2, piece, null, true, 100));
                        }
                    }
                }
                // Check for black.
                y2 = 8;
                if (y == y2 && piece.color == COLOR.BLACK && x == 5) {
                    if (board.getPiece(8, y2) != null && board.getPiece(8, y2).type == PIECETYPE.ROOK && board.getPiece(8, y2).color == piece.color) {
                        if (board.getPiece(6, y2) == null && board.getPiece(7, y2) == null) {
                            // output.add(new Move(5, y2, 7, y2, piece, null, true, 100));
                        }
                    }
                    if (board.getPiece(1, y2) != null && board.getPiece(1, y2).type == PIECETYPE.ROOK && board.getPiece(1, y2).color == piece.color) {
                        if (board.getPiece(4, y2) == null && board.getPiece(3, y2) == null && board.getPiece(2, y2) == null) {
                            // output.add(new Move(5, y2, 3, y2, piece, null, true, 100));
                        }
                    }
                }

                // We check all nine fields around the king. We start at upwards and go with the clock.
                if (checkExists(x, y + 1)) scanField(piece, x, y, x, y + 1, output);
                if (checkExists(x + 1, y + 1)) scanField(piece, x, y, x + 1, y + 1, output);
                if (checkExists(x + 1, y)) scanField(piece, x, y, x + 1, y, output);
                if (checkExists(x + 1, y - 1)) scanField(piece, x, y, x + 1, y - 1, output);
                if (checkExists(x, y - 1)) scanField(piece, x, y, x, y - 1, output);
                if (checkExists(x - 1, y - 1)) scanField(piece, x, y, x - 1, y - 1, output);
                if (checkExists(x - 1, y)) scanField(piece, x, y, x - 1, y, output);
                if (checkExists(x - 1, y + 1)) scanField(piece, x, y, x - 1, y + 1, output);
                break;
            case KNIGHT:
                // We check in a windmill from the upwards-right movement and around the clock. Just trust me.

                // Up-Right
                x2 = x;
                y2 = y;
                x2 += 1;
                y2 += 2;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Right-Up
                x2 = x;
                y2 = y;
                x2 += 2;
                y2 += 1;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Right-Down
                x2 = x;
                y2 = y;
                x2 += 2;
                y2 -= 1;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Down-Right
                x2 = x;
                y2 = y;
                x2 += 1;
                y2 -= 2;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Down-Left
                x2 = x;
                y2 = y;
                x2 -= 1;
                y2 -= 2;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Left-Down
                x2 = x;
                y2 = y;
                x2 -= 2;
                y2 -= 1;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Left-Up
                x2 = x;
                y2 = y;
                x2 -= 2;
                y2 += 1;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);

                // Up-Left
                x2 = x;
                y2 = y;
                x2 -= 1;
                y2 += 2;
                if (checkExists(x2, y2)) scanField(piece, x, y, x2, y2, output);
                break;
            case QUEEN:
                // --- An amalgamation of both rooks and bishops.
                // Let's check the four diagonal directions.
                // Up-Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, ++y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Up-Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, ++y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Down-Left
                x2 = x;
                y2 = y;
                while (checkExists(--x2, --y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Down-Right
                x2 = x;
                y2 = y;
                while (checkExists(++x2, --y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Let's check the four cardinal directions.
                // Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Up.
                x2 = x;
                y2 = y;
                while (checkExists(x2, ++y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Down
                x2 = x;
                y2 = y;
                while (checkExists(x2, --y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                break;
            case BISHOP:
                // Let's check the four diagonal directions.
                // Up-Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, ++y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Up-Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, ++y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Down-Left
                x2 = x;
                y2 = y;
                while (checkExists(--x2, --y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Down-Right
                x2 = x;
                y2 = y;
                while (checkExists(++x2, --y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                break;
            case ROOK:
                // Let's check the four cardinal directions.
                // Left.
                x2 = x;
                y2 = y;
                while (checkExists(--x2, y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Right.
                x2 = x;
                y2 = y;
                while (checkExists(++x2, y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Up.
                x2 = x;
                y2 = y;
                while (checkExists(x2, ++y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                // Down
                x2 = x;
                y2 = y;
                while (checkExists(x2, --y2)) {
                    if (scanField(piece, x, y, x2, y2, output)) break;
                }
                break;
        }
        return output;
    }
}
