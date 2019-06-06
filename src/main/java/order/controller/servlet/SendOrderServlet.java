package order.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.javabean.User;
import order.controller.service.OrderService;
import order.model.javabean.Order;
import util.HttpCommonAction;

@WebServlet("/SendOrderServlet")
public class SendOrderServlet extends HttpServlet {

  Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");

    Order order = gson
        .fromJson(HttpCommonAction.getRequestBody(request.getReader()), Order.class); // 訂單
    Long currentUserID = (Long) request.getSession()
        .getAttribute("userID"); // current request user id

    //Long currentUserID = Long.parseLong(request
    //    .getParameter("userID")); // current request user id

    order.getCustomer().setUserID(currentUserID); // 調整 order 的 user id
    OrderService orderService = new OrderService();
    // 將訂單存入資料庫
    String json = gson.toJson(orderService.addOrder(order));
    orderService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }


  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");

    Long currentUserID = (Long) request.getSession()
        .getAttribute("userID"); // current request user id
    // 應該不會在這邊產生亂碼吧
    Long orderID = Long.parseLong(request.getParameter("orderID")); // order id
    User currentUser = (User) request.getSession()
        .getAttribute("user"); // current request User
    OrderService orderService = new OrderService();
    String json = gson.toJson(orderService.confirmOrder(currentUser, orderID)); // 食客或外送員 確認訂單
    orderService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}
