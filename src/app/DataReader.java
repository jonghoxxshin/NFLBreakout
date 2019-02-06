package app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
    private String[] levelFiles = {"level1_setup.txt", "level2_setup.txt", "level3_setup.txt"};
    private String[] testFiles = {"test_1.txt", "test_2.txt", "test_3.txt"};
    private double width,height;

    public ArrayList<Brick> myBricks;
    public ArrayList<String> testInfo;

    public DataReader(double _width, double _height){
        this.width = _width;
        this.height = _height;
    }

    public void readBricks(int myLevel){
        myBricks = new ArrayList<>();
        int line = 0;
        String level = levelFiles[myLevel-1];
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(level));
        int[] firstLine = toIntArray(scan.nextLine().split(" "));

        //int brickSize = firstLine[0];
        int rows = firstLine[1];
        int columns = firstLine[2];
        int[][] brickLocationArray = new int[rows][columns];

        while(scan.hasNext()){
            int[] intData = toIntArray(scan.nextLine().split(" "));
            brickLocationArray[line] = intData;
            line++;
        }
        parse2D(brickLocationArray, rows, columns);
    }

    public int[] toIntArray(String[] args){
        int length = args.length;
        int[] intArray = new int[length];
        for(int i=0; i<length; i++){
            intArray[i] = Integer.parseInt(args[i]);
        }
        return intArray;
    }

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

    public ArrayList<String> readTestFiles(int testNum){
        testInfo = new ArrayList<>();
        String testFile = testFiles[testNum-3];
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(testFile));
        while(scan.hasNext()){
            String temp = scan.nextLine();
            testInfo.add(temp);
        }
        //System.out.println(testInfo);
        return testInfo;
    }

    public ArrayList<Brick> getMyBricks(){
        return myBricks;
    }
}
