import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {

    private final int populationNumber;

    private Chromosome[] population;

    private final boolean reversed;

    private final double min;
    private final double max;

    public Population(int populationNumber, boolean reversed, double min, double max) {
        this.populationNumber = populationNumber;
        this.reversed = reversed;
        population = new Chromosome[populationNumber];
        this.min = min;
        this.max = max;
        for (int i = 0; i < populationNumber; i++) {
            population[i] = new Chromosome(min, max);
        }
    }

    public void sortChromosomes() {
        if (reversed) {
            Arrays.sort(population, Comparator.comparing(Chromosome::getFitness).reversed());
        } else {
            Arrays.sort(population, Comparator.comparing(Chromosome::getFitness));
        }
    }

    public void crossover() {
        Chromosome[] children = new Chromosome[populationNumber];
        for (int i = 0; i < populationNumber; i += 2) {
            double min = Math.min(population[i].getValue(), population[i + 1].getValue());
            double max = Math.max(population[i].getValue(), population[i + 1].getValue());
            double minRange = min - 0.125 * (max - min);
            double maxRange = max + 0.125 * (max - min);
            if (minRange < this.min) {
                minRange = this.min;
            }
            if (maxRange > this.max) {
                maxRange = this.max;
            }
            Random random = new Random();
            double value1 = random.nextDouble();
            double value2 = random.nextDouble();
            children[i] = new Chromosome(value1 * (maxRange - minRange) + minRange);
            children[i + 1] =
                    new Chromosome(value2 * (maxRange - minRange) + minRange);
        }
        Chromosome[] temp = Arrays.copyOf(population, population.length + children.length);
        System.arraycopy(children, 0, temp, population.length, children.length);

        if (reversed) {
            Arrays.sort(temp, Comparator.comparing(Chromosome::getFitness).reversed());
        }
        else {
            Arrays.sort(temp, Comparator.comparing(Chromosome::getFitness));
        }
        Chromosome[] result = new Chromosome[population.length];
        System.arraycopy(temp, 0, result, 0, population.length);
        this.population = result;
    }

    public void mutate() {
        for (int i = 0; i < populationNumber; i++) {
            population[i].mutate();
        }
    }

    public Chromosome[] getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return "Population{" +
                "populationNumber=" + populationNumber +
                ", population=" + Arrays.toString(population) +
                '}';
    }
}
