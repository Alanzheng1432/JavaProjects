import java.util.Random;

public class GradientWeightedMonteCarlo {

    static final int GRID_SIZE = 500; // resolution of precomputed grid
    static final int N = 1000000;   // number of samples
    static final double[][] gradMag = new double[GRID_SIZE][GRID_SIZE];
    static final double[][] xGrid = new double[GRID_SIZE][GRID_SIZE];
    static final double[][] yGrid = new double[GRID_SIZE][GRID_SIZE];
    static final double[] gradMagFlat;
    static final double[] cumDist;

    static {
        // Precompute the grid and gradient magnitude
        double dx = 2.0 / GRID_SIZE;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                double x = -1 + dx * (i + 0.5);
                double y = -1 + dx * (j + 0.5);
                xGrid[i][j] = x;
                yGrid[i][j] = y;
                gradMag[i][j] = gradientMagnitude(x, y);
            }
        }

        // Flatten gradMag and compute cumulative distribution for sampling
        gradMagFlat = new double[GRID_SIZE * GRID_SIZE];
        cumDist = new double[GRID_SIZE * GRID_SIZE];
        double total = 0;
        for (int k = 0; k < gradMagFlat.length; k++) {
            int i = k / GRID_SIZE;
            int j = k % GRID_SIZE;
            gradMagFlat[k] = gradMag[i][j];
            total += gradMagFlat[k];
        }
        for (int k = 0; k < gradMagFlat.length; k++) {
            gradMagFlat[k] /= total;
            cumDist[k] = gradMagFlat[k] + (k == 0 ? 0 : cumDist[k - 1]);
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        double sum = 0.0;

        for (int n = 0; n < N; n++) {
            // Sample index based on gradient magnitude distribution
            int idx = sampleIndex(rand.nextDouble());
            int i = idx / GRID_SIZE;
            int j = idx % GRID_SIZE;

            double x = xGrid[i][j];
            double y = yGrid[i][j];

            // Estimate: weight = u(x, y) / p(x, y)
            double p = gradMag[i][j]; // unnormalized
            sum += u(x, y) / (p + 1e-7); // avoid divide by 0
        }

        double totalGrad = 0.0;
        for (double val : gradMagFlat) totalGrad += val;

        double estimate = 4.0 * sum / (N * totalGrad);
        System.out.printf("Gradient-weighted Monte Carlo estimate: %.6f%n", estimate);
    }

    public static double u(double x, double y) {
        return Math.exp(Math.pow(x, 6) - Math.pow(y, 4));
    }

    public static double gradientMagnitude(double x, double y) {
        double du_dx = -6 * Math.pow(x, 5) * u(x, y);
        double du_dy = -6 * Math.pow(y, 5) * u(x, y);
        return Math.sqrt(du_dx * du_dx + du_dy * du_dy) + 1e-7; // avoid divide by 0
    }

    // Binary search to sample from cumulative distribution
    public static int sampleIndex(double r) {
        int low = 0, high = cumDist.length - 1;
        while (low < high) {
            int mid = (low + high) / 2;
            if (r < cumDist[mid]) high = mid;
            else low = mid + 1;
        }
        return low;
    }
}
