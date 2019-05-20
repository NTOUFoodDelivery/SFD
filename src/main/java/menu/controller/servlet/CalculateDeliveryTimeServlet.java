package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.FeedbackRes;
import member.model.javabean.MemberSetting;
import member.model.javabean.User;
import order.controller.websocket.TestWebsocket;
import order.model.javabean.Order;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
//        FeedbackRes feedbackRes = new FeedbackRes();
//        feedbackRes.setFeedbackID(3456540L);
//        feedbackRes.setUserID(123213L);
//        feedbackRes.setContent("TEST Content");
//        feedbackRes.setBackContent("TEST BackContent");
//
//        UserDaoImpl userDao = new UserDaoImpl();
//        System.out.println(userDao.searchFeedback());

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
