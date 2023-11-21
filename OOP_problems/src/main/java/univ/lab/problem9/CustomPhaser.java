package univ.lab.problem9;

public class CustomPhaser {
    private final int parties;
    private int currentPhase;
    private int reachedCount;
    private final Object obj = new Object();
    public CustomPhaser(int parties) {
        this.parties = parties;
    }

    public int arrive() {
        synchronized (obj) {
            reachedCount++;
            if (reachedCount == parties) {
                reachedCount = 0;
                obj.notifyAll();
                return currentPhase++;
            }
        }
        return currentPhase;
    }

    public int getPhase() {
        synchronized (obj) {
            return currentPhase;
        }
    }

    public void awaitAdvance(int phase) throws InterruptedException {
        synchronized (obj) {
            while (phase >= currentPhase) {
                obj.wait();
            }
        }
    }

    public void arriveAndAwaitAdvance() throws InterruptedException {
        int phase = arrive();
        awaitAdvance(phase);
    }
}
