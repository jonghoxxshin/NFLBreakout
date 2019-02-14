package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class SplashPage{


    public final static String HOW_TO = "Move the paddle by pressing the left and right arrows on your keyboard. \n" +
                                        "Break all the opponent blocks before losing all your lives!";

    private Text title;
    private Text instruction;
    private Text gameStart;
    private Button startButton;
    private int splash = 0; // 0 -> on splash 1 -> to game 2 -> on game       3 -> test1 4 -> test2 5 -> test3
    private Scene splashScene;

    /**
     * Constructor for the splashPage, takes Paint value as
     * an input that colors the background in that paint value
     * and receives a scene value from setupSplash()
     * @param background
     */
    public SplashPage(Paint background){
        splashScene = setupSplash(background);
    }

    private Scene setupSplash(Paint background){
        var root = new Group();
        Image bkg = new Image("Half.png");
        ImageView mv = new ImageView(bkg);
        Image nfl = new Image("nfl.png");
        ImageView mvNfl = new ImageView(nfl);

        var scene = new Scene(root, bkg.getWidth(), bkg.getHeight() - 100, background);

        title = new Text(scene.getWidth()* 0.27, scene.getHeight() * 0.49, "NFL Break-Out!\n");
        title.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 45));
        title.setFill(Color.BLUE);

        mvNfl.setFitWidth(100);
        mvNfl.setFitHeight(100);
        mvNfl.setX(300);
        mvNfl.setY(150);

        instruction = new Text(scene.getWidth() * 0.2, scene.getHeight() *0.57, HOW_TO);
        instruction.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        instruction.setFill(Color.BLUE);

        gameStart = new Text(scene.getWidth()*0.21, scene.getHeight()*0.69, "Press space, enter, or click anywhere to start next level!");
        gameStart.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));
        gameStart.setFill(Color.BLUE);

        startButton = new Button("Start!");
        startButton.setLayoutX(300);
        startButton.setLayoutY(scene.getHeight()-100);
        startButton.setOnAction(e -> splash = 1);

        root.getChildren().add(mv);
        root.getChildren().add(title);
        root.getChildren().add(mvNfl);
        root.getChildren().add(startButton);
        root.getChildren().add(gameStart);
        root.getChildren().add(instruction);

        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(),e.getY()));
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.COMMA) splash = 3;
            if(e.getCode() == KeyCode.PERIOD) splash = 4;
            if(e.getCode() == KeyCode.SLASH) splash = 5;
        });

        return scene;
    }

    /**
     * Getter method for the Scene value of this splashpage
     * @return
     */
    public Scene getSplashScene() { return splashScene; }

    /**
     * Handler method for mouse input, when pressed anywhere on the screen
     * moves to playing game by setting the splash value to 1
     * @param x
     * @param y
     */
    public void handleMouseInput(double x, double y){
        this.setSplash(1);
    }

    /**
     * Getter method for integer splash value which returns
     * instance variable splash
     * @return integer value of splash
     */
    public int getSplash(){
        return splash;
    }

    /**
     * Setter method for boolean splash value
     * that sets the value of instance variable splash
     * @param state
     */
    public void setSplash(int state){
        this.splash = state;
    }
}
