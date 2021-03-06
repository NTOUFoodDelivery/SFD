package member.util.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import member.model.javabean.User;
import member.util.setting.UserType;
import member.util.setting.WebUrl.Admin;
import member.util.setting.WebUrl.Deliver;
import member.util.setting.WebUrl.Eater;

@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

  private List<String> excludedUrls;
  private List<String> eaterWebUrls;
  private List<String> deliverWebUrls;
  private List<String> adminWebUrls;

  public void destroy() {
  }

  /**
   * <p>
   * 過濾 每個使用者請求 分析 登入狀況.
   * </p>
   *
   * @param req ServletRequest
   * @param resp ServletResponse
   * @param chain FilterChain
   */
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws ServletException, IOException {

    String path = ((HttpServletRequest) req).getServletPath();
    System.out.println(path);
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    HttpSession session = request.getSession();
    if (!excludedUrls.contains(path)) {

      //判斷session是否過期 或 沒登入
      if (session.getAttribute("login") == null) {
        String param = request.getParameter("requestID");
        if (param != null) {
          Long requestID = Long.parseLong(param);
          ConcurrentHashMap userHashMap = (ConcurrentHashMap) request.getServletContext()
              .getAttribute("userHashMap");
          Object userObject = userHashMap.get(requestID);
          if (userObject != null) {
            User user =(User)userObject;
            System.out.println(user);
            if (user != null) {
              session.setAttribute("user", user); // User 保存進 session
              session.setAttribute("userID", requestID); // User id 保存進 session
              session.setAttribute("login", "login"); // login 保存進 session
            } else {
              System.out.println("請求網址時 還沒 登入！！");
            }
          }
        } else {
          System.out.println("請求網址時 還沒 登入！！");
          response.setHeader("sessionstatus", "timeout");
          if (!path.equals(Eater.LOGIN) && !path.equals(Admin.LOGIN) && !path
              .equals(Eater.SIGN_UP)) {
            if (eaterWebUrls.contains(path) || deliverWebUrls.contains(path)) {
              response.sendRedirect(Eater.LOGIN);
            } else if (adminWebUrls.contains(path)) {
              response.sendRedirect(Admin.LOGIN);
            }
          }
        }
        chain.doFilter(request, response);
      } else { // 該 session 有 user 登入了
        // request url is web url-----BEGIN---may redirect
        User user = (User) session.getAttribute("user");
        UserType userCurrentType = user.getUserNow();
        switch (userCurrentType) {
          case Customer: {
            if (path.equals(Eater.LOGIN)) {
              response.sendRedirect(Eater.WELCOME);
            } else {
              if (adminWebUrls.contains(path)) {
                response.sendRedirect(Eater.LOGIN);
              } else if (deliverWebUrls.contains(path)) {
                response.sendRedirect(Eater.LOGIN);
              }
            }
            break;
          }
          case Deliver: {
            if (path.equals(Deliver.LOGIN)) {
              response.sendRedirect(Deliver.WELCOME);
            } else {
              if (adminWebUrls.contains(path)) {
                response.sendRedirect(Deliver.LOGIN);
              } else if (eaterWebUrls.contains(path)) {
                response.sendRedirect(Deliver.LOGIN);
              }
            }
            break;
          }
          case Administrator: {
            if (path.equals(Admin.LOGIN)) {
              response.sendRedirect(Admin.WELCOME);
            } else {
              if (eaterWebUrls.contains(path)) {
                response.sendRedirect(Admin.LOGIN);
              } else if (deliverWebUrls.contains(path)) {
                response.sendRedirect(Admin.LOGIN);
              }
            }
            break;
          }
          default: {
            break;
          }
        }
        // request url is web url-----END---may redirect
        chain.doFilter(request, response);
      }
    } else {
      if(path.equals("/LoginServlet")) {
        String param = request.getParameter("requestID");
        if (param != null) {
          Long requestID = Long.parseLong(param);
          ConcurrentHashMap userHashMap = (ConcurrentHashMap) request.getServletContext()
              .getAttribute("userHashMap");
          Object userObject = userHashMap.get(requestID);
          if (userObject != null) {
            User user =(User)userObject;
            System.out.println("sdf");
            System.out.println(user);
            if (user != null) {
              session.setAttribute("user", user); // User 保存進 session
              session.setAttribute("userID", requestID); // User id 保存進 session
              session.setAttribute("login", "login"); // login 保存進 session
            } else {
              System.out.println("請求網址時 還沒 登入！！");
            }
          }
        }
      }
      chain.doFilter(req, resp);
    }
  }
  /**
   * <p>
   * 把不要過濾 或 一些網頁 的 url存入 list.
   * </p>
   *
   * @param config FilterConfig
   * @throws ServletException ServletException
   */
  public void init(FilterConfig config) throws ServletException {

    String excludePattern = config.getInitParameter("excludedUrls");
    String eaterPattern = config.getInitParameter("eaterWebUrls");
    String deliverPattern = config.getInitParameter("deliverWebUrls");
    String adminPattern = config.getInitParameter("adminWebUrls");

    if (excludePattern != null) {
      excludedUrls = Arrays.asList(excludePattern.split(","));
    }
    if (eaterPattern != null) {
      eaterWebUrls = Arrays.asList(eaterPattern.split(","));
    }
    if (deliverPattern != null) {
      deliverWebUrls = Arrays.asList(deliverPattern.split(","));
    }
    if (adminPattern != null) {
      adminWebUrls = Arrays.asList(adminPattern.split(","));
    }
  }

}
