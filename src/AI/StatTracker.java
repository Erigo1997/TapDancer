package AI;

public class StatTracker {

    private static StatTracker statTracker;
    public int iterations;
    public int[] depthIterations;
    public static final int[] defaultIterations = {1, 20, 400, 9580, 218120, 5923997, 149203380}; // How many iterations it took without evaluation/pruning.


    public void resetDepthIterations() {
        depthIterations = new int[10];
        for (int i = 0; i < depthIterations.length; i++) {
            depthIterations[i] = 0;
        }
    }

    // TODO: Method to compare depthIterations against DefaultIterations.

    public static StatTracker getInstance() {
        if (statTracker == null) {
            statTracker = new StatTracker();
        }
        return statTracker;
    }

}
