package order.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.model.javabean.User;
import member.util.setting.UserType;
import order.controller.service.CommonUserOrderService;
import order.controller.service.DeliverOrderService;
import order.controller.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ShowCurrentOrderServlet")
public class ShowCurrentOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

//        User currentUser = (User)request.getSession().getAttribute("User");

        User currentUser = new User(); // test user
        currentUser.setUserType(UserType.Customer); // test user type
        currentUser.setUserID(1L); // test user id

        OrderService orderService = new OrderService();
        String json = gson.toJson(orderService.showCurrentOrder(currentUser));
        orderService = null;
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
