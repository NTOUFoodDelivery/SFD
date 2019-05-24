package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.controller.service.MemberService;
import member.model.javabean.UserStatus;
import util.HttpCommonAction;
import util.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/SwitchStatusServlet")
public class SwitchStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
//        UserStatus userStatus = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),UserStatus.class);
//        //        Long userID = (Long) request.getSession().getAttribute("User_Id");
//        Long userID = Long.parseLong(request.getParameter("userID"));
//        userStatus.setUserID(userID);
//        boolean success = MemberService.switchStatus(userStatus);
//        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
//        if(success){
//            statusCodeResponse.setStatusCode(HttpServletResponse.SC_OK);
//        }else{
//            statusCodeResponse.setStatusCode(HttpServletResponse.SC_NOT_ACCEPTABLE);
//        }
//        statusCodeResponse.setTime(new Date().toString());
//        String json = gson.toJson(statusCodeResponse);
//        PrintWriter out = response.getWriter();
//        out.print(json);
//        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
