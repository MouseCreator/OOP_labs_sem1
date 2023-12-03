package univ.lab.ninjagame1.client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;
import univ.lab.ninjagame1.util.JSONUtil;

public class SocketCommunicator implements Communicator {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
    private Thread senderThread;
    private Thread receiverThread;
    private final Object obj = new Object();
    public SocketCommunicator() {
    }
    public void start() {
        senderThread = new Thread(this::prepareAndProcess);
        senderThread.start();
        receiverThread = new Thread(this::receiveAndProcess);
        receiverThread.start();
    }

    private void receiveAndProcess() {
        System.out.println("Receive thread");
        try {
            String inputLine;
            while (socket == null) {
                synchronized (obj) {
                    obj.wait();
                }
            }
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                processReceived(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private void processReceived(String message) {
        DesktopDTO desktopDTO = JSONUtil.fromJson(message);
        onReceive.accept(desktopDTO);
    }

    private void prepareAndProcess() {
        try {
            socket = new Socket("192.168.1.102", 6666);
            out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (obj) {
                obj.notifyAll();
            }

            Log.d("COMM", "Socket Connected Successfully!");
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
    public void send(MobileDTO mobileDTO) {
        toJson(mobileDTO);
    }

    private void toJson(MobileDTO mobileDTO) {
        String s = JSONUtil.toJson(mobileDTO);
        System.out.println(s);
        sendInput(s);
    }

}
