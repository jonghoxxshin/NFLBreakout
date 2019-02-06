package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

import static app.Breakout.HEIGHT;

public class powerUp {
    //Array of filenames containing powerUp images (to add power - add file name this this array)
    private static final String[] ALL_POWERUPS = {"air_pump_powerup.png", "challenge_powerup.png", "gatorade_powerup.png", "stretcher_powerup.png"};
    private static final int POWER_SPEED = 200;

    //Member variables associated with each powerUp
    private int myType;
    private double myPosX;
    private double myPosY;
    private double mySize;
    private ImageView myPower;
    private boolean Live;
    private CollisionHandler myCollisionHandler;

    /**
     * Constructor creates a powerUp
     * Called in Brick.java when hasPowerUp = true
     * @param type
     * @param x
     * @param y
     * @param size
     */
    public powerUp(double x, double y, double size){
        Random rand = new Random();
        int dex = rand.nextInt(4);
        myType = dex;
        myPosX = x;
        myPosY = y;
        mySize = size;
        myPower = setMyPower();
        myCollisionHandler = new CollisionHandler();
        Live = true;
    }

    /**
     * Returns ImageView of powerUp based on randomly generated type (from dex above)
     * Sets location and size --> stored in myPower member variable
     * @return
     */
    public ImageView setMyPower(){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(ALL_POWERUPS[myType]));
        var temp = new ImageView(image);
        temp.setX(myPosX + 10);
        temp.setY(myPosY);
        temp.setFitWidth(mySize);
        temp.setFitHeight(mySize);
        temp.setVisible(false);
        return temp;
    }

    /**
     * getter returns ImageView of this powerUp
     * @return
     */
    public ImageView getPowerImg(){ return this.myPower; }

    /**
     * Called by Game.java when a brick is broken and contains a powerUp
     * @param time
     */
    public void dropPower(double time){
        if(myPower.visibleProperty().getValue() == false && Live == true){
            this.myPower.setVisible(true);
        }
        this.myPower.setY(myPower.getY() + POWER_SPEED*time);
    }

    /**
     * If powerUp is caught or reaches bottom of the screen -> no longer catchable therefore - kill
     */
    public void killPower(){ Live = false; }

    /**
     * Called in Game.java when powerUp is dropped - detect if paddle catches power
     * If caught -> return 1 to increment myScore in Game.java if powerUp is caught
     * kill and call handlePower if caught to implement power
     * Check if powerUp has reached bottom of screen and therefore wasn't caught
     * @param pad
     * @param ball
     * @return
     */
    //public int getMyType(){ return myType; }
    public int catchPower(Paddle pad, Ball ball){
        if (myCollisionHandler.detectCollision(getPowerImg(), pad.getPaddle())) {
            getPowerImg().setVisible(false);
            killPower();
            handlePower(pad, ball, myType);
            return 1;
        }
        if (getPowerImg().getY() >= HEIGHT) {
            getPowerImg().setVisible(false);
            killPower();
        }
        return 0;
    }

    /**
     * Called by catchPower to implement respective powerUps
     * Call helper methods in Ball.java and Paddle.java based on type of power
     * SHOULD BE ABSTRACT CLASS BECAUSE OF IF/ELSE IF STATEMENTS
     * @param paddle
     * @param ball
     * @param type
     */
    public void handlePower(Paddle paddle, Ball ball, int type){
        if(type == 0){ //air pump - big ball
            ball.pumpPower();
        }
        else if(type == 1){ //challenge flag - extra life
            paddle.updateLives(1);
        }
        else if(type == 2){ //getorade jug - fast paddle
            paddle.speedUp();
        }
        else if(type == 3){ //stretcher - long paddle
            paddle.stretch();
        }
    }
}