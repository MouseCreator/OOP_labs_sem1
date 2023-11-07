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

        System.out.println(Arrays.toString(solution));
        TridiagonaMatrixSolver solver = new TridiagonaMatrixSolver(process);
        Gaussian gaussianSolver = new Gaussian();
        double[] actual = solver.solve(matrix, vector);
        double[] gaussian = gaussianSolver.solve(matrix, vector);
        System.out.println(Arrays.toString(gaussian));
        System.out.println(Arrays.toString(actual));
        assertTrue(comparator.vectorCompare(actual, solution));
    }


}