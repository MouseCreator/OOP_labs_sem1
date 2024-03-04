package org.example.dtw.recorder;

import org.example.vector.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Recorder {
    public Optional<Recording> record(String sequence) {
        try {
            String[] vectors = sequence.split(",");
            List<Vector3D> resultV = new ArrayList<>();
            for (String vector : vectors) {
                String[] values = vector.split(" ");
                double x = Double.parseDouble(values[0]);
                double y = Double.parseDouble(values[1]);
                double z = Double.parseDouble(values[2]);
                resultV.add(Vector3D.get(x, y, z));
            }
            return Optional.of(new Recording("NEW", resultV));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
