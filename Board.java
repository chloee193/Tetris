package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;


/**
 * The board represents the tetris game's board. It's made of a grid of squares and handles the
 * occupied state, settling pieces, clearing rows, and deals with much of the colorblind
 * implementation.
 */
public class Board {
    private final int rows;
    private final int cols;
    private final Square[][] grid;
    private final Pane boardPane;
    private final List<Rectangle> rectBlocks = new ArrayList<>();
    private boolean colorblindMode = false;

    /**
     * The constructor creates a new tetris board with a specific number of rows and columns, and
     * puts it on the boardPane. Initially all the squares are unnoccupied and black
     * @param rows # of rows on board
     * @param cols # of cols on board
     * @param boardPane the pane the board is put on
     */
    public Board(int rows, int cols, Pane boardPane) {
        this.rows = rows;
        this.cols = cols;
        this.boardPane = boardPane;
        this.grid = new Square[this.rows][this.cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                this.grid[r][c] = new Square(r, c, boardPane, Color.BLACK);
            }
        }
    }

    /**
     * Checks is a specific square is occupied
     * @param row row of the given square
     * @param col col of given square
     * @return true if occupied, false if not
     */
    public boolean spotTaken(int row, int col) {
        if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) {return true;}

        return this.grid[row][col].isOccupied();
    }

    /**
     * settles a piece by marking it's squares as occupied, setting the color
     * @param piece settles this piece
     */
    public void settlePiece(Piece piece) {
        for(Square square : piece.getSquares()) {
            int row = square.getRow();
            int col = square.getCol();
            if (row >=0 && row < this.rows && col >= 0 && col < this.cols) {
                Paint color = square.getColor();
                this.grid[row][col].setOccupied(true, color);
            }
        }
        piece.removeFromPane(); //removes the piece afterwards so the blocks can be visually
        //altered/moved without being covered by the piece
    }

    /**
     * checks for full rows on the board so they can be removed. and the stuff above can move down
     * one row
     * @return true if row is full
     */
    public int checkFullRows() {
        int clearedRows = 0;

        for (int r = Constants.PLAYABLE_ROWS-1; r >= 0; r--) {
            boolean fullRow = true;

            for (int c=0; c < Constants.PLAYABLE_COLS; c++) {
                if (!this.grid[r][c].isOccupied()) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                this.rowsFallDown(r);
                clearedRows++;
                r++;
            }
        }

        return clearedRows;
    }

    /**
     * makes the rows above a cleared row "fall down." copies the occupied status and color of the
     * row above and moves it down one for the whole board
     * @param fullRow the index of the row being removed
     */
    private void rowsFallDown(int fullRow) {
        for (int r = fullRow; r > 0; r--) {
            for (int c = 0; c < this.cols; c++) {
                boolean occupiedAbove = this.grid[r-1][c].isOccupied();
                Paint colorAbove = this.grid[r-1][c].getColor();
                this.grid[r][c].setOccupied(occupiedAbove, colorAbove);
            }
        }
        for (int c = 0; c < this.cols; c++) {
            this.grid[0][c].setOccupied(false, Color.BLACK);
        }
    }

    /**
     * returns the javafx pane for the board
     * @return the board pane
     */
    public Pane getPane() {return this.boardPane; }

    /**
     * Clears all the squares on board and sets them back to black.
     */
    public void clearSquares() {
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                this.grid[r][c].setOccupied(false, Color.BLACK);
            }
        }
        for (Rectangle rectangle : this.rectBlocks) {this.boardPane.getChildren().remove(rectangle);}
        this.rectBlocks.clear();
    }

    /**
     * turns on/not on color blind mode, determines which color mode the board is in
     * @param enabled color blind mode on
     */
    public void setColorblindMode(boolean enabled) {
        this.colorblindMode = enabled;
    }

    /**
     * checks if colorblineMode is currently whats happening with the board
     * @return true is it's enabled, false if not
     */
    public boolean isColorblindMode() {
        return this.colorblindMode;
    }
}



