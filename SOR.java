public class SOR {

    private double[][] A;    // Coefficient matrix
    private double[] b;      // Right-hand side vector
    private double[] x;      // Solution vector
    private double omega;    // Relaxation factor

    // Constructor to initialize the SOR with given parameters
    public SOR(double[][] A, double[] b, double omega) {
        this.A = A;
        this.b = b;
        this.x = new double[b.length]; // Initialize solution vector with the same length as b
        this.omega = omega;
    }

    public static void main(String[] args) {
        double[][] A = {
            {4, -1, -6, 0},
            {-5, -4, 10, 8},
            {0, 9, 4, -2},
            {1, 0, -7, 5}
        };

        double[] b = {2, 21, -12, -6}; // Right-hand side
        double omega = 1.1; // Relaxation factor (typically between 1 and 2)

        // Create an instance of SOR with the given parameters
        SOR sor = new SOR(A, b, omega);
    }
}
