package univ.lab.problem1.server;

import java.io.IOException;

public class ProcessCreator {
    public static void startClient() {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "out/artifacts/OOP_problems_jar/OOP_problems.jar");
        try {
            processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
