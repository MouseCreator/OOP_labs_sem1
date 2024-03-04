package org.example.dtw.distance;


import org.example.vector.Vector3D;

public class ManhattanDistance implements DistanceCalculator<Vector3D> {
    @Override
    public double calculate(Vector3D v1, Vector3D v2) {
        return Math.abs(v1.x() - v2.x()) + Math.abs(v1.y() - v2.y()) + Math.abs(v1.z() - v2.z());
    }
}
