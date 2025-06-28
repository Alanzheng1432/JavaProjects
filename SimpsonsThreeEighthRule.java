
public class SimpsonsThreeEighthRule {

    // Function to integrate (e.g., f(x) = x^3)
    public static double function(double x) {
        return Math.pow(x, 3);  // Change this function as needed
    }

    // Simpson's 3/8 rule implementation
    public static double integrate(double a, double b, int n) {
        if (n % 3 != 0) {
            throw new IllegalArgumentException("n must be a multiple of 3");
        }

        double h = (b - a) / n;
        double sum = function(a) + function(b);

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            if (i % 3 == 0) {
                sum += 2 * function(x);
            } else {
                sum += 3 * function(x);
            }
        }

        return (3 * h / 8) * sum;
    }

    public static void main(String[] args) {
        double a = 0;      // Lower limit
        double b = 1;      // Upper limit
        int n = 6;         // Number of intervals (must be multiple of 3)

        double result = integrate(a, b, n);
        System.out.printf("Approximate integral using Simpson's 3/8 Rule: %.6f\n", result);
    }
}

