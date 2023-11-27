package univ.lab.ninjagame1.client;

import java.io.IOException;
import java.net.Socket;

public class ClientManager {
    private Thread socketThread;
    private Communicator communicator;

    public Communicator start() {
        try {
            Socket socket = new Socket("127.0.0.1", 7777);
            communicator = new Communicator(socket);
            socketThread = new Thread(() -> communicator.start());
            return communicator;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stop() {
        socketThread.interrupt();
        communicator.stopConnection();
    }
}
