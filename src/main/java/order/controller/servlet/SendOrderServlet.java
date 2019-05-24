package order.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.controller.service.OrderService;
import order.model.javabean.Order;
import util.HttpCommonAction;
import util.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/SendOrderServlet")
public class SendOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        Order order = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Order.class);

//        HttpSession session = request.getSession();
//        order.setCustomerID((Long)session.getAttribute("User_Id"));
        Long userID = Long.parseLong(request.getParameter("userID"));
        order.getCustomer().setUserID(userID);
        // 產生訂單編號 將訂單存入資料庫
//        OrderService.addOrder(order);

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
