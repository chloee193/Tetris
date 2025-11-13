package tetris;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The Game class is responsible for mainly managing the Tetris game's logic and controlling things
 * like the user's key events, the timeline, updating the game, game over, and restarting the game.
 * It interacts with the piece and board classes to achieve this game functionality, and uses the
 * panes for visual aspects.
 */
public class Game {
    private final Pane boardPane;
    private final Board board;
    private Piece piece;
    private Timeline timeline;
    private final Label scoreLabel;
    private Label pauseLabel;
    private Label gameOverLabel;
    private boolean gameRunning = true;
    private boolean colorblindMode = false;
    //fields for the level functionality
    private double currentTimelineDuration = Constants.DURATION;
    private int level = 1;
    private int rowsCleared = 0;
    private final Label levelLabel;

    /**
     *Constructor for a  game that takes a board, boardPane, and the two labels for score and level.
     * This initialized the first piece on the board, and sets up the event handling/gets focus for
     * later key events to be able to affect the boardPane.
     * @param board the game board that manages the grid/squares in the board
     * @param boardPane visually displays the board and the pieces
     * @param scoreLabel label displays player's current score
     * @param levelLabel label displays player's current level
     */
    public Game(Board board, Pane boardPane, Label scoreLabel, Label levelLabel) {
        this.boardPane = boardPane;
        this.board = board;
        this.piece = new Piece(board);

        this.scoreLabel = scoreLabel;
        this.levelLabel = levelLabel;
        //makes pause label
        this.makePauseLabel();
        this.makeGameOverLabel();

        //sets up timeline, key event/handling logic
        boardPane.setFocusTraversable(true);
        boardPane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        this.resetTimeLine(this.currentTimelineDuration);
        boardPane.requestFocus();
    }

    /**
     *Deals with the keys the player presses while playing the game. The player can move the pieces
     * left/right, rotate it, move it down one-by-one or instantly dropping it to the bottom. Other
     * things include restarting the game, pausing the game, and changing the color mode to be
     * more color blind friendly.
     * @param e e is the key event that happens when the player presses a key
     */
    private void handleKeyPress(KeyEvent e) {
        //when the game isn't running, the only keys the game reacts to are either unpause or
        //restart. This is meant to make it so that the player can't move pieces when the
        //game isn't running.
        if (!this.gameRunning) {
            if (e.getCode() != KeyCode.P && e.getCode() != KeyCode.R) {
                e.consume();
                return;
            }
        }

        switch(e.getCode()) {
            case LEFT:
                if (this.piece.canMoveLeft(this.board)) {this.piece.moveLeft();}
                break;
            case RIGHT:
                if (this.piece.canMoveRight(this.board)) {this.piece.moveRight();}
                break;
            case DOWN:
                if (this.piece.canMoveDown(this.board)) {this.piece.moveDown();}
                break;
            case SPACE:
                this.piece.drop();
                this.board.settlePiece(this.piece);
                int cleared = this.board.checkFullRows();
                if (cleared > 0) {
                    this.updateScore(cleared);
                }
                this.generateNewPiece();
                break;
            case UP:
                this.piece.rotate(this.board);
                break;
            case P:
                this.pauseMethod();
                break;
            case R:
                this.restartGame();
                break;
            case C: //will also restart game / makes new game in the new color scheme
                if (this.gameRunning) {this.changeColorMode();}
                break;
            default:
                break;
        }
        e.consume();
    }

    /**
     * Every duration of the timeline it calls this method. Written separately for readability. It
     * constantly checks if the pieces can move down, and if they can't it settles the piece and
     * checks for full rows before generating a new piece and continuing the game. It also checks
     * for the player losing, which happens when two pieces intersect (one spawns on the other).
     */
    private void updateGame() {
        if (this.piece.canMoveDown(this.board)) {
            this.piece.moveDown();
        } else {
            this.board.settlePiece(this.piece);
            int cleared = this.board.checkFullRows();
            if (cleared > 0) {
                this.updateScore(cleared);
            }
            this.generateNewPiece();
        }

        boolean gameOver = this.piece.piecesIntersect();
        if (gameOver) {
            this.gameOverMethod("GAME OVER");
        }
    }

    /**
     * generates a new piece at top of the board
     */
    private void generateNewPiece() {
        this.piece = new Piece(this.board);
    }

    /**
     * updates the player's score based on the number of rows that were cleared that "turn,"
     * calculates each number differently. (1 row = 100 points, 2 = 400, 3 = 900, 4 = 2000/Tetris!).
     * Also updates the score label and checks the amount of rows cleared for the level up
     * functionality. Also checks if the player has reached the max score of 100k, where the game
     * would automatically end/player wins (for src).
     * @param rowsCleared stores the number of rows cleared
     */
    private void updateScore(int rowsCleared) { //call this in the full row method thing
        String number = this.scoreLabel.getText().substring(Constants.SCORE_SUBSTRING);
        int value = Integer.parseInt(number);

        if (rowsCleared <=3) {
        value += Constants.SCORE_INCREASE * rowsCleared * rowsCleared;
        } else if (rowsCleared == 4) {
            value += Constants.FOUR_ROWS_SCORE;
        }

        this.scoreLabel.setText("Score: " + value);

        this.rowsCleared += rowsCleared;
        if (this.rowsCleared/Constants.ROWS_PER_LEVEL >= this.level) {
            this.levelUp();
        }

        if (value >= Constants.MAX_SCORE) {
            this.gameOverMethod("YOU WIN");
        }
    }

