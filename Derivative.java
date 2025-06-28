public class Derivative {
    private double h; //step size
    private double x; //Derivtion at x
    public Derivative(double x, double h) {
        this.h = h;
        this.x = x;
    }
    private double CentralDerivative(){
        return (function(x+h) - function(x-h))/(2*h);
    }
    private double forwardDerivative(){
        return (function(x+h) - function(x))/h;
    }
    private double backwardDerivative(){
        return (function(x) - function(x-h))/h;
    }
    public double function(double a){
        return Math.pow(Math.E, a);
    }
    public static void main(String[] args) {
        Derivative derivative = new Derivative(1, 0.001);
        System.out.println("Central Derivative: " + derivative.CentralDerivative());
    }
}
