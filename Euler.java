public class Euler {
    private double step_size;
    private double x0;
    private double y0;
    private double finalx;
    private int iterations;
    private double [] xtrajectory;
    private double [] ytrajectory;

    //ODE constants
    private double a;
    private double w = 44;
    private double v = 88;
    
    public Euler(double step_size, double x0, double y0, double finalx) {
        this.step_size = step_size;
        this.x0 = x0;
        this.y0 = y0;
        this.finalx = finalx;
        this.iterations = ((int) Math.abs(((finalx - x0) / step_size)) + 1);
        this.xtrajectory = new double[iterations +1];
        this.ytrajectory = new double[iterations+1];
        this.xtrajectory[0] = x0;
        this.ytrajectory[0] = y0;
    }
    public void solve_Euler(){
        for (int i = 0; i < iterations - 1; i++) {
            xtrajectory[i + 1] = xtrajectory[i] + step_size;
            this.ytrajectory[i + 1] = ytrajectory[i] + step_size * ODE(xtrajectory[i], ytrajectory[i]);
        }
    }
    private double ODE(double x, double y) { //dy/dx
        return (y/x - (w/v)*Math.sqrt(1+Math.pow(y/x,2)));
    }
    public static void main(String[] args) {
        double step_size = -0.1;
        double x0 = 100;
        double y0 = 0;
        double finalx = 0;
        Euler euler = new Euler(step_size, x0, y0, finalx);
        euler.solve_Euler();
        /*for (int i = 0; i < rk4.iterations; i++) {
            System.out.println("x: " + rk4.xtrajectory[i]);
        } */
        for(int i = 0; i < euler.iterations; i++) {
            System.out.println(euler.ytrajectory[i]);
        }
    }
}
