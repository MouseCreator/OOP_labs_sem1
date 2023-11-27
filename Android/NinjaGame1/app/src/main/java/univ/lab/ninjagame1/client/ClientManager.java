package univ.lab.ninjagame1.client;

import java.io.IOException;
import java.net.Socket;

public class ClientManager {


    public Communicator start() {
        try {
            Socket socket = new Socket("127.0.0.1", 7777);
            Communicator communicator = new Communicator(socket);
            communicator.start();
            return communicator;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
