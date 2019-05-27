package member.controller.service;

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.Feedback;
import member.model.javabean.User;
import member.util.setting.FeedbackCommand;
import member.util.setting.UserType;
import util.HttpCommonAction;

import java.util.List;

public class FeedbackService {

    private UserDaoImpl userDao;

    public Object handleFeedback(User currentUser ,FeedbackCommand feedbackCommand ,Feedback feedback){

        Object result = null;
        if(feedbackCommand != null) { // 有這個指令
            userDao = new UserDaoImpl();
            String msg = "command :: "+feedbackCommand.toString()+" Feedback";
            switch (feedbackCommand) {
                case CREATE:
                case REPLY: {
                    boolean success = writeFeedback(feedback,currentUser);
                    if(success){
                        msg = msg +" work!!";
                    }else{
                        msg = msg +" do not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
                case SHOW: {
                    List feedbackList = showFeedback(currentUser);
                    if(feedbackList.size() > 0){
                        result = feedbackList;
                    }else {
                        result = HttpCommonAction.generateStatusResponse(false,msg+" size is 0");
                    }
                    break;
                }
            }
            userDao = null;
        }else{ // 沒有這個指令
            result = HttpCommonAction.generateStatusResponse(false,"Command not found");
        }
        return result ;
    }

    private boolean writeFeedback(Feedback feedback ,User user){
        if(user.getUserType().equals(UserType.Administrator)) { // 管理員回訊息
            return userDao.replyFeedback(feedback.getFeedbackID(), feedback.getBackContent());
        }else { // 使用者 創 訊息
            return userDao.addFeedback(feedback.getFeedbackID(), user.getUserID(), feedback.getContent());
        }
    }

    private List<Feedback> showFeedback(User user){
        if (user.getUserType().equals(UserType.Administrator)) { // 管理員 拿到所有 訊息
            return userDao.searchFeedback();
        }
        else{ // 使用者 拿到 他自己的 訊息
            return userDao.searchFeedback(user.getUserID());
        }
    }
}
