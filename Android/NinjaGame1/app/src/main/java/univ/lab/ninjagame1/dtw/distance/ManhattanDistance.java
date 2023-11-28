package univ.lab.ninjagame1.dtw.distance;


import univ.lab.ninjagame1.filtered.Vector3;

public class ManhattanDistance implements DistanceCalculator<Vector3> {
    @Override
    public double calculate(Vector3 v1, Vector3 v2) {
        return Math.abs(v1.x() - v2.x()) + Math.abs(v1.y() - v2.y()) + Math.abs(v1.z() - v2.z());
    }
}
