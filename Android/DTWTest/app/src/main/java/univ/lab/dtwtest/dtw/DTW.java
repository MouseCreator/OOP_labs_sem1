package univ.lab.dtwtest.dtw;

public class DTW<T> {

    private final DistanceCalculator<T> distanceCalculator;

    public DTW(DistanceCalculator<T> distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public double dtwDistance(T[] seq1, T[] seq2) {
        int m = seq1.length;
        int n = seq2.length;

        double[][] matrix = new double[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                matrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        matrix[0][0] = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                double cost = distanceCalculator.calculate(seq1[i-1], seq2[j-1]);
                matrix[i][j] = cost + Math.min(Math.min(matrix[i - 1][j], matrix[i][j - 1]), matrix[i - 1][j - 1]);
            }
        }
        return matrix[m][n];
    }

    private int minIndex(double[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty or null");
        }

        int minIndex = 0;
        double minValue = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minValue) {
                minValue = arr[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private int nextMin(double[][] matrix, int i, int j) {
        if (i == 0) {
            return 1;
        }
        if (j == 0) {
            return 0;
        }
        double[] arr = new double[] {matrix[i-1][j], matrix[j-1][j], matrix[i-1][j-1]};
        return minIndex(arr);
    }

    private void print(double[][] matrix) {
        StringBuilder builder = new StringBuilder("Matrix\n");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                builder.append(matrix[i][j]).append(" ");
            }
            builder.append("\n");
        }
        System.out.println(builder);

    }

    private double modification(double[][] matrix, int i, int j) {
        if (i == 0) {
            return j==0 ? 0 : matrix[i][j-1];
        }
        if (j == 0) {
            return matrix[i-1][j];
        }
        return Math.min(Math.min(matrix[i-1][j], matrix[i][j-1]), matrix[i-1][j-1]);
    }

}
