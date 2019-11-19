package AI;

import Enumerators.COLOR;
import Game.Move;

public class State {
    // TODO: Reconsider - do states really need to contain moves?
    public Move move;
    float alpha, beta;
    int depth;
    COLOR turnColor;
}
