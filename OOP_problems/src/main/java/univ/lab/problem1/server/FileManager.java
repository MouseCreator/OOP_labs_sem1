package univ.lab.problem1.server;


import univ.lab.problem1.model.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private final String file;
    public FileManager(String file) {
        this.file = file;
    }

    private String convertModel(Model model) {
        return "Model { " + model.getId() + " " +
                model.getName() + " " + model.getDescription() + "\n";
    }
    public synchronized void save(Model model) {
        String str = convertModel(model);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void reset() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
