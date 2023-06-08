package controllers;

//The GameController interface defines a method that allows a client to obtain the next move from the game controller.

public interface GameController {

	/*
	 * This method takes in an array of double values as input and returns an array
	 * of double values as output. The input array represents the current state of
	 * the game, and the output array represents the next move to be made by the
	 * player. The exact format and meaning of these arrays will depend on the
	 * specific game being played.
	 */
	public double[] nextMove(double[] currentState);

}
