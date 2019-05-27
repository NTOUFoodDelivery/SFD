package member.controller.servlet;

import com.google.gson.Gson;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("User"); // current request user
        Long userID = user.getUserID(); // current request user id
        UserType userType = user.getUserType(); // current request user type
        httpSession.invalidate(); // 註銷 該 session
        UserDaoImpl userDao = new UserDaoImpl();
        boolean success = userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString()); // 設定狀態為 下線
        String json = gson.toJson(HttpCommonAction.generateStatusResponse(success,"logout!!"));
        userDao = null;
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }
}
