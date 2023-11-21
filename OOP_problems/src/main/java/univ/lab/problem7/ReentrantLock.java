package univ.lab.problem7;

public class ReentrantLock {
    private final Object obj = new Object();
    private Thread owner = null;
    public void lock() throws InterruptedException {
        synchronized (obj) {
            if (owner == Thread.currentThread())
                return;
            while (owner != null) {
                obj.wait();
            }
            owner = Thread.currentThread();
        }
    }

    public void unlock() {
        synchronized (obj) {
            if (owner != Thread.currentThread()) {
                throw new IllegalStateException("Current thread is not the owner of the lock!");
            }
            owner = null;
            obj.notify();
        }
    }
}
