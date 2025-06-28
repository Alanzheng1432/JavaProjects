public class GaussSeidel{
    private double A [][];
    private double L [][]; // Lower triangular matrix
    private double U [][]; // Upper triangular matrix
    private double D [][]; // Diagonal matrix
    private double b []; // Right hand side
    private double xk []; // Solution
    private double x0 []; // Initial guess
    private int iterations;
    
    public GaussSeidel(double [][] Matrix, double []RHS, double [] InitialGuess, int loops){
        this.A = Matrix;
        this.L = new double[A.length][A[0].length];
        this.U = new double[A.length][A[0].length];
        this.D = new double[A.length][A[0].length];
        this.b = RHS;
        this.x0 = InitialGuess;
        this.xk = new double[A.length];
        this.iterations = loops;
    }
    public void Decompose(){
        for(int i =0; i<A.length; i++){
            for(int j = 0; j<A[0].length; j++){
                if(i>j){
                    L[i][j] = A[i][j];
                }
                else if(i<j){
                    U[i][j] = A[i][j];
                }
                else{
                    D[i][j] = A[i][j];
                }
            }
        }
    }
    public double [] solveGaussSeidel(){
        Decompose();
        for(int runs = 0; runs < iterations; runs++){
            for(int i = 0; i < xk.length; i++){
                double sum = b[i];
                for(int j = i; j < A.length; j++){ // U matrix
                    sum -= U[i][j] * x0[j]; 
                }
                for(int j = 0; j <= i-1; j++){ //L matrix
                    sum -= L[i][j] * xk[j];
                }
                xk[i] = (sum)/D[i][i];
            }
            x0 = xk.clone();
        }
        return xk;
    }
    public String toString(){
        String str = "";
        for(int i = 0; i<xk.length; i++){
            str += "x"+i+" = "+xk[i]+"\n";
        }
        return str;
    }
    public static void main(String[] args) {
        double [][] A = {{3, 1, -1},
                         {2, 4, 1},
                         {-1, 2, 5}};
        double [] b = {4,1,1};
        double [] x0 = {0,0,0};
        GaussSeidel GS = new GaussSeidel(A, b, x0, 13);
        GS.solveGaussSeidel();
        System.out.println(GS.toString());
        
    }
}