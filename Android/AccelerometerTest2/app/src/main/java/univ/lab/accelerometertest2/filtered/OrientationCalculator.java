package univ.lab.accelerometertest2.filtered;

import android.hardware.SensorEvent;

import univ.lab.accelerometertest2.Vector3;

public interface OrientationCalculator {
    void init(SensorWrapper accWrapper, SensorWrapper magWrapper, SensorWrapper gyroWrapper);
    Vector3 getOrientation();
    Vector3 updateAndGet();
}
