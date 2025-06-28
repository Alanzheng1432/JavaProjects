public class FEM1D {
    public static void main(String[] args) {
        int N = 6;              // number of elements
        double a = 0, b = 1;    // domain [a, b]
        double h = (b - a) / N; // element size

        int nodes = N + 1;
        double[] x = new double[nodes]; // nodal positions
        for (int i = 0; i < nodes; i++) {
            x[i] = a + i * h;
        }

        // Global stiffness matrix and load vector
        double[][] K = new double[nodes][nodes];
        double[] F = new double[nodes];

        // Assembly loop
        for (int e = 0; e < N; e++) {
            int i = e, j = e + 1;

            // Local stiffness matrix for -d²y/dx²
            double[][] Ke = {
                { 1.0 / h, -1.0 / h },
                { -1.0 / h, 1.0 / h }
            };

            // Local load vector for f(x) = 1
            double[] Fe = {
                h / 2.0,
                h / 2.0
            };

            // Assemble into global matrix
            K[i][i] += Ke[0][0]; K[i][j] += Ke[0][1];
            K[j][i] += Ke[1][0]; K[j][j] += Ke[1][1];

            F[i] += Fe[0];
            F[j] += Fe[1];
        }

        // Apply Dirichlet boundary conditions: y(0) = 0, y(1) = 0
        K[0][0] = 1.0; F[0] = 0.0;
        for (int j = 1; j < nodes; j++) K[0][j] = 0;
        for (int i = 1; i < nodes; i++) K[i][0] = 0;

        K[nodes - 1][nodes - 1] = 1.0; F[nodes - 1] = 0.0;
        for (int j = 0; j < nodes - 1; j++) K[nodes - 1][j] = 0;
        for (int i = 0; i < nodes - 1; i++) K[i][nodes - 1] = 0;

        // Solve the linear system K * y = F
        double[] y = gaussianElimination(K, F);

        // Output results
        System.out.println("Node\tX\tY");
        for (int i = 0; i < nodes; i++) {
            System.out.printf("%d\t%.4f\t%.6f%n", i, x[i], y[i]);
        }
    }

    // Simple Gaussian Elimination solver
    public static double[] gaussianElimination(double[][] A, double[] b) {
        int n = b.length;
        for (int i = 0; i < n; i++) {
            // Pivot
            double max = Math.abs(A[i][i]);
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(A[k][i]) > max) {
                    max = Math.abs(A[k][i]);
                    maxRow = k;
                }
            }

            // Swap rows
            double[] temp = A[i];
            A[i] = A[maxRow];
            A[maxRow] = temp;

            double t = b[i];
            b[i] = b[maxRow];
            b[maxRow] = t;

            // Eliminate
            for (int k = i + 1; k < n; k++) {
                double factor = A[k][i] / A[i][i];
                b[k] -= factor * b[i];
                for (int j = i; j < n; j++) {
                    A[k][j] -= factor * A[i][j];
                }
            }
        }

        // Back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = b[i];
            for (int j = i + 1; j < n; j++) {
                x[i] -= A[i][j] * x[j];
            }
            x[i] /= A[i][i];
        }

        return x;
    }
}
