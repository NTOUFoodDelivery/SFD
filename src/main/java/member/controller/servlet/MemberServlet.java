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
import member.model.dao.UserDao;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.MemberCommand;
import util.HttpCommonAction;

@WebServlet(name = "ModifyMemberServlet", urlPatterns = {
    "/MemberServlet/modify", "/MemberServlet/showUsers", "/MemberServlet/showUser"
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
    boolean result = memberService.modifyMember(userID, memberCommand);
    String json = gson.toJson(
        HttpCommonAction.generateStatusResponse(result, "Command :: " + memberCommand.toString()));
    memberService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    String json;
    String account = request.getParameter("account");
    switch (request.getServletPath()) {
      case "/MemberServlet/showUser": {
        UserDaoImpl userDao = new UserDaoImpl();
        json = gson.toJson(userDao.searchUser(account));
        userDao =  null;
        break;
      }
      default: {
        MemberService memberService = new MemberService();
        json = gson.toJson(memberService.showUsers());
        memberService = null;
        break;
      }
    }
    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}
