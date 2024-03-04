package univ.lab.problem6;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomMichaelScottQueueTest {
    private static class Producer implements Runnable {
        private final List<Integer> produced = new ArrayList<>();
        private final CustomMichaelScottQueue<Integer> queue;
        private Producer(CustomMichaelScottQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                produced.add(i);
                queue.enqueue(i);
            }
        }

        public List<Integer> getProduced() {
            return produced;
        }
    }

    private static class Consumer implements Runnable {

        private final List<Integer> consumed = new ArrayList<>();
        private final CustomMichaelScottQueue<Integer> queue;
        private Consumer(CustomMichaelScottQueue<Integer> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                Optional<Integer> dequeue = queue.dequeue();
                if (dequeue.isEmpty()) {
                    i--;
                    continue;
                }
                consumed.add(dequeue.get());
            }
        }

        public List<Integer> getConsumed() {
            return consumed;
        }
    }

    @Test
    void simulation() {
        CustomMichaelScottQueue<Integer> queue = new CustomMichaelScottQueue<>();
        Producer producer1 = new Producer(queue);
        Thread pThread1 = new Thread(producer1);

        Producer producer2 = new Producer(queue);
        Thread pThread2 = new Thread(producer2);

        Consumer consumer1 = new Consumer(queue);
        Thread cThread1 = new Thread(consumer1);

        Consumer consumer2 = new Consumer(queue);
        Thread cThread2 = new Thread(consumer2);

        pThread1.start();
        pThread2.start();
        cThread1.start();
        cThread2.start();

        try {
            pThread1.join();
            pThread2.join();
            cThread1.join();
            cThread2.join();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(consumer1.getConsumed().size()+consumer2.getConsumed().size(),
                producer1.getProduced().size() + producer2.getProduced().size());
    }
}