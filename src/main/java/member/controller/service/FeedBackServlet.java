package member.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.model.javabean.FeedBack;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FeedBackServlet")
public class FeedBackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        FeedBack feedBack = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), FeedBack.class);
        
        //User  = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),User.class);
        
//        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
//        if(UserDAO.searchUser(user.getUserID())){ // 已註冊
//            statusCodeResponse.setStatusCode(HttpServletResponse.SC_NOT_ACCEPTABLE);
//        }else{ // 未註冊
//            UserDAO.addUser(user);
//            statusCodeResponse.setStatusCode(HttpServletResponse.SC_ACCEPTED);
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
