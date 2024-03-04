package univ.lab.problem2.solver;

public class Gaussian implements Solver{
    public double[] solve(double[][] matrix, double[] vector) {
        int n = vector.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                vector[j] -= factor * vector[i];
                for (int k = i; k < n; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }

        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * solution[j];
            }
            solution[i] = (vector[i] - sum) / matrix[i][i];
        }

        return solution;
    }
}
