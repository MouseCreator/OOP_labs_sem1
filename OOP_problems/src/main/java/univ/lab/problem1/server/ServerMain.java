package univ.lab.problem1.server;

public class ServerMain {
    public static final String filename = "src/main/resources/filebase/database.txt";
    public static void main(String[] args) {
        Server server = new Server(filename);
        ProcessCreator.startClient();
        server.runServer();
    }
}
