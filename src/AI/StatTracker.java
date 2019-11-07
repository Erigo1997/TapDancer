package AI;

public class StatTracker {

    private static StatTracker statTracker;
    public int iterations;
    public int[] depthIterations;


    public void resetDepthIterations() {
        depthIterations = new int[10];
        for (int i = 0; i < depthIterations.length; i++) {
            depthIterations[i] = 0;
        }
    }

    public static StatTracker getInstance() {
        if (statTracker == null) {
            statTracker = new StatTracker();
        }
        return statTracker;
    }

}
