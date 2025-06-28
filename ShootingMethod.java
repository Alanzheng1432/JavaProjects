public class ShootingMethod {
    private double ya; // initial value at a
    private double yb; // final value at b
    private double a; // initial point
    private double b; // final point

    private double step_size; //put as negative if x is decreasing
    private int iterations;
    private double[] k_val_y = new double[4];
    private double[] k_val_v = new double[4];
    private double[] xtrajectory;
    private double[] ytrajectory;
    //private double v;
    
    private double epsilon;
    private double s0;
    private double s1;

    public ShootingMethod(double ya, double yb, double a, double b, double h, double shootingspeed1, double shootingspeed2, double epsilon) {
        this.ya = ya;
        this.yb = yb;
        this.a = a;
        this.b = b;
        this.step_size = h;
        this.iterations = ((int) Math.abs(((b - a) / h)) + 1);
        this.xtrajectory = new double[iterations +1];
        this.ytrajectory = new double[iterations+1];
        this.xtrajectory[0] = a;
        this.ytrajectory[0] = ya;
        this.s0 = shootingspeed1;
        this.s1 = shootingspeed2;
    }
    //ODE: y'' + xy = 0
    private double vprime (double x, double y){ //dv/dx
        return -1*x*y;
    }
    private double yprime (double v){ //dy/dx
        return v;
    }
    public double RungeKutta4(double v){ //returns F(v) = y(b,v) - yb or the Error
        for (int i = 0; i < iterations - 1; i++) {
            xtrajectory[i + 1] = xtrajectory[i] + step_size;
            k_valueY(xtrajectory[i], ytrajectory[i], v);
            ytrajectory[i + 1] = ytrajectory[i] + (k_val_y[0] + 2 * k_val_y[1] + 2 * k_val_y[2] + k_val_y[3]) / 6;
            k_valueV(xtrajectory[i], ytrajectory[i], v);
            v = v + (k_val_v[0] + 2 * k_val_v[1] + 2 * k_val_v[2] + k_val_v[3]) / 6;
        }
        //System.out.println("Iterations" + iterations + "Error" +(ytrajectory[iterations - 1] - yb));
        return ytrajectory[iterations - 1] - yb;
    }
    public double getShootingSpeed() {
        double f0 = RungeKutta4(s0); //Prelim Run1
        double f1 = RungeKutta4(s1); //Prelim Run2

        for(int i = 0; i < 3; i++){  //set max iterations to be low if linear
            double s2 = s1 - (f1 * (s1 - s0)) / (f1 - f0);
            System.out.println("Shooting speed: " + s2);
            double f2 = RungeKutta4(s2);
            f0 = f1;
            System.out.println("f0: " + f0); //testing purposes
            f1 = f2;
            System.out.println("f1: " + f1); //testing purposes
            s0 = s1;
            System.out.println("s0: " + s0); //testing purposes
            s1 = s2;
            System.out.println("s1: " + s1+ "\n"); //testing purposes
            if (Math.abs(s1-s0)< epsilon) {
                System.out.println("Converged to shooting speed: " + s1);
                break;
            }
        }
        return s1;
    }
    private void k_valueY(double x, double y, double v) {
        k_val_y[0] = step_size * yprime(v);
        k_val_y[1] = step_size * yprime(v + 0.5 * k_val_y[0]);
        k_val_y[2] = step_size * yprime(v + 0.5 * k_val_y[1]);
        k_val_y[3] = step_size * yprime(v + k_val_y[2]);
    }
    private void k_valueV(double x, double y, double v) {
        k_val_v[0] = step_size * vprime(x, y);
        k_val_v[1] = step_size * vprime(x + step_size/2, y + 0.5 * k_val_v[0]);
        k_val_v[2] = step_size * vprime(x+ step_size/2 , y + 0.5 * k_val_v[1]);
        k_val_v[3] = step_size * vprime(x + step_size, y + k_val_v[2]);
    }
    public static void main(String[] args) {
        double ya = 1; // initial value at a
        double yb = 2; // final value at b
        double a = 0; // initial point
        double b = 2; // final point
        double step_size = 0.01;
        double shootingspeed1 = 0;
        double shootingspeed2 = 1;
        double epsilon = 0.001;

        ShootingMethod sm = new ShootingMethod(ya, yb, a, b, step_size, shootingspeed1, shootingspeed2, epsilon);
        System.out.println("Shooting speed: " + sm.getShootingSpeed());
    
        /*for(int i = 0; i < sm.iterations; i++){
            System.out.println(sm.ytrajectory[i]);
        }*/
        for (int i = 0; i < sm.iterations; i++) {
            System.out.println("x: " + sm.xtrajectory[i] + ", y: " + sm.ytrajectory[i]);
        }
    }
}
