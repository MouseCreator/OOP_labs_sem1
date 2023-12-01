package univ.lab.ninjagame1.filtered;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationManager {
    private Vector3 currentVector = null;
    public Vector3 getCurrentVector() {
        return currentVector;
    }
    private final Context context;

    public OrientationManager(Context context) {
        this.context = context;
    }

    private void recalibrate() {
        currentVector = null;
        orientationCalculator.resetVectors();
    }

    public void start() {
        create();
        resumeSensors();
    }
    public void stop() {
        pauseSensors();
    }
    private void destroyAll() {
        mGyroscope = null;
        mAccelerometer = null;
        mMagnetic = null;
        orientationCalculator = null;
        mSensorManager = null;
        magneticWrapper = null;
        accelerometerWrapper = null;
        gyroscopeWrapper = null;
        magneticEventListener = null;
        accelerometerEventListener = null;
        gyroscopeEventListener = null;
        currentVector = null;
    }
    private Sensor mGyroscope;
    private Sensor mAccelerometer;
    private Sensor mMagnetic;
    private OrientationCalculator orientationCalculator;
    private SensorManager mSensorManager;
    private SensorWrapper magneticWrapper;
    private SensorWrapper accelerometerWrapper;
    private SensorWrapper gyroscopeWrapper;
    private SensorEventListener magneticEventListener;
    private SensorEventListener accelerometerEventListener;
    private SensorEventListener gyroscopeEventListener;

    private void create() {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        advancedGyroListener();
    }

    private void initAccelerometerEventListener() {
        accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                accelerometerWrapper.update(sensorEvent);
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
                currentVector = orientationCalculator.updateAndGet();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void advancedGyroListener() {

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometerWrapper = new SensorWrapper(null);
        magneticWrapper = new SensorWrapper(null);
        gyroscopeWrapper = new SensorWrapper(null);
        initAccelerometerEventListener();
        initGyroscopeEventListener();
        if (mMagnetic == null) {
            initMagneticEventListener();
            orientationCalculator = new MagneticOrientationCalculator();
            orientationCalculator.init(accelerometerWrapper, magneticWrapper, gyroscopeWrapper);
        } else {
            orientationCalculator = new RegularOrientationCalculator();
            orientationCalculator.init(accelerometerWrapper, magneticWrapper, gyroscopeWrapper);
        }

    }
    private void initMagneticEventListener() {
        magneticEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                magneticWrapper.update(sensorEvent);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void resumeSensors() {
        mSensorManager.registerListener(gyroscopeEventListener, mGyroscope, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(accelerometerEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(magneticEventListener, mMagnetic, SensorManager.SENSOR_DELAY_GAME);
    }
    private void pauseSensors() {
        mSensorManager.unregisterListener(accelerometerEventListener);
        mSensorManager.unregisterListener(gyroscopeEventListener);
        mSensorManager.unregisterListener(magneticEventListener);
    }

    public SensorManager sensorManager() {
        return mSensorManager;
    }
}
