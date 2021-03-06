package member.controller.service;

import java.util.List;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.Feedback;
import member.model.javabean.User;
import member.util.setting.FeedbackCommand;
import member.util.setting.UserType;

import util.HttpCommonAction;


public class FeedbackService {

  private UserDaoImpl userDao;

  /**
   * <p>
   * 利用 回饋 指令 執行對應的事.
   * </p>
   *
   * @param currentUser 使用者 物件
   * @param feedbackCommand 回饋 指令
   * @param feedback 回饋 本體
   */
  public Object handleFeedback(User currentUser, FeedbackCommand feedbackCommand,
      Feedback feedback) {

    Object result = null;
    if (feedbackCommand != null) { // 有這個指令
      userDao = new UserDaoImpl();
      String msg = "command :: " + feedbackCommand.toString() + " Feedback";
      switch (feedbackCommand) {
        case CREATE:
        case REPLY: {
          boolean success = writeFeedback(feedback, currentUser);
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case SHOW: {
          List feedbackList = showFeedback(currentUser);
          if (feedbackList.size() > 0) {
            result = feedbackList;
          } else {
            result = HttpCommonAction.generateStatusResponse(false, msg + " size is 0");
          }
          break;
        }
        default: {
          break;
        }
      }
      userDao = null;
    } else { // 沒有這個指令
      result = HttpCommonAction.generateStatusResponse(false, "Command not found");
    }
    return result;
  }

  private boolean writeFeedback(Feedback feedback, User user) {
    if (user.getUserNow().equals(UserType.Administrator)) { // 管理員回訊息
      return userDao.replyFeedback(feedback.getFeedbackId(), feedback.getBackContent());
    } else { // 使用者 創 訊息
      return userDao.addFeedback(feedback.getFeedbackId(), user.getUserId(), feedback.getContent());
    }
  }

  private List<Feedback> showFeedback(User user) {
    if (user.getUserNow().equals(UserType.Administrator)) { // 管理員 拿到所有 訊息
      return userDao.searchFeedback();
    } else { // 使用者 拿到 他自己的 訊息
      return userDao.searchFeedback(user.getUserId());
    }
  }
}
