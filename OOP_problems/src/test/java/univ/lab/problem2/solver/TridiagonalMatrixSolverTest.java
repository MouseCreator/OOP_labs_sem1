package univ.lab.problem2.solver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TridiagonalMatrixSolverTest {

    @Test
    void solve() {
        int N = 4;
        int process = 2;
        Generator generator = new Generator();
        double[][] matrix = generator.generateMatrix(N);
        double[] vector = generator.generateVector(N);
        double[] expected = generator.generateSolution(N);
        TridiagonalMatrixSolver solver = new TridiagonalMatrixSolver(matrix, vector, process);
        double[] actual = solver.solve();
        assertArrayEquals(expected, actual);
    }


}