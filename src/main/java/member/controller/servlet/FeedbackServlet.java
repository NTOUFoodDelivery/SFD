package member.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.controller.service.FeedbackService;
import member.model.javabean.Feedback;
import member.model.javabean.User;
import member.util.setting.FeedbackCommand;
import member.util.setting.UserType;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        FeedbackCommand feedbackCommand = FeedbackCommand.getFeedbackCommand(request.getParameter("cmd")); // command
        Feedback feedback = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Feedback.class); // feedback
//        User currentUser = (User)request.getSession().getAttribute("User"); // current request user

        User currentUser = new User(); // test user
        currentUser.setUserID(1L); // test user
        currentUser.setUserType(UserType.Customer); // test user

        feedback.setUserID(currentUser.getUserID());
        FeedbackService feedbackService = new FeedbackService();
        String json = gson.toJson(feedbackService.handleFeedback(currentUser,feedbackCommand,feedback));
        feedbackService = null;
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
