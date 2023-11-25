package univ.lab.accelerometertest2;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView[] accelerationTexts;
    private TextView[] velocityTexts;
    private TextView[] positionTexts;
    private SensorEventListener accelerationEventListener;
    private SensorEventListener gravityEventListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGravity;
    private Sensor mGyroscope;
    private Sensor mRotation;
    private Sensor mLinearAcceleration;
    private long lastUpdateTime = 0;
    private Vector3 currentPosition = Vector3.zero();
    private Vector3 velocity = Vector3.zero();
    private Vector3 gravity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerationTexts = new TextView[3];
        accelerationTexts[0] = findViewById(R.id.txt_accel_x);
        accelerationTexts[1] = findViewById(R.id.txt_accel_y);
        accelerationTexts[2] = findViewById(R.id.txt_accel_z);

        velocityTexts = new TextView[3];
        velocityTexts[0] = findViewById(R.id.txt_velocity_x);
        velocityTexts[1] = findViewById(R.id.txt_velocity_y);
        velocityTexts[2] = findViewById(R.id.txt_velocity_z);

        positionTexts = new TextView[3];
        positionTexts[0] = findViewById(R.id.txt_position_x);
        positionTexts[1] = findViewById(R.id.txt_position_y);
        positionTexts[2] = findViewById(R.id.txt_position_z);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        initAccelerometerEventListener();
        initGravityEventListener();
    }

    private void initAccelerometerEventListener() {
        accelerationEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                Vector3 acceleration = new Vector3(x, y, z);
                if (gravity != null) {
                    acceleration = acceleration.divide(gravity);
                }
                long currentTime = System.currentTimeMillis();
                if (lastUpdateTime == 0) {
                    lastUpdateTime = currentTime;
                }
                float deltaTime = (currentTime - lastUpdateTime) / 1000.f;
                velocity = velocity.add(acceleration.multiply(deltaTime));
                currentPosition = currentPosition.add(velocity.multiply(deltaTime));
                lastUpdateTime = currentTime;
                printVector(positionTexts, currentPosition);
                printVector(velocityTexts, velocity);
                printVector(accelerationTexts, acceleration);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void initGravityEventListener() {
        gravityEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                gravity = new Vector3(x, y, z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private Vector3 removeNoise(Vector3 acceleration) {
        final double noiseConst = 0.02;
        double x = Math.abs(acceleration.x()) < noiseConst ? 0 : acceleration.x();
        double y = Math.abs(acceleration.y()) < noiseConst ? 0 : acceleration.y();
        double z = Math.abs(acceleration.z()) < noiseConst ? 0 : acceleration.z();
        return new Vector3(x,y,z);
    }

    private void printVector(TextView[] textFamily, Vector3 vector) {
        textFamily[0].setText(String.format(Locale.ENGLISH, "X = %.2f", vector.x()));
        textFamily[1].setText(String.format(Locale.ENGLISH, "Y = %.2f", vector.y()));
        textFamily[2].setText(String.format(Locale.ENGLISH, "Z = %.2f", vector.z()));
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(accelerationEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(gravityEventListener, mGravity, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(accelerationEventListener);
    }
}