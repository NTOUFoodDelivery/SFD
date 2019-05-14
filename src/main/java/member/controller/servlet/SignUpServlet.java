package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import member.model.javabean.MemberSetting;
import tool.HttpCommonAction;
import tool.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        User user = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),User.class);
        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
        if(UserDAO.searchUser(user.getUserID())){ // 已註冊
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_NOT_ACCEPTABLE);
            String currentUserType =  UserDAO.showUserType(user.getUserID());
            if(currentUserType.equals(MemberSetting.UserType.CUSTOMER) && user.getUserType().equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)){
                // 食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)
                UserDAO.modifyUserType(user.getUserID(),MemberSetting.UserType.CUSTOMER_AND_DELIVER);
                statusCodeResponse.setStatusCode(HttpServletResponse.SC_ACCEPTED);
            }
        }else{ // 未註冊
            UserDAO.addUser(user);
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_ACCEPTED);
        }
        statusCodeResponse.setTime(new Date().toString());
        String json = gson.toJson(statusCodeResponse);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
