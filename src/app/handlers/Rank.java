package app.handlers;

public class Rank implements Comparable<Rank>{

    private int myRanking;
    private int myScore;


    public Rank(int s){
        myScore = s;
        myRanking = -1;
    }

    public Rank(int r, int s){
        myRanking = r;
        myScore = s;
    }

    public int getMyRanking() {
        return myRanking;
    }

    public void setMyRanking(int myRanking) {
        this.myRanking = myRanking;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    @Override
    public String toString() {
        return  myRanking +
                "," + myScore +
                '\n';
    }

    @Override
    public int compareTo(Rank o) {
        return o.myScore - this.myScore;
    }
}
