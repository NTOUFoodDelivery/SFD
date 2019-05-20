package util.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/CharacterEncodingFilter")
public class CharacterEncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);
//        ServletRequest requestWrapper = null;
//        if(request instanceof HttpServletRequest) {
//            requestWrapper = new MultiReadHttpServletRequest((HttpServletRequest) request);
//        }
//        if(null == requestWrapper) {
//            System.out.println("包包失敗!");
//            chain.doFilter(request, response);
//        } else {
//            System.out.println("包包大成功!");
//            chain.doFilter(requestWrapper, response);
//        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
