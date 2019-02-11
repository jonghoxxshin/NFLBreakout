package app.handlers;

import app.sprite.Brick;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataHandler {
    private String[] levelFiles = {"level1_setup.txt", "level2_setup.txt", "level3_setup.txt"};
    private String[] testFiles = {"test_1.txt", "test_2.txt", "test_3.txt"};
    private double width,height;

    public List<Brick> myBricks;
    public List<String> testInfo;

    /**
     * Constructor takes in scene's height and width
     * @param _width
     * @param _height
     */
    public DataHandler(double _width, double _height){
        this.width = _width;
        this.height = _height;
    }


    /**
     * Reads text file based on level input from Game.java (1-3)
     * fills 2D array with values from text file -- indicating brick lives and positions
     * Uses toIntArray helper method because originally comes in as strings
     * @param myLevel
     */
    public void readBricks(int myLevel){
        myBricks = new ArrayList<>();
        int line = 0;
        String level = levelFiles[myLevel-1];
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(level));
        //First line contains info regarding level configuration
        int[] firstLine = toIntArray(scan.nextLine().split(" "));

        //int brickSize = firstLine[0];
        int rows = firstLine[1];
        int columns = firstLine[2];
        int[][] brickLocationArray = new int[rows][columns];

        //Fill brickLocationArray with arrays of data (numbers representing brick lives)
        while(scan.hasNext()){
            int[] intData = toIntArray(scan.nextLine().split(" "));
            brickLocationArray[line] = intData;
            line++;
        }
        parse2D(brickLocationArray, rows, columns);
    }

    /**
     * Helper method returns arrays of integers given arrays of strings
     * @param args
     * @return
     */
    public int[] toIntArray(String[] args){
        int length = args.length;
        int[] intArray = new int[length];
        for(int i=0; i<length; i++){
            intArray[i] = Integer.parseInt(args[i]);
        }
        return intArray;
    }

    /**
     * Actually creates bricks and sets brick locations based on number of columns and width of scene
     * @param argArray
     * @param rows
     * @param columns
     */
    public void parse2D(int[][] argArray, int rows, int columns){
        double colWidth = width/columns;
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                int lives = argArray[i][j];
                if(lives != 0){
                    double xLoc = colWidth * (j);
                    double yLoc = colWidth * (i);
                    Brick nBrick = new Brick(lives, xLoc, yLoc, colWidth-10);
                    myBricks.add(nBrick);
                }
            }
        }
    }

    /**
     * Called in TestGame.java when the game is running in test mode
     * Based on test mode input -> reads different test files and sets correct ball/paddle starting position and velocity
     * @param testNum
     * @return
     */
    public List<String> readTestFiles(int testNum){
        testInfo = new ArrayList<>();
        String testFile = testFiles[testNum-3];
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(testFile));
        while(scan.hasNext()){
            String temp = scan.nextLine();
            testInfo.add(temp);
        }
        //System.out.println(testInfo);
        //return testInfo;
        return testInfo;
    }

    /**
     * Getter function returns bricks that were read in from data file
     * @returnz
     */
    public List<Brick> getMyBricks() {return myBricks;}
}
