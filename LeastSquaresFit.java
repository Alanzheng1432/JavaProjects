
import java.util.Arrays;

public class LeastSquaresFit {
    private final double[] x; //X coordinates
    private final double[][] y; //Y coordinates
    private final int order;
    private double [][] A; // Inconsistent Matrix
    private double [][] A_Transpose; // Transpose of Inconsistent Matrix
    private double [][] Normal; // Normal Matrix
    private double [][] Y_Normal; // Normal Vector
    private double [][] Augmented;
    public double [] sol_vector; // Solution Vector


    public LeastSquaresFit(double[] x, double[][] y, int order) {
        this.x = x;
        this.y = y;
        this.order = order;
    }
    public double[] LeastSquares(){
        A = FillMatrix(x);
        A_Transpose = Transpose(A);
        Normal = multiplyMatrices(A_Transpose, A);
        Y_Normal = multiplyMatrices(A_Transpose, y);
        Augmented = new double[Normal.length][Normal[0].length + 1];
        for (int i = 0; i < Normal.length; i++) {
            for (int j = 0; j < Normal[0].length; j++) {
                Augmented[i][j] = Normal[i][j];
            }
        }
        for (int i = 0; i < Y_Normal.length; i++) {
            Augmented[i][Normal[0].length] = Y_Normal[i][0];
        }
        sol_vector = solve(Augmented);
        return sol_vector;
    }
    public double[][] FillMatrix(double a []){
        double [][] M = new double[x.length][order + 1];
        for (int i = 0; i < x.length; i++) {
            M[i][0] = 1;
            for (int j = 1; j < order + 1; j++) {
                M[i][j] = Math.pow(x[i], j);
            }
        }
        return M;
    }
    public double[][] Transpose(double [][] A){
        double [][] Transpose = new double[A[0].length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                Transpose[j][i] = A[i][j];
            }
        }
        return Transpose;
    }
    public double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        double[][] result = new double[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
    public double[] solve(double[][] augmentedMatrix) { //Gaussian  Elimination Sourced by GoogleAI
        int numRows = augmentedMatrix.length;
        int numCols = augmentedMatrix[0].length;

        // Forward elimination
        for (int i = 0; i < numRows; i++) {
            // Find pivot element
            int maxRow = i;
            for (int j = i + 1; j < numRows; j++) {
                if (Math.abs(augmentedMatrix[j][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                    maxRow = j;
                }
            }

            // Swap rows
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;

            // Eliminate elements below pivot
            for (int j = i + 1; j < numRows; j++) {
                double factor = augmentedMatrix[j][i] / augmentedMatrix[i][i];
                for (int k = i; k < numCols; k++) {
                    augmentedMatrix[j][k] -= factor * augmentedMatrix[i][k];
                }
            }
        }

        // Back substitution
        double[] solution = new double[numRows];
        for (int i = numRows - 1; i >= 0; i--) {
            solution[i] = augmentedMatrix[i][numCols - 1];
            for (int j = i + 1; j < numRows; j++) {
                solution[i] -= augmentedMatrix[i][j] * solution[j];
            }
            solution[i] /= augmentedMatrix[i][i];
        }

        return solution;
    }
    public double predict(double x){
        double result = 0;
        for (int i = 0; i < sol_vector.length; i++) {
            result += sol_vector[i] * Math.pow(x, i);
        }
        return result;
    }
    @Override
    public String toString(){
        return "Least Squares Fit \n" + Arrays.toString(sol_vector);
    }
}
