package univ.lab.dtwtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import univ.lab.dtwtest.dtw.GenericDTW;
import univ.lab.dtwtest.dtw.Vector3;
import univ.lab.dtwtest.dtw.VectorDistanceCalculator;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button recordButton;

    private TextView textView;
    private List<Vector3> accelerometerData;
    private List<Vector3> recordedData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        recordButton = findViewById(R.id.recordBtn);
        textView = findViewById(R.id.mainText);
        accelerometerData = new ArrayList<>();

        recordButton.setOnClickListener(v -> {
            if (recordButton.getText().toString().equals("Record")) {
                startRecording();
            } else {
                stopRecording();
            }
        });
    }
    private void startRecording() {
        recordButton.setText("Stop");
        accelerometerData.clear();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopRecording() {
        recordButton.setText("Record");
        if (recordedData == null) {
            textView.setText("Recorded!");
            recordedData = new ArrayList<>(accelerometerData);
        } else {
            GenericDTW<Vector3> dtw = new GenericDTW<>(new VectorDistanceCalculator());
            Vector3[] v1 = new Vector3[recordedData.size()];
            Vector3[] v2 = new Vector3[accelerometerData.size()];
            recordedData.toArray(v1);
            accelerometerData.toArray(v2);
            double ds = dtw.dtwNormalizedDistance(v1, v2);
            textView.setText(String.format(Locale.ENGLISH, "Compared: %f", ds));
        }
        sensorManager.unregisterListener(sensorEventListener);
    }

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
}