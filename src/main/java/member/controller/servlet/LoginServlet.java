package member.controller.servlet;

import com.google.gson.Gson;
import member.controller.service.MemberService;
import member.model.javabean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        HttpSession session = request.getSession();

        MemberService memberService = new MemberService();
        String json = gson.toJson(memberService.login(session,account,password,userType));
        memberService = null;
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<String> info=new ArrayList<>(); // 錯誤訊息
        HttpSession httpSession = request.getSession();
        String json = null;
        if(httpSession.isNew()){
            info.add("reload");
//            httpSession.invalidate(); // 銷毀 session
            json = gson.toJson(info);

        }else{
            User user = (User)httpSession.getAttribute("User");
            if(user == null){
                info.add("reload");
//                httpSession.invalidate(); // 銷毀 session
                json = gson.toJson(info);
            }else {
                System.out.println(user.toString());
                json = gson.toJson(user);
            }
        }
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }


}
