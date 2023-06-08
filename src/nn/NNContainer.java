package nn;

import controllers.GameController;
import controllers.NNController;
import space.Board;

public class NNContainer implements Runnable {

    private NeuralNetwork nn;
    private GameController c;
    private Board b;


    public NNContainer(NeuralNetwork nn) {
        this.nn = nn;
        c = new NNController(nn);
        b = new Board(c);
        b.setSeed(0x12345);
    }

    public NNContainer(NeuralNetwork nn, long seed) {
        this.nn = nn;
        c = new NNController(nn);
        b = new Board(c);
        b.setSeed(seed);
    }

    @Override
    public void run() {
        b.run();
        nn.setFitness(b.getFitness());
        System.out.print("\rIndividual " + Constants.INSTANCE_COUNTER.getAndIncrement() + " ready...");
    }

    public NeuralNetwork getNn() {
        return nn;
    }
}
