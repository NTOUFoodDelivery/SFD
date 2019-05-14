package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import db.demo.dao.RestDAO;
import db.demo.dao.UserDAO;
import member.model.javabean.FeedBack;
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

@WebServlet("/FeedBackServlet")
public class FeedBackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        //FeedBack feedBack = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), FeedBack.class);
        
        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),CommonRequest.class);
        String cmd = commonRequest.getQuery().getCommand(); // 新增 刪除 修改
        List<Object> resultBeans = commonRequest.getResult();
        List<FeedBack> feedBackList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            feedBackList.add(gson.fromJson(jsonObject.toString(),FeedBack.class)); // feedBack資料存取
        }

        switch (cmd){
            case "addFeedback":{
                System.out.println("addFeedback");
                for(FeedBack feedBack : feedBackList){
                	UserDAO.addFeedback(feedBack.getFeedBackID(),feedBack.getUserID(),feedBack.getContent());
                }
                break;
            }
            case "AdministratorGetFeedback":{
                System.out.println("AdministratorGetFeedback");
                for(FeedBack feedBack : feedBackList){
              	UserDAO.AdministratorGetFeedback(feedBack.getFeedBackID());
              }
                break;
            }
            case "replyFeedback":{
                System.out.println("replyFeedback");
                for(FeedBack feedBack : feedBackList){
              	UserDAO.replyFeedback(feedBack.getFeedBackID(),feedBack.getContent());
              }
                break;
            }
            case "CustomerOrDeliverGetFeedback":{
                System.out.println("CustomerOrDeliverGetFeedback");
                for(FeedBack feedBack : feedBackList){
              	UserDAO.CustomerOrDeliverGetFeedback(feedBack.getFeedBackID());
              }
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }

        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
        statusCodeResponse.setStatusCode(HttpServletResponse.SC_OK);
        statusCodeResponse.setTime(new Date().toString());
        String json = gson.toJson(statusCodeResponse);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
