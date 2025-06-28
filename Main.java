public class Main {
  public static void main(String[] args) {
    double [][] A = {{3, 1, -1},
                         {2, 4, 1},
                         {-1, 2, 5}};
        double [] b = {4,1,1};
        double [] x0 = {0,0,0};
        GaussSeidel GS = new GaussSeidel(A, b, x0, 3);
        GS.solveGaussSeidel();
        System.out.println(GS.toString());
  }
}
  
  
