package univ.lab.problem2.solver;

public class SolverRunnable implements Runnable{
    protected final int FROM;
    protected final int TO;
    protected final int N;
    protected final int M;
    protected final double[][] matrix;
    private SolverRunnable(double[][] matrix, int from, int to) {
        FROM = from;
        TO = to;
        N = matrix.length;
        M = TO - FROM;
        this.matrix = matrix;
    }

    public void run() {

    }
    private void addRowFull(int toAdd, int addTo, double multiplyBy) {
        for (int i = 0; i < N; i++) {
            matrix[addTo][i] += matrix[toAdd][i] * multiplyBy;
        }
    }

    protected void doElimination() {
        eliminateA();
        eliminateB();
    }
    private void eliminateA() {
        for (int i = FROM + 1; i < TO; i++) {
            addRowFull(i, i-1, -getA(i)/getC(i-1));
        }
    }
    private void eliminateB() {
        for (int i = TO - 2; i >= FROM; i--) {
            addRowFull(i, i+1, -getC(i+1)/getB(i));
        }
    }

    private double getA(int row) {
        validateRow(row);
        if (row == 0) {
            throw new RuntimeException("Cannot get value A for the first row of the matrix");
        }
        return matrix[row][row-1];
    }
    private double getB(int row) {
        validateRow(row);
        return matrix[row][row];
    }
    private double getC( int row) {
        validateRow(row);
        if (row == N - 1) {
            throw new RuntimeException("Cannot get value C for the last row of the matrix");
        }
        return matrix[row][row+1];
    }

    private void validateRow(int row) {
        if (row >= N) {
            throw new RuntimeException(String.format("Trying to get %d row of matrix with %d rows", row, matrix.length));
        }
    }
}
