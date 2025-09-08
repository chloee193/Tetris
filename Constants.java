package tetris;

public class Constants {

    // width of each square
    public static final int SQUARE_WIDTH = 30;

    // coordinates for squares in each tetris piece
    public static final int[][] I_PIECE_COORDS = {{0, 0}, {0, SQUARE_WIDTH}, {0, 2 * SQUARE_WIDTH}, {0, 3 * SQUARE_WIDTH}};
    public static final int[][] T_PIECE_COORDS = {{-1 * SQUARE_WIDTH, 0}, {-1 * SQUARE_WIDTH, SQUARE_WIDTH}, {-1 * SQUARE_WIDTH, 2 * SQUARE_WIDTH}, {0, SQUARE_WIDTH}};
}
