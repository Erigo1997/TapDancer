package AI;

public class StatTracker {

    private static StatTracker statTracker;
    public int iterations;
    public int[] depthIterations;
    public float[] differenceIterations;
    public static final int[] defaultIterations = {1, 20, 400, 9580, 218120, 5923997, 149203380}; // How many iterations it took without evaluation/pruning.

    public long startTime;

    public void resetDepthIterations() {
        depthIterations = new int[AIMain.maxDepth + 1];
    }

    // TODO: Method to compare depthIterations against DefaultIterations.

    public static StatTracker getInstance() {
        if (statTracker == null) {
            statTracker = new StatTracker();
        }
        return statTracker;
    }

    public void compare() {
        int shortestArray;
        if (defaultIterations.length < depthIterations.length)
            shortestArray = defaultIterations.length;
        else
            shortestArray = depthIterations.length;
        differenceIterations = new float[shortestArray];
        for (int i = 0; i < shortestArray; i++) {
            differenceIterations[i] = ((float) defaultIterations[i])/((float) depthIterations[i]);
            differenceIterations[i] *= 100;
            differenceIterations[i] -= 100;
        }
    }

    public void resetTime() {
        startTime = System.nanoTime();
    }

    public long getTime() {
        return (System.nanoTime() - startTime) / 1000000000;
    }
}
