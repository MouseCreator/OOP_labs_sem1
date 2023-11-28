package univ.lab.ninjagame1.dtw.distance;


import univ.lab.ninjagame1.filtered.Vector3;

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
