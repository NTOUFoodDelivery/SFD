package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.demo.dao.UserDAO;
import member.model.javabean.MemberSetting;
import member.model.javabean.UserStatus;
import order.model.javabean.PushResult;
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

@WebServlet("/SwitchStatusServlet")
public class SwitchStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        UserStatus userStatus = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),UserStatus.class);

        String status = userStatus.getUser_Status();
        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
        statusCodeResponse.setStatusCode(HttpServletResponse.SC_OK);
        switch (status){
            case MemberSetting.UserStatus.DELIVER_ON:{
                String identity = UserDAO.showUserIdentity(userStatus.getUser_Id());
                if(identity.equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)){
                    UserDAO.modifyUserStatus(userStatus.getUser_Id(),status);
                }
                break;
            }
            case MemberSetting.UserStatus.DELIVER_OFF:{
                String identity = UserDAO.showUserIdentity(userStatus.getUser_Id());
                if(identity.equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)){
                    UserDAO.modifyUserStatus(userStatus.getUser_Id(),status);
                }
                break;
            }
            case MemberSetting.UserStatus.OFFLINE:{
                // 如果目前有接單，收回
                String currentUserStatus = UserDAO.showUserType(userStatus.getUser_Id());
                if(currentUserStatus.equals(MemberSetting.UserStatus.DELIVER_BUSY)){}
                // 如果目前有推播訂單，收回
//                String currentUserStatus = UserDAO.showUserType(userStatus.getUser_Id());
//                if(currentUserStatus.equals(MemberSetting.UserStatus.DELIVER_ON)){}
                UserDAO.modifyUserStatus(userStatus.getUser_Id(),status);
                break;
            }
            case MemberSetting.UserStatus.CUSTOMER:{
                UserDAO.modifyUserStatus(userStatus.getUser_Id(),status);
                break;
            }
            case MemberSetting.UserStatus.DELIVER_BUSY:{
                UserDAO.modifyUserStatus(userStatus.getUser_Id(),status);
                break;
            }
            default:{
                statusCodeResponse.setStatusCode(HttpServletResponse.SC_NOT_ACCEPTABLE);
                break;
            }
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
