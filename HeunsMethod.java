public class HeunsMethod {

    private double x0; // initial x
    private double y0; // initial y
    private double h;  // step size
    private int steps; // number of steps

    // Constructor
    public HeunsMethod(double x0, double y0, double h, int steps) {
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

    // Method to perform Heun's method
    public void solve() {
        double x = x0;
        double y = y0;

        System.out.printf("Step\t\tx\t\ty%n");
        System.out.printf("0\t\t%.5f\t%.5f%n", x, y);

        for (int i = 1; i <= steps; i++) {
            double k1 = f(x, y);
            double y_predictor = y + h * k1;
            double k2 = f(x + h, y_predictor);
            y = y + (h / 2.0) * (k1 + k2);
            x = x + h;

            System.out.printf("%d\t\t%.5f\t%.5f%n", i, x, y);
        }
    }

    // Main method
    public static void main(String[] args) {
        // Initial conditions: x0 = 0, y0 = 1, h = 0.1, steps = 10
        HeunsMethod solver = new HeunsMethod(0.0, 1.0, 0.1, 10);
        solver.solve();
    }
}
