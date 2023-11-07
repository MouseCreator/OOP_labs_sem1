package univ.lab.problem1.server;

import org.junit.jupiter.api.Test;
import univ.lab.problem1.model.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    private String read(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Test
    void save() {
        String file = "src/test/resources/file.txt";
        FileManager fileManager = new FileManager(file);
        fileManager.reset();
        Model model = new Model();
        model.setId(1);
        model.setName("Model");
        model.setDescription("Desc");
        fileManager.save(model);
        String result = read(file);
        assertEquals("Model { 1 Model Desc }\n", result);
    }
}