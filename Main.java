import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ArrayList<IrisData> dataset = DataLoader.loadData();

        int populationSize = 10000;
        int generations = 100;

        ArrayList<NeuralNetwork> population = new ArrayList<>();
        Random rand = new Random();

        // INITIAL POPULATION
        for (int i = 0; i < populationSize; i++) {
            population.add(new NeuralNetwork());
        }
            NeuralNetwork bestOverall = null;
            double bestOverallMSE = Double.MAX_VALUE;
        for (int gen = 0; gen < generations; gen++) {

            // 1. Evaluate
            NeuralNetwork best = null;
            double bestMSE = Double.MAX_VALUE;

            for (NeuralNetwork nn : population) {
                double mse = nn.calculateMSE(dataset);

                if (mse < bestMSE) {
                    bestMSE = mse;
                    best = nn;
                }
                 if (mse < bestOverallMSE) {
                    bestOverallMSE = mse;
                    bestOverall = nn;
                }
            }

            System.out.println("Gen " + gen + " Best MSE: " + bestMSE);
            
            // 2. NEW POPULATION
            ArrayList<NeuralNetwork> newPopulation = new ArrayList<>();

            //random shock (isjudinti, kad nestovetu vietoje)
            if (gen % 10 == 0) {
                for (int i = 0; i < 5; i++) {
                    newPopulation.add(new NeuralNetwork());
                }
            }

            // elitism (pasiliekam geriausius 5)
            for (int i = 0; i < 5; i++) {
                newPopulation.add(best.clone());
            }

            // 10% visiškai random NN
            for (int i = 0; i < populationSize * 0.1; i++) {
                newPopulation.add(new NeuralNetwork());
            }

            while (newPopulation.size() < populationSize) {

                NeuralNetwork child = new NeuralNetwork();

                // MUTATION
                mutate(child, rand, gen, generations);
                newPopulation.add(child);
            }
            population = newPopulation;
        }
        double acc = bestOverall.calculateAccuracy(dataset);

        System.out.println("Best MSE: " + bestOverallMSE);
        System.out.println("Accuracy: " + acc + "%");
    }
    private static void mutate(NeuralNetwork nn, Random rand, int gen, int generations) {

        double rate = 0.1 + 0.25 * (1.0 - gen / (double) generations);

        // input-hidden weights
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < nn.hiddenSize; j++) {
                nn.wInputHidden[i][j] += rand.nextGaussian() * rate;
            }
        }

        // hidden-output weights
        for (int i = 0; i < nn.hiddenSize; i++) {
            nn.wHiddenOutput[i] += rand.nextGaussian() * rate;
        }

        // biases
        for (int i = 0; i < nn.hiddenSize; i++) {
            nn.bHidden[i] += rand.nextGaussian() * rate;
        }

        nn.bOutput += rand.nextGaussian() * rate;
    }
}