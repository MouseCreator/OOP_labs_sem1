package univ.lab.problem2.solver;

import java.util.Arrays;
import java.util.Random;

public class Generator {
    private final Random random = new Random();
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
        matrix[n][n-1] = 3;
        return matrix;
    }
    private int randomIntNumber() {
        return random.nextInt(10) + 1;
    }
    public double[][] randomIntMatrix(int dim) {
        double[][] matrix = new double[dim][dim];
        matrix[0][0] = randomIntNumber();
        matrix[0][1] = bigRandomIt(dim);
        for (int i = 1; i < dim - 1; i++) {
            matrix[i][i-1] = randomIntNumber();
            matrix[i][i] = bigRandomIt(dim);
            matrix[i][i+1] = randomIntNumber();
        }
        int n = dim - 1;
        matrix[n][n] = bigRandomIt(dim);
        matrix[n][n-1] = randomIntNumber();
        return matrix;
    }

    private int bigRandomIt(int dim) {
        return dim * 10 + randomIntNumber();
    }

    public double[] randomIntVector(int dim) {
        double[] solution = new double[dim];
        for (int i = 0; i < dim; i++) {
            solution[i] = randomIntNumber();
        }
        return solution;
    }
    private double[] multiply(double[][] matrix, double[] vector) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        if (numCols != vector.length) {
            throw new IllegalArgumentException("Matrix column count must be equal to vector length");
        }
        double[] result = new double[numRows];
        for (int i = 0; i < numRows; i++) {
            double sum = 0.0;
            for (int j = 0; j < numCols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }
    public double[] vectorFromMatrixSolution(double[][] matrix, double[] solution) {
        return multiply(matrix, solution);
    }
    public double[] generateVector(int dim) {
        double[] vector = new double[dim];
        vector[0] = 7;
        Arrays.fill(vector, 1, dim - 1, 6);
        vector[dim-1] = 9;
        return vector;
    }

    public boolean isSolution(double[] sol) {
        VectorComparatorSimple comparator = new VectorComparatorSimple();
        return comparator.vectorCompare(sol, generateSolution(sol.length));
    }
}
