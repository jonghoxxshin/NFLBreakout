package app;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DisplayView {
    private Pane root;
    private int myScore;
    private int myLives;
    private int myLevel;


    /**
     * Constructor for DisplayView object
     * @param score
     * @param lives
     * @param level
     */
    public DisplayView(int score, int lives, int level){
        myScore = score;
        myLives = lives;
        myLevel = level;
    }

    /**
     * Initiates the pane value of the display View object
     */
    public void setView(){
        root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        TextArea displayArea = new TextArea();
        displayArea.setFont(new Font(16));
        displayArea.setText("Lives remaining : " + myLives + "\n Level: " + myLevel + "\n Score: " + myScore);
        root.getChildren().add(displayArea);
    }


    public Pane view(){return root;}
    public int getMyScore() {return myScore;}
    public void setMyScore(int myScore) {this.myScore = myScore;}
    public int getMyLives() {return myLives;}
    public void setMyLives(int myLives) {this.myLives = myLives;}
    public int getMyLevel() {return myLevel;}
    public void setMyLevel(int myLevel) {this.myLevel = myLevel;}
    public void updateStatus(int score, int lives, int level){
        myLives = lives;
        myScore = score;
        myLevel = level;
    }


}
