package univ.lab.problem2.solver;

public class TridiagonalMatrixSolver {
    public void solve(double[][] matrix) {

    }

    private double getA(double[][] matrix, int row) {
        validateRow(matrix, row);
        if (row == 0) {
            throw new RuntimeException("Cannot get value A for the first row of the matrix");
        }
        return matrix[row][row-1];
    }
    private double getB(double[][] matrix, int row) {
        validateRow(matrix, row);
        return matrix[row][row];
    }
    private double getC(double[][] matrix, int row) {
        validateRow(matrix, row);
        if (row == matrix.length - 1) {
            throw new RuntimeException("Cannot get value C for the last row of the matrix");
        }
        return matrix[row][row+1];
    }

    private void validateRow(double[][] matrix, int row) {
        if (row >= matrix.length) {
            throw new RuntimeException(String.format("Trying to get %d row of matrix with %d rows", row, matrix.length));
        }
    }
}
