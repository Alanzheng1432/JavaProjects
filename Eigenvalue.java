
import java.util.Arrays;

public class Eigenvalue {
    private double [][] A;
    private double [] bk;
    private double [] bkn;
    private double [] ev_evolution;
    private int k;

    public Eigenvalue(double[][]A, double [] b, int k) {
        this.A = A;
        this.bk = b;
        this.bkn = new double[b.length];
        this.ev_evolution = new double[k];
        this.k = k;
    }

    private double[] dominantEigenvalue(){
        for(int i = 0; i<k; i++){
            bkn = multiply(bk);
            ev_evolution[i] = estimateEigenvalue();
            bk = Arrays.copyOf(bkn, bkn.length);
        }  
        return ev_evolution; 
    }
    public double [] multiply(double [] vector){ //Multiply a matrix by a vector and Normalize the result
        double sum = 0;
        double [] c = new double[vector.length];
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                c[i] += A[i][j] * vector[j];
            }
        }
        for(int k = 0; k < c.length; k++){
            sum += Math.pow(c[k], 2);
        }
        sum = Math.sqrt(sum);
        for(int i = 0; i < c.length; i++){
            c[i] = c[i]/sum;
        }
        return c;
    }

    public double estimateEigenvalue(){
        double numerator = 0;
        double denominator = 0;

        double [] d = new double[bk.length];
        d = multiply(bkn);
        for(int i = 0; i < d.length; i++){
            numerator += d[i] * bkn[i];
            denominator += bkn[i] * bkn[i];
        }
        return numerator/denominator; //Estimated Eigenvalue
    }
    public static void main(String[] args) {
        double [][] A = {{3, 1, -1},
                         {2, 4, 1},
                         {-1, 2, 5}};
        double [] b = {4,1,1};
        int k = 1000;
        Eigenvalue ev = new Eigenvalue(A, b, k);
        double [] eigenvalue = ev.dominantEigenvalue();
        System.out.println("Eigenvalue: " + eigenvalue[k-1]);
    }
}
