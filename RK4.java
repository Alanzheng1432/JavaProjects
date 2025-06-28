public class RK4 {
    private double step_size; //put as negative if x is decreasing
    private int iterations;
    private double[] k_val = new double[4];
    private double[] xtrajectory;
    private double[] ytrajectory;

    //ODE constants
    private double a;
    private double w = 44;
    private double v = 88;

    public RK4(double step_size, double x0, double y0, double finalx) {
        this.step_size = step_size;
        this.iterations = ((int) Math.abs(((finalx - x0) / step_size)) + 1);
        this.xtrajectory = new double[iterations +1];
        this.ytrajectory = new double[iterations+1];
        this.xtrajectory[0] = x0;
        this.ytrajectory[0] = y0;
    }
    public void RungeKutta4(){
        for (int i = 0; i < iterations - 1; i++) {
            k_value(xtrajectory[i], ytrajectory[i]);
            xtrajectory[i + 1] = xtrajectory[i] + step_size;
            this.ytrajectory[i + 1] = ytrajectory[i] + (k_val[0] + 2 * k_val[1] + 2 * k_val[2] + k_val[3]) / 6;
            
        }
    }
    private double[] k_value(double x, double y){
        this.k_val[0] = step_size * ODE(x, y);
        this.k_val[1] = step_size * ODE(x + step_size / 2, y + k_val[0] / 2);
        this.k_val[2] = step_size * ODE(x + step_size / 2, y + k_val[1] / 2);
        this.k_val[3] = step_size * ODE(x + step_size, y + k_val[2]);
        return k_val;
    }
    private double ODE(double x, double y) { //dy/dx
        return (y/x - (w/v)*Math.sqrt(1+Math.pow(y/x,2)));
    }  
    public static void main(String[] args) {
        double step_size = -0.1;
        double x0 = 100;
        double y0 = 0;
        double finalx = 0;
        RK4 rk4 = new RK4(step_size, x0, y0, finalx);
        rk4.RungeKutta4();
        /*for(int i = 0; i < rk4.iterations; i++) {
            System.out.println("(x,y): (" + rk4.xtrajectory[i] + "," + rk4.ytrajectory[i] + ")");
        } 
        /*for (int i = 0; i < rk4.iterations; i++) {
            System.out.println("x: " + rk4.xtrajectory[i]);
        } */
         
        for(int i = 0; i < rk4.iterations; i++) {
            System.out.println(rk4.ytrajectory[i]);
        } 
    }
}
