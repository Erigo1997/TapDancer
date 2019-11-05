package AI;

public class StatTracker {

    private static StatTracker statTracker;
    public int iterations;

    public static StatTracker getInstance() {
        if (statTracker == null) {
            statTracker = new StatTracker();
        }
        return statTracker;
    }

}
