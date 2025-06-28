import java.util.Arrays;

public class PowerIteration {
    
    // Method to multiply a matrix A with a vector b
    public static double[] matrixVectorMultiply(double[][] A, double[] b) {
        int n = A.length;
        double[] result = new double[n];
        
        for (int i = 0; i < n; i++) {
            result[i] = 0;
            for (int j = 0; j < n; j++) {
                result[i] += A[i][j] * b[j];
            }
        }
        return result;
    }

    // Method to compute the Euclidean norm (length) of a vector
    public static double norm(double[] b) {
        double sum = 0;
        for (double value : b) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }

    // Method to compute the Rayleigh quotient for eigenvalue estimation
    public static double rayleighQuotient(double[] b, double[][] A) {
        double[] Ab = matrixVectorMultiply(A, b);
        double numerator = 0;
        double denominator = 0;
        
        for (int i = 0; i < b.length; i++) {
            numerator += b[i] * Ab[i];
            denominator += b[i] * b[i];
        }
        
        return numerator / denominator;
    }

    // Power iteration method
    public static double[] powerIteration(double[][] A, double[] b0, int numIterations) {
        double[] b = Arrays.copyOf(b0, b0.length); // Copy the initial vector b0
        double[] Ab;
        
        for (int i = 0; i < numIterations; i++) {
            // Matrix-vector multiplication
            Ab = matrixVectorMultiply(A, b);
            
            // Eigenvalue estimation (Rayleigh Quotient)
            double eigenvalue = rayleighQuotient(b, A);
            System.out.println("Iteration " + (i+1) + " - Eigenvalue estimate: " + eigenvalue);
            
            // Update the vector b (no normalization)
            b = Ab;
        }
        
        // Return the final vector (not normalized)
        return b;
    }

    public static void main(String[] args) {
        // Example matrix A
        double[][] A = {
            {4, 1,1},
            {2, 3, 1},
            {1, 2, 3}
        };
        
        // Initial vector b0 (can be chosen randomly)
        double[] b0 = {1, 1, 2};
        
        // Perform power iteration for 10 iterations
        int numIterations = 10;
        double[] dominantEigenvector = powerIteration(A, b0, numIterations);
        
        // Compute the final eigenvalue using the Rayleigh quotient
        double eigenvalue = rayleighQuotient(dominantEigenvector, A);
        System.out.println("Final estimated dominant eigenvalue: " + eigenvalue);
    }
}
