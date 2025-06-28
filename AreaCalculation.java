public class AreaCalculation {

    private int N; // Number of intervals
    private double thetaStart, thetaEnd;
    private double area;

    // Constructor to initialize the parameters and compute the area
    public AreaCalculation(int N, double thetaStart, double thetaEnd) {
        this.N = N;
        this.thetaStart = thetaStart;
        this.thetaEnd = thetaEnd;
        this.area = calculateArea();
    }
    public double func(double theta) {
        return Math.pow(Math.pow(Math.cos(theta), 3) + Math.pow(Math.sin(theta), 3), 2);
    }

    // Method to calculate the area using the midpoint Riemann sum
    public double calculateArea() {
        double deltaTheta = (thetaEnd - thetaStart) / N;
        double areaSum = 0;

        // Midpoint Riemann sum for numerical integration
        for (int i = 0; i < N; i++) {
            // Midpoint of each interval
            double theta = thetaStart + (i + 0.5) * deltaTheta;
            areaSum += func(theta);
        }
        return areaSum * deltaTheta;
    }
    public double getArea() {
        return area;
    }

    public static void main(String[] args) {
        // Parameters for the area calculation
        int N = 1000;  // Number of intervals
        double thetaStart = Math.PI / 4;
        double thetaEnd = (5 * Math.PI) / 4;

        // Create an instance of the AreaCalculation class
        AreaCalculation areaCalc = new AreaCalculation(N, thetaStart, thetaEnd);

        // Output the result
        System.out.println("Approximate area: " + areaCalc.getArea());
    }
}
