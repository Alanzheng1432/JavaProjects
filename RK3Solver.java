public class RK3Solver {

    private double x0;     // Initial x
    private double y0;     // Initial y
    private double h;      // Step size
    private int steps;     // Number of steps

    // Constructor
    public RK3Solver(double x0, double y0, double h, int steps) {
        this.x0 = x0;
        this.y0 = y0;
        this.h = h;
        this.steps = steps;
    }

    // Define the differential equation dy/dx = f(x, y)
    public double f(double x, double y) {
        // Example: dy/dx = x + y
        return x + y;
    }

    // Method to solve using RK3
    public void solve() {
        double x = x0;
        double y = y0;

        System.out.printf("Step\t\tx\t\ty%n");
        System.out.printf("0\t\t%.5f\t%.5f%n", x, y);

        for (int i = 1; i <= steps; i++) {
            double k1 = f(x, y);
            double k2 = f(x + h / 2.0, y + h * k1 / 2.0);
            double k3 = f(x + h, y - h * k1 + 2 * h * k2);

            y = y + (h / 6.0) * (k1 + 4 * k2 + k3);
            x = x + h;

            System.out.printf("%d\t\t%.5f\t%.5f%n", i, x, y);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Initial conditions: x0 = 0, y0 = 1, h = 0.1, steps = 10
        RK3Solver solver = new RK3Solver(0.0, 1.0, 0.1, 10);
        solver.solve();
    }
}

