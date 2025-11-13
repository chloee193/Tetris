package tetris;

import javafx.scene.paint.Color;

/**
 * The constants class stores dimensions, colors and other constants to be used later in the game.
 * It makes it clearer for the rest of the code and helps avoid mystery numbers.
 */
public class Constants {

    // width of each square
    public static final int SQUARE_WIDTH = 30;
    //total width and height of game screen
    public static final int SCENE_WIDTH = 12*SQUARE_WIDTH;
    public static final int SCENE_HEIGHT = 23*SQUARE_WIDTH;
    //number of rows and columns on game board
    public static final int PLAYABLE_ROWS = 20;
    public static final int PLAYABLE_COLS = 10;
    //dimensions of quit button
    public static final int BUTTON_WIDTH = 60;
    public static final int BUTTON_HEIGHT = 30;
    //colors of labels background and control pane color
    public static final String LABEL_COLOR = "-fx-background-color: white";
    public static final String CONTROL_PANE_COLOR = "-fx-background-color: #6bacd5";
    //timeline update duration
    public static final double DURATION = 1; //seconds
    public static final int SCORE_SUBSTRING = 7;
    public static final int SCORE_INCREASE = 100;
    //score label dimensions
    public static final int SCORE_LABEL_WIDTH = 75;
    public static final int SCORE_LABEL_HEIGHT = 20;
    //max score before game stops for SRC
    public static final int MAX_SCORE = 100000;
    public static final int ROWS_PER_LEVEL = 5;
    public static final int GAME_OVER_X_ADJUST = 100;
    public static final int GAME_OVER_Y_ADJUST = 50;
    public static final int FOUR_ROWS_SCORE = 2000;

    // coordinates for squares in each tetris piece
    public static final int[][] I_PIECE_COORDS = {{0, 0}, {0, 1}, {0, 2}, {0, 3}};
    public static final int [][] O_PIECE_COORDS = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    public static final int[][] T_PIECE_COORDS = {{-1, 0}, {-1, 1}, {-1, 2},  {0, 1}};
    public static final int[][] J_PIECE_COORDS = {{0, 0}, {1, 0}, {0, 1}, {0, 2}};
    public static final int [][] L_PIECE_COORDS = {{0, 0}, {1, 0}, {1, 1}, {1, 2}};
    public static final int [][] S_PIECE_COORDS = {{0, 0}, {0, 1}, {1, 1}, {1, 2}};
    public static final int [][] Z_PIECE_COORDS = {{1, 0}, {1, 1}, {0, 1}, {0, 2}};

    //normal piece colors
    public static final Color I_COLOR = Color.web("#E57373");
    public static final Color O_COLOR = Color.web("#A5D6A7");
    public static final Color T_COLOR = Color.web("#FFB74D");
    public static final Color J_COLOR = Color.web("#FFF176");
    public static final Color L_COLOR = Color.web("#CE93D8");
    public static final Color S_COLOR = Color.web("#81D4FA");
    public static final Color Z_COLOR = Color.web("#F48FB1");

    //colorblind piece colors
    public static final Color I_COLOR_CB = Color.web("#0072B2");
    public static final Color O_COLOR_CB = Color.web("#E69F00");
    public static final Color T_COLOR_CB = Color.web("#56B4E9");
    public static final Color J_COLOR_CB = Color.web("#D55E00");
    public static final Color L_COLOR_CB = Color.web("#009E73");
    public static final Color S_COLOR_CB = Color.web("#F0E442");
    public static final Color Z_COLOR_CB = Color.web("#CC79A7");

}
