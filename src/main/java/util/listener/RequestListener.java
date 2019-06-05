package util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener()
public class RequestListener implements ServletRequestListener {

  public void requestInitialized(ServletRequestEvent sre) {
    //request 附上 httpSession
    ((HttpServletRequest) sre.getServletRequest()).getSession();
  }

  public void contextInitialized(ServletContextEvent sce) {
    // 連接資料庫

  }

  public void contextDestroyed(ServletContextEvent sce) {
    // 斷開資料庫
  }

  public RequestListener() {
  }

  public void requestDestroyed(ServletRequestEvent arg0) {
  }
}
