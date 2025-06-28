import java.util.Arrays;

public class LagrangeInterpolation {

    private double[] xValues;
    private double[] yValues;
    private double[] coefficients;

    public LagrangeInterpolation(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("The number of x and y values must be the same.");
        }
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.coefficients = calculateCoefficients();
    }

   private double[] calculateCoefficients() { //Coefficients order ... C_0, C_1*t, C_2*t^2, C_3*t^3, ...
        int n = xValues.length;
        double[] coefficients = new double[n];

        for (int i = 0; i < n; i++) {
            double[] lagrangeBasis = {1};
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    lagrangeBasis = multiplyPolynomials(lagrangeBasis, new double[] {-xValues[j], 1});
                    double divisor = xValues[i] - xValues[j];
                     for (int k = 0; k < lagrangeBasis.length; k++) {
                        lagrangeBasis[k] /= divisor;
                    }
                }
            }
           for (int k = 0; k < lagrangeBasis.length; k++) {
                coefficients[k] += yValues[i] * lagrangeBasis[k];
            }
        }
        return coefficients;
    }
    
    private double[] multiplyPolynomials(double[] poly1, double[] poly2) {
        double[] result = new double[poly1.length + poly2.length - 1];
        for (int i = 0; i < poly1.length; i++) {
            for (int j = 0; j < poly2.length; j++) {
                result[i + j] += poly1[i] * poly2[j];
            }
        }
        return result;
    }

    public double interpolate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }
    @Override 
    public String toString(){
        return "Lagrange Interpolation: \n" + Arrays.toString(coefficients);
    }

    public static void main(String[] args) {
        double[] xValues = {0, 1, 2, 3, 4, 5};
        double[] yValues = {1, 1.4226300865076322, 1.1038664733146537, 1.0218396109601968, 1.0047668827645562, 1.001049147237095};

        LagrangeInterpolation interpolator = new LagrangeInterpolation(xValues, yValues);
        System.out.println(interpolator);
    }
}
