public class MatrixMultiplicationNaive {
    private int size;
    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixC;
    
    public MatrixMultiplicationNaive(double [][]A, double [][]B){
        this.matrixA = A;
        this.matrixB = B;
        this.matrixC = new double[A.length][B[0].length];
        this.size = A.length;
        multiplyMatrices();
    }
    public double [][] multiplyMatrices(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                for(int k=0; k<size; k++){
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixC;
    }
    public String toString(){
        for(int i =0; i < matrixC.length; i++){
            for(int j = 0; j < matrixC[0].length; j++){
                System.out.print(matrixC[i][j] + " ");
            }
            System.out.println();
        }
        return "Naive Matrix Multiplication";
    }
    public static void main(String[] args) {
        double[][] A = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };

        double[][] B = {
            {17, 18, 19, 20},
            {21, 22, 23, 24},
            {25, 26, 27, 28},
            {29, 30, 31, 32}
        };
        MatrixMultiplicationNaive matrixMultiplicationNaive = new MatrixMultiplicationNaive(A, B);
        System.out.println("Result of Naive Matrix Multiplication:" + matrixMultiplicationNaive.toString());
    }
}
