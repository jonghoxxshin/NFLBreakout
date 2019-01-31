package app;

import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle {
    public static final String PADDLE_IMAGE = "paddle.png";

    public static final long PADDLE_WIDTH = 80;
    public static final long PADDLE_HEIGHT = 10;
    public static final long PADDLE_LONG_WIDTH = 150;


    private ImageView myPaddle;
    private int myLives;
    private int paddle_speed = 20;
    private boolean speedUp = false;
    private boolean lengthUp = false;

    private cheatKeys ch = new cheatKeys();

    double screenWidth, screenHeight;

    public Paddle(double screenWidth, double screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        Breakout b = new Breakout();
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myLives = 0;
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

    public void updateLives(int i, Timeline anim){
        myLives += i;
        if(myLives < 0){
            loseAlert(anim);
        }
    }

    public ImageView getPaddle(){
        return myPaddle;
    }


    public int getLives(){
        return myLives;
    }

    public void handleKeyInput(KeyCode code, Ball ball){
        if(code == KeyCode.RIGHT && !(myPaddle.getX() + PADDLE_WIDTH >= screenWidth)){
            myPaddle.setX(myPaddle.getX() + paddle_speed);
        }
        else if(code == KeyCode.LEFT && !(myPaddle.getX() <= 0)){
            myPaddle.setX(myPaddle.getX() - paddle_speed);
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
    }

    public void loseAlert(Timeline anim){
        anim.stop();
        //https://stackoverflow.com/questions/28937392/javafx-alerts-and-their-size
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("YOU LOSE");
        a.setHeaderText("LOSER");
        a.setResizable(true);
        String version = System.getProperty("java.version");
        String content = String.format("You ran out of lives! You lost!", version);
        a.setContentText(content);
        a.show();
        //anim.playFromStart();
    }





}
