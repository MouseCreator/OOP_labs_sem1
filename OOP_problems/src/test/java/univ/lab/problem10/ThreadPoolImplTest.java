package univ.lab.problem10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreadPoolImplTest {

    private record SimpleTask(CountDownLatch latch) implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();

        }
    }

    private record InterruptedTask(CountDownLatch latch) implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            latch.countDown();

        }
    }

    private ThreadPool threadPool;

    @BeforeEach
    void setUp() {
        threadPool = new ThreadPoolImpl(4);
    }

    @Test
    void submit() {
        int nTasks = 20;
        CountDownLatch latch = new CountDownLatch(nTasks);
        for (int i = 0; i < nTasks; i++) {
            threadPool.submit(new SimpleTask(latch));
        }
        try {
            boolean await = latch.await(1, TimeUnit.SECONDS);
            assertTrue(await);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shutdown() {
        int nTasks = 4;
        CountDownLatch latch = new CountDownLatch(nTasks);
        for (int i = 0; i < nTasks; i++) {
            threadPool.submit(new InterruptedTask(latch));
        }

        try {
            threadPool.shutdown();
            boolean await = latch.await(1, TimeUnit.SECONDS);
            assertTrue(await);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}