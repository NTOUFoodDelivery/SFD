package member.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.demo.dao.UserDAO;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.Feedback;
import member.model.javabean.FeedbackReq;
import member.model.javabean.MemberSetting;
import member.model.javabean.User;
import util.javabean.CommonRequest;

import java.util.List;

public class FeedbackService {

    private UserDaoImpl userDao;

    public boolean createFeedback(Feedback feedback ,User user){
        userDao =  new UserDaoImpl();
        boolean success;
        success = userDao.addFeedback(feedback.getFeedbackID(),user.getUserID(),feedback.getContent());
        userDao = null;
        return success;
    }

    public boolean replyFeedback(Feedback feedback){
        userDao =  new UserDaoImpl();
        boolean success;
        success = userDao.replyFeedback(feedback.getFeedbackID(),feedback.getBackContent());
        userDao = null;
        return success;
    }

    public List<Feedback> showFeedback(User user){
        userDao =  new UserDaoImpl();
        List<Feedback> feedbackList;
        if (user.getUserType().equals(MemberSetting.UserType.ADMINISTRATOR)) {
            feedbackList = userDao.searchFeedback();
        }
        else{
            feedbackList = userDao.searchFeedback(user.getUserID());
        }
        return feedbackList;
    }
}
