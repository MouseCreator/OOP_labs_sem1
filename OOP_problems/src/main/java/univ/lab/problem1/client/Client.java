package univ.lab.problem1.client;

import univ.lab.problem1.model.Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 7777)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Model model = new Model();
            model.setId(1);
            model.setName("Hello");
            model.setDescription("Hello to server");
            outputStream.writeObject(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
