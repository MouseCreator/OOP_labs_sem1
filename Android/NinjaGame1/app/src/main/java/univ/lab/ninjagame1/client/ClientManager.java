package univ.lab.ninjagame1.client;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class ClientManager {

    public Communicator start() {
        try {
            Socket socket = new Socket("128.0.175.19", 6666);
            Log.d("COMM", "Connected!");
            SocketCommunicator communicator = new SocketCommunicator(socket);
            communicator.start();
            return communicator;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MockCommunicator();
    }

}
