//import org.apache.commons.math4.legacy.analysis.function.Gaussian;

public class RandomMatrix {
    private int size;
    private double[][] matrix;
    private double[][] b_vector;
    private double[][] x_vector;
    private double min = -1;
    private double max = 1;

    public RandomMatrix(int order) {
        this.size = order;
        this.matrix = new double[size][size+1];
        this.b_vector = new double[size][1];
        this.x_vector = new double[size][1];
        //generateRandomMatrix();
    }
    public void solveMatrix(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                matrix[i][j] = Math.random() * (max-min)+min;
            }
            matrix[i][size] = 1;
        }
        GaussianElimination gauss = new GaussianElimination(matrix);
        System.out.println("Solutions " + gauss.toString());
    }

    
}
