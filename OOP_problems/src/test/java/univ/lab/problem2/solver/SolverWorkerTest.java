package univ.lab.problem2.solver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverWorkerTest {
    private double[][] initMatrix(int size) {
        double[][] resultMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            resultMatrix[i][i] = 2.0;
        }
        for (int i = 0; i < size - 1; i++) {
            resultMatrix[i][i + 1] = 1.0;
            resultMatrix[i + 1][i] = 3.0;
        }
        return resultMatrix;
    }

    private boolean isDiagonal(double[][] matrix) {
        int n = matrix.length;
        boolean hasNonZero = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    hasNonZero = hasNonZero || matrix[i][j] != 0;
                }
                if (i != j && matrix[i][j] != 0) {
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
        double[][] mtx =initMatrix(12);
        double[] vector = new double[12];
        SolverWorker solver = new SolverWorker(mtx, vector, 4, 8);
        solver.doElimination();
        print(solver.getSub());
        assertTrue(isDiagonal(solver.getSub()));
    }

    @Test
    void doEliminationSmall() {
        int N = 4;
        Generator generator = new Generator();
        double[][] matrix = generator.generateMatrix(N);
        double[] vector = generator.generateVector(N);
        SolverWorker solver = new SolverWorker(matrix, vector, 2, 4);
        solver.doElimination();
        print(solver.getSub());
    }
}