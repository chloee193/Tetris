package tetris;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * This is the main class where your Tetris game will start.
 * The main method of this application calls launch, a JavaFX method
 * which eventually calls the start method below. You will need to fill
 * in the start method to start your game!
 *
 *
 * The App class is responsible for starting the tetris game. It extends application and creates the
 * PaneOrganizer, scene, and shows the stage. It handles the highest level logic of the game.
 */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        CpuTimeTracker tracker = new CpuTimeTracker();
        tracker.start();

        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.setOnCloseRequest(e -> {tracker.outputEstimate();});
        stage.show();
    }

    /*
     * Here is the mainline! No need to change this.
     */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
