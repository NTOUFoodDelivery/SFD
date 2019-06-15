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

@WebServlet("/ShowCurrentOrderServlet")
public class ShowCurrentOrderServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    Long currentUserID = (Long) request.getSession().getAttribute("userID");
    User currentUser = (User) request.getSession()
        .getAttribute("user"); // current request User
    OrderService orderService = new OrderService();
    String json = gson.toJson(orderService.showCurrentOrder(currentUser));
    orderService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}
