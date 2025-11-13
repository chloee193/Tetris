package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Piece represents the tetris pieces. There are 7 different ones, all made up of 4 squares. The
 * piece class handles most of the functionality related to the tetris pieces, including the
 * coloring, rotation, and checking where the piece can move.
 */
public class Piece {
    private final Square[] squareArray; //squares that make up a given piece
    private final Board board;


    /**
     * Constructs a piece and it's initial 4 squares
     * @param board the piece "belongs to" the board
     */
    public Piece(Board board) {
        this.squareArray = new Square[4];
        this.board = board;
        this.generatePiece();
    }

    /**
     *     removes the squares of the piece from the pane, used after the piece settles so that the
     *     visual can be displayed without the piece blocking it
     */
    public void removeFromPane(){
        for (Square square: this.squareArray){
            square.removeFromPane(this.board.getPane());
        }
    }

    /**
     * randomly generates one of the 7 pieces at the top of the board, places around the the top
     * middle.
     */
    private void generatePiece() {
        int initialCol = Constants.PLAYABLE_COLS/2 -1;
        int initialRow = 0;

        int pieceType = (int)(Math.random() * PIECE_COORDS.length);
        int[][] positioning = PIECE_COORDS[pieceType];
        Color[] pieceColor = getPieceColors(this.board.isColorblindMode());
        Color color = pieceColor[pieceType];

        for (int i = 0; i < this.squareArray.length; i++) {
            int col = initialCol + positioning[i][0];
            int row = initialRow + positioning[i][1];

            Square s = new Square(row, col, this.board.getPane(), color);
            this.squareArray[i] = s;
        }
    }

    /**
     * stores the coordinates of each of the shapes, helpful for accessing when generating the
     * pieces in generatePiece() method.
     */
    private static final int[][][] PIECE_COORDS = {
            Constants.I_PIECE_COORDS,
            Constants.O_PIECE_COORDS,
            Constants.T_PIECE_COORDS,
            Constants.J_PIECE_COORDS,
            Constants.L_PIECE_COORDS,
            Constants.S_PIECE_COORDS,
            Constants.Z_PIECE_COORDS
    };

    /**
     * potentially returns two different arrays of colors. One is the standard colors, and the other
     * is the colorblind version colors.
     * @param colorblindMode
     * @return
     */
    public static Color[] getPieceColors(boolean colorblindMode) {
            if (colorblindMode) {
                return new Color[]{
                        Constants.I_COLOR_CB,
                        Constants.O_COLOR_CB,
                        Constants.T_COLOR_CB,
                        Constants.J_COLOR_CB,
                        Constants.L_COLOR_CB,
                        Constants.S_COLOR_CB,
                        Constants.Z_COLOR_CB
                };
            } else {
                return new Color[]{
                        Constants.I_COLOR,
                        Constants.O_COLOR,
                        Constants.T_COLOR,
                        Constants.J_COLOR,
                        Constants.L_COLOR,
                        Constants.S_COLOR,
                        Constants.Z_COLOR
                };
            }
    }

    /**
     * Moves piece one square right
     */
    public void moveRight() {
        for(Square currSquare : this.squareArray) {
            currSquare.moveRight();
        }
    }

    /**
     * Moves piece one square left
     */
    public void moveLeft() {
        for(Square currSquare : this.squareArray) {
            currSquare.moveLeft();
        }
    }

    /**
     * Moves piece one square down
     */
    public void moveDown() {
        for(Square currSquare : this.squareArray) {
            currSquare.moveDown();
        }
    }

    /**
     * Calls moveDown() until the piece reaches the lowest/bottom it can go (when it hits another
     * piece or the bottom of the board), so drops the piece down
     */
    public void drop() {
        while (this.canMoveDown(this.board)) {
            this.moveDown();
        }
    }

    /**
     * Checks if a piece can move in specific ways. Only can move when moving somewhere that's still
     * on the board
     * @param board game board
     * @param rowChange how many rows the piece is changing
     * @param colChange how many cols the piece is changing
     * @return true/false depending on if the piece can move
     */
    public boolean canMove(Board board, int rowChange, int colChange) {
        for (Square currSquare : this.squareArray) {

            int newRow = currSquare.getRow() + rowChange;
            int newCol = currSquare.getCol() + colChange;

            if (newRow < 0 || newRow >= Constants.PLAYABLE_ROWS || newCol < 0 ||
                    newCol >= Constants.PLAYABLE_COLS || board.spotTaken(newRow, newCol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Booleans for if the piece can move down, left, right
     * @param board
     * @return
     */
    public boolean canMoveDown(Board board) {
        return this.canMove(board, 1, 0);
    }
    public boolean canMoveLeft(Board board) {
        return this.canMove(board, 0, -1);
    }
    public boolean canMoveRight(Board board) {
        return this.canMove(board, 0, 1);
    }

    /**
     * Returns the squares that make up the pieces in question
     */
    public Square[] getSquares() {
        return this.squareArray;
    }

    /**
     * rotates the piece clockwise. if the piece would go off screen with the rotation, it will
     * adjust it left or right to allow for the rotation to still happen.
     * @param board game board
     */
    public void rotate(Board board) {
        Square centerSquare = this.squareArray[1];
        int centerCol = centerSquare.getCol();
        int centerRow = centerSquare.getRow();

        int[] newCols = new int[this.squareArray.length];
        int[] newRows = new int[this.squareArray.length];

        for (int i = 0; i < this.squareArray.length; i++) {
            Square currSquare = this.squareArray[i];
            int oldColLoc = currSquare.getCol();
            int oldRowLoc = currSquare.getRow();
            newCols[i] = centerCol - centerRow + oldRowLoc;
            newRows[i] = centerRow + centerCol - oldColLoc;
        }

        //checks if the piece can turn, turns piece if the piece has the space to turn, adjusts if
        //the piece can't turn, so it can turn when it's near the walls
        int[] adjustment = new int[]{0, 1, -1, 2, -2};
        for (int adjust : adjustment) {
            int[] adjustCols = new int[newCols.length];
            for (int i = 0; i < newCols.length; i++) {
                adjustCols[i] = newCols[i] + adjust;
            }
            if (this.canTurn(newRows, adjustCols, board)) {
                this.rotatePiece(newRows, adjustCols);
                break;
            }
        }
    }

    /**
     * Checks if the piece can turn and still stay on board
     */
    private boolean canTurn(int[] rows, int[] cols, Board board) {
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] < 0 || rows[i] >= Constants.PLAYABLE_ROWS || cols[i] < 0 ||
                    cols[i] >= Constants.PLAYABLE_COLS || board.spotTaken(rows[i], cols[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     *updates the positions of each square in the piece for rotation
     * @param rows rows after rotation
     * @param cols cols after rotation
     */
    private void rotatePiece(int[] rows, int[] cols) {
        for (int i=0; i < this.squareArray.length; i++) {
            this.squareArray[i].setPosition(rows[i], cols[i]);
        }
    }

    /**
     * checks if any of the squares of one piece intersect with another piece
     * @return true if there's intersection, false if not
     */
    public boolean piecesIntersect() {
        for (Square currSquare : this.squareArray) {
            int row = currSquare.getRow();
            int col = currSquare.getCol();
            if (this.board.spotTaken(row, col)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns a list of the rectangles, helpful for collision purposes checking
     */
    public List<Rectangle> getRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (Square square : this.squareArray) {
            rectangles.add(square.getRectangle());
        }
        return rectangles;
    }
}