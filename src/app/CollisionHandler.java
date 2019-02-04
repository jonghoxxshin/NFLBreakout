package app;

import javafx.scene.image.ImageView;

public class CollisionHandler {

    public CollisionHandler(){

    }

    public boolean detectCollision(ImageView arg1, ImageView arg2){
        if(!arg1.visibleProperty().getValue() || !arg2.visibleProperty().getValue()){
            return false;
        }
        double left1 = arg1.getX();
        double right1 = getRight(arg1);
        double top1 = arg1.getY();
        double bottom1 = getBottom(arg1);
        double left2 = arg2.getX();
        double right2 = getRight(arg2);
        double top2 = arg2.getY();
        double bottom2 = getBottom(arg2);
        if((left1 <= right2 && left1 >= left2) || (right1 >= left2 && right1 <= right2)){
            return verticalOverlap(top1, bottom1, top2, bottom2);
        }
        return false;
    }

    public boolean verticalOverlap(double top1, double bottom1, double top2, double bottom2){
        return((top1 <= bottom2 && top1>=top2) || (bottom1 >= top2 && bottom1<=bottom2));
    }

    public double getRight(ImageView arg){
        return arg.getX() + arg.getBoundsInLocal().getWidth();
    }
    public double getBottom(ImageView arg){
        return arg.getY() + arg.getBoundsInLocal().getHeight();
    }
    public double getCenter(ImageView arg){ return arg.getX() + getRight(arg) / 2;}

    public boolean sideCollision(ImageView arg1, ImageView arg2){
        if(arg1.getY() <= arg2.getY() || (getBottom(arg1)>= getBottom(arg2))){
            //System.out.println("SIDE COLLISION");
            return false;
        }
        else{
            //System.out.println("TOP COLLISION");
            return true;
        }
    }
}
