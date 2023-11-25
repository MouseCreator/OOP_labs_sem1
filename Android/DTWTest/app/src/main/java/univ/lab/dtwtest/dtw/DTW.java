package univ.lab.dtwtest.dtw;

public class DTW<T> {

    private final DistanceCalculator<T> distanceCalculator;

    public DTW(DistanceCalculator<T> distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public double dtwDistance(T[] seq1, T[] seq2) {
        int n = seq1.length;
        int m = seq2.length;
        double[][] matrix = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        matrix[0][0] = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                double cost = distanceCalculator.calculate(seq1[i], seq2[j]);
                matrix[i][j] = cost + modification(matrix, i, j);
            }
        }
        return matrix[n-1][m-1];
    }

    private double modification(double[][] matrix, int i, int j) {
        return Math.min(Math.min(matrix[i-1][j], matrix[i][j-1]), matrix[i-1][j-1]);
    }

}
