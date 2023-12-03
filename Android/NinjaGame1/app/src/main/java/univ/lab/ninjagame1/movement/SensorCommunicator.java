package univ.lab.ninjagame1.movement;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.movement.subs.SensorPublisher;
import univ.lab.ninjagame1.movement.subs.SensorSubscriber;
import univ.lab.ninjagame1.movement.subs.Topic;

public class SensorCommunicator implements SensorPublisher {
    private final HashMap<Topic, List<SensorSubscriber>> topicSubsMap;

    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor magneticField;
    private SensorEventListener accelerometerListener;
    private SensorEventListener gyroscopeListener;
    private SensorEventListener magneticListener;
    private final SensorManager sensorManager;

    public SensorCommunicator(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        topicSubsMap = new HashMap<>();
        topicSubsMap.put(Topic.ACCELEROMETER, new ArrayList<>());
        topicSubsMap.put(Topic.GYROSCOPE, new ArrayList<>());
        topicSubsMap.put(Topic.MAGNETIC_FIELD, new ArrayList<>());
    }

    @Override
    public void acceptSubscriber(SensorSubscriber sensorSubscriber) {
        List<SensorSubscriber> sensorSubscribers = topicSubsMap.get(sensorSubscriber.getTopic());
        if (sensorSubscribers == null) {
            return;
        }
        sensorSubscribers.add(sensorSubscriber);
    }
    public void init() {
        createSensors();
        createListeners();
        register();
    }
    private void sendAll(Topic topic, SensorEvent sensorEvent) {
        Vector3 v = new Vector3(sensorEvent.values);
        List<SensorSubscriber> sensorSubscribers = topicSubsMap.get(topic);
        if (sensorSubscribers == null) {
            return;
        }
        for (SensorSubscriber sensorSubscriber : sensorSubscribers) {
            sensorSubscriber.onUpdate(v);
        }
    }
    private void createListeners() {
        magneticListener = createListenerForTopic(Topic.MAGNETIC_FIELD);
        accelerometerListener = createListenerForTopic(Topic.ACCELEROMETER);
        gyroscopeListener = createListenerForTopic(Topic.GYROSCOPE);
    }

    private SensorEventListener createListenerForTopic(Topic topic) {
       return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                sendAll(topic, sensorEvent);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void createSensors() {
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void stop() {
        unregister();
    }
    private void register() {
        sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(magneticListener, magneticField, SensorManager.SENSOR_DELAY_GAME);
    }

    private void unregister() {
        sensorManager.unregisterListener(gyroscopeListener);
        sensorManager.unregisterListener(accelerometerListener);
        sensorManager.unregisterListener(magneticListener);
    }
}
