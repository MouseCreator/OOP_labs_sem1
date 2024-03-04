package org.example.log;

import org.example.utils.FileManager;

import java.io.IOException;

public class FileLogger implements ULogger{
    private static final String filename = "src/main/resources/logs/log.txt";
    private final FileManager fileManager;
    public FileLogger() {
        fileManager = new FileManager(filename);
    }
    @Override
    public void write(Object obj) {
        write(obj.toString());
    }

    @Override
    public void write(String str) {
        try {
            fileManager.append(str + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart() {
        try {
            fileManager.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
