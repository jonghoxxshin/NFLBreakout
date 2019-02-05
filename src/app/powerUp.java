package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

import static app.Breakout.HEIGHT;

public class powerUp {
    private static final String[] ALL_POWERUPS = {"air_pump_powerup.png", "challenge_powerup.png", "gatorade_powerup.png", "stretcher_powerup.png"};
    private static final int POWER_SPEED = 200;

    private int myType;
    private double myPosX;
    private double myPosY;
    private double mySize;
    private ImageView myPower;
    private boolean Live;
    private CollisionHandler myCollisionHandler;

    public powerUp(int type, double x, double y, double size){
        //Random rand = new Random(3);
        //int dex = rand.nextInt(4);
        //myType = dex;
        myType = type;
        myPosX = x;
        myPosY = y;
        mySize = size;
        myPower = setMyPower();
        myCollisionHandler = new CollisionHandler();
        Live = true;
    }

    public ImageView setMyPower(){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(ALL_POWERUPS[myType]));
        var temp = new ImageView(image);
        temp.setX(myPosX);
        temp.setY(myPosY);
        temp.setFitWidth(mySize);
        temp.setFitHeight(mySize);
        temp.setVisible(false);
        return temp;
    }

    public ImageView getPowerImg(){ return this.myPower; }

    //Bricks have powerUp variable, if break breaks --> if brick.hasPowerUp --> brick.powerUp.dropPower()
    public void dropPower(double time){
        if(myPower.visibleProperty().getValue() == false && Live == true){
            this.myPower.setVisible(true);
        }
        this.myPower.setY(myPower.getY() + POWER_SPEED*time);
    }

    public void killPower(){Live = false;}

    public int catchPower(Paddle pad){
        if (myCollisionHandler.detectCollision(getPowerImg(), pad.getPaddle())) {
            getPowerImg().setVisible(false);
            killPower();
            pad.updateLives(1);
            return 1;
        }
        if (getPowerImg().getY() >= HEIGHT) {
            getPowerImg().setVisible(false);
            killPower();
        }
        return 0;
    }
}
