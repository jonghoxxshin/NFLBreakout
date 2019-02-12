package app;

import app.handlers.CollisionHandler;
import app.handlers.DataHandler;
import app.powerup.powerUp;
import app.sprite.Ball;
import app.sprite.Brick;
import app.sprite.Paddle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


import static app.Breakout.HEIGHT;

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

public class Game {
    public static final Paint BACKGROUND = Color.LIGHTGREEN;
    public static final Image FIELD = new Image("Half.png");
    public static final ImageView BKG_VIEW = new ImageView(FIELD);
    private static final double WIDTH = 684;

    // some things we need to remember during our game
    protected Ball myBall;
    protected Paddle myPaddle;
    protected int numBricks;

    protected List<Brick> myBricks;
    protected int myLevel;
    protected int bricksLeft;
    private List<powerUp> myPowersNew;
    private int myScore;

    //private Group root;
    private Text display;
    public boolean isPaused;
    private CollisionHandler myCollisionHandler;
    private DataHandler myDataHandler;
    //variables for splashPage that needs to be moved
    public Game(int lev){
        myLevel = lev;
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    public Scene createGame() {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        Image bkg = new Image("Half.png");

        double width = bkg.getWidth();
        double height = bkg.getHeight() - 100;
        var scene = new Scene(root, width, height, BACKGROUND);

        ImageView mv = new ImageView(bkg);
        myBall = new Ball(scene.getWidth() / 2, scene.getHeight() - 100);
        myPaddle = new Paddle(width, height);
        myPowersNew = new ArrayList<>();

        //Read in level set up and brick location
        myDataHandler = new DataHandler(width, height);
        myDataHandler.readBricks(myLevel);
        myBricks = myDataHandler.getMyBricks();

        //background setting
        myCollisionHandler = new CollisionHandler();
        bricksLeft = myBricks.size();
        numBricks = bricksLeft;

        //display
        display = new Text("");
        display.setX(width / 2);
        display.setY(height / 2);
        display.setFill(Color.BLUE);

        // order added to the group is the order in which they are drawn
        root.getChildren().add(mv);
        root.getChildren().add(myBall.getBall());
        root.getChildren().add(myPaddle.getPaddle());
        root.getChildren().add(display);

        //Add brick ImageViews to scene
        for (Brick b : myBricks) {
            var tempArray = b.getMyHelmets();
            for(ImageView h:tempArray){
                root.getChildren().add(h);
            }
            if (b.getHasPower()) {
                root.getChildren().add(b.getPower().getPowerImg());
            }
        }

        // respond to input
        scene.setOnKeyPressed(e -> myPaddle.handleKeyPressed(e.getCode(), myBall));
        scene.setOnKeyReleased(e -> myPaddle.handleKeyReleased(e.getCode()));
        //scene.setOnKeyPressed(e -> handleCheatKeys(e.getCode()));
        return scene;
    }


    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    public int step(double elapsedTime) { // -1 => lost  // 1 -> win // 0 -> on-going
        // update attributes of ball and paddle
        myBall.move(elapsedTime);
        myPaddle.move(WIDTH);

        display.setText("Lives remaining : " + myPaddle.getLives() + "\n Level: " + myLevel + "\n Score: " + myScore);

        //Check if ball hits bottom of screen
        if(loseLifeCheck()){
            if (myPaddle.updateLives(-1) == 0) {
                isPaused = true;
                return -1; //You lose -- alert in breakout
            }
        }

        // check for collisions (ball and paddle)
        paddleCollision(elapsedTime);

        //Check for collisions of each brick in scene (all in myBricks)
        brickCollision();
        if(bricksLeft == 0){
            return 1;//beat level
        }

        //If a powerUp was added to myPowersNew (brick with powerUp broke--> drop said powerUp)
        powerHelper(elapsedTime);
        myBall.wallBounce(WIDTH, HEIGHT);

        return 0;
    }

    /**
     * Helper method called in step to check if ball hits bottom of screen
     * @return true if hit bottom of screen, false otherwise
     */
    public boolean loseLifeCheck(){
        if (myCollisionHandler.getBottom(myBall.getBall()) >= HEIGHT) {
            myScore -= 1;
            myBall.resetBall(WIDTH, HEIGHT);
            return true;
        }
        return false;
    }

    /**
     * Helper method called in step to detect brick-ball collisions and update brick, bricksLeft, and myPowersNew accordingly
     */
    public void brickCollision(){
        for (Brick b : myBricks) {
            if (myCollisionHandler.detectCollision(myBall.getBall(), b.getBrick())) {
                myScore++;
                if(myBall.getMyStatus() == 0){
                    if (myCollisionHandler.sideCollision(myBall.getBall(), b.getBrick())) {
                        myBall.updateVeloBrick(-1, 1);
                    } else {
                        myBall.updateVeloBrick(1, -1);
                    }
                    bricksLeft -= b.updateBrick(1, b.getLives());
                }
                //Big ball - breaks brick
                else{
                    bricksLeft -= b.updateBrick(3, b.getLives());
                }
                //If brick breaks and there is a powerUp -- add it to myPowersNew
                if (b.getLives() == 0 && b.getHasPower()) {
                    myPowersNew.add(b.getPower());
                }
            }
        }
    }

    /**
     * Helper method called in step to detect ball-paddle collisions and update ball velocity accordingly
     * @param time
     */
    public void paddleCollision(double time){
        // check for collisions (ball and paddle)
        boolean left = myCollisionHandler.detectCollision(myBall.getBall(), myPaddle.getPaddlePart(0));
        boolean center = myCollisionHandler.detectCollision(myBall.getBall(), myPaddle.getPaddlePart(1));
        boolean right = myCollisionHandler.detectCollision(myBall.getBall(), myPaddle.getPaddlePart(2));

        if (left || center || right) {
            myBall.updateVeloPaddle(left ? -1 : right ? 1 : 0);
            myBall.move(time);

            if (!myBall.firstBounce) {
                myBall.firstBounce = true;
            }
        }
    }

    public void powerHelper(double time){
        for(powerUp p: myPowersNew){
            p.dropPower(time);
            myScore += p.catchPower(myPaddle, myBall);
        }
    }

    public DataHandler getDataHandler(){
        return myDataHandler;
    }

    public int getScore(){
        return myScore;
    }
}
