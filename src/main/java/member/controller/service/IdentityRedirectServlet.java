package member.controller.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/IdentityRedirectServlet")
public class IdentityRedirectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Access-Control-Allow-Origin", "*");

        HttpSession session = request.getSession();

        if(session.isNew()){
            System.out.println("A new session");
        }else{
            System.out.println("IdentityRedirectServlet request :: "+session.getId());
            System.out.println("Welcome back ! ");
        }
    }
}
