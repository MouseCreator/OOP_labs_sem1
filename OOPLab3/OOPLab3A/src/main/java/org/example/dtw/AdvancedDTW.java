package org.example.dtw;

import org.example.dtw.distance.DistanceCalculator;

import java.util.List;

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

        initZeros(matrix, n, m);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double cost = distanceCalculator.calculate(seq1[i - 1], seq2[j - 1]);
                matrix[i][j] = cost + Math.min(Math.min(matrix[i - 1][j], matrix[i][j - 1]), matrix[i - 1][j - 1]);
            }
        }
    }

    private static void initZeros(double[][] matrix, int n, int m) {
        for (int i = 1; i <= n; i++) {
            matrix[i][0] = Double.MAX_VALUE;
        }
        for (int j = 1; j <= m; j++) {
            matrix[0][j] = Double.MAX_VALUE;
        }

        matrix[0][0] = 0;
    }

    @Override
    public double dtwNormalizedDistance(T[] seq1, T[] seq2) {
        return dtwDistance(seq1, seq2) / (seq1.length + seq2.length);
    }

    @Override
    public double dtwNormalizedDistance(List<T> seq1, List<T> seq2) {
        int m = seq1.size();
        int n = seq2.size();
        double[][] matrix = new double[m+1][n+1];
        fillDTWMatrix(seq1, seq2, matrix);
        return matrix[m][n] / (m + n);
    }

    private void fillDTWMatrix(List<T> seq1,List<T>  seq2, double[][] matrix) {
        int m = seq1.size();
        int n = seq2.size();
        initZeros(matrix, m, n);

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                double cost = distanceCalculator.calculate(seq1.get(i-1), seq2.get(j-1));
                matrix[i][j] = cost + Math.min(Math.min(matrix[i - 1][j], matrix[i][j - 1]), matrix[i - 1][j - 1]);
            }
        }
    }
}
