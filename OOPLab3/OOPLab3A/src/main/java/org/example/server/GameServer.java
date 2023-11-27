package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameServer {
    private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
    private boolean running = false;
    public void start(int port) {
        if (running)
            return;
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket client = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(client, messageQueue);
            clientHandler.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    private static class ClientHandler {
        private final Socket clientSocket;
        private BufferedReader in;
        private final ConcurrentLinkedQueue<String> messageQueue;
        public ClientHandler(Socket socket, ConcurrentLinkedQueue<String> queue) {
            this.clientSocket = socket;
            this.messageQueue = queue;
        }

        public void process() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    messageQueue.offer(inputLine);
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
        }
    }

    public ConcurrentLinkedQueue<String> getMessageQueue() {
        return messageQueue;
    }
}
