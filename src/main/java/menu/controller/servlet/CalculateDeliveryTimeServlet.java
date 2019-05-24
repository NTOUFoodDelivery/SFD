package menu.controller.servlet;
import order.model.javabean.Order.CustomerBean;
import order.model.javabean.Order.DeliverBean;
import order.model.javabean.Order.OrderBean;

import java.util.ArrayList;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.controller.websocket.TestWebsocket;
import order.model.daoImpl.OrderDaoImpl;
import order.model.javabean.Order;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/CalculateDeliveryTimeServlet")
public class CalculateDeliveryTimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
//        User user = new User();
//        user.setUserID(123213L);
//        user.setUserName("sjfsfjsdf");
//        user.setAccount("fyhdejdk");
//        user.setPassword("987654567dscsd");
//        user.setEmail("asdasdas");
//        user.setPhoneNumber("asdasd");
//        user.setUserType("asdasd");
//        user.setUserStatus("ldsmlks");
//        Feedback feedbackRes = new Feedback();
//        feedbackRes.setFeedbackID(3456540L);
//        feedbackRes.setUserID(123213L);
//        feedbackRes.setContent("TEST Content");
//        feedbackRes.setBackContent("TEST BackContent");
//
//        UserDaoImpl userDao = new UserDaoImpl();
//        System.out.println(userDao.searchFeedback());

//        Rest rest = new Rest();
//        rest.setRestID(2561273L);
//        rest.setRestName("Test Rest");
//        rest.setRestAddress("Test Rest");
//        rest.setDescription("Test Rest");
//        rest.setRestPhoto("Test Rest");
//        RestDaoImpl restDao = new RestDaoImpl();
//        MenuDaoImpl menuDao = new MenuDaoImpl();
//        Menu menu = new Menu();
//        menu.setFoodID(876543L);
//        menu.setFoodName("Test Menu");
//        menu.setRestID(2561273L);
//        menu.setCost(678);
//        menu.setDescription("Test Menu");
//        menu.setImage("Test Menu");
//        System.out.println(menuDao.searchRestMenu(menu.getRestID()));




        Order order = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Order.class);
        OrderDaoImpl orderDao = new OrderDaoImpl();

        System.out.println(orderDao.ordertoHistory(order.getOrder().getOrderID()));

//        String json = gson.toJson(orderDao.searchIdleOrder());
//        TestWebsocket.testPushSession.getBasicRemote().sendText(gson.toJson(order));
////        String json = gson.toJson(HttpCommonAction.getRequestBody(request.getReader()));
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(order));
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
