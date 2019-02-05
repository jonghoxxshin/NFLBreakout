package app;

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
import java.util.*;

public class Brick {

    private int myLives;
    private ImageView myHelmet;
    private double myPosX;
    private double myPosY;
    private boolean hasPowerUp;
    private double mySize;
    private powerUp myPower;


    public Brick(int lives, double posX, double posY, double size){
        myLives = lives;
        myPosX = posX;
        myPosY = posY;
        mySize = size;
        myHelmet = new ImageView(setHelmet(lives));
        setPosition(myHelmet, myPosX, myPosY, size);
        //https://stackoverflow.com/questions/2444019/how-do-i-generate-a-random-integer-between-min-and-max-in-java
        //Randomly decide whether brick is to have a powerUp and if it does, randomly assign said powerUp
        hasPowerUp = powerBool();
        if(hasPowerUp){
            myPower = new powerUp(1, myPosX, myPosY, mySize/2);
        } else{ myPower = null; }
    }

    public int updateBrick(int damage){
        this.myLives -= damage;
        if(myLives <= 0){
            this.myHelmet.setVisible(false);
            return 1;
        }
        else{
            //System.out.println("HERE, SHOULD CHANGE PIC");
            this.myHelmet = new ImageView(setHelmet(myLives));
            return 0;
        }
    }

    public Image setHelmet(int lives){
        String res;
        if(lives == 3){
            res = "eagles_helmet.png";
        }
        else if(lives == 2){
            res = "giants_helmet.png";
        }
        else if(lives == 1){
            res = "redskins_helmet.png";
        }
        else{
            return null;
        }
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(res));
        return image;
    }

    public boolean powerBool(){
        Random rand = new Random();
        int powerInt = rand.nextInt(11);
        return (powerInt <= 10);
    }

    public void setPosition(ImageView pic, double xPos, double yPos, double size){
        pic.setX(xPos);
        pic.setY(yPos);
        pic.setFitHeight(size);
        pic.setFitWidth(size);
    }

    public double getX(){ return myPosX; }
    public double getY(){ return myPosY; }

    public ImageView getBrick(){ return myHelmet; }

    public int getLives(){ return myLives; }

    public boolean getHasPower(){ return hasPowerUp; }

    public powerUp getPower(){ return this.myPower; }
}
