package order.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import member.model.javabean.User;
import order.controller.service.OrderService;
import order.model.javabean.Order;
import order.util.setting.OrderConfirm;
import util.HttpCommonAction;
import util.javabean.StatusCodeResponse;

@WebServlet("/SendOrderServlet")
public class SendOrderServlet extends HttpServlet {

  // 新訂單
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    Order order = gson
        .fromJson(HttpCommonAction.getRequestBody(request.getReader()), Order.class); // 訂單
    Long currentUserID = (Long) request.getSession()
        .getAttribute("userID"); // current request user id

    order.getCustomer().setUserID(currentUserID); // 調整 order 的 user id
    OrderService orderService = new OrderService();
    // 將訂單存入資料庫
    boolean result = orderService.addOrder(order);
    String json = gson.toJson(HttpCommonAction.generateStatusResponse(result, ""));
    orderService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }

  // 雙方 確認 訂單
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    Long orderID = Long.parseLong(request.getParameter("orderID")); // order id
    User currentUser = (User) request.getSession()
        .getAttribute("user"); // current request User

    OrderService orderService = new OrderService();
    OrderConfirm result = orderService.confirmOrder(currentUser, orderID);
    StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
    statusCodeResponse.setResult(result.toString());
    statusCodeResponse.setTime(new Date().toString());
    String json = gson.toJson(statusCodeResponse); // 食客或外送員 確認訂單
    orderService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}
