package order.util.timer;

import order.controller.timerTask.PushOrderTask;

import java.util.Timer;

public class PushOrderTimer {
    private Timer timer = null ;

    private int sec;
    public PushOrderTimer(int secs) {
        sec = secs;
    }
    public void start() {
        timer = new Timer();
        timer.schedule(new PushOrderTask(),
                0, sec * 1000);
    }
    public void stop() {
        timer.cancel();
    }
    public void reset() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new PushOrderTask(),
                0, sec * 1000);
    }
}