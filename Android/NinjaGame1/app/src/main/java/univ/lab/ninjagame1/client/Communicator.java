package univ.lab.ninjagame1.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Communicator {
    private final Socket socket;
    private PrintWriter out;
    private final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    public Communicator(Socket socket) {
        this.socket = socket;
    }
    public void start() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            processQueue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processQueue() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String message = senderQueue.take();
                out.println(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void send(MovementParams movementParams) {
        String s = transformParams(movementParams);
        try {
            senderQueue.put(s);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String transformParams(MovementParams movementParams) {
        return String.format(Locale.ENGLISH, "%f %f %f %d", movementParams.getxAngle(),
                movementParams.getyAngle(), movementParams.getzAngle(), movementParams.getSpeed());
    }

    public void sendInput(String input) {
        try {
            senderQueue.put(input);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopConnection() {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
