package org.example.game.timer;

public class Timer {
    private boolean started = false;
    private boolean finished = false;
    private long finishTime = 0;
    public boolean finished() {
        if (finished) {
            return true;
        }
        if (!started) {
            return false;
        }
        if (System.currentTimeMillis() > finishTime) {
            finished = true;
        }
        return finished;
    }

    public void runFor(int timeMillis) {
        long startTime = System.currentTimeMillis();
        finishTime = startTime + timeMillis;
        started = true;
    }

    public void reset() {
        started = false;
        finished = false;
        finishTime = 0;
    }
    public boolean started() {
        return started;
    }
}
