package member.controller.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.controller.service.MemberService;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

  Gson gson = new Gson();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
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

    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    HttpSession httpSession = request.getSession();
    Long userID = (Long) httpSession.getAttribute("userID");
    ConcurrentHashMap userHashMap = (ConcurrentHashMap) httpSession.getServletContext()
        .getAttribute("userHashMap");
    User currentUser = (User) userHashMap.get(userID);
    String json = gson.toJson(currentUser);
    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }


}
