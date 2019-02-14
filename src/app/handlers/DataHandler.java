package app.handlers;

import app.sprite.Brick;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.*;

public class DataHandler {
    public static final String HIGH_SCORE_FILE = "high_score.txt";

    private String[] levelFiles = {"level1_setup.txt", "level2_setup.txt", "level3_setup.txt"};
    private String[][] testFiles = {{"lv1_test1.txt", "lv1_test2.txt", "lv1_test3.txt"}, {"lv2_test1.txt", "lv2_test2.txt", "lv2_test3.txt"}, {"lv3_test1.txt", "lv3_test2.txt", "lv3_test3.txt"}};
    private double width,height;

    private List<Brick> myBricks;
    private List<String> testInfo;
    private int myHighestScore;

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
    public List<String> readTestFiles(int testNum, int level){
        testInfo = new ArrayList<>();
        String testFile = testFiles[level-1][testNum-3];
        //System.out.println(testFile);
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(testFile));
        while(scan.hasNext()){
            String temp = scan.nextLine();
            testInfo.add(temp);
        }
        return testInfo;
    }

    /**
     * Given a sorted ArrayList of Ranks, finds the highest score and updates the private variable myHighestScore
     * @param scoreList
     */

    public void getHighestScore(ArrayList<Rank> scoreList){

        if(scoreList.size()==0){
            myHighestScore = 0;
        }
        else myHighestScore =  scoreList.get(0).getMyScore();
    }

    /**
     * Given a sorted ArrayList of Ranks, writes the ranking out to a text file named HIGH_SCORE_FILE
     * @param scoreList
     */
    public void writeToFile(ArrayList<Rank> scoreList){
        try {
            File file = new File(this.getClass().getClassLoader().getResource(HIGH_SCORE_FILE).toURI());
            if(!file.exists()) {
                if(!file.createNewFile()) {
                    return;
                }
            }

            var output = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < scoreList.size(); i++) {
                output.write(scoreList.get(i).toString());
            }
            output.close();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


    }

    /**
     * Given an arrayList of Ranks and a score, the score is added to the
     * list and the list is sorted and the score is given a subsequent rank
     * @param score
     * @param list
     * @return A sorted rank list with the new score added with its appropriate rank
     */
    public ArrayList<Rank> addRankToList(int score, ArrayList<Rank> list){
        int  tempScore = score;
        ArrayList<Rank> newList = new ArrayList<>();
        newList = list;
        Rank tempRank = new Rank(score);
        list.add(tempRank);
        Collections.sort(list);
        tempRank.setMyRanking(list.indexOf(tempRank)+1);
        return newList;
    }

    /**
     * Reads in a text file that is named HIGH_SCORE_FILE and forms ArrayList of Rank objects
     * and sorts that list based on the score value of each Rank object
     * @return Sorted ArrayList of Ranks that is read from an text file that contains the past scores.
     */
    public ArrayList<Rank> readScoreFile(){
        ArrayList<Rank> list = new ArrayList<>();
        try {
            var file = new File(this.getClass().getClassLoader().getResource(HIGH_SCORE_FILE).toURI());
            var input = new Scanner(file);
            String[] temp = {};
            while (input.hasNext()) {
                temp = input.next().split(",");
                Rank myRank = new Rank(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                list.add(myRank);
            }
            Collections.sort(list);
            getHighestScore(list);
        } catch(Exception e) {
            e.printStackTrace();
;        }
        return list;
    }

    /**
     * Given an ArrayList of Ranks as input, updates the rank of each of the Rank object while iterating
     * through the list
     * @param in
     */
    public void updateRanking(ArrayList<Rank> in){
        for(int i = 0; i<in.size();i++){
            in.get(i).setMyRanking(i+1);
        }
    }

    /**
     * Given a score, adds a new score the the file and forms a new text file with the new score placed and
     * sorted in rank
     * @param score
     */
    public void updateHighScore(int score){
        ArrayList<Rank> previousList;
        //read in from current file
        previousList = readScoreFile();
        ArrayList<Rank> newList;
        newList = addRankToList(score, previousList);
        updateRanking(newList);
        writeToFile(newList);

    }

    /**
     * Return current highscore
     * @return current highscore
     */
    public int getCurrentHigh(){
        return myHighestScore;
    }





    /**
     * Getter function returns bricks that were read in from data file
     * @return return the ArrayList of bricks
     */
    public List<Brick> getMyBricks() {return myBricks;}

}
