package order.util.timer;

import java.util.Timer;
import order.controller.timertask.PushOrderTask;

public class PushOrderTimer {

  private Timer timer = null;

  private int sec;

  public PushOrderTimer(int secs) {
    sec = secs;
  }

  /**
   * <p>開始 推播計時.</p>
   */
  public void start() {
    timer = new Timer();
    timer.schedule(new PushOrderTask(),
        0, sec * 1000);
  }

  /**
   * <p>停止 推播計時.</p>
   */
  public void stop() {
    timer.cancel();
  }

  /**
   * <p>重置 推播計時.</p>
   */
  public void reset() {
    timer.cancel();
    timer = new Timer();
    timer.schedule(new PushOrderTask(),
        0, sec * 1000);
  }
}