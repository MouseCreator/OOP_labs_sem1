package univ.lab.accelerometertest2.filtered;

import android.hardware.SensorEvent;

import univ.lab.accelerometertest2.Vector3;

public class MagneticOrientationCalculator implements OrientationCalculator {
    private final double TRUST_CONST;
    public MagneticOrientationCalculator() {
        TRUST_CONST = 0.8;
    }
    private SensorWrapper accWrapper;
    private SensorWrapper magWrapper;
    private SensorWrapper gyroWrapper;
    public void init(SensorWrapper accWrapper, SensorWrapper magWrapper, SensorWrapper gyroWrapper) {
        this.accWrapper = accWrapper;
        this.magWrapper = magWrapper;
        this.gyroWrapper = gyroWrapper;
    }

    private void processAccelerometerMagnetometer(SensorEvent accValues, SensorEvent magValues, double estimateYaw) {
        float[] acc = accValues.values;
        float ax = acc[0];
        float ay = acc[1];
        float az = acc[2];
        double pitch = Math.atan2(ay, Math.sqrt(ax*ax + az*az));
        double roll = Math.atan2(-ax, az);

        double sinRoll = Math.sin(roll);
        double cosRoll = Math.cos(roll);
        double sinPitch = Math.sin(pitch);
        double cosPitch = Math.cos(pitch);
        double yaw;
        try {
            float[] mag = magValues.values;
            float mx = mag[0];
            float my = mag[1];
            float mz = mag[2];

            yaw = Math.atan2(sinRoll * mx - cosRoll * my,
                    cosPitch * mx + sinPitch * sinRoll * my + sinPitch * cosPitch + mz);
        } catch (NullPointerException e) {
            currentMagnetic = new Vector3(pitch, roll, estimateYaw);
            return;
        }
        currentMagnetic = new Vector3(pitch, roll, yaw);
    }

    private Vector3 currentGyro = Vector3.zero();
    private Vector3 currentMagnetic = Vector3.zero();
    private long lastGyroUpdate = 0;

    private void processGyroscope(float[] gyroValues) {
        float gx = gyroValues[0];
        float gy = gyroValues[1];
        float gz = gyroValues[2];
        double gXNew, gYNew, gZNew;
        if (lastGyroUpdate == 0) {
            gXNew = currentGyro.x();
            gYNew = currentGyro.y();
            gZNew = currentGyro.z();
            lastGyroUpdate = System.currentTimeMillis();
        } else {
            long time = System.currentTimeMillis();
            double dT = (time - lastGyroUpdate) / 1000.0;
            gXNew = currentGyro.x() + gx * dT;
            gYNew = currentGyro.y() + gy * dT;
            gZNew = currentGyro.z() + gz * dT;
            lastGyroUpdate = time;
        }
        currentGyro = new Vector3(gXNew, gYNew, gZNew);
    }
    public Vector3 getOrientation() {
        return currentGyro.multiply(TRUST_CONST).add(currentMagnetic.multiply(1-TRUST_CONST));
    }
    public Vector3 getMagnetic() {
        return currentMagnetic;
    }
    public Vector3 getGyro() {
        return currentGyro;
    }

    public Vector3 updateAndGet() {
        try {
            processAccelerometerMagnetometer(accWrapper.get(), magWrapper.get(), gyroWrapper.get().values[2]);
            processGyroscope(gyroWrapper.get().values);
            return getOrientation();
        } catch (NullPointerException e) {
            return new Vector3(-1, -2, -3);
        }
    }
}
