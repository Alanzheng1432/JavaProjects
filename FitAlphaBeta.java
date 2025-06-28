public class FitAlphaBeta {

    // Model: y = 1 + alpha * t * exp(-beta * t)
    public static double model(double t, double alpha, double beta) {
        return 1 + alpha * t * Math.exp(-beta * t);
    }

    // Compute loss (sum of squared residuals)
    public static double loss(double[] t, double[] y, double alpha, double beta) {
        double sum = 0;
        for (int i = 0; i < t.length; i++) {
            double pred = model(t[i], alpha, beta);
            double diff = y[i] - pred;
            sum += diff * diff;
        }
        return sum;
    }

    // Fit alpha and beta using gradient descent
    public static double[] fit(double[] t, double[] y, double alpha0, double beta0, double lr, int maxIter) {
        double alpha = alpha0;
        double beta = beta0;

        for (int iter = 0; iter < maxIter; iter++) {
            double gradAlpha = 0;
            double gradBeta = 0;

            for (int i = 0; i < t.length; i++) {
                double expTerm = Math.exp(-beta * t[i]);
                double pred = 1 + alpha * t[i] * expTerm;
                double error = pred - y[i];

                // Partial derivatives
                gradAlpha += 2 * error * t[i] * expTerm;
                gradBeta += 2 * error * (-alpha * t[i] * t[i] * expTerm);
            }

            // Update parameters
            alpha -= lr * gradAlpha;
            beta -= lr * gradBeta;

            // Optionally print progress
            if (iter % 100 == 0) {
                double currentLoss = loss(t, y, alpha, beta);
                System.out.printf("Iter %d: Loss = %.6f, alpha = %.6f, beta = %.6f%n",
                        iter, currentLoss, alpha, beta);
            }
        }

        return new double[]{alpha, beta};
    }

    public static void main(String[] args) {
        // Example data points: (t, y)
        double[] t = {0, 1, 2, 3, 4, 5};
        double[] y = {1, 1.4226300865076322, 1.1038664733146537, 1.0218396109601968, 1.0047668827645562, 1.001049147237095};

        // Initial guesses
        double alpha0 = 1.0;
        double beta0 = 1.0;
        double learningRate = 0.01;
        int maxIterations = 250000;

        // Fit the model
        double[] params = fit(t, y, alpha0, beta0, learningRate, maxIterations);
        double alpha = params[0];
        double beta = params[1];

        System.out.printf("\nFitted parameters:\nalpha = %.6f\nbeta  = %.6f\n", alpha, beta);

        // Show fit
        System.out.println("\nt\tObserved y\tFitted y");
        for (int i = 0; i < t.length; i++) {
            double yFit = model(t[i], alpha, beta);
            System.out.printf("%.2f\t%.4f\t\t%.4f%n", t[i], y[i], yFit);
        }
    }
}
