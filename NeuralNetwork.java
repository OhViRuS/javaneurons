import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
    public double[][] wInputHidden; //w - weight
    public double[] wHiddenOutput;
    public double[] bHidden; //b - bias
    public double bOutput;
    public int hiddenSize = 6;

    public NeuralNetwork() {
        initializeWeights();
    }
    private void initializeWeights(){
        Random rand = new Random();

        wInputHidden = new double[4][hiddenSize];
        wHiddenOutput = new double[hiddenSize];
        bHidden = new double[hiddenSize];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < hiddenSize; j++) {
                wInputHidden[i][j] = rand.nextDouble() * 2 - 1;
            }
        }
        for (int i = 0; i < hiddenSize; i++){
            wHiddenOutput[i] = rand.nextDouble() * 2 - 1;
            bHidden[i] = rand.nextDouble() * 2 - 1;
        }
        bOutput = rand.nextDouble() * 2 - 1;
    }

    public double forward(double[] input){
        double[] hidden = new double[hiddenSize];
        double sumOut = 0;
        for (int j = 0; j < hiddenSize; j++) {

            double sum = 0;

            for (int i = 0; i < 4; i++) {
                sum += input[i] * wInputHidden[i][j]; //x1*w[0][0] x2*w[1][0] x3*w[2][0] ...
             }

            sum += bHidden[j]; //+bias

            hidden[j] = sigmoid(sum);
        }
        for(int i = 0; i < hiddenSize; i++){
            sumOut += hidden[i] * wHiddenOutput[i];
        }
        sumOut+=bOutput; //+bias
        return sumOut;
    }
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
    public double calculateMSE(ArrayList<IrisData> dataset) {
        double sum = 0.0;
        for (IrisData data : dataset) {
            double output = forward(data.features);
            double error = data.target - output;
            sum += error * error;
        }
        return sum / dataset.size();
    }
    public NeuralNetwork clone() {
        NeuralNetwork copy = new NeuralNetwork();

        // copy input-hidden weights
        for (int i = 0; i < wInputHidden.length; i++) {
            for (int j = 0; j < wInputHidden[i].length; j++) {
                copy.wInputHidden[i][j] = this.wInputHidden[i][j];
            }
        }

        // copy hidden-output weights
        for (int i = 0; i < wHiddenOutput.length; i++) {
            copy.wHiddenOutput[i] = this.wHiddenOutput[i];
        }

        // copy biases
        for (int i = 0; i < bHidden.length; i++) {
            copy.bHidden[i] = this.bHidden[i];
        }

        copy.bOutput = this.bOutput;

        return copy;
    }
    public double calculateAccuracy(ArrayList<IrisData> dataset) {
        int correct = 0;

        for (IrisData data : dataset) {

            double output = forward(data.features);
            int prediction = predict(output);

            if (prediction == data.target) {
                correct++;
            }
        }

        return (correct / (double) dataset.size()) * 100.0;
    }
    private int predict(double output) {
        if (output < 1.5) return 1;
        else if (output < 2.5) return 2;
        else return 3;
    }
}
