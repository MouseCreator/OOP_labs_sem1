package univ.lab.ninjagame1.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketCommunicator implements Communicator {
    private final Socket socket;
    private PrintWriter out;
    private final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private Thread socketThread;
    public SocketCommunicator(Socket socket) {
        this.socket = socket;
    }
    public void start() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            socketThread = new Thread(this::processQueue);
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
        sendInput(s);
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
            socketThread.interrupt();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
