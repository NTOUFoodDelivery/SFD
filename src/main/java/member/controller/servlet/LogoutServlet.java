package member.controller.servlet;

import db.demo.dao.UserDAO;
import member.model.javabean.MemberSetting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Long userID = (Long)httpSession.getAttribute("User_Id");
        String userType = (String)httpSession.getAttribute("User_Type");
        httpSession.invalidate(); // 註銷 該 session
        UserDAO.modifyUserStatus(userID,MemberSetting.UserStatus.OFFLINE); // 設定狀態為 下線
        if(MemberSetting.UserType.ADMINISTRATOR.equals(userType)){
            response.sendRedirect("login.html"); // 去 管理者 登入介面
        }else{
            response.sendRedirect("login.html"); // 去 食客/外送員 登入介面
        }
    }
}
