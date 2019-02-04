package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    public static final int BALL_SPEED = 100;
    public static final String BALL_IMAGE = "cowboys_logo.png";

    public boolean firstBounce;
    private ImageView myBall;
    private int myVeloX = 0;
    private int myVeloY = -2;
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
        firstBounce = false;
    }

    public void move(double elapsedTime) {
        myBall.setX(myBall.getX() + BALL_SPEED * myVeloX * elapsedTime);
        myBall.setY(myBall.getY() + BALL_SPEED * myVeloY * elapsedTime);
    }

    public void wallBounce(double width, double height){
        //if(myBall.getX() <= 0 || myBall.getX() + mySize >= stage.getWidth()){
        if(myBall.getX() <= 0 || myBall.getX() + mySize >= width){

                myVeloX *= -1;
        }
        if(myBall.getY() <= 0){
            myVeloY *= -1;
        }
        //if(myBall.getY() + myBall.getBoundsInLocal().getHeight() > stage.getHeight()){
        if(myBall.getY() + myBall.getFitHeight() > height){
            myVeloY = 0;
        }
    }

    public void updateVeloBrick(double x, double y){
        myVeloX *= x;
        myVeloY *= y;
    }

    public void updateVeloPaddle(int pos){ // pos -1 if left 0 if center 1 if right
        myVeloX += pos;
        myVeloY = -Math.abs(myVeloY);
    }

    public void resetBall(double screenWidth, double screenHeight){
        myBall.setX(screenWidth/2);
        myBall.setY(screenHeight-300);
        myVeloX = 0;
        myVeloY = Math.abs(myVeloY);
        firstBounce = false;
    }

    public int getXVelo(){ return myVeloX;}

    public int getYVelo(){return myVeloY;}

    public ImageView getBall(){
        return myBall;
    }

    public void setVeloX(int i){this.myVeloX = i;}

    public void setVeloY(int i){this.myVeloY = i;}
}
