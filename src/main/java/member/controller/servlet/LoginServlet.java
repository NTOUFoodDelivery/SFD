package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import member.controller.service.MemberService;
import member.model.javabean.User;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    String account = request.getParameter("account");
    String password = request.getParameter("password");
    String userType = request.getParameter("userType");
    HttpSession session = request.getSession();
    String json;
    MemberService memberService = new MemberService();
    ConcurrentHashMap userHashMap = (ConcurrentHashMap) request.getServletContext()
        .getAttribute("userHashMap");
    json = gson.toJson(memberService.login(userHashMap, session, account, password, userType));
    memberService = null;
    gson = null;
    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    HttpSession httpSession = request.getSession();
    User currentUser = (User) httpSession.getAttribute("user");
    String json = gson.toJson(currentUser);
    gson = null;
    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }

}
