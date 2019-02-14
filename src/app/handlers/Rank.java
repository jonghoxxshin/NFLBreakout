package app.handlers;

public class Rank implements Comparable<Rank>{

    private int myRanking;
    private int myScore;

    /**
     * Construct a Rank object with only score as an input and set rank of this
     * instance as -1
     * @param s
     */
    public Rank(int s){
        myScore = s;
        myRanking = -1;
    }

    /**
     * Constructor that takes both score and rank as input and set those values
     * for this instance
     * @param r
     * @param s
     */
    public Rank(int r, int s){
        myRanking = r;
        myScore = s;
    }

    /**
     * Get the ranking of this object
     * @return integer myRanking
     */
    public int getMyRanking() {
        return myRanking;
    }

    /**
     * Set the myRanking value of this object
     * @param myRanking
     */
    public void setMyRanking(int myRanking) {
        this.myRanking = myRanking;
    }

    /**
     * Get the value of myScore of this object
     * @return Integer myScore
     */
    public int getMyScore() {
        return myScore;
    }

    /**
     * Set the myScore value of this object
     * @param myScore
     */
    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    /**
     * Returns {Rank,Score} form of this Rank object in to a string
     * @return String that contains rank and score of a past score
     */
    @Override
    public String toString() {
        return  myRanking +
                "," + myScore +
                '\n';
    }

    /**
     * Compare the sorting order of more than two of Rank objects
     * @param o
     * @return Negative number if input Rank has lower score, 0 if two Ranks have same score,
     * positive number if input Rank has higher score
     */
    @Override
    public int compareTo(Rank o) {
        return o.myScore - this.myScore;
    }
}
