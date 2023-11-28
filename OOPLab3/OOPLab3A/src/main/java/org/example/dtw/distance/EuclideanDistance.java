package org.example.dtw.distance;


import org.example.vector.Vector3D;

public class EuclideanDistance implements DistanceCalculator<Vector3D> {
    @Override
    public double calculate(Vector3D v1, Vector3D v2) {
        return (v1.subtract(v2)).magnitude();
    }
}
