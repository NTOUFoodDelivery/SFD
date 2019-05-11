package order.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import member.model.javabean.MemberSetting;
import tool.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/ShowCommonUserCurrentOrderServlet")
public class ShowCommonUserCurrentOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String parm = request.getParameter("userID");
        String json = null;
        int userID;
        try {
            userID = Integer.parseInt(parm);
            if(UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.CUSTOMER)) {
                // 查詢 食客 當前訂單
                OrderDAO.searchEaterOrder(userID);
            }
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            statusCodeResponse.setTime(new Date().toString());
            json = gson.toJson(statusCodeResponse);
        }

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
