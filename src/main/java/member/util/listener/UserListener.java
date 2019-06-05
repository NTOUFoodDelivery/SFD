package member.util.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import member.model.daoImpl.UserDaoImpl;
import member.util.setting.UserStatus;


@WebListener()
public class UserListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  private Map<String, Long> userHashMap;

  // Public constructor is required by servlet spec
  public UserListener() {
  }

  /**
   * <p>web app init user hash map.</p>
   *
   * @param sce ServletContextEvent
   */
  public void contextInitialized(ServletContextEvent sce) {
    userHashMap = new ConcurrentHashMap<>();
    sce.getServletContext().setAttribute("userHashMap", userHashMap);
  }

  /**
   * <p>when web app close .</p>
   *
   * @param sce ServletContextEvent
   */
  public void contextDestroyed(ServletContextEvent sce) {
    UserDaoImpl userDao = new UserDaoImpl();
    for (Long userID : userHashMap.values()) { //將 使用者 轟下線
      userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString()); // 設定狀態為 下線
    }
    userDao = null;
  }

  /**
   * <p>when session dead close.. ,update User in db status.. .</p>
   * @param se HttpSessionEvent
   */
  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    HttpSession session = se.getSession();
    Long userID = (Long) session.getAttribute("userID"); // user id
    userHashMap.remove(session.getId()); // 剔除 user hash map
  }
}
