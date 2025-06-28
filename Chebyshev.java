public class Chebyshev {
    public static double[] chebyshevNodes(int n, double a, double b) {
        double[] nodes = new double[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = (a + b) / 2 + (b - a) / 2 * Math.cos((2 * i + 1) * Math.PI / (2 * n));
        }
        return nodes;
    }
    public static void main(String[] args) {
        int n = 5;
        double a = 0;
        double b = 1;
        double[] nodes = chebyshevNodes(n, a, b);
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("Node " + i + ": " + nodes[i]);
        }
    }
}
