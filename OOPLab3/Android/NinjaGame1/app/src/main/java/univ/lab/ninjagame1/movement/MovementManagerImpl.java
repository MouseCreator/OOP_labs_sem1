package univ.lab.ninjagame1.movement;

import android.hardware.SensorManager;

import java.util.List;

import univ.lab.ninjagame1.filtered.OrientationManager;
import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.movement.record.DataRecorder;

public class MovementManagerImpl implements MovementManager {

    private final DataRecorder dataRecorder;
    private final SensorCommunicator sensorCommunicator;
    private final OrientationManager orientationManager;
    public MovementManagerImpl(SensorManager sensorManager) {
        sensorCommunicator = new SensorCommunicator(sensorManager);
        orientationManager = new OrientationManager();
        dataRecorder = new DataRecorder(sensorCommunicator);
        sensorCommunicator.init();
    }
    @Override
    public Vector3 getCurrentOrientation() {
        return orientationManager.getCurrentVector();
    }

    @Override
    public void resetOrientation() {
        orientationManager.recalibrate();
    }

    @Override
    public void startAccelerometerRecording() {
        dataRecorder.startRecording();
    }

    @Override
    public List<Vector3> stopAccelerometerRecording() {
        return dataRecorder.finishRecording();
    }

    @Override
    public void begin() {
        sensorCommunicator.start();
        orientationManager.start(sensorCommunicator);
    }

    @Override
    public void stop() {
        sensorCommunicator.stop();
        orientationManager.stop();
        dataRecorder.finishRecording();
    }
}
