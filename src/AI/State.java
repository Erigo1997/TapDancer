package AI;

import Enumerators.COLOR;
import Game.Move;

public class State {
    // TODO: Reconsider - do states really need to contain moves?
    Move move;
    float alpha, beta;
    int depth;
    COLOR turnColor;
}
