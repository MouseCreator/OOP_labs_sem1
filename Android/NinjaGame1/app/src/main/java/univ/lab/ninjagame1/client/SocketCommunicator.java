package univ.lab.ninjagame1.client;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketCommunicator implements Communicator {
    private Socket socket;
    private PrintWriter out;
    private final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private Thread socketThread;
    public SocketCommunicator() {
    }
    public void start() {
        socketThread = new Thread(this::prepareAndProcess);
        socketThread.start();
    }
    private void prepareAndProcess() {
        try {
            socket = new Socket("192.168.1.102", 6666);
            Log.d("COMM", "Socket Connected Successfully!");
            out = new PrintWriter(socket.getOutputStream(), true);
            processQueue();
        } catch (IOException e) {
            e.printStackTrace();
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
