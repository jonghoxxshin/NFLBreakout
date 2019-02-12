package app.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class Ball {
    //Speed and image doesn't change
    public static final int BALL_SPEED = 100;
    public static final String BALL_IMAGE = "cowboys_logo.png";

    //Local variables used
    public boolean firstBounce;
    private ImageView myBall;
    private int myVeloX = 0;
    private int myVeloY = -2;
    //Status 0 = normal ball; 1 = powerUp big ball
    private int myStatus = 0;
    private double mySize;

    /**
     * Constructore - sets member variables for Ball object
     * Uses setBall helper to set the location and size of the ImageView to be displayed
     * @param xPos
     * @param yPos
     */
    public Ball(double xPos, double yPos){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new ImageView(image);
        myBall.setX(xPos);
        myBall.setY(yPos);
        setBall(0, 20);
        firstBounce = false;
    }

    /**
     * Called in Game.java step method to move the ball
     * @param elapsedTime
     */
    public void move(double elapsedTime) {
        myBall.setX(myBall.getX() + BALL_SPEED * myVeloX * elapsedTime);
        myBall.setY(myBall.getY() + BALL_SPEED * myVeloY * elapsedTime);
    }

    /**
     * Called in Game.java step method ensures ball remains in the scene/stage
     * @param width
     * @param height
     */
    public void wallBounce(double width, double height){
        if(myBall.getX() <= 0 || myBall.getX() + mySize >= width){
                myVeloX *= -1;
        }
        if(myBall.getY() <= 0){
            myVeloY *= -1;
        }
        //If hits bottom of the screen, no bounce back because lose life
        if(myBall.getY() + myBall.getFitHeight() > height){
            myVeloY = 0;
        }
    }

    /**
     * Ball bouncing off a brick moves differently than bouncing off a paddle
     * Direct bounces off bricks
     * @param x
     * @param y
     */
    public void updateVeloBrick(double x, double y){
        myVeloX *= x;
        myVeloY *= y;
    }

    /**
     * X velocity changes based on where the ball hits the paddle (left, middle, or right section)
     * Y velocity simply flips
     * @param pos
     */
    public void updateVeloPaddle(int pos){ // pos -1 if left 0 if center 1 if right
        myVeloX += pos;
        myVeloY *= -1;
    }

    /**
     * Occurs when user clicks 'R' key or when ball hits the bottom of the screen (player loses life/misses ball)
     * resets member variables as required
     * @param screenWidth
     * @param screenHeight
     */
    public void resetBall(double screenWidth, double screenHeight){
        myBall.setX(screenWidth/2);
        myBall.setY(screenHeight-200);
        myVeloX = 0;
        myVeloY = Math.abs(myVeloY);
        setBall(0, 20);
        firstBounce = false;
    }

    /**
     * Returns the ImageView of the ball to be added to the scene
     * Used to check for collisions since need ImageView object
     * @return
     */
    public ImageView getBall(){ return myBall; }

    /**
     * called in TestGame.java to set the required x and y velocities for various test cases
     * @param i
     */
    public void setVeloX(int i){this.myVeloX = i;}
    public int getMyVeloX(){return this.myVeloX;}
    public void setVeloY(int i){this.myVeloY = i;}

    /**
     * returns ball status to determine if ball currently has a power up
     * Used in Game.java when detecting ball-brick collisions
     * @return
     */
    public int getMyStatus(){ return myStatus; }

    /**
     * called by powerUp.java to handle the powerup that inflates the ball
     */
    public void pumpPower() {
        if (myStatus != 1) {
            setBall(1, 30);
        }
        timeOut();
    }

    /**
     * Helper method used in Ball.java to set and reset ball's state upon powerUp catches
     * @param status
     * @param size
     */
    public void setBall(int status, double size){
        myStatus = status;
        myBall.setFitWidth(size);
        myBall.setFitHeight(size);
        mySize = size;
    }

    /**
     * Called after delay time (5 seconds) when airPump powerUp is caught
     * Allows ball to be "big" for 5 seconds before resetting to normal params
     */
    //https://stackoverflow.com/questions/35512648/adding-a-timer-to-my-program-javafx
    public void timeOut(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setBall(0, 20);
            }
        };
        timer.schedule(task, 5010l);
    }
}
