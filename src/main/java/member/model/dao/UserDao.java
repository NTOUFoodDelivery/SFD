package member.model.dao;

import member.model.javabean.Feedback;
import member.model.javabean.User;

import java.util.List;

public interface UserDao {

    /**
     *
     * @description: 找尋 符合條件的 使用者
     * @param [account, password, userType]//
     * @return java.lang.Long
     */
    Long searchUserId(String account, String password, String userType);

    /**
    *
    * @description: 找尋 全部 使用者 ACCOUNT
    * @param [account, password, userType]
    * @return java.lang.String
    */
    List<User> searchUseraccount();
    /**
     *
     * @description:  找尋 符合條件的 使用者
     * @param [account, password, userType]
     * @return member.model.javabean.User
     */
    User searchUser(String account, String password, String userType);

    /**
     *
     * @description:  找尋 符合條件的 使用者
     * @param [account, password]
     * @return member.model.javabean.User
     */
    User searchUser(String account, String password);

    /**
     *
     * @description:  找尋 符合條件的 使用者
     * @param [account]
     * @return member.model.javabean.User
     */
    User searchUser(String account);

    /**
     *
     * @description: 得到使用者資訊
     * @author BerSerKer
     * @date 2019-05-28 20:01
     * @param [userID]
     * @return member.model.javabean.User
     */
    User showUser(Long userId);

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
    String showUserStatus(Long userId);

    /**
     *
     * @description: 查看 使用者目前Type
     * @param [userID]
     * @return java.lang.String
     */
    String showUserType(Long userId);
    
    /**
    *
    * @description: 查看 使用者目前Now
    * @param [userID]
    * @return java.lang.String
    */
   String showUserNow(Long userId);

    /**
     *
     * @description: 刪除 使用者
     * @param [userID]
     * @return boolean
     */
    boolean delUser(Long userId);

    /**
     *
     * @description: 更改 使用者的Status
     * @param [userID, userStatus]
     * @return boolean
     */
    boolean modifyUserStatus(Long userId, String userStatus);

    /**
     *
     * @description: 更改 使用者的Type
     * @param [userID, userType]
     * @return boolean
     */
    boolean modifyUserType(Long userId, String userType);

    /**
    *
    * @description: 更改 使用者的Type
    * @param [userID, userType]
    * @return boolean
    */
   boolean modifyUserNow(Long userId, String userNow);
    /**
     *
     * @description: 找尋 有無該使用者
     * @param [userID]
     * @return boolean
     */
    boolean searchUser(Long userId);

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
    boolean addFeedback(Long FeedbackId, Long userId, String Content);

    /**
     *
     * @description: 管理者 回覆客服
     * @param [FeedbackID, BackContent]
     * @return boolean
     */
    boolean replyFeedback(Long FeedbackId, String BackContent);
    // 在 service 層 須合併 ------------------------------------------------------ END

    // 在 service 層 須合併 ------------------------------------------------------ BEGIN
    /**
     *
     * @description: 食客/外送員 拿到feedback資訊
     * @param [userID]
     * @return java.util.List<member.model.javabean.Feedback>
     */
    List<Feedback> searchFeedback(Long userId);

    /**
     *
     * @description: 管理員 拿到feedback資訊
     * @param []
     * @return java.util.List<member.model.javabean.Feedback>
     */
    List<Feedback> searchFeedback();
    // 在 service 層 須合併 ------------------------------------------------------ END
}
