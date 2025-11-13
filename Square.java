package tetris;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * The square class represents each of the squares on the tetris board. It gives them a position,
 * visual, and defines if they are "occupied" by a piece/block or not.
 */
public class Square {
    private final Rectangle rectangle;
    private int row;
    private int col;
    private boolean occupied;

    /**
     * Constructor for square take an initial position, places on the pane, and a color (originally
     * set to black)
     * @param row which row the square is in
     * @param col which col the square is in
     * @param boardPane the pane where the squares are added
     * @param color the color the squares display for the player to see
     */
    public Square(int row, int col, Pane boardPane, Color color) {
        this.row = row;
        this.col = col;
        this.occupied = false;
        this.rectangle = new Rectangle(col*Constants.SQUARE_WIDTH, row*Constants.SQUARE_WIDTH,
                Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
        this.rectangle.setFill(color);
        this.rectangle.setStroke(Color.BLACK);
        boardPane.getChildren().add(this.rectangle);
    }

    /**
     * removed a rectangle from the boardPane
     * @param boardPane the pane the rectangle gets removed from
     */
    public void removeFromPane(Pane boardPane){
        boardPane.getChildren().remove(this.rectangle);
    }

    /**
     * Moves the square one column (30px) right. This is used for the key event RIGHT.
     */
    public void moveRight() {
        this.rectangle.setX(this.rectangle.getX() + Constants.SQUARE_WIDTH);
        this.col += 1;
    }

    /**
     * Moves the square one column (30px) left. Use for the key event LEFT.
     */
    public void moveLeft() {
        this.rectangle.setX(this.rectangle.getX() - Constants.SQUARE_WIDTH);
        this.col -= 1;
    }

    /**
     * Moves the square one row (30px) down. Use for key event DOWN.
     */
    public void moveDown() {
        this.rectangle.setY(this.rectangle.getY() + Constants.SQUARE_WIDTH);
        this.row += 1;
    }

    /**
     * Places a square at a given row and col, changes the visual and behind the scenes position of
     * the rectangle
     * @param newRow rect's new row
     * @param newCol rect's new col
     */
    public void setPosition(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
        this.rectangle.setX(this.col * Constants.SQUARE_WIDTH);
        this.rectangle.setY(this.row * Constants.SQUARE_WIDTH);
    }

    /**
     * Accessor for the row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Accessor for the col
     */
    public int getCol() {
        return this.col;
    }

    /**
     * visually changes color of the rectangle
     * @param color the new color
     */
    public void setColor(Paint color){
        this.rectangle.setFill(color);
    }

    /**
     * returns if the square is occupied by a piece (true/false)
     * @return true/false boolean
     */
    public boolean isOccupied() {
        return this.occupied;
    }

    /**
     * gets the current color of the square in question, returns it
     * @return returns the color of the square
     */
    public Paint getColor() {
        return this.rectangle.getFill();
    }

    /**
     * returns the rectangle that represents the square you want to get
     * @return rectangle
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * sets if the square is occupied by a piece, and a color
     * @param occupied true for occupied, false for unoccupied
     * @param color color the square to be set to
     */
    public void setOccupied(boolean occupied, Paint color) {
        this.occupied = occupied;
        this.rectangle.setFill(color);
    }
}