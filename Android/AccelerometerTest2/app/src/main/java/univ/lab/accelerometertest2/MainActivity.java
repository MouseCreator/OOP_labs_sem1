package univ.lab.accelerometertest2;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtCurrentAcceleration, txtPrevAcceleration, txtAcceleration;
    private ProgressBar progressBarShakeMeter;
    private SensorEventListener sensorEventListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double accelerationCurrentValue = 0;
    private double accelerationPreviousValue = 0;
    private double changeOfAccelerationValue = 0;
    private long lastUpdateTime = 0;
    private Vector3 currentPosition = new Vector3(0, 0, 0);
    private Vector3 accel = null;
    private Vector3 velocity = new Vector3(0, 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAcceleration = findViewById(R.id.txt_accel);
        txtCurrentAcceleration = findViewById(R.id.txt_currentAccel);
        txtPrevAcceleration = findViewById(R.id.txt_prevAccel);
        progressBarShakeMeter = findViewById(R.id.prog_shakeMeter);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        progressBarShakeMeter.setProgress((int) changeOfAccelerationValue);
        initSensorEventListener();
    }

    private void initSensorEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                Vector3 acceleration = new Vector3(x, y, z);
                if (accel == null) {
                    accel = acceleration;
                } else {
                    Vector3 temp = new Vector3(accel);
                    accel = new Vector3(acceleration);
                    acceleration = acceleration.subtract(temp);
                }
                long currentTime = System.currentTimeMillis();
                float deltaTime = (currentTime - lastUpdateTime) / 1000.f; // Convert to seconds

                velocity = velocity.add(acceleration.multiply(deltaTime));
                currentPosition = currentPosition.add(velocity.multiply(deltaTime));
                lastUpdateTime = currentTime;

                txtAcceleration.setText(String.format(Locale.ENGLISH, "X = %.2f", acceleration.x()));
                txtPrevAcceleration.setText(String.format(Locale.ENGLISH, "Y = %.2f", acceleration.y()));
                txtCurrentAcceleration.setText(String.format(Locale.ENGLISH, "Z = %.2f", acceleration.z()));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}