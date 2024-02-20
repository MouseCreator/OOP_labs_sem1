package univ.lab.dtwtest.dtw.distance;

import univ.lab.dtwtest.dtw.Vector3;

public class CosineDistance implements DistanceCalculator<Vector3>{
    @Override
    public double calculate(Vector3 v1, Vector3 v2) {
        double dotProduct = dot(v1, v2);
        double magnitudeProduct = v1.magnitude() * v2.magnitude();
        return dotProduct / magnitudeProduct;
    }
    public double dot(Vector3 v1, Vector3 v2) {
        return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
    }
}
