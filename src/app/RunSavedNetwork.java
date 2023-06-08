package app;

import controllers.GameController;
import controllers.NNController;
import nn.Constants;
import nn.NeuralNetwork;
import space.SpaceInvaders;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class RunSavedNetwork {
    
    /* INSTRUCTIONS */

    // Set the index of the last generation individual (The file only keeps the top 10 individuals)
    public static final int index = 0;
    
    // Set the seed to be used in the game Board
    public static final long seed = 0x12345;
    
    // Set to false if a seed is to be used, or true if you want a random game Board
    public static boolean isRandom = false;
    
    // Set the filename with the Neural Network list
    public static final String filename = "trained-with-seed-0x12345.nn";

    // Finally just run the main method.
    public static void main(String[] args) {

        List<NeuralNetwork> nn = null;

        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            nn = (List<NeuralNetwork>) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        GameController c = new NNController(nn.get(index));
        SpaceInvaders.showControllerPlaying(c, isRandom ? Constants.RANDOM.nextLong() : seed);
    }

}
