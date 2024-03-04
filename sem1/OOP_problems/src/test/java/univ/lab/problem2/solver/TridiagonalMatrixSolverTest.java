package univ.lab.problem2.solver;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TridiagonalMatrixSolverTest {
    private double[][] parseCustomMatrix(String row) {
        String[] rows = row.split("\n");
        int numRows = rows.length;
        int numCols = rows[0].trim().split(" ").length;
        double[][] matrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] values = rows[i].trim().split(" ");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }

        return matrix;
    }
    @Test
    void solve() {
        int N = 50;
        int process = 4;
        Generator generator = new Generator();
        double[][] matrix = generator.generateMatrix(N);
        double[] vector = generator.generateVector(N);
        TridiagonalMatrixHelper solver = new TridiagonalMatrixHelper(matrix, vector, process);
        double[] actual = solver.solve();
        assertTrue(generator.isSolution(actual));
    }

    @Test
    void solveRandom() {
        int N = 99;
        int process = 3;
        Generator generator = new Generator();
        VectorComparator comparator = new VectorComparatorSimple();
        double[][] matrix = generator.randomIntMatrix(N);
        double[] solution = generator.randomIntVector(N);
        double[] vector = generator.vectorFromMatrixSolution(matrix, solution);
        TridiagonaMatrixSolver solver = new TridiagonaMatrixSolver(process);
        Gaussian gaussianSolver = new Gaussian();
        double[] actual = solver.solve(matrix, vector);
        double[] gaussian = gaussianSolver.solve(matrix, vector);
        assertTrue(comparator.vectorCompare(actual, solution));
        assertTrue(comparator.vectorCompare(gaussian, actual));
    }


}