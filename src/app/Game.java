package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;

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
    protected Ball myBall; //= new app.Ball(myScene.getWidth()/2, myScene.getHeight()/2);
    protected Paddle myPaddle;
    protected ArrayList<Brick> myBricks;
    protected int numBricks;
    private int myLevel;
    private int bricksLeft;
    private ArrayList<ImageView> myPowers;
    private CheatKeys ch = new CheatKeys();
    private int myScore;

    //private Group root;
    private Text display;
    public boolean isPaused;
    private CollisionHandler myCollisionHandler;
    private DataReader myDataReader;
    //variables for splashPage that needs to be moved

    // Create the game's "scene": what shapes will be in the game and their starting properties
    public Scene createGame() {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        Image bkg = new Image("Half.png");

        double width = bkg.getWidth();
        double height = bkg.getHeight();
        var scene = new Scene(root, width, height, BACKGROUND);

        ImageView mv = new ImageView(bkg);
        myBall = new Ball(scene.getWidth() / 2, scene.getHeight() - 300);
        myPaddle = new Paddle(width, height);
        myLevel = 1;
        myPowers = new ArrayList<>();
        //Read in level set up and brick location

        myDataReader = new DataReader(width, height);
        myDataReader.readBricks(myLevel);
        myBricks = myDataReader.getMyBricks();

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


        for (Brick b : myBricks) {
            root.getChildren().add(b.getBrick());
            if (b.getPowerUp() != null) {
                root.getChildren().add(b.getPowerUp());
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
        // update attributes
        myBall.move(elapsedTime);
        myPaddle.move(WIDTH);

        display.setText("Lives remaining : " + Integer.toString(myPaddle.getLives()) + "\n Level: " + myLevel + "\n Score: " + myScore);

        if (myCollisionHandler.getBottom(myBall.getBall()) >= HEIGHT) {
            if (myPaddle.updateLives(-1) == 0) {
                isPaused = true;
                return -1;
            }
            myBall.resetBall(WIDTH, HEIGHT);
        }

        // check for collisions
        boolean left = myCollisionHandler.detectCollision(myBall.getBall(), myPaddle.getPaddlePart(0));
        boolean center = myCollisionHandler.detectCollision(myBall.getBall(), myPaddle.getPaddlePart(1));
        boolean right = myCollisionHandler.detectCollision(myBall.getBall(), myPaddle.getPaddlePart(2));

        if (left || center || right) {
            myBall.updateVeloPaddle(left ? -1 : right ? 1 : 0);
            myBall.move(elapsedTime);

            if (!myBall.firstBounce) {
                myBall.firstBounce = true;
                System.out.println("made it here");
            }
        }


        for (Brick b : myBricks) {
            if (myCollisionHandler.detectCollision(myBall.getBall(), b.getBrick())) {
                myScore++;
                if (myCollisionHandler.sideCollision(myBall.getBall(), b.getBrick())) {
                    myBall.updateVeloBrick(-1, 1);
                } else {
                    myBall.updateVeloBrick(1, -1);
                }
                //myBall.updateVeloBrick(-1, -1);
                bricksLeft -= b.updateBrick(1);
                if (bricksLeft == 0) {
                    return 1;
                }
                if (b.getLives() == 0 && b.getPowerUp() != null) {
                    //System.out.println("HERE");
                    myPowers.add(b.showPowerUp());
                }
            }
        }

        for (ImageView p : myPowers) {
            dropPowerUp(p, elapsedTime);
        }

        //change direction in x-axis when hits a wall
        myBall.wallBounce(WIDTH, HEIGHT);

        return 0;
    }


    public void dropPowerUp(ImageView power, double time) {
        power.setY(power.getY() + 100 * time);
        if (myCollisionHandler.detectCollision(power, myPaddle.getPaddle())) {
            myScore++;
            power.setVisible(false);
            myPaddle.updateLives(1);
            System.out.println(myPaddle.getLives());
        }
        if (power.getY() >= HEIGHT) {
            power.setVisible(false);
        }
    }
}
