package menu.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.RestDAO;
import db.demo.javabean.Rest;
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

@WebServlet(name = "ModifyMenuServlet")
public class ModifyMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),CommonRequest.class);
        String cmd = commonRequest.getQuery().getCommand(); // 新增 刪除 修改
        List<Object> resultBeans = commonRequest.getResult();
        List<Menu> MenuList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            MenuList.add(gson.fromJson(jsonObject.toString(),Menu.class)); // 餐廳資訊物件
        }

        switch (cmd){
            case "add":{
                System.out.println("add");
                for(Menu menu : MenuList){
                    System.out.println(menu.getFood_Id());
//                    RestDAO.addRestMenu(menu);
                }
                break;
            }
            case "delete":{
                System.out.println("delete");
                for(Menu menu : MenuList){
                    System.out.println(menu.getFood_Id());
//                    RestDAO.delRestMenu(menu.getFood_Id());
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
