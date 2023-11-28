package univ.lab.ninjagame1.client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;
import univ.lab.ninjagame1.util.JSONUtil;

public class SocketCommunicator implements Communicator {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private final ConcurrentLinkedQueue<String> receiveQueue = new ConcurrentLinkedQueue<>();
    private Thread senderThread;
    private Thread receiverThread;
    public SocketCommunicator() {
    }
    public void start() {
        senderThread = new Thread(this::prepareAndProcess);
        senderThread.start();
    }

    private void receiveAndProcess() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                receiveQueue.offer(inputLine);
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareAndProcess() {
        try {
            socket = new Socket("192.168.1.102", 6666);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.d("COMM", "Socket Connected Successfully!");
            receiverThread = new Thread(this::receiveAndProcess);
            receiverThread.start();
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
    private final int SHURIKEN_MESSAGE = 0;
    public Optional<DesktopDTO> receive() {
        String s = receiveQueue.poll();
        if (s == null) {
            return Optional.empty();
        }
        return Optional.of(JSONUtil.fromJson(s));
    }
    public void send(MovementParams movementParams) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(SHURIKEN_MESSAGE);
        mobileDTO.setVectorData(transformParams(movementParams));
        sendInput(JSONUtil.toJson(mobileDTO));
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
            senderThread.interrupt();
            receiverThread.interrupt();
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
