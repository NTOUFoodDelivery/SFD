package member.controller.servlet;

import com.google.gson.Gson;
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

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new Gson();
    ConcurrentHashMap userHashMap = (ConcurrentHashMap)request.getServletContext().getAttribute("userHashMap");
    HttpSession httpSession = request.getSession();
    MemberService memberService = new MemberService();
    String json = gson.toJson(memberService.logout(userHashMap,httpSession));
    memberService = null;
    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }
}
