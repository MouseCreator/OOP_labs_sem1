package univ.lab.ninjagame1.client.recording;

import java.util.ArrayList;
import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;

public class RecordingManager {
    private List<Vector3> vectors = new ArrayList<>();
    private boolean isRecording = false;
    public void start() {
        isRecording = true;
    }
    public void append(Vector3 data) {
        vectors.add(data);
    }
    public List<Vector3> summarize() {
        List<Vector3> temp = vectors;
        vectors = new ArrayList<>();
        isRecording = false;
        return temp;

    }

    public boolean isRecording() {
        return isRecording;
    }
}
