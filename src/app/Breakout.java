package app;

import app.tests.TestGame;
import app.tests.TestsLev1;
import app.tests.TestsLev2;
import app.tests.TestsLev3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;


/**
 * A basic example JavaFX program for the first lab.
 *
 * Cool! No WAY!! YETALLY!
 *
 * Branched dude!
 *
 * Right on
 *
 * * @author Ryan Bloom & Jongho Shin
 */
public class Breakout extends Application {

    public static final String TITLE = "DALLAS COWBOYS BREAKOUT!";
    public static final int HEIGHT = 628;
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
    private String alertMsg;
    private MusicPlayer myMusicPlayer;
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

        myMusicPlayer = new MusicPlayer();
        myMusicPlayer.check();


        //MediaView mediaView = new MediaView(mp);

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
        //If game != null --> level was beaten and increment to next level
        if (splashState == 1) {
            //No game has been played, initialize level 1
            if(game == null){
                game = new Game(1);
            }
            else{
                int lev = game.getMyLevel();
                game = new Game(lev);
            }
        } else if (splashState >= 3) {
            //No game has been played, start at level 1 tests
            if(game == null){
                game = new TestsLev1(splashState);
            }
            else{
                int lev = game.getMyLevel();
                if(lev == 1){game = new TestsLev1(splashState);}
                else if(lev == 2){game = new TestsLev2(splashState);}
                else if(lev == 3){game = new TestsLev3(splashState);}
            }
            alertMsg = ((TestGame) game).getMsg();
        }
        //if 2 --> alert message, no game started
        if (splashState == 1 || splashState >= 3) {
            stage.setScene(game.createGame());
            splashPage.setSplash(2);
            gamePaused = false;
        }
        alertCheck(elapsedTime);

    }

    /**
     * Helper method to check if an alert should be thrown an
     * @param time
     */
    public void alertCheck(double time){
        if(game != null && !gamePaused) {
            int res = game.step(time);
            if(res == -1) { // lost
                alerter(1, "You ran out of lives! You lost and are out of the playoffs!");
            } else if(res == 1) { // won
                if(game.getMyLevel() <= 2){
                    alerter(0, "You broke all the bricks! Onto the next round!");
                }
                else{
                    game.getDataHandler().updateHighScore(game.getScore());
                    alerter(0, "You beat the final level! SUPER BOWL CHAMPS!");
                }
                //alerter(0, "You broke all the bricks! You beat the level!");
            } else if(res == 2){
                //test success
                alerter(2, alertMsg);
            }else if(res ==3){
                //test failed
            }
        }
    }

    /**
     * Displays splash page after losing, winning, or completing a test case
     */
    public void resetGame(){
        stage.setScene(splashPage.getSplashScene());
        splashPage.setSplash(0);
    }

    /**
     * Called when game ends (either lose, win, or test case finishes)
     * @param i
     * @param msg
     */
    public void alerter(int i, String msg){ //0 if win, 1 if lose, 2 if test
        gamePaused = true;
        //https://stackoverflow.com/questions/28937392/javafx-alerts-and-their-size
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        String title = "";
        String header = "";
        String con = "";
        if( i == 0){
            title = "You win";
            header = "Winner";
            con = msg;
        }
        else if(i == 1) {
            title = "You lose";
            header = "Loser";
            con = msg;
        }
        else if (i == 2){
            title = "Test result";
            header = "Success";
            con = alertMsg;
        }
        a.setTitle(title);
        a.setHeaderText(header);
        a.setResizable(true);
        String version = System.getProperty("java.version");
        String content = String.format(con, version);
        a.setContentText(content);
        a.setOnHidden(evt -> handleAlert(i));
        a.show();
    }

    /**
     * Handles alert message quits -- either reset game or move onto next level (if game was won)
     * @param res
     */
    //https://stackoverflow.com/questions/44742134/animationtimer-showandwait-is-not-allowed-during-animation-processing
    public void handleAlert(int res){
        if(res == 1){
            game = null;
        }
        else if(res == 0){
            game.setMyLevel(game.getMyLevel() + 1);
            if(game.getMyLevel() > 3){
                game = null;
            }
        }
        resetGame();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
