package app;

import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;

public class CheatKeys {

    public void addLives(Paddle p){
        p.updateLives(1);
        System.out.println(p.getLives());
    }

}
