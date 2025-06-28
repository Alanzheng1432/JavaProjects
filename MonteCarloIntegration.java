import java.util.Random;

public class MonteCarloIntegration {

    public static double u(double x, double y) {
        return Math.exp(-Math.pow(x, 6) - Math.pow(y, 6));
    }

    public static void main(String[] args) {
        int N = 10000000;  // Number of samples
        double sum = 0.0;
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            double x = -1 + 2 * rand.nextDouble(); // Random x in [-1, 1]
            double y = -1 + 2 * rand.nextDouble(); // Random y in [-1, 1]
            sum += u(x, y);
        }

        double area = 4.0; // Area of the square domain [-1,1] x [-1,1]
        double volumeEstimate = area * sum / N;

        System.out.printf("Estimated volume under u(x, y): %.6f%n", volumeEstimate);
    }
}

