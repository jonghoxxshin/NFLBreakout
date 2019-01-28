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

public class Ball {
    public static final int BALL_SPEED = 50;
    public static final String BALL_IMAGE = "cowboys_logo.png";

    private ImageView myBall;
    private int myVeloX = 1;
    private int myVeloY = 1;
    //0 = normal ball; 1 = powerUp big ball (breaks any brick in one hit)
    private int myBallStatus = 0;

    //Scene myScene = new Scene();

    public Ball(int xPos, int yPos){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new ImageView(image);
        myBall.setX(xPos);
        myBall.setY(yPos);
    }

    void move(double elapsedTime) {
        myBall.setX(myBall.getX() + BALL_SPEED * myVeloX * elapsedTime);
        myBall.setY(myBall.getY() + BALL_SPEED * myVeloY * elapsedTime);
    }

    void wallBounce(){
        if(myBall.getX() < 0 || myBall.getX() > myScene.getWidth() - myBall.getBoundsInLocal().getWidth()){
            myVeloX *= -1;
        }
        if(myBall.getY() < 0){
            myVeloY *= -1;
        }
        if(myBall.getY() + myBall.getBoundsInLocal().getHeight() > myScene.getHeight()){
            myVeloY = 0;
        }
    }
}
