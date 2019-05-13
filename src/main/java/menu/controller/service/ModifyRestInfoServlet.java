package menu.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import menu.model.response.javabean.RestInfo;
import tool.HttpCommonAction;
import tool.javabean.CommonRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.List;

@WebServlet("/ModifyRestInfoServlet")
public class ModifyRestInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),CommonRequest.class);
        String cmd = commonRequest.getQuery().getCommand();
        List<Object> resultBeans = commonRequest.getResult();
        switch (cmd){
            case "add":{
                System.out.println("add");
                System.out.println(resultBeans.get(0));
//                for(Object object : resultBeans){
//                    RestInfo restInfo = (RestInfo)object;
//                    System.out.println(restInfo.getDescription());
//                }
                break;
            }
            case "delete":{
                System.out.println("delete");
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
//        String json = gson.toJson(jsonObject);
//        PrintWriter out = response.getWriter();
//        out.print(json);
//        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
