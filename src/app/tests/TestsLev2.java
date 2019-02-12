package app.tests;

import app.powerup.*;
import javafx.scene.Scene;

public class TestsLev2 extends TestGame {
    /**
     * Constructor creates mock game when a test key is pressed to initialize tests
     *
     * @param testNum
     */
    public TestsLev2(int testNum) {
        super(testNum, 2);
    }

    @Override
    public Scene createGame() {
        if(getTestNum() == 3){//"," extra life
            var temp = new ExtraLife(getX(), getY(), 40);
            myGroup.getChildren().add(temp.getPowerImg());
            myPowersNew.add(temp);
        }
        else if(getTestNum() == 4){//"." double brick break
            setBallHelper();
        }
        else if(getTestNum() == 5){//"/" big ball power up
            var temp = new BigBall(getX(), getY(), 40);
            myGroup.getChildren().add(temp.getPowerImg());
            myPowersNew.add(temp);
        }
        return scene;
    }

    @Override
    public int step(double elapsedTime) {
        if(getTestNum() == 3 && myPaddle.getLives() > 4){
            return 2;
        }
        if(getTestNum() == 4 && bricksLeft < numBricks){
            return 2;
        }
        if(getTestNum() == 5 && myBall.getMyStatus() == 1 && bricksLeft < numBricks){
            return 2;
        }
        return superStep(elapsedTime);
    }
}
