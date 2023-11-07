package univ.lab.problem2.solver;

public class ThomasAlgorithmSolver {

    private static double calculate_z(double[][] matrix, double[] alpha_vector, int i) {
        return matrix[i][i] - alpha_vector[i] * matrix[i][i-1];
    }
    public static double[] solve(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] alpha_vector = new double[n];
        double[] beta_vector = new double[n];
        alpha_vector[0] = matrix[0][1] / matrix[0][0];
        beta_vector[0] = vector[0] / matrix[0][0];
        for (int i = 1; i < n - 1; i++) {
            double z = calculate_z(matrix, alpha_vector, i);
            alpha_vector[i+1] = matrix[i][i+1] / z;
            beta_vector[i+1] = (vector[i] + matrix[i][i-1]) / z;
        }
        double[] result = new double[n];
        result[n-1] = (vector[n-1] + matrix[n-1][n-2] * beta_vector[n-1]) / calculate_z(matrix, alpha_vector, n-1);

        for (int i = n - 2; i >= 0; i--) {
            int j = i+1;
            result[i] = alpha_vector[j] * result[j] + beta_vector[j];
        }

        return result;
    }
}
