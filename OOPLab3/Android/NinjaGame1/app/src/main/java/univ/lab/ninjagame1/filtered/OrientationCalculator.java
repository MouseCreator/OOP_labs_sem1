package univ.lab.ninjagame1.filtered;

public interface OrientationCalculator {
    Vector3 getOrientation();
    Vector3 updateAndGet();
    void resetVectors();
}
