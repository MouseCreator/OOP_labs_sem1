package univ.lab.problem2.solver;

public class SolverWorker implements Runnable{
    protected final int FROM;
    protected final int TO;
    protected final int N;
    protected final int M;
    protected final double[][] matrix;
    public SolverWorker(double[][] matrix, int from, int to) {
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
    public double[][] getSub() {
        double[][] sub = new double[M][M];
        for (int i = 0; i < M; i++) {
            System.arraycopy(matrix[FROM + i], FROM, sub[i], 0, M);
        }
        return sub;
    }
    private void eliminateA() {
        for (int i = FROM + 1; i < TO; i++) {
            double a = getA(i);
            double c = getC(i-1);
            addRowFull(i-1, i, -a/c);
        }
    }
    private void eliminateB() {
        for (int i = TO - 2; i >= FROM; i--) {
            addRowFull(i+1, i, -getB(i)/getC(i+1));
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
        if (row == N - 1) {
            throw new RuntimeException("Cannot get value C for the last row of the matrix");
        }
        return matrix[row][row+1];
    }
    private double getC( int row) {
        validateRow(row);
        return matrix[row][row];
    }

    private void validateRow(int row) {
        if (row >= N) {
            throw new RuntimeException(String.format("Trying to get %d row of matrix with %d rows", row, matrix.length));
        }
    }
    /*
    a5 = [C][C-1] #not 1
    c5 = [C][C]
    g5 = [C][C+1]

    d8 = [C+1][C] #not 1
    c8 = [C+1][C+1] #not P
    b8 = [C+1][C+2] #not P
     */
    public void fillProcessMatrix(double[][] processMatrix, int rank, int maxRank) {
        int c = 2 * rank;
        processMatrix[c][c] = matrix[FROM][FROM];
        if (rank != 0) {
            processMatrix[c][c - 1] = matrix[FROM][FROM - 1];
            processMatrix[c + 1][c] = matrix[TO - 1][TO - 1];
        }
        if (rank != maxRank) {
            processMatrix[c][c+1] = matrix[FROM][TO];
            processMatrix[c + 1][c + 1] = matrix[TO - 1][TO - 1];
            processMatrix[c + 1][c + 2] = matrix[TO - 1][TO];
        }
    }
}
