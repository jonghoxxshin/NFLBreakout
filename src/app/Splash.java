package app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Splash {
    private Text title;
    private Text tips;
    private Text instruction;
    private Text gameStart;
    private Scene myView;
    private Paint background = Color.WHITE;
    private Button startButton;
    private Text testText;

    public Scene Splash(){



        myView = new Scene(Breakout.root, Breakout.SIZE_WIDTH, Breakout.BLOCK_HEIGHT, background);
        title = new Text(Breakout.SIZE_WIDTH/2, 100, "Break-Out\n");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        instruction = new Text(Breakout.SIZE_WIDTH/2, 200, "Move the paddle side-ways to get\n most point with out dropping the ball\n");
        instruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        gameStart = new Text(Breakout.SIZE_WIDTH/2,300, "Press <Space> or Click Start button to start the game!\n");
        gameStart.setFont(Font.font("Times New Roman", FontWeight.BOLD,30));

        tips = new Text(Breakout.SIZE_WIDTH/2, 400,"(Breaking non-target blocks will deduct your points)");
        tips.setFont(Font.font("Times New Roman", FontWeight.BOLD,15));

        Breakout.root.getChildren().addAll(title,tips,instruction,gameStart);
        myView.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myView;
    }


    protected void handleKeyInput(KeyCode code){
        if (code == KeyCode.SPACE){

            myView = gamePage.myView;



        }
    }






}
