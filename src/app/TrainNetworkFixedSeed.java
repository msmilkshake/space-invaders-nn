package app;

import controllers.GameController;
import controllers.NNController;
import nn.Constants;
import nn.GeneticAlgorithm;
import nn.NeuralNetwork;
import nn.NNContainer;
import space.SpaceInvaders;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrainNetworkFixedSeed {

    public static int populationSize = 200;
    public static final int maxGens = 50;

    public static void main(String[] args) {

        List<NeuralNetwork> generation = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; ++i) {
            generation.add(new NeuralNetwork(
                    Constants.INPUT_DIM, Constants.HIDDER_DIM, Constants.OUTPUT_DIM, Constants.RANDOM));
            generation.get(i).initializeWeights();
        }

        headlessMultiInstances(generation);

        for (int i = 1; i < maxGens; ++i) {
            System.out.println(" # # # - - - G E N E R A T I O N  N U M B E R :   # " + i + " - - - # # #");
            List<NeuralNetwork> newGeneration = GeneticAlgorithm.generateNewGeneration(generation);
            headlessMultiInstances(newGeneration);
            generation = newGeneration;
        }

        Collections.sort(generation);

        System.out.println("The Chosen ONE - Individual #" +
                generation.get(0).getId() + ": " + generation.get(0).getFitness());

        saveTopTen(generation);

        GameController c = new NNController(generation.get(0));
        SpaceInvaders.showControllerPlaying(c, 0x12345);

    }

    public static void saveTopTen(List<NeuralNetwork> gen) {
        try (FileOutputStream fileOut = new FileOutputStream("top-ten.nn");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(new ArrayList<>(gen.subList(0, 10)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void headlessMultiInstances(List<NeuralNetwork> nnList) {
        ExecutorService executorService = Executors.newFixedThreadPool(populationSize);
        Constants.INSTANCE_COUNTER.set(1);
        NNContainer[] individuals = new NNContainer[populationSize];

        for (int i = 0; i < populationSize; ++i) {
            individuals[i] = new NNContainer(nnList.get(i));
            executorService.submit(individuals[i]);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            System.out.println("\nAll individuals finished evaluation.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

