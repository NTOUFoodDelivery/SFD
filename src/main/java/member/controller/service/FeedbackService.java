package member.controller.service;

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.Feedback;
import member.model.javabean.User;
import member.util.setting.UserType;

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
        if (user.getUserType().equals(UserType.Administrator.toString())) {
            feedbackList = userDao.searchFeedback();
        }
        else{
            feedbackList = userDao.searchFeedback(user.getUserID());
        }
        return feedbackList;
    }
}
