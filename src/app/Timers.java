package app;

import java.util.Timer;
import java.util.TimerTask;

public class Timers {
    public void timeOut(String powType){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //reset powerUp to original state
                /*
                if(powType.equals("stretch")){
                    setPaddle(paddle_speed, 80);
                }
                else if(powType.equals("speedUp")){
                    setPaddle(10, paddleWidth);
                }
                else if(powType.equals("bigBall")){
                    setBall(0,20);
                }*/
            }
        };
        timer.schedule(task, 5000l);
    }
}
