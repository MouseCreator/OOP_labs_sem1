package univ.lab.ninjagame1.client.recording;

import android.hardware.SensorManager;

import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;

public class RecordingManager {
    private final RecordingListener recordingListener = new RecordingListener();
    private boolean isRecording = false;
    public void start(SensorManager sensorManager) {
        isRecording = true;
        recordingListener.register(sensorManager);
    }
    public List<Vector3> summarize(SensorManager sensorManager) {
        isRecording = false;
        recordingListener.unregister(sensorManager);
        return recordingListener.getRecordings();

    }
    public boolean isRecording() {
        return isRecording;
    }
}
