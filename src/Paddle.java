import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class Paddle {
    public static final String PADDLE_IMAGE = "paddle.png";
    public static final long PADDLE_WIDTH = 30;
    public static final long PADDLE_HEIGHT = 10;
    public static final long PADDLE_LONG_WIDTH = 50;

    private ImageView myPaddle;
    private Scene myScene;
    private int myLives;
    private int paddle_speed = 20;
    private boolean speedUp = false;
    private boolean lengthUp = false;



    public Paddle(){
        Breakout b = new Breakout();
        myScene = b.getScene();
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myLives = 3;
        myPaddle.setX(myScene.getWidth()/2);
        myPaddle.setY(myScene.getHeight()-10);
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

    public void upDateLive(int i){
        this.myLives += i;
    }

    public ImageView getPaddle(){
        return myPaddle;
    }


    public int getLives(){
        return myLives;
    }

    public void handleKeyInput(KeyCode code){
        if(code == KeyCode.RIGHT){
            myPaddle.setX(myPaddle.getX() + paddle_speed);
        }
        else if(code == KeyCode.LEFT){
            myPaddle.setX(myPaddle.getX() - paddle_speed);
        }

    }





}