    /**
     * increase level, increase speed of the timeline (difficulty), and updates the level label.
     * Used in updateScore.
     */
    private void levelUp() {
        this.level++;
        this.levelLabel.setText("Level: " + this.level);
        this.currentTimelineDuration = Math.max(0.1, this.currentTimelineDuration*0.95);
        this.timeline.stop();
        this.timeline.getKeyFrames().clear();
        KeyFrame kf = new KeyFrame(Duration.seconds(this.currentTimelineDuration),
                e -> this.updateGame());
        this.timeline.getKeyFrames().add(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * handles game over functionality. stops the timeline, stops the game running, and displays the
     * game over message. This is used twice, when the player loses ("GAME OVER") or when the player
     * wins-reaching 100k ("YOU WIN").
     * @param message
     */
    private void gameOverMethod(String message) {
        this.timeline.stop();
        this.gameRunning = false;
        this.gameOverLabel.setText(message);
        this.gameOverLabel.setVisible(true);
    }

    /**
     * Creates the pause label, which is in the top left corner of the board. initially not visible.
     */
    private void makePauseLabel() {
        this.pauseLabel = new Label("GAME PAUSED");
        this.pauseLabel.setStyle(Constants.LABEL_COLOR);
        this.pauseLabel.setAlignment(Pos.TOP_LEFT);
        this.pauseLabel.setTranslateX(5);
        this.pauseLabel.setTranslateY(5);
        this.boardPane.getChildren().add(this.pauseLabel);
        this.pauseLabel.setVisible(false);
    }

    /**
     * creates the game over label, which is initially not visible.
     */
    private void makeGameOverLabel() {
        this.gameOverLabel = new Label("GAME OVER");
        this.gameOverLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: red; " +
                "-fx-background-color: white");

        this.gameOverLabel.setLayoutX(Constants.SCENE_WIDTH/2.0 - Constants.GAME_OVER_X_ADJUST);
        this.gameOverLabel.setLayoutY(Constants.SCENE_HEIGHT/2.0 - Constants.GAME_OVER_Y_ADJUST);

        this.boardPane.getChildren().add(this.gameOverLabel);
        this.gameOverLabel.toFront();
        this.gameOverLabel.setVisible(false);
    }

    /**
     * pauses/resumes the game, which affects the timeline and gameRunning, as well as changing the
     * visibility of the pause label.
     */
    private void pauseMethod() {
        if (this.timeline.getStatus() == Animation.Status.RUNNING) {
            this.timeline.stop();
            this.gameRunning = false;
            this.pauseLabel.setVisible(true);
        } else {
            this.timeline.play();
            this.gameRunning = true;
            this.pauseLabel.setVisible(false);
        }
    }

    /**
     * restarts the game. can be called at any point in the game, resets everything back to it's
     * initial values, including the timeline, board, labels, level, score.
     */
    private void restartGame() {
        //stops timeline + clears board
        this.timeline.stop();
        this.board.clearSquares();

        //remove the rectangle pieces visually on the board
        for (Rectangle rectangle : this.piece.getRectangles()) {
            this.boardPane.getChildren().remove(rectangle);
        }

        //hide the game over and pause labels
        if (this.pauseLabel != null) this.pauseLabel.setVisible(false);
        if (this.gameOverLabel != null) this.gameOverLabel.setVisible(false);

        //resets score label, level, level label and timeline duration back to starting values
        this.scoreLabel.setText("Score: 0");
        this.level = 1;
        this.levelLabel.setText("Level 1");
        this.currentTimelineDuration = Constants.DURATION;

        //stopping + resetting the timeline with original duration
        this.resetTimeLine(this.currentTimelineDuration);

        //make new piece and restarting/resuming the game
        this.piece = new Piece(this.board);
        this.gameRunning = true;
        this.timeline.play();
    }

    /**
     * changes the color "mode" from colorblind mode to normal mode or vice versa. Called on key
     * event "C."
     */
    private void changeColorMode() {
        this.colorblindMode = !this.colorblindMode;
        this.board.setColorblindMode(this.colorblindMode);
        this.restartGame();
    }

    /**
     * This method resets the timeline and defines it's duration time, which is useful for the
     * restart and initializing the game.
     * @param duration duration is the time the timeline takes to update every time
     */
    private void resetTimeLine(double duration) {
        if (this.timeline != null) {
            this.timeline.stop();
            this.timeline.getKeyFrames().clear();
        } else {
            this.timeline = new Timeline();
        }

        KeyFrame kf = new KeyFrame(Duration.seconds(duration), e -> this.updateGame());
        this.timeline.getKeyFrames().add(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }
}