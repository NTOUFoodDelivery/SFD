package order.util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import order.util.timer.PushOrderTimer;

@WebListener()
public class PushOrderListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  private PushOrderTimer pushOrderTimer = null;

  public PushOrderListener() {
  }

  /**
   * <p>開啟 推播計時.</p>
   *
   * @param sce ServletContextEvent
   */
  public void contextInitialized(ServletContextEvent sce) {

    String status = "[SYS] SMS reply listener start .";
    sce.getServletContext().log(status);
    System.out.println(status);
    pushOrderTimer = new PushOrderTimer(10);
    pushOrderTimer.start();
  }

  /**
   * <p>關閉 推播計時.</p>
   *
   * @param sce ServletContextEvent
   */
  public void contextDestroyed(ServletContextEvent sce) {
    String status = "[SYS] SMS reply listener stop .";
    sce.getServletContext().log(status);
    System.out.println(status);
    if (pushOrderTimer != null) {
      pushOrderTimer.stop();
    }
  }
}
