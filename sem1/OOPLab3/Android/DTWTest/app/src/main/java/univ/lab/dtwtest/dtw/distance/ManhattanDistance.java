package univ.lab.dtwtest.dtw.distance;

import univ.lab.dtwtest.dtw.Vector3;

public class ManhattanDistance implements DistanceCalculator<Vector3> {
    @Override
    public double calculate(Vector3 v1, Vector3 v2) {
        return Math.abs(v1.x() - v2.x()) + Math.abs(v1.y() - v2.y()) + Math.abs(v1.z() - v2.z());
    }
}
