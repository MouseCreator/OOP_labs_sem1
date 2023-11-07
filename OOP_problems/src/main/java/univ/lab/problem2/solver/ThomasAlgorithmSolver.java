package univ.lab.problem2.solver;

public class ThomasAlgorithmSolver {

    private static double calculate_z(double[][] matrix, double[] alpha_vector, int i) {
        return - (matrix[i][i] + alpha_vector[i] * matrix[i][i-1]);
    }
    public static double[] solve(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] alpha_vector = new double[n];
        double[] beta_vector = new double[n];
        alpha_vector[1] = - matrix[0][1] / matrix[0][0];
        beta_vector[1] = vector[0] / matrix[0][0];
        for (int i = 1; i < n - 1; i++) {
            double z = calculate_z(matrix, alpha_vector, i);
            alpha_vector[i+1] = matrix[i][i+1] / z;
            beta_vector[i+1] = (matrix[i][i-1] * beta_vector[i] - vector[i]) / z;
        }
        double[] result = new double[n];
        int last = n-1;
        result[last] = (matrix[last][last-1] * beta_vector[last] - vector[last]) / calculate_z(matrix, alpha_vector, last);

        for (int i = n - 2; i >= 0; i--) {
            int j = i+1;
            result[i] = alpha_vector[j] * result[j] + beta_vector[j];
        }

        return result;
    }
}
