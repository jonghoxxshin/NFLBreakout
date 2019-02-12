package app.tests;

import javafx.scene.Scene;

public class TestsLev1 extends TestGame{
    /**
     * Constructor creates mock game when a test key is pressed to initialize tests
     *
     * @param testNum
     */
    public TestsLev1(int testNum) {
        super(testNum, 1);
    }

    @Override
    public Scene createGame() {
        if(getTestNum() == 3) { //"," corner bounce for level 1
            myBricks.clear();
            setBallHelper();
        }
        else if(getTestNum() == 4){ //"." brick break for level 1
            myBall.setVeloY(getyVel());
        }
        else if(getTestNum() == 5){ //"/" lose life for level 1
            myPaddle.getPaddle().setVisible(false);
            myPaddle.getPaddle().setX(getX());
            myPaddle.getPaddle().setY(getY());
        }
        return scene;
    }


    @Override
    public int step(double elapsedTime) {
        if(getTestNum()==3 && myBall.getBall().getY() > 400 && myBall.getBall().getX()> 400){
            return 2;
        }
        if(getTestNum()==4 && bricksLeft < numBricks){
            return 2;
        }
        if(getTestNum()==5 && myPaddle.getLives()<4){
            return 2;
        }
        return superStep(elapsedTime);
    }
}
