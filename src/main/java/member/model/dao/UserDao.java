package member.model.dao;

import member.model.javabean.User;
import member.model.javabean.FeedbackRes;

import java.util.List;

public interface UserDao {
    // 登入
    Long loginID(String account, String password, String userType);
    // 登入
    User loginUser(String account, String password, String userType);
    // 註冊
    boolean addUser(User user);
    // 查看 使用者目前身份
    String showUserIdentity(Long userID);
    // 查看 使用者目前Type
    String showUserType(Long userID);
    // 刪除 使用者
    boolean delUser(Long userID);
    // 更改 使用者的Status
    boolean modifyUserStatus(Long userID, String userStatus);
    // 更改 使用者的Type
    boolean modifyUserType(Long userID, String userType);
    // 找尋 有無該使用者
    boolean searchUser(Long userID);
    // 查詢 全部的空閒的 外送員
    List<User> searchIdleDeliverUser();
    // 可能須合併 ------------------------------------------------------ BEGIN
    // 食客/外送員 傳送客服 給 管理者
    boolean addFeedback(Long FeedbackID, Long UserID, String Content);
    // 管理者 回覆客服
    boolean replyFeedback(Long FeedbackID, String BackContent);
    // 可能須合併 ------------------------------------------------------ END
    // 使用者 拿到feedback資訊
    List<FeedbackRes> searchFeedback(Long userID);
}
