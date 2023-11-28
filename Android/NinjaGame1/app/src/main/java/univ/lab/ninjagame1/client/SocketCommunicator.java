package univ.lab.ninjagame1.client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;
import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.util.JSONUtil;

public class SocketCommunicator implements Communicator {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private Thread senderThread;
    private Thread receiverThread;
    public SocketCommunicator() {
    }
    public void start() {
        senderThread = new Thread(this::prepareAndProcess);
        senderThread.start();
    }

    private void receiveAndProcess() {
        System.out.println("Receive thread");
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String p = inputLine;
                Thread thread = new Thread(() -> processReceived(p));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processReceived(String message) {
        DesktopDTO desktopDTO = JSONUtil.fromJson(message);
        onReceive.accept(desktopDTO);
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
                System.out.println(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static final int SHURIKEN_MESSAGE = 0;
    public static final int RECORD_MESSAGE = 1;
    public void send(MovementParams movementParams) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(SHURIKEN_MESSAGE);
        mobileDTO.setVectorData(transformParams(movementParams));
        toJson(mobileDTO);
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
    private Consumer<DesktopDTO> onReceive = t -> {};
    @Override
    public void onReceive(Consumer<DesktopDTO> consumer) {
        onReceive = consumer;
    }

    @Override
    public void sendMessageType(List<Vector3> recordedVector) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(RECORD_MESSAGE);
        mobileDTO.setVectorData(transformRecords(recordedVector));
        toJson(mobileDTO);
    }

    @Override
    public void sendMessageType(int type) {
        MobileDTO mobileDTO = new MobileDTO();
        mobileDTO.setMessageType(type);
        toJson(mobileDTO);
    }

    private void toJson(MobileDTO mobileDTO) {
        sendInput(JSONUtil.toJson(mobileDTO));
    }

    private String transformRecords(List<Vector3> recordedVector) {
        int size = recordedVector.size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            Vector3 v =recordedVector.get(i);
            builder.append(v.x()).append(" ").append(v.y()).append(" ").append(v.z());
            if (i != size-1)
                builder.append(",");
        }
        return builder.toString();
    }
}
