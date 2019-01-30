package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;


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
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.LIGHTGREEN;

    // some things we need to remember during our game
    private Scene myScene;
    private Ball myBall; //= new app.Ball(myScene.getWidth()/2, myScene.getHeight()/2);
    private Paddle myPaddle;
    private ArrayList<Brick> myBricks;
    private int myLevel;
    private String[] levelFiles = {"level1_setup.txt", "level2_setup.txt", "level3_setup.txt"};
    private Timeline animation;
    private int bricksLeft;
    private ArrayList<ImageView> myPowers;
    private cheatKeys ch = new cheatKeys();
    //private Group root;

    /**
     * Initialize what will be displayed and how it will be updated.
     *
     * Wahtever
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        //myPaddle = new Paddle();
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        //var animation = new Timeline();
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
        myScene = scene;
        myBall = new Ball(scene.getWidth()/2, scene.getHeight()-50);
        myPaddle = new Paddle(scene);
        myLevel = 1;
        animation = new Timeline();
        myPowers = new ArrayList<>();
        //Read in level set up and brick location
        readBricks();
        //System.out.println(myBricks.size());
        bricksLeft = myBricks.size();

        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBall.getBall());
        root.getChildren().add(myPaddle.getPaddle());

        for(Brick b : myBricks){
            root.getChildren().add(b.getBrick());
            if(b.getPowerUp() != null){
                root.getChildren().add(b.getPowerUp());
            }
        }

        // respond to input
        scene.setOnKeyPressed(e -> myPaddle.handleKeyInput(e.getCode()));
        //scene.setOnKeyPressed(e -> handleCheatKeys(e.getCode()));
        return scene;
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        myBall.move(elapsedTime);

        if(getBottom(myBall.getBall()) >= myScene.getHeight()){
            myPaddle.updateLives(-1, animation);
            myBall.resetBall();
        }

        // check for collisions
        if(detCollision(myBall.getBall(), myPaddle.getPaddle())){
            double diff = getCenter(myBall.getBall()) - getCenter(myPaddle.getPaddle());
            double xChange = diff * 0.05;
            myBall.updateVeloPaddle(xChange, -1);
        }
        for(Brick b: myBricks){
            if(detCollision(myBall.getBall(), b.getBrick())){
                myBall.updateVeloBrick(-1, -1);
                bricksLeft -= b.updateBrick(1);
                if(bricksLeft == 0){
                    winLevel(animation);
                }
                if(b.getLives() == 0 && b.getPowerUp() != null){
                    System.out.println("HERE");
                    myPowers.add(b.showPowerUp());
                }
            }
        }

        for(ImageView p: myPowers){
            dropPowerUp(p, elapsedTime);
        }

        //change direction in x-axis when hits a wall
        myBall.wallBounce(myScene);
    }

    public Scene getScene(){
        return myScene;
    }

    //Need is side collision and is top/bottom collision
    public boolean detCollision(ImageView arg1, ImageView arg2){
        if(!arg1.visibleProperty().getValue() || !arg2.visibleProperty().getValue()){
            return false;
        }
        double left1 = arg1.getX();
        double right1 = getRight(arg1);
        double top1 = arg1.getY();
        double bottom1 = getBottom(arg1);
        double left2 = arg2.getX();
        double right2 = getRight(arg2);
        double top2 = arg2.getY();
        double bottom2 = getBottom(arg2);
        if((left1 <= right2 && left1 >= left2) || (right1 >= left2 && right1 <= right2)){
            return verticalOverlap(top1, bottom1, top2, bottom2);
        }
        return false;
    }

    public boolean verticalOverlap(double top1, double bottom1, double top2, double bottom2){
        return((top1 <= bottom2 && top1>=top2) || (bottom1 >= top2 && bottom1<=bottom2));
    }
    public double getRight(ImageView arg){
        return arg.getX() + arg.getBoundsInLocal().getWidth();
    }
    public double getBottom(ImageView arg){
        return arg.getY() + arg.getBoundsInLocal().getHeight();
    }
    public double getCenter(ImageView arg){ return arg.getX() + getRight(arg) / 2;}

    public void readBricks(){
        myBricks = new ArrayList<>();
        int line = 0;
        String level = levelFiles[myLevel-1];
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(level));
        int[] firstLine = toIntArray(scan.nextLine().strip().split(" "));

        //int brickSize = firstLine[0];
        int rows = firstLine[1];
        int columns = firstLine[2];
        int[][] brickLocationArray = new int[rows][columns];

        while(scan.hasNext()){
            int[] intData = toIntArray(scan.nextLine().strip().split(" "));
            brickLocationArray[line] = intData;
            line++;
        }
        parse2D(brickLocationArray, rows, columns);
    }

    public int[] toIntArray(String[] args){
        int length = args.length;
        int[] intArray = new int[length];
        for(int i=0; i<length; i++){
            intArray[i] = Integer.parseInt(args[i]);
        }
        return intArray;
    }

    public void parse2D(int[][] argArray, int rows, int columns){
        double colWidth = myScene.getWidth()/columns;
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                int lives = argArray[i][j];
                if(lives != 0){
                    double xLoc = colWidth * (j);
                    double yLoc = colWidth * (i) + colWidth;
                    Brick nBrick = new Brick(lives, xLoc, yLoc, colWidth);
                    myBricks.add(nBrick);
                }
            }
        }
    }

    public void dropPowerUp(ImageView power, double time){
        power.setY(power.getY() + 100 * time);
        if(detCollision(power, myPaddle.getPaddle())){
            power.setVisible(false);
            myPaddle.updateLives(1, animation);
            System.out.println(myPaddle.getLives());
        }
        if(power.getY() >= myScene.getHeight()){
            power.setVisible(false);
        }
    }

    public void winLevel(Timeline anim){
        anim.stop();
        //https://stackoverflow.com/questions/28937392/javafx-alerts-and-their-size
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("YOU WIN");
        a.setHeaderText("WINNER");
        a.setResizable(true);
        String version = System.getProperty("java.version");
        String content = String.format("You broke all the bricks! You beat the level!", version);
        a.setContentText(content);
        a.show();
    }

    /*
    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
//        if (myGrower.contains(x, y)) {
//            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
//            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
//        }
    }
    */
    /*
    public void handleCheatKeys(KeyCode code){
        if(code == KeyCode.R){
            myBall.resetBall();
        }
    }
    */

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
