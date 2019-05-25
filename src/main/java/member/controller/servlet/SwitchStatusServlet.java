package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.controller.service.MemberService;
import member.model.javabean.User;
import member.util.setting.UserStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SwitchStatusServlet")
public class SwitchStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        UserStatus userStatus = UserStatus.getUserStatus(request.getParameter("userStatus")); // user Status
        User currentUser = (User) request.getSession().getAttribute("User"); // current request user

//        User currentUser = new User(); // test user
//        currentUser.setUserID(1L); // test user id
//        currentUser.setUserType(UserType.Customer); // test user type
//        currentUser.setUserStatus(UserStatus.CUSTOMER); // test user status

        MemberService memberService = new MemberService();
        String json = gson.toJson(memberService.switchStatus(currentUser,userStatus));
        memberService = null;
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
