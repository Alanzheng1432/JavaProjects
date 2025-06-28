public class RNG {
    private final int maxIterations;         // Meter reaches 100% at this value 
    private final double initialChance;      // Initial chance of success
    private final double successIncrement;   // How much the chance increases each step

    public RNG(int expected, double initialChance) {
        this.maxIterations = expected;
        this.initialChance = initialChance;
        this.successIncrement = (2 * initialChance) / expected;
    }

    private int runSingleTrial() {
        double chance = initialChance;

        for (int i = 0; i < maxIterations; i++) {
            if (Math.random() < chance) {
                return i + 1; // Return the number of iterations taken to succeed
            }
            chance += successIncrement;
        }

        return maxIterations; // Guaranteed success at last trial
    }

    public double expectedValue(int numTrials) {
        int total = 0;

        for (int t = 0; t < numTrials; t++) {
            total += runSingleTrial();
        }

        return (double) total / numTrials;
    }

    public static void main(String[] args) {
        int maxIterations = 1771;
        double initialChance = 2.4 * (1.0 / 1771.12);
        RNG rng = new RNG(maxIterations, initialChance);
    
        int trials = 1000;
        System.out.println("Trial Results:");
    
        for (int i = 0; i < trials; i++) {
            int result = rng.runSingleTrial();
            System.out.println(result);  // each result on its own line
        }
    }
    
}
