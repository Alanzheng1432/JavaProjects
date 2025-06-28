public class Newtons {
    private double root;
    private int iterations = 0;
    
    public Newtons(double first_guess){
        this.root = first_guess;
    }
    public double newton_Root(){
        for(int i = 0; i < 100; i++) {
            root = root - (function(root) / first_derivative(root));
            iterations++;
        }
        /*do { 
            root = root - function(root) / first_derivative(root);
            iterations++;
        } while ((areEqual(root, given_root, deci_places) == false)); */
        return root;
    }
    public static double function(double x){ // Change Function below to the desired function
        return Math.pow(x, 3) / 6 - Math.pow(x, 4) / 24 - Math.pow(x,2) / 6 + 1/45;
    }
    public static double first_derivative(double x){ // Change derivative below to the derivative of the desired function
        return Math.pow(x, 2) / 2 - Math.pow(x, 3) / 6 - Math.pow(x, 1) / 3;
    }
    @Override
    public String toString(){
        return  "Newton's Method: \nThe approximate root is " + root + "\n" +
                "Iterations: " + iterations + "\n";
    }
    private static boolean areEqual(double x, double given_root, int decimalPlaces) {
        double epsilon = Math.pow(10, -decimalPlaces);
        return Math.abs(function(x) - 0) < epsilon;
    }
    public static void main(String[] args) {
        double first_guess = 0.7; // Initial guess
        Newtons newton = new Newtons(first_guess);
        newton.newton_Root();
        System.out.println(newton.toString());
    }
}
