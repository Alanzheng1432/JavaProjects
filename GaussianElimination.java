public class GaussianElimination {
    double [][] augmentedMatrix;
    double [] solution;
    public GaussianElimination(double [][]A) {
        this.augmentedMatrix = A;
        this.solution = solve();
    }

    public double[] solve() { 
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
        solution = new double[numRows];
        for (int i = numRows - 1; i >= 0; i--) {
            solution[i] = augmentedMatrix[i][numCols - 1];
            for (int j = i + 1; j < numRows; j++) {
                solution[i] -= augmentedMatrix[i][j] * solution[j];
            }
            solution[i] /= augmentedMatrix[i][i];
        }

        return solution;
    }
    @Override
    public String toString() {
        for(int i =0; i < solution.length; i++){
            System.out.println("x_" + i + " = " + solution[i]);
        }
        return "";
    } 
    public static void main(String[] args) {
        double[][] A = {{1,2,-1,5},{2,-1,3,1},{-1,3,2,4}};
        GaussianElimination gauss = new GaussianElimination(A);
        System.out.println("Solution: " + gauss.toString());
        
    }
}
