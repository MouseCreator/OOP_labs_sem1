package org.example.server;

import org.example.dto.DesktopDTO;
import org.example.dto.MobileDTO;
import org.example.utils.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameServer {
    private final ConcurrentLinkedQueue<MobileDTO> messageQueue = new ConcurrentLinkedQueue<>();
    private final BlockingQueue<DesktopDTO> sendQueue = new LinkedBlockingQueue<>();
    private boolean running = false;
    private final Object object = new Object();
    private Socket client;
    public void start(int port) {
        if (running)
            return;
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            client = serverSocket.accept();
            synchronized (object) {
                object.notifyAll();
            }
            ClientHandler clientHandler = new ClientHandler(client, messageQueue);
            clientHandler.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    public BlockingQueue<DesktopDTO> getSendQueue() {
        return sendQueue;
    }

    public void startWriter() {
        ClientSender clientHandler = new ClientSender(sendQueue);
        clientHandler.process();
    }

    private static class ClientHandler {
        private final Socket clientSocket;
        private BufferedReader in;
        private final ConcurrentLinkedQueue<MobileDTO> messageQueue;
        public ClientHandler(Socket socket, ConcurrentLinkedQueue<MobileDTO> queue) {
            this.clientSocket = socket;
            this.messageQueue = queue;
        }

        public void process() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    messageQueue.offer(JSONUtil.fromJSON(inputLine));
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Exited receiver");
        }
    }

    private class ClientSender {
        private PrintWriter out;
        private final BlockingQueue<DesktopDTO> sendQueue;
        public ClientSender(BlockingQueue<DesktopDTO> sendQueue) {
            this.sendQueue = sendQueue;
        }

        public void process() {
            synchronized (object) {
                while (client == null) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                while (!Thread.currentThread().isInterrupted()) {
                    String json = JSONUtil.toJSON(sendQueue.take());
                    System.out.println(json);
                    out.println(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                try {
                    out.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Exited sender");
        }
    }

    public ConcurrentLinkedQueue<MobileDTO> getMessageQueue() {
        return messageQueue;
    }
}
