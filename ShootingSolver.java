public class ShootingSolver {
    private double ya, yb, a, b;
    private double step_size;
    private int iterations;
    private double epsilon;
    private double[] xtrajectory;
    private double[] ytrajectory;

    public ShootingSolver(double ya, double yb, double a, double b, double h, double epsilon) {
        this.ya = ya;
        this.yb = yb;
        this.a = a;
        this.b = b;
        this.step_size = h;
        this.epsilon = epsilon;
        this.iterations = (int) Math.abs((b - a) / h) + 1;
    }

    private double yprime(double v) {
        return v;
    }

    private double vprime(double y) {
        return -Math.pow(y, 3);
    }

    public double RungeKutta4(double v_init, boolean storeTrajectory) {
        double[] x_temp = new double[iterations];
        double[] y_temp = new double[iterations];
        double x = a;
        double y = ya;
        double v = v_init;

        x_temp[0] = x;
        y_temp[0] = y;

        for (int i = 0; i < iterations - 1; i++) {
            double[] k_y = new double[4];
            double[] k_v = new double[4];

            k_y[0] = step_size * yprime(v);
            k_v[0] = step_size * vprime(y);

            k_y[1] = step_size * yprime(v + 0.5 * k_v[0]);
            k_v[1] = step_size * vprime(y + 0.5 * k_y[0]);

            k_y[2] = step_size * yprime(v + 0.5 * k_v[1]);
            k_v[2] = step_size * vprime(y + 0.5 * k_y[1]);

            k_y[3] = step_size * yprime(v + k_v[2]);
            k_v[3] = step_size * vprime(y + k_y[2]);

            x = x + step_size;
            y = y + (k_y[0] + 2 * k_y[1] + 2 * k_y[2] + k_y[3]) / 6;
            v = v + (k_v[0] + 2 * k_v[1] + 2 * k_v[2] + k_v[3]) / 6;

            x_temp[i + 1] = x;
            y_temp[i + 1] = y;
        }

        if (storeTrajectory) {
            xtrajectory = x_temp;
            ytrajectory = y_temp;
        }

        return y_temp[iterations - 1] - yb;
    }

    public double getShootingSpeed(double s0, double s1) {
        double f0 = RungeKutta4(s0, false);
        double f1 = RungeKutta4(s1, false);

        int maxIter = 20;
        for (int i = 0; i < maxIter; i++) {
            if (Math.abs(f1) < epsilon) break;

            double s2 = s1 - f1 * (s1 - s0) / (f1 - f0);
            double f2 = RungeKutta4(s2, false);

            s0 = s1;
            f0 = f1;
            s1 = s2;
            f1 = f2;
        }

        RungeKutta4(s1, true);
        return s1;
    }

    public void printTrajectory() {
        for (int i = 0; i < iterations; i++) {
            System.out.printf("x: %.4f, y: %.6f%n", xtrajectory[i], ytrajectory[i]);
        }
    }

    public static void main(String[] args) {
        double ya = 0;
        double yb = 1;
        double a = 0;
        double b = 1;
        double h = 0.01;
        double epsilon = 1e-6;

        double guess1 = 0.8;
        double guess2 = 1.3;

        ShootingSolver solver = new ShootingSolver(ya, yb, a, b, h, epsilon);
        double shootingSpeed = solver.getShootingSpeed(guess1, guess2);

        System.out.printf("Final shooting speed y'(0): %.8f%n", shootingSpeed);
        System.out.printf("y(1) = %.8f, Error = %.2e%n", solver.ytrajectory[solver.iterations - 1],
                solver.ytrajectory[solver.iterations - 1] - yb);
        solver.printTrajectory();
    }
}
