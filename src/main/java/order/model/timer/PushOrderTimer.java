package order.model.timer;

import order.model.timerTask.PushOrderTask;

import java.util.Date;
import java.util.Timer;

public class PushOrderTimer {
    private final Timer timer = new Timer();
    private final int min;
    public PushOrderTimer(int minutes) {
        min = minutes;
    }
    public void start() {
        Date date = new Date();
        timer.schedule(new PushOrderTask()
                , date, min * 60 * 1000);
    }
    public void stop() {
        timer.cancel();
    }
}