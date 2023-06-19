
import java.util.Random;

public class Chromosome {

    private double value;
    private double fitness;
    private final double MUTATION_RATE = 0.1;
    private double min;
    private double max;

    public Chromosome(double min, double max) {
        this.min = min;
        this.max = max;
        Random random = new Random();
        value = random.nextFloat() * (max - min) + min;
        this.fitness = countFunction(value);
    }

    public Chromosome(double value) {

        this.value = value;
        this.fitness = countFunction(value);
    }

    public double getValue() {
        return value;
    }

    public double getFitness() {
        return fitness;
    }

    public double countFunction(double x) {
        return Math.pow(x, Math.sin(10 * x));
    }

    public void mutate() {
        double rate = new Random().nextDouble();
        if (rate < MUTATION_RATE) {
            double delta = countSign() * 0.1;
            if (this.value + delta > min && this.value + delta < max) {
                this.value = this.value + delta;
                this.fitness = countFunction(value);
            }
        }
    }

    public int countSign() {
        double sign = new Random().nextDouble() * 2 - 1;
        if (sign >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "value=" + value +
                ", fitness=" + fitness +
                '}';
    }
}
