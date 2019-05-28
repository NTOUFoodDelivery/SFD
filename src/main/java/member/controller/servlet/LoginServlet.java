package member.controller.servlet;

import com.google.gson.Gson;
import member.controller.service.MemberService;
import member.model.daoImpl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    public static Map<String,Long> eaterSession = new ConcurrentHashMap<>();
    public static Map<String,Long> deliverSession = new ConcurrentHashMap<>();
    public static Map<String,Long> adminSession = new ConcurrentHashMap<>();

    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        HttpSession session = request.getSession();
        String json;

        MemberService memberService = new MemberService();
        json = gson.toJson(memberService.login(session, account, password, userType));
        memberService.showOnlineDeliver();
        memberService.showOnlineEater();
        memberService = null;

        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpSession httpSession = request.getSession();
        Long userID = (Long)httpSession.getAttribute("userID");
        UserDaoImpl userDao = new UserDaoImpl();
        String json = gson.toJson(userDao.showUser(userID));
        userDao = null;
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }


}
