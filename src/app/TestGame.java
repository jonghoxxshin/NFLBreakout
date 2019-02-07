package app;

import javafx.scene.Scene;
import java.util.List;

public class TestGame extends Game {
    private int testNum;
    private Scene scene;
    private DataReader datRead;
    private List<String> testInfo;

    private int x;
    private int y;
    private int xVel;
    private int yVel;
    private String testMsg;

    /**
     * Constructor creates mock game when a test key is pressed to initialize tests
     * @param testNum
     */
    public TestGame(int testNum) {
        super(1);
        this.scene = super.createGame();
        this.testNum = testNum;
        //Reads test info data from text files
        this.datRead = new DataReader(0, 0);
        this.testInfo = datRead.readTestFiles(testNum);
        parseTestInfo(testInfo);
        //System.out.println(testInfo);
    }

    /**
     * Sets initial position and velocities as required based on test cases
     * @return
     */
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

    /**
     * Runs 60 times per second to animate the game and check if test case was success
     * Returning 2 indicates game should stop and alert displayed
     * @param elapsedTime
     * @return
     */
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

    /**
     * Helper method called in Constructor
     * Takes in arrayList of strings and prases integer values setting initial conditions as required based on test number
     * @param input
     */
    public void parseTestInfo(List<String> input){
        String[] pos = input.get(0).split(" ");
        String[] vels = input.get(1).split(" ");
        this.testMsg = input.get(2);
        this.x = Integer.parseInt(pos[0]);
        this.y = Integer.parseInt(pos[1]);
        this.xVel = Integer.parseInt(vels[0]);
        this.yVel = Integer.parseInt(vels[1]);
    }

    /**
     * Getter gets testMsg to be displayed in alert message after test is complete
     * Varies based on test number
     * @return
     */
    public String getMsg(){
        return this.testMsg;
    }
}
