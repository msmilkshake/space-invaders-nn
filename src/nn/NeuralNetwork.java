package nn;

import java.io.Serializable;
import java.util.Random;

public class NeuralNetwork implements Comparable<NeuralNetwork>, Serializable {

    private static final long serialVersionUID = 2619092955438295854L;

    private static int idGenerator = 0;
    
    private int inputDim;
    private int hiddenDim;
    private int outputDim;
    private double[][] inputWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;
    private int gensToLive = Constants.GENS_TO_LIVE;

    private Random random = new Random();
    private double fitness = 0.0;
    private int id;

    public NeuralNetwork(int inputDim, int hiddenDim, int outputDim, Random r) {
        this(inputDim, hiddenDim, outputDim);
        random = r;
        id = idGenerator++;
    }

    public NeuralNetwork(int inputDim, int hiddenDim, int outputDim) {
        this.inputDim = inputDim;
        this.hiddenDim = hiddenDim;
        this.outputDim = outputDim;
        this.inputWeights = new double[inputDim][hiddenDim];
        this.hiddenBiases = new double[hiddenDim];
        this.outputWeights = new double[hiddenDim][outputDim];
        this.outputBiases = new double[outputDim];
        id = idGenerator++;
    }

    public NeuralNetwork(int inputDim, int hiddenDim, int outputDim, double[] values) {
        this(inputDim, hiddenDim, outputDim);
        int offset = 0;
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                inputWeights[i][j] = values[i * hiddenDim + j];
            }
        }
        offset = inputDim * hiddenDim;
        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] = values[offset + i];
        }
        offset += hiddenDim;
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] = values[offset + i * outputDim + j];
            }
        }
        offset += hiddenDim * outputDim;
        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] = values[offset + i];
        }
        id = idGenerator++;

    }

    public void setChromosome(double[] values) {
        int offset = 0;
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                inputWeights[i][j] = values[i * hiddenDim + j];
            }
        }
        offset = inputDim * hiddenDim;
        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] = values[offset + i];
        }
        offset += hiddenDim;
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] = values[offset + i * outputDim + j];
            }
        }
        offset += hiddenDim * outputDim;
        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] = values[offset + i];
        }
        id = idGenerator++;
    }

    public int getChromosomeSize() {
        return inputWeights.length * inputWeights[0].length + hiddenBiases.length
                + outputWeights.length * outputWeights[0].length + outputBiases.length;
    }

    public double[] getChromosome() {
        double[] chromosome = new double[getChromosomeSize()];
        int offset = 0;
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                chromosome[i * hiddenDim + j] = inputWeights[i][j];
            }
        }
        offset = inputDim * hiddenDim;
        for (int i = 0; i < hiddenDim; i++) {
            chromosome[offset + i] = hiddenBiases[i];
        }
        offset += hiddenDim;
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                chromosome[offset + i * outputDim + j] = outputWeights[i][j];
            }
        }
        offset += hiddenDim * outputDim;
        for (int i = 0; i < outputDim; i++) {
            chromosome[offset + i] = outputBiases[i];
        }

        return chromosome;

    }

    public void initializeWeights() {
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                inputWeights[i][j] = random.nextDouble() * 2 - 1;
            }
        }
        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] = random.nextDouble() * 2 - 1;
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] = random.nextDouble() * 2 - 1;
            }
        }
        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] = random.nextDouble() * 2 - 1;
        }
    }

    public double[] forward(double[] input) {
        double[] hidden = new double[hiddenDim];
        for (int i = 0; i < hiddenDim; i++) {
            double sum = 0.0;
            for (int j = 0; j < inputDim; j++) {
                double d = input[j];
                sum += d * inputWeights[j][i];
            }
            hidden[i] = sum + hiddenBiases[i];
        }
        double[] output = new double[outputDim];
        for (int i = 0; i < outputDim; i++) {
            double sum = 0.0;
            for (int j = 0; j < hiddenDim; j++) {
                sum += hidden[j] * outputWeights[j][i];
            }
            output[i] = sum + outputBiases[i];
        }
        double sum = 0.0;
        for (int i = 0; i < outputDim; i++) {
            sum += output[i];
        }
        for (int i = 0; i < outputDim; i++) {
            output[i] /= sum;
            output[i] = 1.0 / (1.0 + Math.exp(-output[i]));
        }

        return output;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(NeuralNetwork o) {
        return Double.compare(o.fitness, fitness);
    }

    public int getId() {
        return id;
    }

    public double getFitness() {
        return fitness;
    }

    public void decrementGen() {
        --gensToLive;
    }

    public boolean isDead() {
        return gensToLive == 0;
    }
}
