package member.controller.service;

import db.demo.dao.UserDAO;
import order.controller.websocket.PushOrderWebSocket;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        List<String> info=new ArrayList<>(); // 錯誤訊息

        if(account==null||"".equals(account)){ // account不能空著喔
            info.add("account不能空著喔");
        }

        if(password==null||"".equals(password)){ // password不能空著喔
            info.add("password不能空著喔");
        }

        if(userType==null||"".equals(userType)){ // userType不能空著喔
            info.add("userType不能空著喔");
        }
//        request.getSession().setAttribute("account",account); // account 保存進 session 全域變數中
//        request.getSession().setAttribute("userType",userType); // userType 保存進 session 全域變數中
//
//        request.getRequestDispatcher("chatDemo.jsp").forward(request,response); // 跳轉回登入頁面

        HttpSession session = request.getSession();
        if(info.size()==0){
            try {
                int userID = UserDAO.login(account,password,userType);
                if(userID != -1){
                    // ------判斷使用者登入狀況------- BEGIN
//                    if(PushOrderWebSocket.httpSessions.get(session) == null){ // 單個登入
                        PushOrderWebSocket.httpSessions.put(session,userID);
                        session.setAttribute("login","login");
                        session.setAttribute("account",account); // account 保存進 session 全域變數中
                        session.setAttribute("userType",userType); // userType 保存進 session 全域變數中

                        response.sendRedirect("chatDemo.jsp");
//                    request.getRequestDispatcher("chatDemo.jsp").forward(request,response); // 跳轉回登入頁面
//                    }

                    List<HttpSession> keyList = (List<HttpSession>)getKey(PushOrderWebSocket.httpSessions,userID);
                    int i = 0 ;
                    for(HttpSession key : keyList){
                        if(!key.getId().equals(session.getId())){ // 登入其他瀏覽器的同一個帳號
                            PushOrderWebSocket.httpSessions.remove(key);
                            key.invalidate(); // 銷毀 session
                        }
                        System.out.println(i+" :: "+key.getId());
                        i++;
                    }
                    // ------判斷使用者登入狀況------- END
                }else {
                    info.add("登入失敗，錯誤的帳號、密碼或userType");
                    request.setAttribute("info", info); // 保存錯誤訊息
                    session.invalidate(); // 銷毀 session
//                    response.sendRedirect("LoginDemo.jsp");
                    request.getRequestDispatcher("LoginDemo.jsp").forward(request,response); // 跳轉回登入頁面
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            request.setAttribute("info", info); // 保存錯誤訊息
            session.invalidate(); // 銷毀 session
//            response.sendRedirect("LoginDemo.jsp");
            request.getRequestDispatcher("LoginDemo.jsp").forward(request,response); // 跳轉回登入頁面
        }
    }

    public static Object getKey(Map map, Object value){
        List<Object> keyList = new ArrayList<>();
        for(Object key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList;
    }

}
