package space;

public interface Commons {

    int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
    int IMAGE_SIZE = 358*322;
    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 5;

    int GROUND = 290;
    int BOMB_HEIGHT = 5;

    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int ALIEN_INIT_X = 150;
    int ALIEN_INIT_Y = 5;

    int GO_DOWN = 15;
    int NUMBER_OF_LINES = 3;//24;
    int NUMBER_OF_ALIENS_TO_DESTROY = 18;//24;
    int CHANCE = 1;
    int DELAY = 0;
    int PLAYER_WIDTH = 15;
    int PLAYER_HEIGHT = 10;
    
    int STATE_SIZE = Commons.NUMBER_OF_ALIENS_TO_DESTROY * 3 * 2 + 1 + 3;
    int NUM_ACTIONS = 4; // 0 - left, 1 - right, 2 - stop, 3 - fire.
    int MAX_DIAGONAL = (int) Math.sqrt(Math.pow(Commons.BOARD_WIDTH, 2) + Math.pow(Commons.BOARD_HEIGHT, 2));
}
