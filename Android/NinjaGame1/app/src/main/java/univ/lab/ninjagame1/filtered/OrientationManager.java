package univ.lab.ninjagame1.filtered;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import univ.lab.ninjagame1.movement.SensorCommunicator;
import univ.lab.ninjagame1.movement.subs.SensorSubscriber;
import univ.lab.ninjagame1.movement.subs.Topic;

public class OrientationManager implements SensorSubscriber {
    private final AtomicReference<Vector3> currentVector = new AtomicReference<>(null);
    private SensorCommunicator sensorCommunicator;
    public Vector3 getCurrentVector() {
        return currentVector.get();
    }
    public OrientationManager() {
        initWrappers();
    }
    public void recalibrate() {
        currentVector.set(null);
        orientationCalculator.resetVectors();
    }
    public void start(SensorCommunicator sensorCommunicator) {
        this.sensorCommunicator = sensorCommunicator;
        if (sensorCommunicator.magneticAvailable()) {
            orientationCalculator = new MagneticOrientationCalculator(accelerometerWrapper, magneticWrapper, gyroscopeWrapper);
        } else {
            orientationCalculator = new RegularOrientationCalculator(accelerometerWrapper, gyroscopeWrapper);

        }
        sensorCommunicator.acceptSubscriber(this);
    }

    private void initWrappers() {
        magneticWrapper = new SensorWrapper();
        accelerometerWrapper = new SensorWrapper();
        gyroscopeWrapper = new SensorWrapper();
    }

    public void stop() {
        destroyAll();
        sensorCommunicator.deleteSubscriber(this);
    }
    private void destroyAll() {
        orientationCalculator = null;
        magneticWrapper.reset();
        accelerometerWrapper.reset();
        gyroscopeWrapper.reset();
        currentVector.set(null);
    }

    private OrientationCalculator orientationCalculator;
    private SensorWrapper magneticWrapper;
    private SensorWrapper accelerometerWrapper;
    private SensorWrapper gyroscopeWrapper;

    @Override
    public void onUpdate(Topic topic, Vector3 newData) {
        switch (topic) {
            case GYROSCOPE:
                gyroscopeWrapper.update(newData);
                currentVector.set(orientationCalculator.updateAndGet());
                break;
            case ACCELEROMETER:
                accelerometerWrapper.update(newData);
                break;
            case MAGNETIC_FIELD:
                magneticWrapper.update(newData);
                break;

        }
    }

    @Override
    public List<Topic> getTopics() {
        List<Topic> topics = new ArrayList<>();
        topics.add(Topic.GYROSCOPE);
        topics.add(Topic.ACCELEROMETER);
        topics.add(Topic.MAGNETIC_FIELD);
        return topics;
    }
}
