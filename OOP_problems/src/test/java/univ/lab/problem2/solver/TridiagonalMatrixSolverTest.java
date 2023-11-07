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
        int N = 10;
        int process = 2;
        Generator generator = new Generator();
        VectorComparator comparator = new VectorComparatorSimple();
        double[][] matrix = generator.randomIntMatrix(N);
        double[] solution = generator.randomIntVector(N);
        double[] vector = generator.vectorFromMatrixSolution(matrix, solution);
        print(matrix);
        System.out.println(Arrays.toString(solution));
        TridiagonaMatrixSolver solver = new TridiagonaMatrixSolver(process);
        Gaussian gaussianSolver = new Gaussian();
        double[] actual = solver.solve(matrix, vector);
        double[] gaussian = gaussianSolver.solve(matrix, vector);
        System.out.println(Arrays.toString(gaussian));
        System.out.println(Arrays.toString(actual));

        print(matrix);
        print(vector);
        assertTrue(comparator.vectorCompare(actual, solution));
    }

    @Test
    void solveGiven() {
        String matrixStr = """
                1.0 10.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
                8.0 9.0 3.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
                0.0 3.0 4.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0
                0.0 0.0 3.0 8.0 4.0 0.0 0.0 0.0 0.0 0.0
                0.0 0.0 0.0 10.0 1.0 9.0 0.0 0.0 0.0 0.0
                0.0 0.0 0.0 0.0 10.0 4.0 2.0 0.0 0.0 0.0
                0.0 0.0 0.0 0.0 0.0 9.0 10.0 10.0 0.0 0.0
                0.0 0.0 0.0 0.0 0.0 0.0 9.0 9.0 2.0 0.0
                0.0 0.0 0.0 0.0 0.0 0.0 0.0 6.0 6.0 5.0
                0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 10.0 9.0
                """;
        String vectorStr = """
                4.0 8.0 7.0 1.0 7.0 1.0 8.0 6.0 7.0 2.0""";
        Generator generator = new Generator();
        double[][] matrix = parseCustomMatrix(matrixStr);
        double[] solution = parseCustomVector(vectorStr);
        double[] vector = generator.vectorFromMatrixSolution(matrix, solution);

        TridiagonaMatrixSolver solver = new TridiagonaMatrixSolver(2);
        VectorComparator comparator = new VectorComparatorSimple();
        double[] actual = solver.solve(matrix, vector);
        print(solution);
        print(actual);
        assertTrue(comparator.vectorCompare(actual, solution));
    }

    private double[] parseCustomVector(String vectorStr) {
        String[] values = vectorStr.trim().split(" ");
        double[] vector = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            vector[j] = Double.parseDouble(values[j]);
        }
        return vector;
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
    private void print(double[] vector) {
        System.out.println(Arrays.toString(vector));
    }


}