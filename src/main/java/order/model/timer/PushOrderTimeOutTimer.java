package order.model.timer;

import order.model.timerTask.PushOrderTimeOutTask;

import java.util.Date;
import java.util.Timer;

public class PushOrderTimeOutTimer {
    private static Timer timer = null ;

    private int sec;
    public PushOrderTimeOutTimer(int secs) {
        sec = secs;
    }
    public void start() {
        timer = new Timer();
        Date date = new Date();
        timer.schedule(new PushOrderTimeOutTask(),
                0, sec * 1000);
    }
    public void stop() {
        timer.cancel();
    }
    public void reset() {
        timer.cancel();
        timer = new Timer();
        Date date = new Date();

        timer.schedule(new PushOrderTimeOutTask(),
                0, sec * 1000);
    }
    public void setSec(int secs) {
        sec = secs;
    }
}
