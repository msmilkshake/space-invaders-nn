package nn;

import space.Commons;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public interface Constants {

    int INPUT_DIM = Commons.NUMBER_OF_ALIENS_TO_DESTROY * 7 + 3;
    int HIDDER_DIM = 250;
    int OUTPUT_DIM = 4;
    int GENS_TO_LIVE = 5;

    Random RANDOM = new Random();
    AtomicInteger INSTANCE_COUNTER = new AtomicInteger(1);

}
