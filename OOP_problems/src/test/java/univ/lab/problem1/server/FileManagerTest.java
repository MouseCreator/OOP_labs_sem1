package univ.lab.problem1.server;

import org.junit.jupiter.api.Test;
import univ.lab.problem1.model.Model;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    void save() {
        FileManager fileManager = new FileManager("src/test/resources/file.txt");
        Model model = new Model();
        model.setId(1);
        model.setName("Model");
        model.setDescription("Desc");
        fileManager.save(model);
    }
}