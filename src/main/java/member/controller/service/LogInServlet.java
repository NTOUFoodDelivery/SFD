package member.controller.service;

import db.demo.dao.UserDAO;
import db.demo.javabean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");

        List<String> info=new ArrayList<>(); // 錯誤訊息

        if(userID==null||"".equals(userID)){ // ID不能空著喔
            info.add("ID不能空著喔");
        }

        if(password==null||"".equals(password)){ // 密碼不能空著喔
            info.add("密碼不能空著喔");
        }
        if(info.size()==0){
            try {

                if(UserDAO.login(userID,password)){
                    info.add("登入成功，歡迎"+userID+"！");
                    response.sendRedirect("chatDemo.jsp"); // 跳轉頁面
                }else {
                    info.add("登入失敗，錯誤的ID和密碼");
                    request.setAttribute("info", info); // 保存錯誤訊息
                    request.getRequestDispatcher("LoginDemo.jsp").forward(request,response); // 跳轉回登入頁面
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
