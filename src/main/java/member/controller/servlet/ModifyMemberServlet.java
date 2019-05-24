package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.controller.service.MemberService;
import member.model.javabean.User;
import member.util.setting.FeedbackCommand;
import member.util.setting.MemberCommand;
import member.util.setting.UserType;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ModifyMemberServlet")
public class ModifyMemberServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        User user = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),User.class); // user

        HttpSession httpSession = request.getSession();
        User currentUser = (User)httpSession.getAttribute("User"); // current request user

        MemberService memberService = new MemberService();
        String json = null;
        UserType userType = UserType.getUserType(currentUser.getUserType());
        switch (userType){
            case Administrator:{
                System.out.println("Administrator");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(memberService.modifyMemberStatus(user)));
                break;
            }
            case Customer:{
                String userStatus = request.getParameter("userStatus");
                System.out.println("Customer");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(memberService.switchCustomerStatus(currentUser,userStatus)));
                break;
            }
            case Customer_and_Deliver:{
                System.out.println("Customer_and_Deliver");
                String userStatus = request.getParameter("userStatus");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(memberService.switchDeliverStatus(currentUser,userStatus)));
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
