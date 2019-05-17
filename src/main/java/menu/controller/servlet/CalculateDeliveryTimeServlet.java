package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.RestDAO;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import order.controller.websocket.TestWebsocket;
import order.model.javabean.Order;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CalculateDeliveryTimeServlet")
public class CalculateDeliveryTimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        Order order = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Order.class);
        TestWebsocket.testPushSession.getBasicRemote().sendText(gson.toJson(order));
//        String json = gson.toJson(HttpCommonAction.getRequestBody(request.getReader()));
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(order));
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
