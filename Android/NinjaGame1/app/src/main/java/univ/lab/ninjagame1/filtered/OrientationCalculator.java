package univ.lab.ninjagame1.filtered;

public interface OrientationCalculator {
    void init(SensorWrapper accWrapper, SensorWrapper magWrapper, SensorWrapper gyroWrapper);
    Vector3 getOrientation();
    Vector3 updateAndGet();
    void resetVectors();
}
