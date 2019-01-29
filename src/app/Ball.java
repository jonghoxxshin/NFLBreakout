package app;

import app.Breakout;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    public static final int BALL_SPEED = 50;
    public static final String BALL_IMAGE = "cowboys_logo.png";

    private ImageView myBall;
    private int myVeloX = 1;
    private int myVeloY = 1;
    //0 = normal ball; 1 = powerUp big ball (breaks any brick in one hit)
    private int myBallStatus = 0;
    private double mySize;

    Breakout b = new Breakout();
    private Scene myScene = b.getScene();

    //Scene myScene = new Scene();

    public Ball(double xPos, double yPos){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new ImageView(image);
        myBall.setX(xPos);
        myBall.setY(yPos);
        mySize = 20;
        myBall.setFitWidth(mySize);
        myBall.setFitHeight(mySize);
    }

    public void move(double elapsedTime) {
        myBall.setX(myBall.getX() + BALL_SPEED * myVeloX * elapsedTime);
        myBall.setY(myBall.getY() + BALL_SPEED * myVeloY * elapsedTime);
    }

    public void wallBounce(Scene scene){
        myScene = scene;
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

    public void updateVelo(double x, double y){
        myVeloX *= x;
        myVeloY *= y;
    }

    public void resetBall(){
        myBall.setX(myScene.getWidth()/2);
        myBall.setY(myScene.getHeight()/2);
    }

    public ImageView getBall(){
        return myBall;
    }
}
