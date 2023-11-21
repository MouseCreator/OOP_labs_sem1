package univ.lab.problem9;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;

class CustomPhaserTest {

    private static class Worker implements Runnable {

        private final CustomPhaser phaser;
        private final String name;
        private final int waitTime;

        private final PrintStream printStream;
        private Worker(CustomPhaser phaser, String name, int waitTime, PrintStream printStream) {
            this.phaser = phaser;
            this.name = name;
            this.waitTime = waitTime;
            this.printStream = printStream;
        }

        @Override
        public void run() {
            try {
                work();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void work() throws InterruptedException {
            for (int i = 0; i < 3; i++) {
                printStream.println(name + " before arrival");
                Thread.sleep(waitTime);

                int arrive = phaser.arrive();
                printStream.println(name + " arrived. Phase: " + arrive);
                Thread.sleep(waitTime);

                phaser.awaitAdvance(arrive);
                printStream.println(name + " advanced. Phase: " + arrive);
                Thread.sleep(waitTime);
            }
        }
    }
    @Test
    void simulatePhaser() {
        CustomPhaser phaser = new CustomPhaser(3);
       // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       // PrintStream printStream = new PrintStream(outputStream);
        Thread thread1 = new Thread(new Worker(phaser, "W1", 180, System.out));
        Thread thread2 = new Thread(new Worker(phaser, "W2", 220, System.out));
        Thread thread3 = new Thread(new Worker(phaser, "W3", 300, System.out));
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}