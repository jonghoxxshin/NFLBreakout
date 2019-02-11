package app.handlers;

import javafx.scene.image.ImageView;

public class CollisionHandler {

    /**
     * Returns true if collision is detected, false otherwise
     * Uses helper method "verticalOverlap" after checking manually if horizontal overlap
     * @param arg1
     * @param arg2
     * @return
     */
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

    /**
     * Helper method called by detectCollision
     * returns true if 2 image views overlap in y values (top and bottom)
     * @param top1
     * @param bottom1
     * @param top2
     * @param bottom2
     * @return
     */
    public boolean verticalOverlap(double top1, double bottom1, double top2, double bottom2){
        return((top1 <= bottom2 && top1>=top2) || (bottom1 >= top2 && bottom1<=bottom2));
    }
    /**
     * Helper method to get right side of an ImageView (based on width)
     * @param arg
     * @return
     */
    public double getRight(ImageView arg){
        return arg.getX() + arg.getBoundsInLocal().getWidth();
    }
    /**
     * Helper method to get bottom side of ImageView (based on height)
     * @param arg
     * @return
     */
    public double getBottom(ImageView arg){
        return arg.getY() + arg.getBoundsInLocal().getHeight();
    }
    public double getCenter(ImageView arg){ return arg.getX() + getRight(arg) / 2;}

    /**
     * Called when brick-ball collision is detected
     * Return true if ball hits side of brick - update x velocity only
     * Return false if ball hits bottom or top of brick - update y velocity only
     * @param arg1
     * @param arg2
     * @return
     */
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

    /**
     * Detects top collision, used in tandem with sideCollision to detect corner hits and handle accordingly
     * @param arg1
     * @param arg2
     * @return
     */
    public boolean topCollision(ImageView arg1, ImageView arg2){
        if(arg1.getX() <= arg2.getX() || (getRight(arg1)>=getRight(arg2))){
            System.out.println("NOT TOP");
            return false;
        }
        System.out.println("TOP COLLISION");
        return true;
    }
}
