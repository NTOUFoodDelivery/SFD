package member.controller.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import member.controller.service.MemberService;
import member.model.javabean.MemberApiResponse;
import member.util.setting.Validate;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new Gson();
    MemberService memberService = new MemberService();
    String json;
    Validate validate;

    validate = memberService.logout(request);

    MemberApiResponse memberApiResponse = new MemberApiResponse();
    memberApiResponse.setTime(new Date().toString());
    memberApiResponse.setResult(validate.toString());

    json = gson.toJson(memberApiResponse);
    memberService = null;

    PrintWriter out = response.getWriter();
    out.println(json);
    out.flush();
  }
}
