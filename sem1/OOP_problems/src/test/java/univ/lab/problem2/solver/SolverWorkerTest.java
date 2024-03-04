package univ.lab.problem2.solver;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverWorkerTest {
    private double[][] initMatrix() {
        double[][] resultMatrix = new double[12][12];
        for (int i = 0; i < 12; i++) {
            resultMatrix[i][i] = 2.0;
        }
        for (int i = 0; i < 11; i++) {
            resultMatrix[i][i + 1] = 1.0;
            resultMatrix[i + 1][i] = 3.0;
        }
        return resultMatrix;
    }


    private boolean isNotZero(double v) {
        return Math.abs(v) > 0.00001;
    }
    private boolean isDiagonal(double[][] matrix) {
        int n = matrix.length;
        boolean hasNonZero = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    hasNonZero = hasNonZero || isNotZero(matrix[i][j]);
                }
                if (i != j && isNotZero(matrix[i][j])) {
                    return false;
                }
            }
        }
        return hasNonZero;
    }

    private void print(double[][] matrix) {
        int n = matrix.length;
        StringBuilder builder = new StringBuilder();
        for (double[] doubles : matrix) {
            for (int j = 0; j < n; j++) {
                builder.append(doubles[j]).append(' ');
            }
            builder.append('\n');
        }
        System.out.println(builder);
    }
    @Test
    void doElimination() {
        double[][] mtx =initMatrix();
        double[] vector = new double[12];
        SolverWorker solver = new SolverWorker(mtx, vector, 4, 8);
        solver.doElimination();
        print(solver.getSub());
        assertTrue(isDiagonal(solver.getSub()));
    }

    @Test
    void doEliminationSmall() {
        int N = 8;
        Generator generator = new Generator();
        double[][] matrix = generator.generateMatrix(N);
        double[] vector = generator.generateVector(N);
        SolverWorker solver = new SolverWorker(matrix, vector, 4, 8);
        solver.doElimination();
        assertTrue(isDiagonal(solver.getSub()));
    }
    private double[][] duplicate2DArray(double[][] original) {
        int numRows = original.length;
        int numCols = original[0].length;

        double[][] duplicate = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            System.arraycopy(original[i], 0, duplicate[i], 0, numCols);
        }
        return duplicate;
    }
    @Test
    void doEliminationRandom() {
        int N = 50;
        Generator generator = new Generator();
        Gaussian gaussian = new Gaussian();

        double[][] matrix = generator.randomIntMatrix(N);
        double[][] matrixCopy = duplicate2DArray(matrix);
        double[] vector = generator.randomIntVector(N);
        double[] vectorCopy = duplicate1DArray(vector);
        SolverWorker solver = new SolverWorker(matrix, vector, 25, 50);
        solver.doElimination();
        assertTrue(isDiagonal(solver.getSub()));

        double[] sol1 = gaussian.solve(matrix, vector);
        double[] sol2 = gaussian.solve(matrixCopy, vectorCopy);

        VectorComparator comparator = new VectorComparatorSimple();
        boolean solutionNotChange = comparator.vectorCompare(sol1, sol2);
        assertTrue(solutionNotChange);


    }

    public double[] duplicate1DArray(double[] original) {
        return Arrays.copyOf(original, original.length);
    }
}