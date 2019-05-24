package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import member.controller.service.FeedbackService;
import member.model.javabean.Feedback;
import member.model.javabean.MemberSetting;
import member.model.javabean.User;
import util.HttpCommonAction;
import util.javabean.CommonRequest;
import util.javabean.StatusCodeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        String cmd = request.getParameter("cmd"); // command
        Feedback feedback = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Feedback.class); // feedback

        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("User"); // user

        FeedbackService feedbackService = new FeedbackService();
        String json = null;
        switch (cmd){
            case MemberSetting.Feedback.CREATE:{
                System.out.println("CREATE");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(feedbackService.createFeedback(feedback,user)));
                break;
            }
            case MemberSetting.Feedback.REPLY:{
                System.out.println("REPLY");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(feedbackService.replyFeedback(feedback)));
                break;
            }
            case MemberSetting.Feedback.SHOW:{
                System.out.println("SHOW");
                json = gson.toJson(feedbackService.showFeedback(user));
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
