package univ.lab.problem1.server;

import univ.lab.problem1.model.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public Server(String file) {
        fileManager = new FileManager(file);
    }
    private final FileManager fileManager;
    public void runServer() {
        try(ServerSocket serverSocket = new ServerSocket(15777)) {
            Socket clientSocket = serverSocket.accept();
            processClient(clientSocket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processClient(Socket clientSocket) throws IOException {

        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
        try {
            Object object = inputStream.readObject();
            Model model = (Model) object;
            save(model);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clientSocket.close();
    }

    private void save(Model model) {
        fileManager.save(model);
    }
}
