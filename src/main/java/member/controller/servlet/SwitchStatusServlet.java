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

import member.controller.service.MemberService;
import member.model.javabean.MemberApiResponse;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import member.util.setting.Validate;

@WebServlet("/SwitchStatusServlet")
public class SwitchStatusServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    UserStatus userStatus = UserStatus
        .getUserStatus(request.getParameter("userStatus")); // user Status
    User currentUser = (User) request.getSession()
        .getAttribute("user"); // current request User
    MemberService memberService = new MemberService();
    Validate validate = memberService.switchStatus(currentUser, userStatus);
    MemberApiResponse memberApiResponse = new MemberApiResponse();
    memberApiResponse.setResult(validate.toString());
    //memberApiResponse.setMessage();
    memberApiResponse.setTime(new Date().toString());
    String json = gson.toJson(memberApiResponse);
    memberService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }
}
