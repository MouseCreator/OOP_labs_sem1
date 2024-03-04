package univ.lab.problem1.client;

import univ.lab.problem1.model.ModelGenerator;

public class ClientMain {
    public static void main(String[] args) {
        ModelGenerator generator = new ModelGenerator();
        Client client = new Client(generator.generate());
        client.runClient();
    }
}
