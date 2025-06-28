public class EulerWithNewton {

    // Function to evaluate f(x') = e^(-x') - x' - x^3 + 3e^(-t^3)
    public static double f(double xPrime, double x, double t) {
        return Math.exp(-xPrime) - xPrime - Math.pow(x, 3) + 3 * Math.exp(-Math.pow(t, 3));
    }

    // Derivative of f(x') with respect to x' to use in Newton's method
    public static double fPrime(double xPrime) {
        return -Math.exp(-xPrime) - 1;
    }

    // Newton's method to solve f(x') = 0
    public static double newtonMethod(double x, double t, double xPrimeGuess, int maxIter, double tol) {
        double xPrime = xPrimeGuess;
        for (int i = 0; i < maxIter; i++) {
            // Calculate f(x') and f'(x')
            double fxPrime = f(xPrime, x, t);
            double fxPrimePrime = fPrime(xPrime);

            // Update using Newton's method formula
            double xPrimeNext = xPrime - fxPrime / fxPrimePrime;

            // Check for convergence
            if (Math.abs(xPrimeNext - xPrime) < tol) {
                return xPrimeNext;
            }

            // Update the guess for x'
            xPrime = xPrimeNext;
        }

        // If no convergence, return the last guess
        return xPrime;
    }

    // Euler method to solve the ODE using Newton's method at each step
    public static double[] eulerMethodNewton(double x0, double t0, double tEnd, double h, int maxIter, double tol) {
        // Number of steps
        int numSteps = (int) ((tEnd - t0) / h);
        
        // Initialize arrays to store time and solution
        double[] tValues = new double[numSteps + 1];
        double[] xValues = new double[numSteps + 1];
        tValues[0] = t0;
        xValues[0] = x0;

        // Initial guess for x'(0)
        double xPrimeGuess = 0.0;

        // Loop over the time steps
        for (int i = 0; i < numSteps; i++) {
            double t = tValues[i];
            double x = xValues[i];

            // Use Newton's method to solve for x' at this time step
            double xPrime = newtonMethod(x, t, xPrimeGuess, maxIter, tol);

            // Euler method: update x using the derivative x'
            xValues[i + 1] = xValues[i] + h * xPrime;

            // Update the guess for x'(t) for the next iteration
            xPrimeGuess = xPrime;

            // Update the time
            tValues[i + 1] = t + h;
        }

        return xValues;
    }

    public static void main(String[] args) {
        // Initial conditions and parameters
        double x0 = 1;  // x(0) = 1
        double t0 = 0;  // Start time
        double tEnd = 5;  // End time
        double h = 0.01;  // Step size for Euler method
        int maxIter = 100;  // Maximum iterations for Newton's method
        double tol = 1e-6;  // Convergence tolerance for Newton's method

        // Solve the IVP using Euler method with Newton's method for x'
        double[] xValues = eulerMethodNewton(x0, t0, tEnd, h, maxIter, tol);

        // Output the results
        System.out.println("t\t\tx(t)");
        for (int i = 0; i < xValues.length; i++) {
            System.out.printf("%.2f\t\t%.6f\n", t0 + i * h, xValues[i]);
        }
        System.out.println("Value at t = 0: " + xValues[0]);
        System.out.println("Value at t = 1: " + xValues[100]);
        System.out.println("Value at t = 2: " + xValues[200]);
        System.out.println("Value at t = 3: " + xValues[300]);
        System.out.println("Value at t = 4: " + xValues[400]);
        System.out.println("Value at t = 5: " + xValues[500]);
    }
}
