package controllers;

import nn.NeuralNetwork;

public class NNController implements GameController {

    private NeuralNetwork nn;

    public NNController(NeuralNetwork nn) {
        this.nn = nn;
    }

    @Override
    public double[] nextMove(double[] currentState) {
        return nn.forward(currentState);
    }
    
}
