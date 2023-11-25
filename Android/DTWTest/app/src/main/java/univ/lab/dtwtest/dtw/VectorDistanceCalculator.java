package univ.lab.dtwtest.dtw;

public class VectorDistanceCalculator implements DistanceCalculator<Vector3> {
    @Override
    public double calculate(Vector3 v1, Vector3 v2) {
        return (v1.subtract(v2)).magnitude();
    }
}
