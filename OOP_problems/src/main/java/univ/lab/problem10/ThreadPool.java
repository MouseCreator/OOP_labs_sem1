package univ.lab.problem10;

public interface ThreadPool {
    void submit(Runnable task);
    void shutdown();
}

