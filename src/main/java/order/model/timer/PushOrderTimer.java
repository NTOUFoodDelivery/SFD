package order.model.timer;

import order.model.timerTask.PushOrderTask;

import java.util.Date;
import java.util.Timer;

public class PushOrderTimer {
    private static Timer timer = null ;

    private int sec;
    public PushOrderTimer(int secs) {
        sec = secs;
    }
    public void start() {
        timer = new Timer();
        Date date = new Date();
        timer.schedule(new PushOrderTask(),
                0, sec * 1000);
    }
    public void stop() {
        timer.cancel();
    }
    public void reset() {
        timer.cancel();
        timer = new Timer();
        Date date = new Date();

        timer.schedule(new PushOrderTask(),
                0, sec * 1000);
    }
    public void setSec(int secs) {
        sec = secs;
    }
}