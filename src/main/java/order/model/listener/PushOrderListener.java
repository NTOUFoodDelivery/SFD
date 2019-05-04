package order.model.listener;

import timer.PushOrderTimer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class PushOrderListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private PushOrderTimer pushOrderTimer = null;

    public PushOrderListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {

        String status = "[SYS] SMS reply listener start .";
        sce.getServletContext().log(status);
        System.out.println(status);
        pushOrderTimer = new PushOrderTimer(1);
        pushOrderTimer.start();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        String status = "[SYS] SMS reply listener stop .";
        sce.getServletContext().log(status);
        System.out.println(status);
        if (pushOrderTimer != null) {
            pushOrderTimer.stop();
        }
    }
}
