package member.util.filter;

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

    private List<String> excludedUrls;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String path = ((HttpServletRequest) req).getServletPath();
        if(!excludedUrls.contains(path)){
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpSession session = request.getSession();
            //判斷session是否過期 或 沒登入
            if (session.getAttribute("login") == null) {
                System.out.println("session died");
                String errors = "您還沒有登入，或者session已過期。請先登入！";
                request.setAttribute("Message", errors);
                response.setHeader("sessionstatus", "timeout");
                chain.doFilter(request, response);
            } else { // 該 session 有 user 登入了
                if(path.equals("/LoginDemo.html")){ // 有登入了 還想進 登入頁面 ------- 把他踢回去
                    Long userID = (Long) session.getAttribute("userID");
                    System.out.println(userID);
                    UserDaoImpl userDao = new UserDaoImpl();
                    User user = userDao.showUser(userID);
                    String retUrl = request.getHeader("Referer");
                    switch (user.getUserType()){
                        case Customer:{
                            retUrl = "testEater.html";
                            break;
                        }
                        case Customer_and_Deliver:{
                            retUrl = "testDeliver.html";
                            break;
                        }
                        case Administrator:{
                            retUrl = "index.html";
                            break;
                        }
                    }
                    userDao = null;
                    response.sendRedirect(retUrl);
                }else {
                    chain.doFilter(request, response);
                }
            }
        }else {
            chain.doFilter(req, resp);
        }
//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        String excludePattern = config.getInitParameter("excludedUrls");

        if(excludePattern != null){
            excludedUrls = Arrays.asList(excludePattern.split(","));
        }
    }

}
