package member.model.dao;

import member.model.javabean.Feedback;
import member.model.javabean.User;

import java.util.List;

public interface UserDao {

    /**
     *
     * @description: 登入
     * @param [account, password, userType]
     * @return java.lang.Long
     */
    Long loginID(String account, String password, String userType);

    /**
     *
     * @description: 登入
     * @param [account, password, userType]
     * @return member.model.javabean.User
     */
    User loginUser(String account, String password, String userType);

    /**
     *
     * @description: 註冊
     * @param [user]
     * @return boolean
     */
    boolean addUser(User user);

    /**
     *
     * @description: 使用者目前身份
     * @param [userID]
     * @return java.lang.String
     */
    String showUserStatus(Long userID);

    /**
     *
     * @description: 查看 使用者目前Type
     * @param [userID]
     * @return java.lang.String
     */
    String showUserType(Long userID);

    /**
     *
     * @description: 刪除 使用者
     * @param [userID]
     * @return boolean
     */
    boolean delUser(Long userID);

    /**
     *
     * @description: 更改 使用者的Status
     * @param [userID, userStatus]
     * @return boolean
     */
    boolean modifyUserStatus(Long userID, String userStatus);

    /**
     *
     * @description: 更改 使用者的Type
     * @param [userID, userType]
     * @return boolean
     */
    boolean modifyUserType(Long userID, String userType);

    /**
     *
     * @description: 找尋 有無該使用者
     * @param [userID]
     * @return boolean
     */
    boolean searchUser(Long userID);

    /**
     *
     * @description: 查詢 全部的空閒的 外送員
     * @param []
     * @return java.util.List<member.model.javabean.User>
     */
    List<User> searchIdleDeliverUser();

    // 在 service 層 須合併 ------------------------------------------------------ BEGIN
    /**
     *
     * @description: 食客/外送員 傳送客服 給 管理者
     * @param [FeedbackID, UserID, Content]
     * @return boolean
     */
    boolean addFeedback(Long FeedbackID, Long UserID, String Content);

    /**
     *
     * @description: 管理者 回覆客服
     * @param [FeedbackID, BackContent]
     * @return boolean
     */
    boolean replyFeedback(Long FeedbackID, String BackContent);
    // 在 service 層 須合併 ------------------------------------------------------ END

    // 在 service 層 須合併 ------------------------------------------------------ BEGIN
    /**
     *
     * @description: 食客/外送員 拿到feedback資訊
     * @param [userID]
     * @return java.util.List<member.model.javabean.Feedback>
     */
    List<Feedback> searchFeedback(Long userID);

    /**
     *
     * @description: 管理員 拿到feedback資訊
     * @param []
     * @return java.util.List<member.model.javabean.Feedback>
     */
    List<Feedback> searchFeedback();
    // 在 service 層 須合併 ------------------------------------------------------ END
}
