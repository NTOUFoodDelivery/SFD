package order.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.controller.service.DeliverOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ShowDeliveryStaffCurrentOrderServlet")
public class ShowDeliveryStaffCurrentOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
//        Long userID = (Long) request.getSession().getAttribute("User_Id");
        Long userID = Long.parseLong(request.getParameter("userID"));
        // 拿 外送員 當前訂單
        String json = gson.toJson(DeliverOrderService.getCurrentOrder(userID));
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
