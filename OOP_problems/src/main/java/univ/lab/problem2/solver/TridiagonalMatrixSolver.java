package univ.lab.problem2.solver;

import java.util.List;

public class TridiagonalMatrixSolver {
    private final double[][] matrix;
    private final int N;
    private int M;
    private int P;
    private List<SolverWorker> workerList;
    public TridiagonalMatrixSolver(double[][] matrix) {
        this.matrix = matrix;
        this.N = matrix.length;
    }

    public void solve() {
        double[] d = new double[N];
    }


    public double[][] getProcessMatrix() {
        double[][] processMatrix = new double[2*P][2*P];
        for (int i = 0; i < P; i++) {
            workerList.get(i).fillProcessMatrix(processMatrix, i, P - 1);
        }
        return processMatrix;
    }


}
