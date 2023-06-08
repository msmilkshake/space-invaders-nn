package space;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controllers.GameController;

public class SpaceInvaders extends JFrame {
	
	private static int curX = 0;
	private static int offsetX = 0;
	
	private static int curY = 0;
	private static int offsetY = 0;
	
	private static int numWindows = 0;
	
	private static int windowWidth = 358;
	private static int windowHeight = 350;
	private static int maxPerRow = 5;
	private static int maxPerCol = 3;

	private Board board;

	public SpaceInvaders() {

		initUI();
	}

	private void initUI() {
		board = new Board();
		add(board);

		setTitle("Space Invaders");
		setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}


	public static void showControllerPlaying(GameController controller, long seed) {
		EventQueue.invokeLater(() -> {

			var ex = new SpaceInvaders();
			ex.setController(controller);
			ex.setSeed(seed);
			ex.setBounds(curX + offsetX, curY + offsetY, windowWidth, windowHeight);
			++numWindows;
			if (numWindows % maxPerRow == 0) {
				curX = 0;
				curY += windowHeight + offsetY;
			} else {
				curX += windowWidth + offsetX;
			}
			if (numWindows % (maxPerRow * maxPerCol) == 0) {
				curY = 0;
				offsetX += 10;
				offsetY += 10;
			}
			ex.setVisible(true);
		});
	}
	
	public void setController(GameController controller) {
		board.setController(controller);
	}

	public void setSeed(long seed) {
		board.setSeed(seed);

	}
}
