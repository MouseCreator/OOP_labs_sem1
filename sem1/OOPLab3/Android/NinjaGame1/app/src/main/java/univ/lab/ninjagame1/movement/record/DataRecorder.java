package univ.lab.ninjagame1.movement.record;

import java.util.ArrayList;
import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.movement.SensorCommunicator;
import univ.lab.ninjagame1.movement.subs.SensorSubscriber;
import univ.lab.ninjagame1.movement.subs.Topic;

public class DataRecorder implements SensorSubscriber {
    private final SensorCommunicator sensorCommunicator;
    private final List<Vector3> currentRecording = new ArrayList<>();

    private boolean isRecording = false;

    public DataRecorder(SensorCommunicator sensorCommunicator) {
        this.sensorCommunicator = sensorCommunicator;
    }
    @Override
    public void onUpdate(Topic topic, Vector3 newData) {
        currentRecording.add(newData);
    }

    @Override
    public List<Topic> getTopics() {
        List<Topic> list = new ArrayList<>();
        list.add(Topic.ACCELEROMETER);
        return list;
    }

    public void startRecording() {
        if (isRecording)
            return;
        sensorCommunicator.acceptSubscriber(this);
        isRecording = true;
    }
    public List<Vector3> finishRecording() {
        if (!isRecording)
            return new ArrayList<>();
        sensorCommunicator.deleteSubscriber(this);
        List<Vector3> result = new ArrayList<>(currentRecording);
        currentRecording.clear();
        isRecording = false;
        return result;
    }
}
