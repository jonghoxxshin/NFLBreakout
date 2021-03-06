package app.sprite;

import app.Breakout;
import app.sprite.Ball;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Paddle {

    public static final String PADDLE_IMAGE = "paddle.png";
    public static final long PADDLE_HEIGHT = 10;


    private ImageView myPaddle;
    private int myLives;
    private double paddleSpeed = 10;
    private double paddleWidth = 80;
    private double paddleVelocity = 0;
    private Set<KeyCode> currentlyPressed = new HashSet<>();

    double screenWidth, screenHeight;

    /**
     * Initializes paddle (size changes with screen size)
     * @param screenWidth
     * @param screenHeight
     */
    public Paddle(double screenWidth, double screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Breakout b = new Breakout();
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myLives = 4;
        myPaddle.setX(screenWidth/2);
        myPaddle.setY(screenHeight-10);
        myPaddle.setFitWidth(paddleWidth);
        myPaddle.setFitHeight(PADDLE_HEIGHT);
    }

    /**
     * Called when ball hits bottom of screen (lose life) or when paddle catches extra life powerUp
     * Returns 0 when lose game (lives = 0)
     * @param i
     * @return an interger value 0 when the game is lost, returns 1 otherwise
     */
    public int updateLives(int i){
        myLives += i;
        if(myLives < 1){
            return 0;
        }
        //Lose a life, powerUps should be lost too
        if(i<0){
            setPaddle(10, 80);
        }
        return 1;
    }

    /**
     * Getter returns ImageView of paddle
     * @return
     */
    public ImageView getPaddle() { return myPaddle; }

    /**
     * Used to vary the bounce of ball upon paddle collision depending on location that the ball hits the paddle
     * @param idx
     * @return imageView of the paddle object
     */
    public ImageView getPaddlePart(int idx) {
        var dummy = new ImageView();
        dummy.setX(myPaddle.getX()+idx*myPaddle.getFitWidth()/3);
        dummy.setFitWidth(myPaddle.getFitWidth()/3);
        dummy.setY(myPaddle.getY());
        dummy.setFitHeight(myPaddle.getFitHeight());
        return dummy;
    }

    /**
     * Getter returns number of lives user (paddle) has left
     * @return
     */
    public int getLives(){
        return myLives;
    }

    /**
     * Set velocity of paddle
     * @param v
     */
    private void setVX(double v) { paddleVelocity = v; }

    /**
     * A move method for this paddle to be invoked during evey step of the game
     * Checks the boundaries of the screen to make sure that the paddle does not move
     * out of the screen
     * @param screenWidth
     */
    public void move(double screenWidth) {
        myPaddle.setX(Math.max(Math.min(myPaddle.getX() + paddleVelocity, screenWidth-myPaddle.getFitWidth()), 0));
    }

    /**
     * A handler method for when a key is released.
     * If the key going the opposite of current direction is still pressed
     * change the moving direction of paddle to that direction
     * @param code
     */
    public void handleKeyReleased(KeyCode code) {
        currentlyPressed.remove(code);
        if(currentlyPressed.size() == 0) {
            setVX(0);
        } else {
            if(currentlyPressed.iterator().next() == KeyCode.LEFT) setVX(-paddleSpeed);
            else setVX(paddleSpeed);
        }
    }

    /**
     * handles key inputs to move paddle and handle cheatKeys
     * @param code
     * @param ball
     */
    public void handleKeyPressed(KeyCode code, Ball ball){
        if(code == KeyCode.RIGHT){
            setVX(paddleSpeed);
            currentlyPressed.add(code);
        }
        else if(code == KeyCode.LEFT){
            setVX(-paddleSpeed);
            currentlyPressed.add(code);
        }
        else if(code == KeyCode.L){
            myLives++;
        }
        else if(code == KeyCode.R){
            myPaddle.setX(screenWidth/2);
            myPaddle.setY(screenHeight-10);
            ball.resetBall(screenWidth, screenHeight);
        }
        else if(code == KeyCode.S){
            stretch();
        }
        else if(code == KeyCode.F){
            speedUp();
        }
        else if(code == KeyCode.B){
            ball.pumpPower();
        }
    }

    /**
     * Handles "gatorade" powerUp -> increases paddle speed by 1.5 for 5 seconds
     */
    public void speedUp(){
        setPaddle(15, paddleWidth);
        timeOut("speed");
    }

    /**
     * Handles "stretcher" powerUp -> increases paddle size to 150px for 5 seconds
     */
    public void stretch(){
        setPaddle(paddleSpeed, 150);
        timeOut("stretch");
    }

    /**
     * Called by speedUp() and stretch() to reset paddle after 5 second delay
     * @param powType
     */
    //https://stackoverflow.com/questions/35512648/adding-a-timer-to-my-program-javafx
    public void timeOut(String powType){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(powType.equals("stretch")){
                    setPaddle(paddleSpeed, 80);
                }
                else{
                    setPaddle(10, paddleWidth);
                }
            }
        };
        timer.schedule(task, 5000l);
    }

    /**
     * Helper method sets paddle speed and width based on given parameters
     * Called in constructor, speedUp, stretch, timeOut
     * @param speed
     * @param width
     */
    public void setPaddle(double speed, double width){
        myPaddle.setFitWidth(width);
        paddleSpeed = speed;
        paddleWidth = width;
        myPaddle.setFitHeight(PADDLE_HEIGHT);
    }

    /**
     * Getter method to check paddle width for level 3 test 1
     * @return a double value of paddleWidth for this object
     */
    public double getPaddleWidth(){return this.paddleWidth;}

    /**
     * Getter method to get the speed of paddle
     * @return a double value of paddleSpeed for this object
     */
    public double getPaddleSpeed() {return this.paddleSpeed;}
}
