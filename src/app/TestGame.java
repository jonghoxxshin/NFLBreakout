package app;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class TestGame extends Game {
    private int testNum;
    private Scene scene;
    private DataReader datRead;
    private ArrayList<String> testInfo;

    private int x;
    private int y;
    private int xVel;
    private int yVel;
    private String testMsg;

    public TestGame(int testNum) {
        super();
        this.scene = super.createGame();
        this.testNum = testNum;
        this.datRead = new DataReader(0, 0);
        this.testInfo = datRead.readTestFiles(testNum);
        parseTestInfo(testInfo);
        //System.out.println(testInfo);
    }

    @Override
    public Scene createGame() {
        //Scene scene = super.createGame();
        if(testNum == 3) {
            myBricks.clear();
            myBall.setVeloY(yVel);
            myBall.setVeloX(xVel);
            myBall.getBall().setX(x);
            myBall.getBall().setY(y);
        }
        else if(testNum == 4){
            myBall.setVeloY(yVel);
        }
        else if(testNum == 5){
            myPaddle.getPaddle().setVisible(false);
            myPaddle.getPaddle().setX(x);
            myPaddle.getPaddle().setY(y);
        }
        return scene;
    }

    @Override
    public int step(double elapsedTime) {
        if(testNum==3 && myBall.getBall().getY() > 400 && myBall.getBall().getX()> 400){
            return 2;
        }
        if(testNum==4 && bricksLeft < numBricks){
            return 2;
        }
        if(testNum==5 && myPaddle.getLives()<4){
            return 2;
        }

        return super.step(elapsedTime);
    }

    public void parseTestInfo(ArrayList<String> input){
        String[] pos = input.get(0).split(" ");
        String[] vels = input.get(1).split(" ");
        this.testMsg = input.get(2);
        this.x = Integer.parseInt(pos[0]);
        this.y = Integer.parseInt(pos[1]);
        this.xVel = Integer.parseInt(vels[0]);
        this.yVel = Integer.parseInt(vels[1]);
    }

    public String getMsg(){
        return this.testMsg;
    }
}
