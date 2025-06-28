import java.util.Random;

public class Buffon {
    private int trials;
    private double disk_diameter;
    private double disk_radius;
    private double line_length;
    private double acceptaces = 0;
    private double height;

    public Buffon(int trials, double disk_diameter, double line_length) {
        this.trials = trials;
        this.line_length = line_length;
        this.disk_radius = disk_diameter / 2;
        this.disk_diameter = disk_diameter;
        this.height = Math.ceil(disk_diameter);
    }
    private double experiment(){
        if(disk_diameter <= line_length){
            for(int i =0; i<trials; i++){
                Random rand = new Random(); 
                double randomDouble = (rand.nextDouble() * height);
                if(randomDouble > (height - disk_radius) || randomDouble < disk_radius){
                    acceptaces++;
                }
            }
            return acceptaces/trials;
        }
        else{
            for(int i =0; i<trials; i++){
                Random rand = new Random(); 
                double randomDouble = (rand.nextDouble() * height);
                if(randomDouble > (height - disk_radius) || randomDouble < disk_radius){
                    acceptaces += (height);
                }
            }
            return acceptaces/trials;
        }
    }
    public double getAcceptanceRate() {
        return experiment();
    }
    public static void main(String[] args) {
        int trials = 4444444;
        double line_length = 1;

        Buffon buffon1 = new Buffon(trials, 0.1, line_length);
        double acceptanceRate = buffon1.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diameter 1/10: " + acceptanceRate);

        Buffon buffon2 = new Buffon(trials, 0.2, line_length);
        double acceptanceRate2 = buffon2.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 2/10: " + acceptanceRate2);

        Buffon buffon3 = new Buffon(trials, 0.3, line_length);
        double acceptanceRate3 = buffon3.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 3/10: " + acceptanceRate3);

        Buffon buffon4 = new Buffon(trials, 0.4, line_length);
        double acceptanceRate4 = buffon4.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 4/10: " + acceptanceRate4);

        Buffon buffon5 = new Buffon(trials, 0.5, line_length);
        double acceptanceRate5 = buffon5.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 5/10: " + acceptanceRate5);

        Buffon buffon6 = new Buffon(trials, 0.6, line_length);
        double acceptanceRate6 = buffon6.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 6/10: " + acceptanceRate6);

        Buffon buffon7 = new Buffon(trials, 0.7, line_length);
        double acceptanceRate7 = buffon7.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 7/10: " + acceptanceRate7);

        Buffon buffon8 = new Buffon(trials, 0.8, line_length);
        double acceptanceRate8 = buffon8.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 8/10: " + acceptanceRate8);

        Buffon buffon9 = new Buffon(trials, 0.9, line_length);
        double acceptanceRate9 = buffon9.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 9/10: " + acceptanceRate9);
        
        Buffon buffon10 = new Buffon(trials, 1, line_length);
        double acceptanceRate10 = buffon10.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 10/10: " + acceptanceRate10);

        Buffon buffon20 = new Buffon(trials, 2, line_length);
        double acceptanceRate20 = buffon20.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 20/10: " + acceptanceRate20);

        Buffon buffon30 = new Buffon(trials, 3, line_length);
        double acceptanceRate30 = buffon30.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 30/10: " + acceptanceRate30);

        Buffon buffon31 = new Buffon(trials, 3.1, line_length);
        double acceptanceRate31 = buffon31.getAcceptanceRate();
        System.out.println("Acceptance Rate for Diamter 31/10: " + acceptanceRate31);
    }
}
