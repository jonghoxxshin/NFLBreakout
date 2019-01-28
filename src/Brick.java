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

public class Brick {


    private int myLives;
    private ImageView myHelmet;
    private int myPosX;
    private int myPosY;
    /*
    private boolean hasPowerUp;
    private PowerUp[] allPowerUps;
    private PowerUp myPowerUp;
    */

    public Brick(int lives, int posX, int posY){
        myLives = lives;
        myPosX = posX;
        myPosY = posY;
        myHelmet = new ImageView(setHelmet(lives));
        //https://stackoverflow.com/questions/2444019/how-do-i-generate-a-random-integer-between-min-and-max-in-java
        //Randomly decide whether brick is to have a powerUp and if it does, randomly assign said powerUp
        //setPowerUp();
    }

    public void updateBrick(int damage){
        myLives -= damage;
        myHelmet = new ImageView(setHelmet(myLives));
        /*
        if(this.hasPowerUp){
            this.hasPowerUp = false;
        }
        else{
            setPowerUp();
        }
        */
    }

    public Image setHelmet(int lives){
        String res;
        if(lives == 3){
            res = "eagles_helmet.png";
        }
        if(lives == 2){
            res = "giants_helmet.png";
        }
        if(lives == 1){
            res = "redskins_helmet.png";
        }
        else{
            return null;
        }
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(res));
        return image;
    }

    /*
    public void setPowerUp(){
        this.hasPowerUp = false;
        Random rand = new Random();
        int powerInt = rand.nextInt(11);
        if(powerInt < 4){
            this.hasPowerUp = true;
        }
        if(hasPowerUp){
            int dex = rand.nextInt(4);
            this.myPowerUp = allPowerUps[dex];
        }
        else{
            this.myPowerUp = null;
        }
    }
    */
}
