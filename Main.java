import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ArrayList<IrisData> dataset = DataLoader.loadData();

        int populationSize = 500;
        int generations = 1000;

        ArrayList<NeuralNetwork> population = new ArrayList<>();
        Random rand = new Random();

        // INITIAL POPULATION
        for (int i = 0; i < populationSize; i++) {
            population.add(new NeuralNetwork());
        }

        NeuralNetwork bestOverall = null;
        double bestOverallMSE = Double.MAX_VALUE;

        for (int gen = 0; gen < generations+1; gen++) {

            // 1. Evaluate
            double bestMSE = Double.MAX_VALUE;

            for (NeuralNetwork nn : population) {
                double mse = nn.calculateMSE(dataset);
                nn.currentMSE = mse;

                if (mse < bestMSE) {
                    bestMSE = mse;
                }

                if (mse < bestOverallMSE) {
                    bestOverallMSE = mse;
                    bestOverall = nn;
                }
            }

            if (gen % 20 == 0 || gen == generations - 1) {
                System.out.println("Gen " + gen + " Best MSE: " + bestMSE);
            }

            // 2. SORT population
            population.sort((a, b) -> Double.compare(a.currentMSE, b.currentMSE));

            // 3. ELITISM (TOP 10%)
            int eliteCount = (int)(populationSize * 0.1);

            ArrayList<NeuralNetwork> newPopulation = new ArrayList<>();

            for (int i = 0; i < eliteCount; i++) {
                newPopulation.add(population.get(i)); // top individuals
            }

            // uzpildome su mutated children
            while (newPopulation.size() < populationSize) {

                //imame random elite reiksme
                NeuralNetwork parent = population.get(rand.nextInt(eliteCount));

                NeuralNetwork child = new NeuralNetwork();

                //parents svoriai kopijuojami i nauja
                copy(parent, child);

                mutate(child, rand, gen, generations);

                newPopulation.add(child);
            }

            population = newPopulation;
        }

        double acc = bestOverall.calculateAccuracy(dataset);

        System.out.println("Best MSE: " + bestOverallMSE);
        System.out.println("Accuracy: " + acc + "%");
    }

    private static void copy(NeuralNetwork from, NeuralNetwork to) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < from.hiddenSize; j++) {
                to.wInputHidden[i][j] = from.wInputHidden[i][j];
            }
        }

        for (int i = 0; i < from.hiddenSize; i++) {
            to.wHiddenOutput[i] = from.wHiddenOutput[i];
            to.bHidden[i] = from.bHidden[i];
        }

        to.bOutput = from.bOutput;
    }

    private static void mutate(NeuralNetwork nn, Random rand, int gen, int generations) {

        double rate = 0.1 + 0.25 * (1.0 - gen / (double) generations);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < nn.hiddenSize; j++) {
                nn.wInputHidden[i][j] += rand.nextGaussian() * rate;
            }
        }

        for (int i = 0; i < nn.hiddenSize; i++) {
            nn.wHiddenOutput[i] += rand.nextGaussian() * rate;
            nn.bHidden[i] += rand.nextGaussian() * rate;
        }

        nn.bOutput += rand.nextGaussian() * rate;
    }
}
