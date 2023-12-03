package univ.lab.ninjagame1.filtered;

public class RegularOrientationCalculator implements OrientationCalculator {
    private final double TRUST_CONST;
    public RegularOrientationCalculator() {
        TRUST_CONST = 0.8;
    }
    private SensorWrapper accWrapper;
    private SensorWrapper gyroWrapper;
    public void init(SensorWrapper accWrapper, SensorWrapper magWrapper, SensorWrapper gyroWrapper) {
        this.accWrapper = accWrapper;
        this.gyroWrapper = gyroWrapper;
    }
    /*
    if vector is pointing (x,y), then angle theta = atan2(y,x)
     */
    private void processAccelerometerMagnetometer(Vector3 vector3, double estimateYaw) {
        double ax = vector3.x();
        double ay = vector3.y();
        double az = vector3.z();
        double pitch = Math.atan2(ay, Math.sqrt(ax*ax + az*az));
        double roll = Math.atan2(-ax, az);
        currentMagnetic = new Vector3(pitch, roll, estimateYaw);
    }

    private Vector3 currentGyro = Vector3.zero();
    private Vector3 currentMagnetic = Vector3.zero();
    private long lastGyroUpdate = 0;
    private void processGyroscope(Vector3 vector3) {
        double gx = vector3.x();
        double gy = vector3.y();
        double gz = vector3.z();
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
    @Override
    public Vector3 getOrientation() {
        return currentGyro.multiply(TRUST_CONST).add(currentMagnetic.multiply(1-TRUST_CONST));
    }
    @Override
    public Vector3 updateAndGet() {
        try {
            processAccelerometerMagnetometer(accWrapper.get(), gyroWrapper.get().z());
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
