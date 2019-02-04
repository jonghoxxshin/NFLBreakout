package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * A basic example JavaFX program for the first lab.
 *
 * Cool! No WAY!! YETALLY!
 *
 * Branched dude!
 *
 * Right on
 *
 * * @author Robert C. Duvall
 */
public class Breakout extends Application {

    public static final String TITLE = "Example JavaFX";
    public static final int HEIGHT = 728;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.LIGHTGREEN;
    public static final Image FIELD = new Image("Half.png");
    public static final ImageView BKG_VIEW = new ImageView(FIELD);
    private static final double WIDTH = 684;

    private Timeline animation;
    private Stage stage;
    private SplashPage splashPage;
    private Game game;
    private boolean gamePaused = false;

    /**
     * Initialize what will be displayed and how it will be updated.
     *
     * Wahtever
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        this.stage = stage;
        splashPage = new SplashPage(BACKGROUND);
        stage.setTitle(TITLE);
        stage.show();
        stage.setScene(splashPage.getSplashScene());
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        int splashState = splashPage.getSplash();
        if (splashState == 1) {
            game = new Game();
        } else if (splashState >= 3) {
            game = new TestGame(splashState);
        }

        if (splashState == 1 || splashState >= 3) {
            stage.setScene(game.createGame());
            splashPage.setSplash(2);
            gamePaused = false;
        }

        if(game != null && !gamePaused) {
            int res = game.step(elapsedTime);
            if(res == -1) { // lost
                alerter(1);
                stage.setScene(splashPage.getSplashScene());
                splashPage.setSplash(0);
                game = null;
            } else if(res == 1) { // won
                alerter(0);
                // game.nextLevel();
            } else if(res == 2){
                //test success
                alerter(2);
            }else if(res ==3){
                //test failed
            }
        }
    }





    public void alerter(int i){ //0 if win, 1 if lose, 2 if test
        gamePaused = true;
        //https://stackoverflow.com/questions/28937392/javafx-alerts-and-their-size
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        String title = "";
        String header = "";
        String con = "";
        if( i == 0){
            title = "You win";
            header = "Winner";
            con = "You broke all the bricks! You beat the level!";
        }
        else if(i == 1) {
            title = "You lose";
            header = "Loser";
            con = "You ran out of lives! You lost!";


        }
        else if (i == 2){
            title = "Test result";
            header = "Success";
            con = "The test was successful!";

        }
        a.setTitle(title);
        a.setHeaderText(header);
        a.setResizable(true);
        String version = System.getProperty("java.version");
        String content = String.format(con, version);
        a.setContentText(content);
        a.show();
    }




    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
