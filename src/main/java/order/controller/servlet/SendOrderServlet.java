package order.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.model.javabean.User;
import order.controller.service.OrderService;
import order.model.javabean.Order;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SendOrderServlet")
public class SendOrderServlet extends HttpServlet {
    Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        Order order = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Order.class); // 訂單
        User currentUser = (User)request.getSession().getAttribute("User"); // current request user
//        Long userID = Long.parseLong(request.getParameter("userID")); // test user
        Long userID = currentUser.getUserID();

        order.getCustomer().setUserID(userID);
        OrderService orderService = new OrderService();

        // 將訂單存入資料庫
        String json = gson.toJson(orderService.addOrder(order));
        orderService = null;

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        User currentUser = (User)request.getSession().getAttribute("User"); // current request user
        Long orderID = Long.parseLong(request.getParameter("orderID")); // order id

        OrderService orderService = new OrderService();
        String json = gson.toJson(orderService.confirmOrder(currentUser,orderID)); // 食客或外送員 確認訂單
        orderService = null;

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
