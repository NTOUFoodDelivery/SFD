package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.RestDAO;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import member.model.javabean.MemberSetting;
import menu.model.response.javabean.Menu;
import tool.HttpCommonAction;
import tool.javabean.CommonRequest;
import tool.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/ModifyMemberServlet")
public class ModifyMemberServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),CommonRequest.class);
        String cmd = commonRequest.getQuery().getCommand(); // 新增 刪除 修改...
        List<Object> resultBeans = commonRequest.getResult();
        List<User> userList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            userList.add(gson.fromJson(jsonObject.toString(),User.class)); // 使用者物件
        }
        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
        statusCodeResponse.setStatusCode(HttpServletResponse.SC_OK);
        switch (cmd){
            case MemberSetting.Command.USER_BAN:{
                System.out.println("ban");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.DELETE:{
                System.out.println("delete");
                for(User user : userList){
                    UserDAO.delUser(user.getUserID());
                }
                break;
            }
            case MemberSetting.Command.DELIVER_ON:{
                System.out.println("deliverOn");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.CUSTOMER:{
                System.out.println("customer");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.DELIVER_BUSY:{
                System.out.println("deliverBusy");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(),cmd);
                }
                break;
            }
            case MemberSetting.Command.DELIVER_OFF:{
                System.out.println("deliverOff");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.OFFLINE:{
                System.out.println("offline");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.ADMINISTRATOR:{
                System.out.println("admin");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            default:{
                System.out.println("default");
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
