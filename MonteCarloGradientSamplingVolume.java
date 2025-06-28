import java.util.Random;

public class MonteCarloGradientSamplingVolume {
    static Random rand = new Random();

    public static void main(String[] args) {
        int numSamples = 1000000; // Number of samples
        double sum = 0.0;

        for (int i = 0; i < numSamples; i++) {
            // Step 1: Sample uniformly in [-1, 1] x [-1, 1]
            double x = 2 * rand.nextDouble() - 1; // Uniform sample between -1 and 1
            double y = 2 * rand.nextDouble() - 1;

            // Step 2: Compute gradient magnitude at (x, y)
            double gradMag = gradientMagnitude(x, y);

            // Step 3: Compute the acceptance probability (we use the gradient magnitude)
            double maxGrad = 6 * Math.exp(-1); // Upper bound for gradient magnitude
            double threshold = rand.nextDouble() * maxGrad;

            // Step 4: Rejection sampling based on gradient magnitude
            if (gradMag > threshold) {
                // The weight for the sample is the inverse of the probability (gradient magnitude)
                double weight = 1.0 / gradMag;
                sum += u(x, y) * weight;
            }
        }

        // The domain area (since we are sampling in [-1,1] x [-1,1], the area is 4)
        double domainArea = 4.0;
        
        // Compute the final volume estimate
        double estimatedVolume = sum / numSamples;
        
        // Output the result
        System.out.printf("Estimated volume under u(x, y) = e^-(x^6 + y^6) with gradient-based sampling: %.6f%n", estimatedVolume);
    }

    // Function u(x, y) = e^-(x^6 + y^6)
    public static double u(double x, double y) {
        return Math.exp((Math.pow(x, 6) - Math.pow(y, 4)));
    }

    // Compute the gradient magnitude at point (x, y)
    public static double gradientMagnitude(double x, double y) {
        double u = u(x, y);
        double du_dx = -6 * Math.pow(x, 5) * u;
        double du_dy = -6 * Math.pow(y, 5) * u;
        return Math.sqrt(du_dx * du_dx + du_dy * du_dy);
    }
}
