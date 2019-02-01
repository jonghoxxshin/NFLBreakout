package app;

import app.Breakout;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Ball {
    public static final int BALL_SPEED = 200;
    public static final String BALL_IMAGE = "cowboys_logo.png";

    private ImageView myBall;
    private int myVeloX = -1;
    private int myVeloY = -1;
    //0 = normal ball; 1 = powerUp big ball (breaks any brick in one hit)
    private int myBallStatus = 0;
    private double mySize;

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

    public void wallBounce(int sceneSize){
        //if(myBall.getX() <= 0 || myBall.getX() + mySize >= stage.getWidth()){
        if(myBall.getX() <= 0 || myBall.getX() + mySize >= sceneSize){

                myVeloX *= -1;
        }
        if(myBall.getY() <= 0){
            myVeloY *= -1;
        }
        //if(myBall.getY() + myBall.getBoundsInLocal().getHeight() > stage.getHeight()){
        if(myBall.getY() + myBall.getBoundsInLocal().getHeight() > sceneSize){
            myVeloY = 0;
        }
    }

    public void updateVeloBrick(double x, double y){
        myVeloX *= x;
        myVeloY *= y;
    }

    public void updateVeloPaddle(double x, double y){
        myVeloX += x;
        myVeloY *= y;
    }

    public void resetBall(double screenWidth, double screenHeight){
        myBall.setX(screenWidth/2);
        myBall.setY(screenHeight-100);
        myVeloX = 1;
        myVeloY = -1;
    }

    public double getXVelo(){
        return myVeloX;
    }

    public ImageView getBall(){
        return myBall;
    }
}
