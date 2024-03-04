package univ.lab.problem2.solver;

public class VectorComparatorSimple implements VectorComparator {
    public boolean vectorCompare(double[] expected, double[] actual) {
        if (expected.length != actual.length) {
            return false;
        }
        int n = expected.length;
        for (int i = 0; i < n; i++) {
            if (Math.abs(expected[i] - actual[i]) > 0.000001) {
                return false;
            }
        }
        return true;
    }
}
