public class Secant {
    private double x0;
    private double x1;
    private final double given_root;
    private final int decimalPlaces;
    private double x;
    private double x2;
    private int iterations = 0;

    public Secant(double x0, double x1, double root, int decimals){
        this.x0 = x0;
        this.x1 = x1;
        this.given_root = root;
        this.decimalPlaces = decimals;
    }
    public double Secant_root(){
        do{
            x2 = x1 - function(x1) * ((x1 - x0) / (function(x1) - function(x0)));
            x0 = x1;
            x1 = x2;
            iterations++;
        } while(areEqual(x2, given_root, decimalPlaces) == false);
        x = x2;
        return x;
    }
    @Override
    public String toString(){
        return "Secant Method \nThe approximate root is " + x + "\n" +
                "Iterations: " + iterations + "\n";
    }
    public static double function(double x){ // Change Function below to the desired function
        return Math.exp(-1*Math.pow(x, 3)) - Math.pow(x, 4) - Math.sin(x);
    }
    private static boolean areEqual(double x, double given_root, int decimalPlaces) {
        double epsilon = Math.pow(10, -decimalPlaces);
        return Math.abs(function(x) - 0) < epsilon;
    }
}
