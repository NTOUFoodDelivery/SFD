package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import member.controller.service.MemberService;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.MemberApiResponse;
import member.model.javabean.User;
import member.util.setting.UserType;
import member.util.setting.Validate;


@WebServlet(name = "/LoginServlet", urlPatterns = {"/LoginServlet/admin", "/LoginServlet"})
public class LoginServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    String account = request.getParameter("account");
    String password = request.getParameter("password");
    Validate validate = Validate.ERROR;
    UserDaoImpl userDao = new UserDaoImpl();
    User user = userDao.searchUser(account, password);
    MemberService memberService = new MemberService();
    if(user != null) {
      switch (request.getServletPath()) {
        case "/LoginServlet/admin": {
          if (user.getUserType().equals(UserType.Administrator)) {
            validate = memberService.login(request, user);
          }
          break;
        }
        default: {
          if (!user.getUserType().equals(UserType.Administrator)) {
            validate = memberService.login(request,user);
          }
          break;
        }
      }
    }
    MemberApiResponse memberApiResponse = new MemberApiResponse();
    memberApiResponse.setResult(validate.toString());
    memberApiResponse.setTime(new Date().toString());
    if (validate.equals(Validate.SUCCESS)) {
      memberApiResponse.setMessage(gson.toJson(user));
    }
    System.out.println(request.getSession());
    System.out.println(validate);
    String json = gson.toJson(memberApiResponse);
    userDao = null;
    memberService = null;

    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    String json;

    User currentUser;
    HttpSession httpSession = request.getSession();
    currentUser = (User) httpSession.getAttribute("user");
    System.out.println(currentUser);
    json = gson.toJson(currentUser);

    System.out.println(request.getSession());
    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }

}
