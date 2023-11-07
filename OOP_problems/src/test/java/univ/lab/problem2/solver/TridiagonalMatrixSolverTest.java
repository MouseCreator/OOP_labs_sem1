package univ.lab.problem2.solver;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TridiagonalMatrixSolverTest {

    @Test
    void solve() {
        int N = 50;
        int process = 4;
        Generator generator = new Generator();
        double[][] matrix = generator.generateMatrix(N);
        double[] vector = generator.generateVector(N);
        TridiagonalMatrixSolver solver = new TridiagonalMatrixSolver(matrix, vector, process);
        double[] actual = solver.solve();
        System.out.println(Arrays.toString(actual));
        assertTrue(generator.isSolution(actual));
    }


}