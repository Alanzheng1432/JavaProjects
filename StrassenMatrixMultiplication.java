import java.io.*;
import java.util.Random;

public class StrassenMatrixMultiplication {
    static final int n = 1 << 10; // 1024
    static final int THRESHOLD_DEPTH = 2;

    public static void main(String[] args) {
        // Step 1: Generate two random n x n matrices A and B
        float[][] A = generateRandomMatrix(n);
        float[][] B = generateRandomMatrix(n);

        // Step 2: Perform Strassen's Matrix Multiplication
        float[][] result = strassenMultiply(A, B, 0);

        // Step 3: Write matrices A, B, and result to CSV files
        try {
            writeMatrixToCSV(A, "matrix_A.csv");
            writeMatrixToCSV(B, "matrix_B.csv");
            writeMatrixToCSV(result, "matrix_result.csv");

            System.out.println("Matrices written to files.");

            // Step 4: Calculate and write the inverse of the result matrix
            float[][] resultInverse = invertMatrix(result);
            writeMatrixToCSV(resultInverse, "matrix_result_inverse.csv");
            System.out.println("Matrix inverse written to matrix_result_inverse.csv.");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Generates a random n x n matrix with elements in the range [-1, 1]
    static float[][] generateRandomMatrix(int size) {
        Random rand = new Random();
        float[][] matrix = new float[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = rand.nextFloat() * 2 - 1; // range [-1, 1]
        return matrix;
    }

    // Perform Strassen's Matrix Multiplication
    static float[][] strassenMultiply(float[][] A, float[][] B, int depth) {
        int size = A.length;
        if (depth >= THRESHOLD_DEPTH || size <= 256) {
            return standardMultiply(A, B);
        }

        int newSize = size / 2;
        float[][][] subMatrices = splitMatrices(A, B, newSize);

        // Calculate M1 to M7 (Strassen's recursive multiplications)
        float[][] M1 = strassenMultiply(add(subMatrices[0], subMatrices[3]), add(subMatrices[4], subMatrices[7]), depth + 1); // M1 = (A11 + A22) * (B11 + B22)
        float[][] M2 = strassenMultiply(add(subMatrices[2], subMatrices[3]), subMatrices[4], depth + 1);                     // M2 = (A21 + A22) * B11
        float[][] M3 = strassenMultiply(subMatrices[0], subtract(subMatrices[5], subMatrices[7]), depth + 1);              // M3 = A11 * (B12 - B22)
        float[][] M4 = strassenMultiply(subMatrices[3], subtract(subMatrices[6], subMatrices[4]), depth + 1);              // M4 = A22 * (B21 - B11)
        float[][] M5 = strassenMultiply(add(subMatrices[0], subMatrices[1]), subMatrices[7], depth + 1);                   // M5 = (A11 + A12) * B22
        float[][] M6 = strassenMultiply(subtract(subMatrices[2], subMatrices[0]), add(subMatrices[4], subMatrices[5]), depth + 1); // M6 = (A21 - A11) * (B11 + B12)
        float[][] M7 = strassenMultiply(subtract(subMatrices[1], subMatrices[3]), add(subMatrices[6], subMatrices[7]), depth + 1); // M7 = (A12 - A22) * (B21 + B22)

        // Combine the results to get the final matrix
        float[][] C11 = add(subtract(add(M1, M4), M5), M7);
        float[][] C12 = add(M3, M5);
        float[][] C21 = add(M2, M4);
        float[][] C22 = add(subtract(add(M1, M3), M2), M6);

        return combineMatrices(C11, C12, C21, C22);
    }

    // Standard matrix multiplication (for base case of recursion)
    static float[][] standardMultiply(float[][] A, float[][] B) {
        int n = A.length;
        float[][] result = new float[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    result[i][j] += A[i][k] * B[k][j];
        return result;
    }

    // Split the matrices A and B into submatrices for Strassen multiplication
    static float[][][] splitMatrices(float[][] A, float[][] B, int newSize) {
        float[][] A11 = new float[newSize][newSize];
        float[][] A12 = new float[newSize][newSize];
        float[][] A21 = new float[newSize][newSize];
        float[][] A22 = new float[newSize][newSize];
        float[][] B11 = new float[newSize][newSize];
        float[][] B12 = new float[newSize][newSize];
        float[][] B21 = new float[newSize][newSize];
        float[][] B22 = new float[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + newSize];
                A21[i][j] = A[i + newSize][j];
                A22[i][j] = A[i + newSize][j + newSize];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + newSize];
                B21[i][j] = B[i + newSize][j];
                B22[i][j] = B[i + newSize][j + newSize];
            }
        }

        return new float[][][]{A11, A12, A21, A22, B11, B12, B21, B22};
    }

    // Matrix addition
    static float[][] add(float[][] A, float[][] B) {
        int n = A.length;
        float[][] result = new float[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                result[i][j] = A[i][j] + B[i][j];
        return result;
    }

    // Matrix subtraction
    static float[][] subtract(float[][] A, float[][] B) {
        int n = A.length;
        float[][] result = new float[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                result[i][j] = A[i][j] - B[i][j];
        return result;
    }

    // Combine four submatrices into one result matrix
    static float[][] combineMatrices(float[][] C11, float[][] C12, float[][] C21, float[][] C22) {
        int newSize = C11.length;
        int size = newSize * 2;
        float[][] result = new float[size][size];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                result[i][j] = C11[i][j];
                result[i][j + newSize] = C12[i][j];
                result[i + newSize][j] = C21[i][j];
                result[i + newSize][j + newSize] = C22[i][j];
            }
        }
        return result;
    }

    // Write the matrix to a CSV file
    static void writeMatrixToCSV(float[][] matrix, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(String.format("%.6f", matrix[i][j]));
                    if (j < matrix[i].length - 1) writer.write(",");
                }
                writer.newLine();
            }
        }
    }

    // Inverts the matrix using Gauss-Jordan Elimination
    static float[][] invertMatrix(float[][] matrix) {
        int n = matrix.length;
        float[][] augmented = new float[n][2 * n];
        
        // Create the augmented matrix [matrix | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
                augmented[i][j + n] = (i == j) ? 1 : 0;
            }
        }

        // Perform the Gauss-Jordan Elimination
        for (int i = 0; i < n; i++) {
            // Find pivot element
            if (augmented[i][i] == 0) {
                System.out.println("Matrix is singular and cannot be inverted.");
                return null;  // Matrix is singular, no inverse
            }
            
            // Normalize the pivot row
            float pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }

            // Eliminate the current column in other rows
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    float factor = augmented[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmented[j][k] -= factor * augmented[i][k];
                    }
                }
            }
        }

        // Extract the inverse matrix from the augmented matrix
        float[][] inverse = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmented[i][j + n];
            }
        }

        return inverse;
    }
}
