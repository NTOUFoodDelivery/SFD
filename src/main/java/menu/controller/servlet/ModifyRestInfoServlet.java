package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.RestDAO;
import db.demo.javabean.Rest;
import menu.model.response.javabean.RestInfo;
import tool.HttpCommonAction;
import tool.javabean.CommonRequest;
import tool.javabean.StatusCodeResponse;

import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/ModifyRestInfoServlet")
public class ModifyRestInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),CommonRequest.class);
        String cmd = commonRequest.getQuery().getCommand(); // 新增 刪除 修改
        List<Object> resultBeans = commonRequest.getResult();
        List<Rest> restList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            restList.add(gson.fromJson(jsonObject.toString(),Rest.class)); // 餐廳資訊物件
        }

        switch (cmd){
            case "add":{
                System.out.println("add");
                for(Rest rest : restList){
                    RestDAO.addRest(rest);
                }
                break;
            }
            case "delete":{
                System.out.println("delete");
                for(Rest rest : restList){
                    System.out.println(rest.getRestID());
                    RestDAO.delRest(rest.getRestID());
                }
                break;
            }
            case "modify":{
                System.out.println("modify");
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
