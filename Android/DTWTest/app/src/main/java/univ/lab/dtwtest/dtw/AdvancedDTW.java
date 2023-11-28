package univ.lab.dtwtest.dtw;

public class AdvancedDTW<T> implements DTW<T> {

    private final DistanceCalculator<T> distanceCalculator;

    public AdvancedDTW(DistanceCalculator<T> distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }
    @Override
    public double dtwDistance(T[] seq1, T[] seq2) {
        int m = seq1.length;
        int n = seq2.length;
        double[][] matrix = new double[m+1][n+1];
        fillMatrix(matrix, seq1, seq2);
        return matrix[m][n];
    }

    private void fillMatrix(double[][] matrix, T[] seq1, T[] seq2) {
        int n = seq1.length;
        int m = seq2.length;

        for (int i = 1; i <= n; i++) {
            matrix[i][0] = Double.MAX_VALUE;
        }
        for (int j = 1; j <= m; j++) {
            matrix[0][j] = Double.MAX_VALUE;
        }

        matrix[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double cost = distanceCalculator.calculate(seq1[i - 1], seq2[j - 1]);
                matrix[i][j] = cost + Math.min(Math.min(matrix[i - 1][j], matrix[i][j - 1]), matrix[i - 1][j - 1]);
            }
        }
    }

    @Override
    public double dtwNormalizedDistance(T[] seq1, T[] seq2) {
        return 0;
    }
}
