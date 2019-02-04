package app;

import javafx.scene.Scene;

public class TestGame extends Game {
    private int testNum;

    public TestGame(int testNum) {
        super();
        this.testNum = testNum;
    }

    @Override
    public Scene createGame() {
        Scene scene = super.createGame();

        if(testNum == 3) {
            myBricks.clear();
            myBall.setVeloY(-1);
            myBall.setVeloX(-1);
        }
        if(testNum == 4){
            myBall.setVeloY(-1);
        }
        if(testNum == 5){
            myPaddle.getPaddle().setVisible(false);
            myPaddle.getPaddle().setX(0);
            myPaddle.getPaddle().setY(0);
        }

        return scene;
    }

    @Override
    public int step(double elapsedTime) {
        if(testNum==3 && myBall.getYVelo()>0 && myBall.getXVelo()>0){
            return 2;
        }
        if(testNum==4 && myBricks.size()<numBricks){
            //need to be worked on
            return 2;
        }
        if(testNum==5 && myPaddle.getLives()<0){
            return 2;
        }

        return super.step(elapsedTime);
    }
}
