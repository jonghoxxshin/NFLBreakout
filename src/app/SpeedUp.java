package app;

public class SpeedUp extends powerUp {
    /**
     * Constructor calls super powerUp constructor with known type
     * @param x
     * @param y
     * @param size
     */
    public SpeedUp(double x, double y, double size){
        super(x, y, size, 2);
    }

    /**
     * Calls paddle helper method to handle powerUp
     * @param paddle
     * @param ball
     */
    @Override
    public void handlePower(Paddle paddle, Ball ball) {
        paddle.speedUp();
    }
}
