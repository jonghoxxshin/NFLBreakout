package app.tests;

import app.Game;
import app.handlers.DataHandler;
import javafx.scene.Scene;

import java.util.List;

abstract public class TestGame extends Game {
    private int testNum;
    protected Scene scene;
    private DataHandler datRead;
    private List<String> testInfo;

    private int x;
    private int y;
    private int xVel;
    private int yVel;
    private String testMsg;
    //private int myLevel;

    /**
     * Constructor creates mock game when a test key is pressed to initialize tests
     * @param testNum
     */
    public TestGame(int testNum, int level) {
        super(level);
        //myLevel = level;
        this.scene = super.createGame();
        this.testNum = testNum;
        //Reads test info data from text files
        this.datRead = new DataHandler(0, 0);
        this.testInfo = datRead.readTestFiles(testNum, level);
        parseTestInfo(testInfo);
    }

    /**
     * Sets initial position and velocities as required based on test cases
     * @return
     */
    @Override
    abstract public Scene createGame();

    /**
     * Runs 60 times per second to animate the game and check if test case was success
     * Returning 2 indicates game should stop and alert displayed
     * @param elapsedTime
     * @return
     */
    @Override
    abstract public int step(double elapsedTime);


    public int superStep(double elapsedTime){
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

    /**
     * Helper method called in 3 setLevel#Tests() methods
     * Sets initial ball positions for tests that have to do with myBall
     */
    public void setBallHelper(){
        myBall.setVeloY(yVel);
        myBall.setVeloX(xVel);
        myBall.getBall().setX(x);
        myBall.getBall().setY(y);
    }

    public int getTestNum(){return this.testNum;}
    public int getxVel(){return this.xVel;}
    public int getyVel(){return this.yVel;}
    public int getX() {return this.x;}
    public int getY() {return this.y;}
}
