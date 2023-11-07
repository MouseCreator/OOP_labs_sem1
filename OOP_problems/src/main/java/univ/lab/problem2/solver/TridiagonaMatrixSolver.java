package univ.lab.problem2.solver;

import java.util.ArrayList;
import java.util.List;
public class TridiagonaMatrixSolver implements Solver {
    private final int nProcesses;

    public TridiagonaMatrixSolver(int nProcesses) {
        this.nProcesses = nProcesses;
    }

    @Override
    public double[] solve(double[][] matrix, double[] vector) {
        TridiagonalMatrixHelper helper = new TridiagonalMatrixHelper(matrix, vector, nProcesses);
        return helper.solve();
    }
}
class TridiagonalMatrixHelper {
    private final double[][] matrix;
    private final double[] vector;
    private final int N;
    private final int P;
    private int STEP;
    private final List<SolverWorker> workerList;
    public TridiagonalMatrixHelper(double[][] matrix, double[] vector, int numWorkers) {
        this.matrix = matrix;
        this.N = matrix.length;
        this.vector = vector;
        this.P = numWorkers;
        workerList = new ArrayList<>(P);
    }

    public double[] solve() {
        initWorkers();
        startWorkers();
        double[] processSolution = solveProcessMatrix();
        return toSolution(processSolution);
    }

    private double[] solveProcessMatrix() {
        double[][] processMatrix = getProcessMatrix();
        double[] processVector = getProcessVector();
        Solver solver = new ThomasAlgorithmSolver();
        return solver.solve(processMatrix, processVector);
    }

    private double[] getProcessVector() {
        int t = 0;
        int dim = 2 * P;
        double[] processVector = new double[dim];
        for (int i = 0; i < dim; i++) {
            processVector[i] = vector[t];
            t = nextT(t);
        }
        return processVector;
    }

    private void startWorkers() {
        List<Thread> threads = new ArrayList<>(P);
        for (SolverWorker worker : workerList) {
            Thread thread = new Thread(worker);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initWorkers() {
        STEP = N / P;
        for (int i = 0; i < P - 1; i++) {
            int from = i * STEP;
            int to = (i + 1) * STEP;
            SolverWorker worker = new SolverWorker(matrix, vector, from, to);
            workerList.add(worker);
        }
        SolverWorker lastWorker = new SolverWorker(matrix, vector, (P-1)*STEP, N);
        workerList.add(lastWorker);
    }

    private double[][] getProcessMatrix() {
        double[][] processMatrix = new double[2*P][2*P];
        for (int i = 0; i < P; i++) {
            workerList.get(i).fillProcessMatrix(processMatrix, i, P - 1);
        }
        return processMatrix;
    }
    private void subsToSolution(int index, double[] solution) {
        double res = vector[index];
        int t = 0;
        for (int i = 0; i < 2 * P; i++) {
            res -= solution[t] * matrix[index][t];
            t = nextT(t);
        }
        solution[index] = res / matrix[index][index];
    }
    private double[] toSolution(double[] processSolution) {
        double[] solution = new double[N];
        int t = 0;
        for (int i = 0; i < 2 * P; i++) {
            solution[t] = processSolution[i];
            t = nextT(t);
        }
        t = 0;
        for (int i = 0; i < N; i++) {
            if (i == t) {
                t = nextT(t);
            } else {
                subsToSolution(i, solution);
            }
        }
        return solution;
    }
    private int nextT(int current) {
        if (current == (P-1)*STEP)
            return N-1;
        int res = (current) % STEP == 0 ? current + STEP - 1 : current + 1;
        return Math.min(N - 1, res);
    }


}
