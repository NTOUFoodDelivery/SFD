package member.model.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/LogFilter")
public class LogFilter implements Filter {

    private List<String> excludedUrls;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String path = ((HttpServletRequest) req).getServletPath();

        if(!excludedUrls.contains(path)){
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpSession session = request.getSession();
            //判斷session是否過期
            if (session.getAttribute("login") == null) {
                String errors = "您還沒有登入，或者session已過期。請先登入！";
                request.setAttribute("Message", errors);
                //跳轉至登入頁面
                request.getRequestDispatcher("LoginDemo.jsp").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        String excludePattern = config.getInitParameter("excludedUrls");

        if(excludePattern != null){
            excludedUrls = Arrays.asList(excludePattern.split(","));
        }
    }

}
