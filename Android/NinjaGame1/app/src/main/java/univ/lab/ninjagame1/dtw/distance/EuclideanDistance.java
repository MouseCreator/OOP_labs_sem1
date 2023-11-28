package univ.lab.ninjagame1.dtw.distance;


import univ.lab.ninjagame1.filtered.Vector3;

public class EuclideanDistance implements DistanceCalculator<Vector3> {
    @Override
    public double calculate(Vector3 v1, Vector3 v2) {
        return (v1.subtract(v2)).magnitude();
    }
}
