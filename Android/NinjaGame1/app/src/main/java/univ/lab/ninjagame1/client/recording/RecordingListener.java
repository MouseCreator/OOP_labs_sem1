package univ.lab.ninjagame1.client.recording;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;

public class RecordingListener {
    private List<Vector3> accelerometerData = new ArrayList<>();
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                Vector3 acceleration = new Vector3(x, y, z);
                accelerometerData.add(acceleration);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void register(SensorManager sensorManager) {
        accelerometerData = new ArrayList<>();
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    public void unregister(SensorManager sensorManager) {
        sensorManager.unregisterListener(sensorEventListener);
    }

    public List<Vector3> getRecordings() {
        return accelerometerData;
    }
}
