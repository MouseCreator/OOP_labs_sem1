package univ.lab.accelerometertest2;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import univ.lab.accelerometertest2.filtered.MagneticOrientationCalculator;
import univ.lab.accelerometertest2.filtered.SensorWrapper;
import univ.lab.accelerometertest2.filtered.Vector3;

public class MainActivity extends AppCompatActivity {

    private TextView[] accelerationTexts;
    private TextView[] velocityTexts;
    private TextView[] positionTexts;
    private Sensor mGyroscope;
    private Sensor mAccelerometer;
    private Sensor mMagnetic;
    private MagneticOrientationCalculator orientationCalculator;
    private SensorManager mSensorManager;

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
        advancedGyroListener();
    }


    private void initAccelerometerEventListener() {
        accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                accelerometerWrapper.update(sensorEvent);
                Vector3 v = new Vector3(sensorEvent.values);
                printVector(accelerationTexts, v);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void initGyroscopeEventListener() {
        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                gyroscopeWrapper.update(sensorEvent);
                Vector3 estimate = orientationCalculator.updateAndGet();
                printVector(positionTexts, estimate);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void advancedGyroListener() {
        orientationCalculator = new MagneticOrientationCalculator();
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometerWrapper = new SensorWrapper(null);
        magneticWrapper = new SensorWrapper(null);
        gyroscopeWrapper = new SensorWrapper(null);
        initAccelerometerEventListener();
        initMagneticEventListener();
        initGyroscopeEventListener();
        orientationCalculator.init(accelerometerWrapper, magneticWrapper, gyroscopeWrapper);
    }
    private void initMagneticEventListener() {
        magneticEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                magneticWrapper.update(sensorEvent);
                Vector3 v = new Vector3(sensorEvent.values);
                printVector(velocityTexts, v);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }
    private SensorWrapper magneticWrapper;
    private SensorWrapper accelerometerWrapper;
    private SensorWrapper gyroscopeWrapper;
    private SensorEventListener magneticEventListener;
    private SensorEventListener accelerometerEventListener;
    private SensorEventListener gyroscopeEventListener;

    private void printVector(TextView[] textFamily, Vector3 vector) {
        textFamily[0].setText(String.format(Locale.ENGLISH, "X = %.2f", vector.x()));
        textFamily[1].setText(String.format(Locale.ENGLISH, "Y = %.2f", vector.y()));
        textFamily[2].setText(String.format(Locale.ENGLISH, "Z = %.2f", vector.z()));
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(gyroscopeEventListener, mGyroscope, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(accelerometerEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(magneticEventListener, mMagnetic, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(accelerometerEventListener);
        mSensorManager.unregisterListener(gyroscopeEventListener);
        mSensorManager.unregisterListener(magneticEventListener);
    }
}