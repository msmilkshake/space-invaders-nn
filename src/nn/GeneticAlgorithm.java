package nn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    public static final double fittestPercent = 0.15;
    public static final double parentsToKeepPercent = 0.05;
    public static final double parentCloneMutationPercent = 0.50;
    public static final double randomPercent = 0.05;
    public static final double mutationPercent = 0.10;
    public static final double mutationProbability = 0.15;
    public static final double minCross = 0.1;
    public static final double maxCross = 0.3;


    public static List<NeuralNetwork> generateNewGeneration(List<NeuralNetwork> gen) {
        Collections.sort(gen);
        
        int fittestNum = (int) (gen.size() * fittestPercent);
        System.out.println("#### List of parents: ####");
        List<NeuralNetwork> parents = gen.subList(0, fittestNum);
        for (NeuralNetwork nn : parents) {
            System.out.println("Individual #" + nn.getId() + ", Fitness: " + nn.getFitness());
        }

        List<NeuralNetwork> newGen = new ArrayList<>();

        int numParentsToKeep = (int) (gen.size() * parentsToKeepPercent);
        
        int parentIndex = 0;
        for (int i = 0; i < numParentsToKeep; ++i, ++parentIndex) {
            while (gen.get(parentIndex).isDead()) {
                ++parentIndex;
            }
            gen.get(parentIndex).decrementGen();
            
            newGen.add(gen.get(parentIndex++));
        }
        
        // Clone the parents to keep and mutate them
        for (int i = 0; i < numParentsToKeep; ++i) {
            double[] chromosome = newGen.get(i).getChromosome();
            for (int j = 0; j < chromosome.length; ++j) {
                if (Constants.RANDOM.nextDouble() < parentCloneMutationPercent) {
                    chromosome[j] = Constants.RANDOM.nextDouble() * 2.0 - 1.0;
                }
            }
            newGen.add(new NeuralNetwork(
                    Constants.INPUT_DIM,
                    Constants.HIDDER_DIM,
                    Constants.OUTPUT_DIM,
                    chromosome));
        }
        

        int numOfRandoms = (int) (gen.size() * randomPercent);
        newGen.addAll(generateRandomIndividuals(numOfRandoms));

        List<NeuralNetwork> crossoverChildren = crossover(parents, gen.size() - numOfRandoms - numParentsToKeep * 2);
        newGen.addAll(crossoverChildren);

        return newGen;
    }

    private static List<NeuralNetwork> generateRandomIndividuals(int n) {
        List<NeuralNetwork> randoms = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            NeuralNetwork nn = new NeuralNetwork(
                    Constants.INPUT_DIM,
                    Constants.HIDDER_DIM,
                    Constants.OUTPUT_DIM,
                    Constants.RANDOM);
            nn.initializeWeights();
            randoms.add(nn);
        }
        return randoms;
    }

    private static List<NeuralNetwork> crossover(List<NeuralNetwork> parents, int n) {
        
        double[] weights = new double[parents.size()];
        double weight = 0.75;
        
        for (int i = 0; i < weights.length; ++i) {
            weights[i] = weight;
            weight -= weight >= 0.1 ? 0.1 : 0.0;
        }
        
        int chromosomeSize = parents.get(0).getChromosomeSize();
        List<NeuralNetwork> children = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            
            
            int fatherIndex = 0;
            int motherIndex = 0;
            for (int j = 0; j < parents.size(); ++j) {
                double num = Constants.RANDOM.nextDouble();
                if (num < weights[j]) {
                    fatherIndex = j;
                    break;
                }
            }
            for (int j = 0; j < parents.size(); ++j) {
                double num = Constants.RANDOM.nextDouble();
                if (num < weights[j]) {
                    motherIndex = j;
                    if (motherIndex == fatherIndex) {
                        motherIndex = (motherIndex + 1) % parents.size();
                    }
                    break;
                }
            }

            double[] parent1Chromosomes = parents.get(fatherIndex).getChromosome();
            double[] parent2Chromosomes = parents.get(motherIndex).getChromosome();
            double[] childChromosomes = new double[chromosomeSize];

            // Ensures Diversity
            boolean isToMutate = Constants.RANDOM.nextDouble() < mutationProbability;
            if (cloneCheck(parent1Chromosomes, parent2Chromosomes)) {
                isToMutate = true;
            }
            
            for (int j = 0; j < chromosomeSize; ++j) {
                if (isToMutate && Constants.RANDOM.nextDouble() < mutationPercent) {
                    childChromosomes[j] = Constants.RANDOM.nextDouble() * 2.0 - 1.0;
                } else {
                    childChromosomes[j] =
                            Constants.RANDOM.nextDouble() < 0.1 + (maxCross - minCross) * Constants.RANDOM.nextDouble() ?
                                    parent1Chromosomes[j] :
                                    parent2Chromosomes[j];
                }
            }

            children.add(new NeuralNetwork(
                    Constants.INPUT_DIM,
                    Constants.HIDDER_DIM,
                    Constants.OUTPUT_DIM,
                    childChromosomes));
        }

        return children;
    }

    private static boolean cloneCheck(double[] chromosome1, double[] chromosome2) {
        for (int i = 0; i < chromosome1.length; ++i) {
            if (chromosome1[i] != chromosome2[i]) {
                return false;
            }
        }
        return true;
    }
}
