package univ.lab.problem1.client;

import univ.lab.problem1.model.Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final Model myModel;
    public Client() {
        myModel = createModel();
    }
    public Client(Model model) {
        myModel = model;
    }
    public void runClient() {
        try(Socket socket = new Socket("127.0.0.1", 15777)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(myModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Model createModel() {
        Model model = new Model();
        model.setId(1);
        model.setName("Hello");
        model.setDescription("Hello to server");
        return model;
    }
}
