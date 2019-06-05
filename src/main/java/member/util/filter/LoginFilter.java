package member.util.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.UserType;

@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

  private List<String> excludedUrls;

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
    //if (!excludedUrls.contains(path)) {
    //  HttpServletRequest request = (HttpServletRequest) req;
    //  HttpServletResponse response = (HttpServletResponse) resp;
    //  HttpSession session = request.getSession();
    //  //判斷session是否過期 或 沒登入
    //  if (session.getAttribute("login") == null) {
    //    System.out.println("session died");
    //    response.setHeader("sessionstatus", "timeout");
    //    if (path.equals("/web/index_eater.html")) {
    //      response.sendRedirect("/web/login.html");
    //    } else if (path.equals("/web/Administrator.html")) {
    //      response.sendRedirect("/web/Administrator_Login.html");
    //    }
    //    chain.doFilter(request, response);
    //  } else { // 該 session 有 user 登入了
    //    if (path.equals("/web/login.html") || path
    //        .equals("/web/Administrator_Login.html")) { // 有登入了 還想進 登入頁面 ------- 把他踢回去
    //      Long userID = (Long) session.getAttribute("userID");
    //      UserType userType = (UserType) session.getAttribute("type");
    //      String retUrl = request.getHeader("Referer");
    //      switch (userType) {
    //        case Customer: {
    //          retUrl = "/web/index_eater.html";
    //          break;
    //        }
    //        case Customer_and_Deliver: {
    //          retUrl = "/web/index_deliver.html";
    //          break;
    //        }
    //        case Administrator: {
    //          retUrl = "/web/Administrator.html";
    //          break;
    //        }
    //        default: {
    //          break;
    //        }
    //      }
    //      response.sendRedirect(retUrl);
    //    } else {
    //      chain.doFilter(request, response);
    //    }
    //  }
    //} else {
    //  chain.doFilter(req, resp);
    //}
    chain.doFilter(req, resp);
  }

  /**
   * <p>
   * 把不要過濾的 url存入 list.
   * </p>
   *
   * @param config FilterConfig
   * @throws ServletException ServletException
   */
  public void init(FilterConfig config) throws ServletException {
    String excludePattern = config.getInitParameter("excludedUrls");

    if (excludePattern != null) {
      excludedUrls = Arrays.asList(excludePattern.split(","));
    }
  }

}
