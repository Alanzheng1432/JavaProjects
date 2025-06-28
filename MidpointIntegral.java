public class MidpointIntegral {
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;
    private int n;
    private double area;
    private double dx;
    private double dy;

    public MidpointIntegral(double xmin, double xmax, double ymin, double ymax, int n) {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.n = n;
        this.area = 0.0;
        this.dx = (xmax - xmin) / n;
        this.dy = (ymax - ymin) / n;
        this.getArea();

    }
    // Function to check if a point satisfies the equation (x^2 + y^2)^2 = x^3 + y^3
    private static boolean isInsideDomain(double x, double y) {
        double leftSide = Math.pow(x * x + y * y, 3);
        double rightSide = 4*Math.pow(x, 2) * Math.pow(y, 2);
        return ((leftSide - rightSide) <= 0.0001); // Allow some tolerance for numerical precision
    }
    private double computeArea(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x = xmin + (i + 0.5) * dx;
                double y = ymin + (j + 0.5) * dy;
                if (isInsideDomain(x, y)) {
                    area += 1.0;
                }
            }
        }
        area *= dx * dy;
        return area;
    }
    public double getArea(){
        return computeArea();
    }
    public static void main(String[] args) {
        // Compute the area enclosed by the curve using the Midpoint Rule
        MidpointIntegral midpoint = new MidpointIntegral(-0.5, 0.5, -1/Math.sqrt(8), 1/Math.sqrt(8), 1000);
        System.out.println("Approximate area by Midpoints: " + (midpoint.getArea()));

        // Output the result
    }
}
