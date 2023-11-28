package org.example.dtw.recorder;

import org.example.utils.FileManager;
import org.example.vector.Vector3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestureManager {

    private final String filename = "src/main/resources/gestures/gestures.txt";

    private final FileManager fileManager = new FileManager(filename);
    public List<Recording> readRecordings() {
        try {
            String recordings = fileManager.read();
            return parseRecordings(recordings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Recording> parseRecordings(String recordings) {
        String[] values = recordings.split("\n");
        List<Recording> result = new ArrayList<>();
        for (String v : values) {
            v = v.trim();
            if (v.isEmpty())
                continue;
            String[] keyValue = v.split(":", 2);
            if (keyValue.length != 2)
                continue;
            String tag = keyValue[0].trim();
            String[] vectors = keyValue[1].trim().split(",");
            List<Vector3D> v3d = new ArrayList<>();
            for (String vector : vectors) {
                String[] vs = vector.split(" ");
                double x = Double.parseDouble(vs[0]);
                double y = Double.parseDouble(vs[1]);
                double z = Double.parseDouble(vs[2]);
                v3d.add(Vector3D.get(x, y, z));
            }
            result.add(new Recording(tag, v3d));
        }
        return result;
    }
    private String convertRecording(Recording recording) {
        StringBuilder builder = new StringBuilder(recording.getTag() + ":");
        int size = recording.getValues().size();
        for (int i = 0; i < size; i++) {
            Vector3D v = recording.getValues().get(i);
            builder.append(v.x()).append(" ").append(v.y()).append(" ").append(v.z());
            if (i != size-1)
                builder.append(",");
        }
        return builder.toString();
    }
    public void appendRecording(Recording recording) {
        String recordingString = convertRecording(recording) + "\n";
        try {
            fileManager.append(recordingString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
