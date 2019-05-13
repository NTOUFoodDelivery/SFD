package menu.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.RestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ShowRestInfoServlet")
public class ShowRestInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

//        JsonObject jsonObject = RestDAO.searchRestInfo();
        List<JsonObject> jsonObjects = OrderDAO.searchIdelOrder();
        for(JsonObject jsonObject : jsonObjects){
            System.out.println(gson.toJson(jsonObject));
        }
//        String json = gson.toJson(jsonObject);
//        PrintWriter out = response.getWriter();
//        out.print(json);
//        out.flush();
    }
}
