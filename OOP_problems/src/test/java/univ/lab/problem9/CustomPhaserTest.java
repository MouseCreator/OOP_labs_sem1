package univ.lab.problem9;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomPhaserTest {

    private record Worker(CustomPhaser phaser, String name, int waitTime, PrintStream printStream) implements Runnable {
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
                    Thread.sleep(waitTime);

                    printStream.println(name + " arrived.");
                    int arrive = phaser.arrive();
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Thread thread1 = new Thread(new Worker(phaser, "W1", 180, printStream));
        Thread thread2 = new Thread(new Worker(phaser, "W2", 220, printStream));
        Thread thread3 = new Thread(new Worker(phaser, "W3", 300, printStream));
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();

            String output = outputStream.toString();
            System.out.println(output);
            String expected = """
                    W1 arrived.
                    W2 arrived.
                    W3 arrived.
                    W1 advanced. Phase: 0
                    W2 advanced. Phase: 0
                    W3 advanced. Phase: 0
                    W1 arrived.
                    W2 arrived.
                    W3 arrived.
                    W2 advanced. Phase: 1
                    W1 advanced. Phase: 1
                    W3 advanced. Phase: 1
                    W1 arrived.
                    W2 arrived.
                    W3 arrived.
                    W2 advanced. Phase: 2
                    W1 advanced. Phase: 2
                    W3 advanced. Phase: 2
                    """;
            expected = reformat(expected);
            output = reformat(output);
            assertEquals(expected, output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String reformat(String str) {
        return str.replace("W1", "W").replace("W2", "W").
                replace("W3", "W").replace("\r\n", "\n");
    }
}