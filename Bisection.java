public class Bisection {
    private final double initial_interval;
    private final double given_root;
    private final int deci_places;
    private double left_bound;
    private double right_bound;
    private int iterations = 0;
    private double x = 0;
    private double final_root;
    
    public Bisection(double LInterval, double RInterval, double root, int decimals) {
        this.left_bound = LInterval;
        this.right_bound = RInterval;
        this.given_root = root;
        this.deci_places = decimals;
        initial_interval = RInterval - LInterval;
    }
    public double Bisection_Root(){
        do { 
            x = (left_bound + right_bound) / 2.0;   // +2 FPs
            if(function(x) * function(left_bound) < 0){ // +2 FPs
                right_bound = x;
            }
            else{
                left_bound = x;
            }
            iterations++;
        } while ((areEqual(x, given_root, deci_places) == false));
        final_root = (left_bound + right_bound) / 2.0; //+2 FPs
        return final_root;
    }
    @Override
    public String toString(){
        return "Bisection Method \nThe root is contained in the interval [" + left_bound + ", " + right_bound + "] \n" +
                "The approximate root is " + final_root + "\n" +
                "Iterations: " + iterations + "\n" +
                "Floating Point Operations: " + (iterations * 6 + 2) + "\n" +
                "The solution error " + (initial_interval/(Math.pow(2, iterations+1))) + "\n";
    }
    private static double function(double x){ // Change expression for different function
        return Math.exp(-1*Math.pow(x, 3)) - Math.pow(x, 4) - Math.sin(x); //16 FLOP
    }
    private static boolean areEqual(double x, double given_root, int decimalPlaces) {
        double epsilon = Math.pow(10, -decimalPlaces);
        return Math.abs(function(x) - 0) < epsilon;
    }
}
