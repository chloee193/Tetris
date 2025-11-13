package tetris;

import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

/**
 * The PaneOrganizer is responsible for the visual/javafx elements of the game. It handles
 * the panes, labels, border, buttons and the UI.
 */
public class PaneOrganizer {
    private final Pane borderPane;
    private final Pane boardPane;
    private final Label scoreLabel = new Label("Score: 0");
    private Label levelLabel;

    /**
     * The PaneOrganizer Constructor creates the board and arranges all the style elements of it (
     * like width, height, cols/rows). It adds the labels and quit button. It initializes the game
     * and sets up the game's visual.
     */
    public PaneOrganizer() {
        //making the boardPane
        this.boardPane = new Pane();
        this.boardPane.setPrefSize(Constants.PLAYABLE_COLS * Constants.SQUARE_WIDTH,
                Constants.PLAYABLE_ROWS * Constants.SQUARE_WIDTH);
        this.boardPane.setLayoutX(Constants.SQUARE_WIDTH);
        this.boardPane.setLayoutY(Constants.SQUARE_WIDTH);

        //making the borderPane
        this.borderPane = new Pane();
        this.borderPane.setPrefSize(this.boardPane.getPrefWidth() +
                2*Constants.SQUARE_WIDTH, this.boardPane.getPrefHeight() +
                3*Constants.SQUARE_WIDTH);
        this.makeBorder();

        //initialize + place score label
        this.setupLabelPane();

        //adding boardpane to borderpane
        this.borderPane.getChildren().add(this.boardPane);

        //set up button pane
        this.setupButtonPane();

        //initializes the game
        Board board = new Board(Constants.PLAYABLE_ROWS, Constants.PLAYABLE_COLS, this.boardPane);
        Game game = new Game(board, this.boardPane, this.scoreLabel, this.levelLabel);
    }

    /**
     * draws a grey border with dark grey outline around the tetris board, which helps the player
     * see where their blocks are visually on the screen, and clearly define the edge of the board.
     */
    private void makeBorder() {
        int numRows = Constants.PLAYABLE_ROWS + 3;
        int numCols = Constants.PLAYABLE_COLS + 2;

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c ++) {
                if (r == 0 || r == numRows - 2 || c == 0 || c == numCols - 1) {
                    Rectangle rectangle = new Rectangle(c*Constants.SQUARE_WIDTH,
                            r*Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH,
                            Constants.SQUARE_WIDTH);
                    rectangle.setFill(Color.GRAY);
                    rectangle.setStroke(Color.DARKGRAY);
                    this.borderPane.getChildren().add(rectangle);
                }
            }
        }
    }

    /**
     * getter method for the root pane of the UI.
     * @return the root pane with the game's visual aspects
     */
    public Pane getRoot() {return this.borderPane; }

    /**
     * Sets up the pane at the bottom that contains the quit button. defines all the style/visual
     * elements of the pane, and has the key event for the quit button.
     */
    private void setupButtonPane() {
        //quit button setup
        Button quitButton = new Button("Quit");
        quitButton.setPrefWidth(Constants.BUTTON_WIDTH);
        quitButton.setPrefHeight(Constants.BUTTON_HEIGHT);
        quitButton.setStyle(Constants.LABEL_COLOR);

        HBox controlPane = new HBox(quitButton);
        controlPane.setPrefHeight(Constants.BUTTON_HEIGHT);
        controlPane.setPrefWidth(Constants.SCENE_WIDTH);
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setStyle(Constants.CONTROL_PANE_COLOR);

        quitButton.setOnAction((e) -> System.exit(0));
        quitButton.setFocusTraversable(false);

        this.borderPane.getChildren().add(controlPane);
        controlPane.setLayoutY(this.borderPane.getPrefHeight() - Constants.BUTTON_HEIGHT);
    }

    /**
     * sets up the label pane at the top of the game that contains the player's score and current
     * level. adds it to the borderpane and outlines the visual aspects of this pane.
     */
    private void setupLabelPane() {
        this.levelLabel = new Label("Level 1");
        this.levelLabel.setStyle(Constants.LABEL_COLOR);
        this.scoreLabel.setStyle(Constants.LABEL_COLOR);

        javafx.scene.layout.BorderPane labelPane = new javafx.scene.layout.BorderPane();
        labelPane.setPrefSize(Constants.SCENE_WIDTH, Constants.SCORE_LABEL_HEIGHT);
        labelPane.setStyle(Constants.LABEL_COLOR);

        labelPane.setRight(this.scoreLabel);
        labelPane.setLeft(this.levelLabel);

        this.borderPane.getChildren().add(labelPane);
    }
}