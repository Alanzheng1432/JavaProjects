import java.util.Arrays;

public class MonteCarloCutter {
    private double[] Parameters; //(x,y,alpha)0
    private double[] PreviousParameters; //(x,y,alpha)1
    private double iterations;
    private double T0;
    private double T;
    private double oldArea = 0;
    private double proposed_area = 0;
    private int n; // Number of points in the grid
    private double xmin; // Minimum x value
    private double xmax; // Maximum x value
    private double ymin; // Minimum y value
    private double ymax; // Maximum y value
    private double dx; // Width of each grid cell
    private double dy; // Height of each grid cell
    private double acceptances = 0;
    private int k;

    public MonteCarloCutter(double[] Parameters, double iterations, double T0, int gridSize, double xmin, double xmax, double ymin, double ymax) {
        this.Parameters = Parameters;
        this.PreviousParameters = Parameters;
        this.iterations = iterations;
        this.T0 = T0;
        this.T = T0;
        this.n = gridSize;  //Number of Mesh Points for Area Calculation
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.dx = (xmax - xmin) / n;
        this.dy = (ymax - ymin) / n;
        this.oldArea = computeArea();
    }
    public void monteCarlo(){
        for(int i =0; i < iterations; i++){
            new_parameters();
            this.proposed_area = computeArea();
            if (Math.exp((proposed_area - oldArea) / T) >= 1) {
                this.oldArea = proposed_area;
                this.PreviousParameters = Arrays.copyOf(Parameters, Parameters.length);
                acceptances++;
            }
            else{
                if(Math.random() < (Math.exp((proposed_area - oldArea) / T))){
                    this.oldArea = proposed_area;
                    this.PreviousParameters = Arrays.copyOf(Parameters, Parameters.length);
                    acceptances++;
                }
                else{
                    Parameters = Arrays.copyOf(PreviousParameters, PreviousParameters.length);
                }
            }
        }
    }
    private void new_parameters(){
        double min = -0.01;
        double max = 0.01;
        //this.T=T0/((k+2)*Math.log(k+2)); //Temperature decay
        this.T = T0 / (k);
        //this.T = T0 / Math.log(2+k); //Avoid division by zero
        Parameters[0] += min + (Math.random() * (max - min));
        Parameters[1] += min + (Math.random() * (max - min));
        Parameters[2] += min + (Math.random() * (max - min)); // Ensure alpha is between 0 and 2π
        Parameters[2] = Parameters[2] % (2 * Math.PI); // Wrap around to keep alpha within [0, 2π]
        k++;
        //return Parameters;
    }
    private static boolean isInsideDomain(double x, double y) {
        double leftSide = Math.pow(x * x + y * y, 3);
        double rightSide = 4*Math.pow(x, 2) * Math.pow(y, 2);
        return ((leftSide - rightSide) <= 0.0001); // Allow some tolerance for numerical precision
    }
    private double computeArea(){
        double new_area = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x = xmin + (i + 0.5) * dx;
                double y = ymin + (j + 0.5) * dy;

                double xTransformed = (x * Math.cos(Parameters[2]) - y * Math.sin(Parameters[2])) + Parameters[0];
                double yTransformed = (x * Math.sin(Parameters[2]) + y * Math.cos(Parameters[2]))+ Parameters[1];
                if (isInsideDomain(xTransformed, yTransformed)) {
                    new_area += 1.0;
                }
            }
        }
        new_area *= dx * dy;
        //System.out.println("Proposed Area: " + proposed_area + "\n"+ "Iteration: " + k);
        return new_area;
    }
    public String toString() {
        return "Final Area Parameters: " + Arrays.toString(Parameters) + "\n" +
                "Area: " + oldArea + "\n" +
                "Acceptance Rate of State Proposals: " + (acceptances / iterations) * 100 + "%\n";
    }
    public static void main(String[] args) {
        double initalx = (Math.random() * 2) -1;
        double initialy = (Math.random() * 2) -1;
        double initialalpha = (Math.random() * Math.PI);
        double[] Parameters = {initalx, initialy, initialalpha}; // Initial parameters (x, y, alpha)
        double iterations = 10000; // Number of iterations
        double T0 = 0.35; // Initial temperature
        int gridSize = 200; // Size of the grid
        double xmin = -0.5; // Minimum x value
        double xmax = 0.5; // Maximum x value
        double ymin = -1/(Math.sqrt(8)); // Minimum y value
        double ymax = 1/(Math.sqrt(8)); // Maximum y value

        MonteCarloCutter mcc = new MonteCarloCutter(Parameters, iterations, T0, gridSize, xmin, xmax, ymin, ymax);
        mcc.monteCarlo();
        System.out.println(mcc.toString());
    }
}
