package member.controller.servlet;

import com.google.gson.Gson;

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
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
import java.util.Map;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        HttpSession session = request.getSession();
        if(info.size()==0){
            try {
                UserDaoImpl userDao = new UserDaoImpl();
                User user = userDao.loginUser(account,password,userType);

                if(user != null){
                    // ------判斷使用者登入狀況------- BEGIN
//                    if(PushOrderWebSocket.httpSessions.get(session) == null){ // 單個登入
                    PushOrderWebSocket.httpSessions.put(session,user);
                    session.setAttribute("login","login");
                    session.setAttribute("User_Id",user.getUserID()); // User_Id 保存進 session 全域變數中
                    session.setAttribute("User",user);
//                    session.setAttribute("Account",user.getAccount()); // Account 保存進 session 全域變數中
//                    session.setAttribute("Password",user.getPassword()); // Password 保存進 session 全域變數中
//                    session.setAttribute("User_Name",user.getUserName()); // User_Name 保存進 session 全域變數中
//                    session.setAttribute("Email",user.getEmail()); // Email 保存進 session 全域變數中
//                    session.setAttribute("Phone_Number",user.getPhoneNumber()); // Phone_Number 保存進 session 全域變數中
//                    session.setAttribute("Last_Address",user.getLastAddress()); // Last_Address 保存進 session 全域變數中
//                    session.setAttribute("User_Type",user.getUserType()); // User_Type 保存進 session 全域變數中
//                    session.setAttribute("User_Status",user.getUserStatus()); // User_Status 保存進 session 全域變數中

                    UserType currentUserType = UserType.getUserType(user.getUserType());

                    switch (currentUserType){
                        case Customer:{
                            user.setUserType(UserStatus.CUSTOMER.toString());
                            userDao.modifyUserStatus(user.getUserID(),UserStatus.CUSTOMER.toString());
//                            response.sendRedirect("web/index_eater.html");
                            Gson gson = new Gson();
                            info.add("CUSTOMER");
                            response.getWriter().println(gson.toJson(info));
                            break;
                        }
                        case Customer_and_Deliver:{
                            user.setUserType(UserStatus.DELIVER_ON.toString());
                            userDao.modifyUserStatus(user.getUserID(),UserStatus.DELIVER_ON.toString());
//                            response.sendRedirect("web/index_deliver.html");
                            Gson gson = new Gson();
                            info.add("CUSTOMER_AND_DELIVER");
                            response.getWriter().println(gson.toJson(info));
                            break;
                        }
                        case Administrator:{
                            user.setUserType(UserStatus.CUSTOMER.toString());
                            userDao.modifyUserStatus(user.getUserID(),UserStatus.ADMINISTRATOR.toString());
//                            response.sendRedirect("web/index_admin.html");
                            Gson gson = new Gson();
                            info.add("ADMINISTRATOR");
                            response.getWriter().println(gson.toJson(info));
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                    userDao = null;

//                    response.sendRedirect("chatDemo.jsp");
//                    request.getRequestDispatcher("chatDemo.jsp").forward(request,response); // 跳轉回登入頁面
//                    }

                    List<HttpSession> keyList = (List<HttpSession>)getKey(PushOrderWebSocket.httpSessions,user);
                    List<HttpSession> oneUserSameKeyList = new ArrayList<>();
                    int count = 0 ;
                    for(HttpSession key : keyList){
                        if(!key.getId().equals(session.getId())){ // 一個使用者 但有多個session，要踢除其他的session
                            PushOrderWebSocket.httpSessions.remove(key);
                            key.invalidate(); // 銷毀 session
                        }else{ // 和目前登入 session 一樣的 放進List 裡
                            oneUserSameKeyList.add(key);
                        }
                        System.out.println(count+" :: "+key.getId());
                        count++;
                    }
//                    if(oneUserSameKeyList.size()>1){ // 如果 相同的 session 登入 多個 同一個使用者
//                        for(int i =0;i<oneUserSameKeyList.size()-1;i++){ // 剔除 其他session
//                            PushOrderWebSocket.httpSessions.remove(oneUserSameKeyList.get(i));
//                            oneUserSameKeyList.get(i).invalidate(); // 銷毀 session
//                        }
//                    }

                    // ------判斷使用者登入狀況------- END
                }else {
                    info.add("登入失敗，錯誤的帳號、密碼或userType");
                    info.add(0,"error");
//                    request.setAttribute("info", info); // 保存錯誤訊息
                    session.invalidate(); // 銷毀 session
                    Gson gson = new Gson();
                    response.getWriter().println(gson.toJson(info));
                    System.out.println(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            info.add(0,"error");
//            request.setAttribute("info", info); // 保存錯誤訊息
            Gson gson = new Gson();
            session.invalidate(); // 銷毀 session
            response.getWriter().println(gson.toJson(info));
            System.out.println(info);


        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        List<String> info=new ArrayList<>(); // 錯誤訊息
        Gson gson = new Gson();
        HttpSession httpSession = request.getSession();
        if(httpSession.isNew()){
            info.add("reload");
//            httpSession.invalidate(); // 銷毀 session
            response.getWriter().println(gson.toJson(info));
        }else{
            User user = (User)httpSession.getAttribute("User");
            System.out.println(user.toString());
            response.getWriter().println(gson.toJson(user));
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
