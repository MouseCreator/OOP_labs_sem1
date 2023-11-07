package univ.lab.problem2.solver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThomasAlgorithmSolverTest {
    private double[][] parseMatrix(String input) {
        String[] rows = input.trim().split("\n");
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
        double[][] matrix = parseMatrix("""
                1 1 0
                1 3 2
                0 1 2
                """);
        double[] vector = new double[]{1,1,1};
        double[] solve = ThomasAlgorithmSolver.solve(matrix, vector);
        double[] solution = new double[]{2,-1,1};
        assertArrayEquals(solution, solve);
    }

    @Test
    void solveGenerated() {
        Generator generator = new Generator();
        int N = 50;
        double[][] matrix = generator.generateMatrix(N);
        double[] vector = generator.generateVector(N);
        double[] solution = ThomasAlgorithmSolver.solve(matrix, vector);
        assertTrue(generator.isSolution(solution));
    }


}