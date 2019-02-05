package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class powerUp {
    private static final String[] ALL_POWERUPS = {"air_pump_powerup.png", "challenge_powerup.png", "gatorade_powerup.png", "stretcher_powerup.png"};
    private static final int POWER_SPEED = 200;

    private int myType;
    private double myPosX;
    private double myPosY;
    private double mySize;
    private ImageView myPower;
    private boolean Live;

    public powerUp(int type, double x, double y, double size){
        //Random rand = new Random(3);
        //int dex = rand.nextInt(4);
        //myType = dex;
        myType = type;
        myPosX = x;
        myPosY = y;
        mySize = size;
        myPower = setMyPower();
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

    public ImageView getPowerImg(){
        return this.myPower;
    }

    //Bricks have powerUp variable, if break breaks --> if brick.hasPowerUp --> brick.powerUp.dropPower()
    public void dropPower(double time){
        if(myPower.visibleProperty().getValue() == false && Live == true){
            this.myPower.setVisible(true);
        }
        this.myPower.setY(myPower.getY() + POWER_SPEED*time);
    }

    public void killPower(){Live = false;}
}
