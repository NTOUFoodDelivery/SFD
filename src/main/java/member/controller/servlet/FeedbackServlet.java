package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import member.controller.service.FeedbackService;
import member.model.javabean.Feedback;
import member.model.javabean.User;
import member.util.setting.FeedbackCommand;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
        User currentUser = (User)httpSession.getAttribute("User"); // current request user

        FeedbackService feedbackService = new FeedbackService();
        String json = null;
        FeedbackCommand feedbackCommand = FeedbackCommand.getFeedbackCommand(cmd);
        switch (feedbackCommand){
            case CREATE:{
                System.out.println("CREATE");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(feedbackService.createFeedback(feedback,currentUser)));
                break;
            }
            case REPLY:{
                System.out.println("REPLY");
                json = gson.toJson(HttpCommonAction.getStatusCodeResponse(feedbackService.replyFeedback(feedback)));
                break;
            }
            case SHOW:{
                System.out.println("SHOW");
                json = gson.toJson(feedbackService.showFeedback(currentUser));
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
