package univ.lab.ninjagame1.filtered;

public class MagneticOrientationCalculator implements OrientationCalculator {
    private final double TRUST_CONST;

    private final SensorWrapper accWrapper;
    private final SensorWrapper magWrapper;
    private final SensorWrapper gyroWrapper;

    public MagneticOrientationCalculator(SensorWrapper accelerometerWrapper, SensorWrapper magneticWrapper, SensorWrapper gyroscopeWrapper) {
        this.accWrapper = accelerometerWrapper;
        this.magWrapper = magneticWrapper;
        TRUST_CONST = 0.8;
        this.gyroWrapper = gyroscopeWrapper;
    }
    private void processAccelerometerMagnetometer(Vector3 accValues, Vector3 magValues, double estimateYaw) {
        double ax = accValues.x();
        double ay = accValues.y();
        double az = accValues.z();

        double pitch = Math.atan2(ay, Math.sqrt(ax*ax + az*az));
        double roll = Math.atan2(-ax, az);

        double sinRoll = Math.sin(roll);
        double cosRoll = Math.cos(roll);
        double sinPitch = Math.sin(pitch);
        double cosPitch = Math.cos(pitch);
        double yaw;
        try {
            double mx = magValues.x();
            double my = magValues.y();
            double mz = magValues.z();

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

    private void processGyroscope(Vector3 gyroValues) {
        double gx = gyroValues.x();
        double gy = gyroValues.y();
        double gz = gyroValues.z();
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
        return currentGyro.multiply(TRUST_CONST).add(currentMagnetic.multiply(1 - TRUST_CONST));
    }

    public Vector3 updateAndGet() {
        try {
            processAccelerometerMagnetometer(accWrapper.get(), magWrapper.get(), gyroWrapper.get().z());
            processGyroscope(gyroWrapper.get());
            return getOrientation();
        } catch (NullPointerException e) {
            return new Vector3(-1, -2, -3);
        }
    }

    @Override
    public void resetVectors() {
        currentMagnetic = null;
        currentGyro = null;
    }
}
