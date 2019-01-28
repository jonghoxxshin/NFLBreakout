package example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;


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
public class ExampleBounce extends Application {

    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.LIGHTGREEN;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final Paint BOUNCER_COLOR = Color.BROWN;
    public static final int BOUNCER_SPEED = 60;
    public static final Paint PADDLE_COLOR = Color.GRAY;
    public static final int PADDLE_SPEED = 30;





    // some things we need to remember during our game
    private Scene myScene;
    private Circle myBouncer;
    private int velocityX = 1;
    private int velocityY = 1;
    private Rectangle myPaddle;



    /**
     * Initialize what will be displayed and how it will be updated.
     *
     * Wahtever
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        //var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        // x and y represent the top left corner, so center it

        myBouncer = new Circle(width/2, height/2, 10);
        myBouncer.setFill(BOUNCER_COLOR);
        myPaddle = new Rectangle(30, 15,PADDLE_COLOR);
        myPaddle.setY(height-20);
        myPaddle.setX(width/2);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBouncer);
        root.getChildren().add(myPaddle);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        myBouncer.setCenterX(myBouncer.getCenterX() + BOUNCER_SPEED * velocityX * elapsedTime);
        myBouncer.setCenterY(myBouncer.getCenterY() + BOUNCER_SPEED * velocityY * elapsedTime);

        // check for collisions
        // with shapes, can check precisely

        //check if paddle and mover intersects
        var intersect = Shape.intersect(myBouncer, myPaddle);
        if(intersect.getBoundsInLocal().getWidth()!=-1){
            velocityY *= -1;
        }


        //change direction in x-axis when hits a wall
        if(myBouncer.getCenterX() <= 0 ||
                myBouncer.getCenterX()>= myScene.getWidth() - myBouncer.getBoundsInLocal().getWidth()){
            velocityX *= -1 * Math.random();
        }
        //chage direction in y-axis when hits a wall
        if(myBouncer.getCenterY() <= 0 ){
            velocityY *= -1;
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
//        if (myGrower.contains(x, y)) {
//            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
//            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
//        }
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
