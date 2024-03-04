package org.example.dtw.distance;


import org.example.vector.Vector3D;

public class CosineDistance implements DistanceCalculator<Vector3D>{
    @Override
    public double calculate(Vector3D v1, Vector3D v2) {
        double dotProduct = dot(v1, v2);
        double magnitudeProduct = v1.magnitude() * v2.magnitude();
        return dotProduct / magnitudeProduct;
    }
    public double dot(Vector3D v1, Vector3D v2) {
        return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
    }
}
