package univ.lab.problem2.solver;

import java.util.Arrays;

public class Generator {
    public double[] generateSolution(int n) {
        double[] onesVector = new double[n];
        Arrays.fill(onesVector, 1.0);
        return onesVector;
    }

    public double[][] generateMatrix(int dim) {
        double[][] matrix = new double[dim][dim];
        matrix[0][0] = 4;
        matrix[0][1] = 3;
        for (int i = 1; i < dim - 1; i++) {
            matrix[i][i-1] = 2;
            matrix[i][i] = 1;
            matrix[i][i+1] = 3;
        }
        int n = dim - 1;
        matrix[n][n] = 6;
        matrix[n][n-1] = 2;
        return matrix;
    }
    public double[] generateVector(int dim) {
        double[] vector = new double[dim];
        vector[0] = 7;
        vector[dim-1] = 8;
        Arrays.fill(vector, 1, dim - 1, 6);
        return vector;
    }
}