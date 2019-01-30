package app;

import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;

public class cheatKeys {

    public void addLives(Paddle p, Timeline t){
        p.updateLives(1, t);
        System.out.println(p.getLives());
    }
}
