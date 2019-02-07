package app;

public class ExtraLife extends powerUp {
    /**
     * Constructor calls super powerUp constructor with known type
     * @param x
     * @param y
     * @param size
     */
    public ExtraLife(double x, double y, double size){
        super(x, y, size, 1);
    }

    /**
     * Calls Paddle helper method to handle powerUp
     * @param paddle
     * @param ball
     */
    @Override
    public void handlePower(Paddle paddle, Ball ball) {
        paddle.updateLives(1);
    }
}
