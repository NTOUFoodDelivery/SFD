package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener()
public class RequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre)  {
        //request 附上 httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();
    }
    public RequestListener() {
    }

    public void requestDestroyed(ServletRequestEvent arg0)  {
    }
}
