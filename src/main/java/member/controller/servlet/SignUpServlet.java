package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import member.controller.service.MemberService;
import member.model.javabean.User;
import util.HttpCommonAction;
import util.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        User user = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),User.class);

        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
        MemberService memberService = new MemberService();
        if(memberService.signUp(user)){
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_ACCEPTED);
        }else {
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
        statusCodeResponse.setTime(new Date().toString());
        String json = gson.toJson(statusCodeResponse);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
