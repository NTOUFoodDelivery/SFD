package member.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import member.util.setting.WebUrl.Admin;
import member.util.setting.WebUrl.Eater;

@WebServlet(
    name = "LoginPageServlet",
    urlPatterns = {
        "/login/eater", "/login/admin"
    })
public class LoginPageServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    String url = request.getServletPath();
    switch (url) {
      case "/login/admin": { // 管理員
        request.getRequestDispatcher(Admin.LOGIN).forward(request,response);
        break;
      }
      default: { // 使用者
        request.getRequestDispatcher(Eater.LOGIN).forward(request,response);
        break;
      }
    }

  }
}
