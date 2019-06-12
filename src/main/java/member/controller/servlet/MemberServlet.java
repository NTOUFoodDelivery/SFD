package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.controller.service.MemberService;
import member.util.setting.MemberCommand;

@WebServlet(name = "ModifyMemberServlet", urlPatterns = {
    "/MemberServlet/modify", "/MemberServlet/showUsers"
})
public class MemberServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    MemberCommand memberCommand = MemberCommand
        .getMemberCommand(request.getParameter("cmd")); // cmd
    Long userID = Long.parseLong(request.getParameter("userID")); // user id

    MemberService memberService = new MemberService();
    String json = gson.toJson(memberService.modifyMember(userID, memberCommand));
    memberService = null;
    gson = null;
    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    String json = gson.toJson("sddsf");
    gson = null;
    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}