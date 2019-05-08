package menu.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.RestDAO;
import menu.model.request.javabean.RestMenuReq;
import order.model.javabean.PushResult;
import tool.HttpCommonAction;
import tool.ResultSetToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ShowMenuServlet")
public class ShowMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        RestMenuReq restMenuReq = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),RestMenuReq.class);

        String restName = restMenuReq.getRestName();
        String restAddress = restMenuReq.getRestAddress();

        JsonObject jsonObject = RestDAO.searchRestMenu(restName,restAddress);

        String json = gson.toJson(jsonObject);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
