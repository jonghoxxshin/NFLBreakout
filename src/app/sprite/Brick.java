package app.sprite;

import app.powerup.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Brick {
    //All possible helmet/brick images
    private final static String[] ALL_HELMETS = {"redskins_rect_crop.JPG", "giants_rect_crop.JPG", "eagles_rect_crop.JPG"};

    //Member variables associated with Brick objects
    private int myLives;
    private double myPosX;
    private double myPosY;
    private boolean hasPowerUp;
    private double mySize;
    private powerUp myPower;
    private List<ImageView> myHelmets;

    /**
     * Constructor - creates new brick object
     * myHelmets array contains all helmet logos for brick (3 if lives = 3, 2 if lives = 2, 1 if lives = 1)
     * @param lives
     * @param posX
     * @param posY
     * @param size
     */
    public Brick(int lives, double posX, double posY, double size){
        myHelmets = new ArrayList<>();
        myLives = lives;
        myPosX = posX;
        myPosY = posY;
        mySize = size;

        //setHelmets- helper method that fills myHelmets array
        setHelmet(lives);
        for(ImageView h:myHelmets){
            setPosition(h, myPosX, myPosY, mySize);
        }

        //Randomly decide whether brick is to have a powerUp
        hasPowerUp = powerBool();
        if(hasPowerUp){
            Random rand = new Random();
            int type = rand.nextInt(4);
            myPower = setMyPower(type);
        } else{ myPower = null; }
    }

    /**
     * Called in Game.java whenever a ball-brick collision is detected
     * Updates myLives (by -1 if ball is normal, by -3 if ball is big)
     * returns 1 if brick is destroyed --> this 1 is used to track how many bricks are left to be broken before level is won
     * returns 0 if brick is not destroyed because bricksLeft should not be decremented
     * @param damage
     * @param lives
     * @return
     */
    public int updateBrick(int damage, int lives){
        this.myLives = lives - damage;
        if(lives - damage <= 0){
            //If brick is destroyed --> all imageViews should be invisible
            for(ImageView h:myHelmets){
                h.setVisible(false);
            }
            return 1;
        }
        else{
            //If brick is not destroyed (loses 1 life) --> current ImageView should be invisible
            //ImageView of lower life brick should be set to visible
            myHelmets.get(lives-1).setVisible(false);
            myHelmets.get(lives-2).setVisible(true);
            return 0;
        }
    }

    /**
     * Helper method fills myHelmets member variable with correct ImageViews for brick of lives myLives
     * @param lives
     */
    public void setHelmet(int lives){
        for(int i=0; i<lives; i++){
            myHelmets.add(loadHelmets(i));
        }
    }

    /**
     * called in Constructor --> randomly decides if this brick is to have a powerUp (true) or not (false)
     * Currently a 3/10 change that a brick has a powerUp
     * @return
     */
    public boolean powerBool(){
        Random rand = new Random();
        int powerInt = rand.nextInt(11);
        return (powerInt <= 2);
    }

    /**
     * selects which concrete class of super class powerUp to initialize based on random input
     * @param type
     * @return
     */
    public powerUp setMyPower(int type){
        if(type == 0){return new BigBall(myPosX, myPosY, mySize/2);}
        else if(type == 1){return new ExtraLife(myPosX, myPosY, mySize/2);}
        else if(type == 2){ return new SpeedUp(myPosX, myPosY, mySize/2);}
        else { return new Stretch(myPosX, myPosY, mySize/2);}
    }

    /**
     * Sets size and location of ImageView pics
     * Called for every ImageView in myHelmets array
     * @param pic
     * @param xPos
     * @param yPos
     * @param size
     */
    public void setPosition(ImageView pic, double xPos, double yPos, double size){
        pic.setX(xPos);
        pic.setY(yPos);
        pic.setFitHeight(size);
        pic.setFitWidth(size);
    }

    /**
     * Returns x and y positions of brick respectively
     * @return
     */
    public double getX(){ return myPosX; }
    public double getY(){ return myPosY; }

    /**
     * Used in Game.java to retrieve current ImageView being displayed for brick in question
     * Used for collision detections
     * @return
     */
    public ImageView getBrick(){
        if(myLives>0){
            return myHelmets.get(myLives -1);
        }
        return myHelmets.get(0);
    }

    /**
     * called in createGame method of Game.java to add all helmet ImageView to scene
     * @return
     */
    public List<ImageView> getMyHelmets(){ return myHelmets; }

    /**
     * Returns lives of current brick
     * @return
     */
    public int getLives(){ return myLives; }

    /**
     * returns true if brick has powerUp, false otherwise
     * @return
     */
    public boolean getHasPower(){ return hasPowerUp; }

    /**
     * returns actual powerUp of brick (null if no power)
     * @return
     */
    public powerUp getPower(){ return this.myPower; }

    /**
     * Helper method called by setHelmet --> loads all ImageViews and sets visibility to highest level ImageView
     * @param i
     * @return
     */
    public ImageView loadHelmets(int i){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(ALL_HELMETS[i]));
        ImageView temp = new ImageView(image);
        if(i+1 != myLives){
            temp.setVisible(false);
        }
        return temp;
    }

    /**
     * Implementing equals and hashCode for collections
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brick brick = (Brick) o;
        return myLives == brick.myLives &&
                Double.compare(brick.myPosX, myPosX) == 0 &&
                Double.compare(brick.myPosY, myPosY) == 0 &&
                hasPowerUp == brick.hasPowerUp &&
                Double.compare(brick.mySize, mySize) == 0 &&
                Objects.equals(myPower, brick.myPower) &&
                Objects.equals(myHelmets, brick.myHelmets);
    }
    @Override
    public int hashCode() {
        return Objects.hash(myLives, myPosX, myPosY, hasPowerUp, myPower);
    }
}
