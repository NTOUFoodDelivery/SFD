package menu.util.listener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import menu.controller.service.RestInfoService;
import menu.model.javabean.Menu;
import menu.model.javabean.Rest;

@WebListener()
public class RestMenuListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  // Public constructor is required by servlet spec
  public RestMenuListener() {
  }


  /**
   * <p>init restInfoList and restMenuHashMap.</p>
   *
   * @param sce ServletContextEvent
   */
  public void contextInitialized(ServletContextEvent sce) {

    ServletContext servletContext = sce.getServletContext();
    // init restInfo----BEGIN
    RestInfoService restInfoService = new RestInfoService();
    List<Rest> restInfoList = restInfoService.getRestInfo();
    servletContext.setAttribute("restInfoList", restInfoList);
    restInfoService = null;
    // init restInfo----END
    //// init restMenu----BEGIN
    //Map<Long, List<Menu>> restMenuHashMap = new ConcurrentHashMap<>();
    //servletContext.setAttribute("restMenuHashMap", restMenuHashMap);
    //// init restMenu----BEGIN
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }

}
