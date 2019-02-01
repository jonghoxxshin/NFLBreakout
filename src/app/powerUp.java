package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class powerUp {
    private static final String[] ALL_POWERUPS = {"air_pump_powerup.png", "challenge_powerup.png", "gatorade_powerup.png", "stretcher_powerup.png"};
    private static final int POWER_SPEED = 100;

    private int myType;
    private int myPosX;
    private int myPosY;
    private int mySize;
    private ImageView myPower;

    public powerUp(int type, int x, int y, int size){
        myType = type;
        myPosX = x;
        myPosY = y;
        mySize = size;
        myPower = setMyPower();
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

    //Bricks have powerUp variable, if break breaks --> if brick.hasPowerUp --> brick.powerUp.dropPower()
    public void dropPower(double time){
        if(myPower.isVisible() == false){
            myPower.setVisible(true);
        }
        myPower.setY(myPower.getY() + 100*time);
    }



}
