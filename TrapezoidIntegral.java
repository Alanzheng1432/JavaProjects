public class TrapezoidIntegral {

    // Function to check if a point satisfies the equation (x^2 + y^2)^2 = x^3 + y^3
    public static boolean isInsideDomain(double x, double y) {
        double leftSide = Math.pow(x * x + y * y, 2);
        double rightSide = Math.pow(x, 3) + Math.pow(y, 3);
        return ((leftSide - rightSide) < 1e-5); // Allowing some tolerance for precision
    }

    // Function to approximate the integral using the Trapezoidal Rule
    public static double trapezoidalRule(double x1, double x2, double y1, double y2, int nx, int ny) {
        // Step sizes in x and y directions
        double hx = (x2 - x1) / nx;
        double hy = (y2 - y1) / ny;

        // Integral approximation
        double integral = 0.0;

        // Iterate over subintervals in x and y
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                // Coordinates of the current grid point
                double x0 = x1 + i * hx;
                double y0 = y1 + j * hy;
                double x1_ = x1 + (i + 1) * hx;
                double y1_ = y1 + (j + 1) * hy;

                // Evaluate function at the 4 corners of the trapezoid
                if (isInsideDomain(x0, y0)) integral += 1;
                if (isInsideDomain(x1_, y0)) integral += 1;
                if (isInsideDomain(x0, y1_)) integral += 1;
                if (isInsideDomain(x1_, y1_)) integral += 1;
            }
        }

        // Multiply by step sizes to get the total area
        integral *= hx * hy / 4;

        return integral;
    }

    public static void main(String[] args) {
        // Define the limits of integration and number of subintervals
        double x1 = -1;    // Lower limit for x
        double x2 = 1;     // Upper limit for x
        double y1 = -1;    // Lower limit for y
        double y2 = 1;     // Upper limit for y
        int nx = 1000;     // Number of subintervals in x direction (higher = more accurate)
        int ny = 1000;     // Number of subintervals in y direction (higher = more accurate)

        // Compute the integral using the Trapezoidal Rule
        double result = trapezoidalRule(x1, x2, y1, y2, nx, ny);

        // Output the result
        System.out.println("The integral result is approximately: " + result);
    }
}
