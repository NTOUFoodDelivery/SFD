package member.controller.service;

import db.demo.dao.UserDAO;
import order.controller.websocket.PushOrderWebSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        String userType = request.getParameter("userType");

        List<String> info=new ArrayList<>(); // 錯誤訊息

        if(userID==null||"".equals(userID)){ // userID不能空著喔
            info.add("userID不能空著喔");
        }

        if(password==null||"".equals(password)){ // password不能空著喔
            info.add("password不能空著喔");
        }

        if(userType==null||"".equals(userType)){ // userType不能空著喔
            info.add("userType不能空著喔");
        }
//        request.getSession().setAttribute("userID",userID); // userID 保存進 session 全域變數中
//        request.getSession().setAttribute("userType",userType); // userType 保存進 session 全域變數中
//
//        request.getRequestDispatcher("chatDemo.jsp").forward(request,response); // 跳轉回登入頁面
        if(info.size()==0){
            try {

                if(UserDAO.login(userID,password,userType)){
                    HttpSession session = request.getSession();
                    session.setAttribute("userID",userID); // userID 保存進 session 全域變數中
                    session.setAttribute("userType",userType); // userType 保存進 session 全域變數中

                    if(userType.equals("deliver")){

                    }else if(userType.equals("eater"))
                    {

                    }

//                    response.sendRedirect("chatDemo.jsp");
                    request.getRequestDispatcher("chatDemo.jsp").forward(request,response); // 跳轉回登入頁面
                }else {
                    info.add("登入失敗，錯誤的ID、密碼或userType");
                    request.setAttribute("info", info); // 保存錯誤訊息
                    request.getRequestDispatcher("LoginDemo.jsp").forward(request,response); // 跳轉回登入頁面
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
