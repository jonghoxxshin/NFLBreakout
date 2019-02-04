package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class Paddle {
    public static final String PADDLE_IMAGE = "paddle.png";

    public static final long PADDLE_WIDTH = 80;
    public static final long PADDLE_HEIGHT = 10;
    public static final long PADDLE_LONG_WIDTH = 150;


    private ImageView myPaddle;
    private int myLives;
    private int paddle_speed = 10;
    private boolean speedUp = false;
    private boolean lengthUp = false;

    private double paddle_velocity = 0;
    private Set<KeyCode> currentlyPressed = new HashSet<>();

    private CheatKeys ch = new CheatKeys();

    double screenWidth, screenHeight;

    public Paddle(double screenWidth, double screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Breakout b = new Breakout();
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myLives = 4;
        myPaddle.setX(screenWidth/2);
        myPaddle.setY(screenHeight-10);
        myPaddle.setFitHeight(PADDLE_HEIGHT);
        myPaddle.setFitWidth(PADDLE_WIDTH);
    }

    public void speedPowerUp(){
        if(this.speedUp){
            this.paddle_speed = 20;
        }
        else{
            this.paddle_speed = 10;
        }
    }
    public void lengthPowerUp(){
        if(this.lengthUp){
            this.myPaddle.setFitWidth(PADDLE_LONG_WIDTH);
        }
        else{
            this.myPaddle.setFitWidth(PADDLE_WIDTH);
        }
    }

    public int updateLives(int i){
        myLives += i;
        if(myLives < 1){
            return 0;
        }
        return 1;
    }

    public ImageView getPaddle() { return myPaddle; }

    public ImageView getPaddlePart(int idx) {
        var dummy = new ImageView();
        dummy.setX(myPaddle.getX()+idx*myPaddle.getFitWidth()/3);
        dummy.setFitWidth(myPaddle.getFitWidth()/3);
        dummy.setY(myPaddle.getY());
        dummy.setFitHeight(myPaddle.getFitHeight());
        return dummy;
    }


    public int getLives(){
        return myLives;
    }

    private void setVX(double v) { paddle_velocity = v; }

    public void move(double screenWidth) {
        myPaddle.setX(Math.max(Math.min(myPaddle.getX() + paddle_velocity, screenWidth-myPaddle.getFitWidth()), 0));
    }

    public void handleKeyReleased(KeyCode code) {
        currentlyPressed.remove(code);
        if(currentlyPressed.size() == 0) {
            setVX(0);
        } else {
            if(currentlyPressed.iterator().next() == KeyCode.LEFT) setVX(-paddle_speed);
            else setVX(paddle_speed);
        }
    }

    public void handleKeyPressed(KeyCode code, Ball ball){
        if(code == KeyCode.RIGHT){
            setVX(paddle_speed);
            currentlyPressed.add(code);
        }
        else if(code == KeyCode.LEFT){
            setVX(-paddle_speed);
            currentlyPressed.add(code);
        }
        else if(code == KeyCode.L){
            myLives++;
            //System.out.println(myLives);
        }
        else if(code == KeyCode.R){
            myPaddle.setX(screenWidth/2);
            myPaddle.setY(screenHeight-10);
            ball.resetBall(screenWidth, screenHeight);
        }
        else if(code == KeyCode.COMMA){

        }
        else if(code == KeyCode.PERIOD){

        }
        else if(code == KeyCode.SLASH){

        }
    }
}
