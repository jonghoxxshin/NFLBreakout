package app.powerup;

import app.powerup.powerUp;
import app.sprite.Ball;
import app.sprite.Paddle;

public class BigBall extends powerUp {
    /**
     * Constructor calls super powerUp constructor with known type
     * @param x
     * @param y
     * @param size
     */
    public BigBall(double x, double y, double size){
        super(x, y, size, 0);
    }

    /**
     * Calls Ball helper method to handle powerUp
     * @param paddle
     * @param ball
     */
    @Override
    public void handlePower(Paddle paddle, Ball ball) {
        ball.pumpPower();
    }
}
