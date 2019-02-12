package app.tests;

import app.powerup.*;
import javafx.scene.Scene;

public class TestsLev3 extends TestGame {
    /**
     * Constructor creates mock game when a test key is pressed to initialize tests
     *
     * @param testNum
     */
    public TestsLev3(int testNum) {
        super(testNum, 3);
    }


    @Override
    public Scene createGame() {
        if(getTestNum() == 3){//"," stretcher power
            var temp = new Stretch(getX(), getY(), 40);
            myGroup.getChildren().add(temp.getPowerImg());
            myPowersNew.add(temp);
        }
        else if(getTestNum() == 4){//"." speedUp power
            var temp = new SpeedUp(getX(), getY(), 40);
            myGroup.getChildren().add(temp.getPowerImg());
            myPowersNew.add(temp);
        }
        else if(getTestNum() == 5){//"/" paddle hit test
            setBallHelper();
        }
        return scene;
    }

    @Override
    public int step(double time) {
        if(getTestNum() == 3 && myPaddle.getPaddleWidth() > 80){
            return 2;
        }
        else if(getTestNum() == 4 && myPaddle.getPaddleSpeed() > 10){
            return 2;
        }
        if(getTestNum() == 5 && (myBall.getBall().getX() > 450 || myBall.getBall().getX() < 350)){
            return 2;
        }
        return superStep(time);
    }
}
