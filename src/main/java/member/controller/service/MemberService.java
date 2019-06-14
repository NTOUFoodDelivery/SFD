package member.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.MemberCommand;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import member.util.setting.Validate;
import util.HttpCommonAction;


public class MemberService {

  private UserDaoImpl userDao;

  /**
   * <p>
   * 管理員 更改 使用者 狀態 之類的.
   * </p>
   *
   * @param userID 使用者ID
   * @param memberCommand 指令
   */

  public Object modifyMember(Long userID, MemberCommand memberCommand) {

    Object result = null;
    if (memberCommand != null) { // 有這個指令
      userDao = new UserDaoImpl();
      String msg = "command :: " + memberCommand.toString() + " Member";
      switch (memberCommand) {
        case USER_BAN: { // ban 使用者
          boolean success;
          success = userDao.modifyUserStatus(userID, memberCommand.toString());
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case DELETE: { // 刪除 使用者
          boolean success;
          success = userDao.delUser(userID);
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
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

  /**
   * <p>
   * 轉換 使用者 身份.
   * </p>
   *
   * @param currentUser 使用者 User 物件
   * @param userType 使用者 身份
   */
  public Object switchType(User currentUser, UserType userType) {

    Object result = null;
    String msg = "command :: " + userType;
    boolean success;
    if (userType != null) {
      userDao = new UserDaoImpl();
      UserType currentUserType = currentUser.getUserNow();
      switch (currentUserType) {
        case Deliver: {
          success = switchDeliverType(currentUser, userType);
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case Customer: {
          success = switchCustomerType(currentUser, userType);
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        default: {
          break;
        }

      }
      userDao = null;
    } else {
      msg += "can not found!!";
      result = HttpCommonAction.generateStatusResponse(false, msg);
    }
    return result;
  }

  /**
   * <p>
   * 轉換 使用者 狀態.
   * </p>
   *
   * @param currentUser 使用者 User 物件
   * @param userStatus 使用者 狀態
   */
  public Object switchStatus(User currentUser, UserStatus userStatus) {

    Object result = null;
    String msg = "command :: " + userStatus;
    boolean success;
    if (userStatus != null) {
      userDao = new UserDaoImpl();
      UserType currentUserType = currentUser.getUserNow();
      switch (currentUserType) {
        case Deliver: {
          success = switchDeliverStatus(currentUser, userStatus);
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case Customer: {
          success = switchCustomerStatus(currentUser, userStatus);
          if (success) {
            msg = msg + " work!!";
          } else {
            msg = msg + " do not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        default: {
          break;
        }

      }
      userDao = null;
    } else {
      msg += "can not found!!";
      result = HttpCommonAction.generateStatusResponse(false, msg);
    }
    return result;
  }

  // 轉換上下線 切換身份
  // 討論！！
  private boolean switchDeliverStatus(User currentUser, UserStatus userStatus) {
    boolean success = false;
    if (!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
      // 如果目前有接單 或 目前有推播訂單，收回接單
      if (currentUser.getUserStatus().equals(UserStatus.DELIVER_BUSY)
          || currentUser.getUserStatus().equals(UserStatus.PUSHING)) {
        // ---------------------------收回接單-------
        currentUser.setUserStatus(UserStatus.DELIVER_ON); // 更新 session user 的狀態 為可推播
        success = userDao.modifyUserStatus(currentUser.getUserId(),
            UserStatus.DELIVER_ON.toString()); // 更新 資料庫 user 狀態
      } else {
        currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
        success = userDao
            .modifyUserStatus(currentUser.getUserId(), userStatus.toString()); // 更新 資料庫 user 狀態
      }
    }
    return success;
  }

  private boolean switchCustomerStatus(User currentUser, UserStatus userStatus) {
    boolean success = false;
    if (!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
      currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
      success = userDao
          .modifyUserStatus(currentUser.getUserId(), userStatus.toString()); // 更新 資料庫 user 狀態
    }
    return success;
  }

  // 轉換上下線 切換身份
  // 討論！！
  private boolean switchDeliverType(User currentUser, UserType userType) {
    boolean success = false;
    //if (!currentUser.getUserNow().equals(userType)) { // 不同身份 才要變
    //
    //    currentUser.setUserNow(UserType.Deliver); // 更新 session user 的狀態 為可推播
    //    success = userDao.modifyUserStatus(currentUser.getUserId(),
    //        UserStatus.DELIVER_ON.toString()); // 更新 資料庫 user 狀態
    //  } else {
    //    currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
    //    success = userDao
    //        .modifyUserStatus(currentUser.getUserId(), userStatus.toString()); // 更新 資料庫 user 狀態
    //  }
    //}
    return success;
  }

  private boolean switchCustomerType(User currentUser, UserType userType) {
    boolean success = false;
    //if (!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
    //  currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
    //  success = userDao
    //      .modifyUserStatus(currentUser.getUserId(), userStatus.toString()); // 更新 資料庫 user 狀態
    //}
    return success;
  }


  /**
   * <p>
   * 登入.
   * </p>
   *
   * @param request 請求中的 HttpServletRequest
   * @param account 使用者 key in 的 帳號
   * @param password 使用者 key in 的 密碼
   */
  public Validate login(HttpServletRequest request, String account, String password) {

    HttpSession session = request.getSession();
    ConcurrentHashMap userHashMap = (ConcurrentHashMap) request.getServletContext()
        .getAttribute("userHashMap");
    Validate validate;
    if (checkLoginKeyIn(account, password)) { // 使用者欄位 輸入正常
      if (!checkSessionIsLogin(session)) { // 這個session 沒登入過 任何 user
        User user = validate(account, password);
        if (user != null) { // 資料庫 有這個使用者
          if (user.getUserType().equals(UserType.User_Ban)) { // 如果他被 BAN 了
            validate = Validate.USER_IS_BAN;
          } else {
            if (!checkUserHashMapIsLogin(userHashMap,
                user.getUserId())) { // user hash map 沒有 這個 User
              login(session, userHashMap, user);
              validate = Validate.SUCCESS;
            } else { // user hash map 有 這個 User --重複登入
              validate = Validate.HASH_MAP_LOGIN_REPEAT;
            }
          }
        } else { // 資料庫 沒有這個使用者
          validate = Validate.USER_NOT_EXIST;
        }
        userDao = null;
      } else { // 這個session 已經有 user 登入了
        validate = Validate.SESSION_LOGIN_REPEAT;
      }
    } else { // 使用者欄位 輸入異常
      validate = Validate.KEY_IN_ERROR;
    }
    return validate;
  }

  private void login(HttpSession session, ConcurrentHashMap userHashMap, User user) {
    Long currentUserID = user.getUserId();
    UserType signUserType = user.getUserType();
    userDao = new UserDaoImpl();
    userDao.modifyUserNow(user.getUserId(), signUserType.toString());
    userDao = null;
    userHashMap.put(currentUserID, user.getUserId()); // User 存進 hash map
    session.setAttribute("user", user); // User 保存進 session
    session.setAttribute("userID", currentUserID); // User id 保存進 session
    session.setAttribute("login", "login"); // login 保存進 session
    if (signUserType.equals(UserType.Administrator)) {
      user.setUserNow(signUserType);
    } else {
      user.setUserNow(UserType.Customer);
    }
  }

  private boolean checkLoginKeyIn(String account, String password) {
    if (account == null || "".equals(account) || password == null || "".equals(password)) {
      return false;
    }
    return true;
  }

  private User validate(String account, String password) {
    userDao = new UserDaoImpl();
    User user = userDao.searchUser(account, password); // 檢查 資料庫 有無此 帳密 使用者 --------
    userDao = null;
    return user;
  }

  private boolean checkSessionIsLogin(HttpSession session) {
    if (session.getAttribute("login") == null) { // 這個session 沒登入過 任何 user
      return false;
    }
    return true;
  }

  private boolean checkUserHashMapIsLogin(ConcurrentHashMap userHashMap, Long userID) {
    if (userHashMap.get(userID) != null) { // 在 servlet hash map 有 User
      return true;
    }
    return false;
  }

  /**
   * <p>
   * 註冊.
   * </p>
   *
   * @param currentUser 使用者
   */
  public Object signUp(User currentUser) {
    userDao = new UserDaoImpl();
    Object result = null;
    boolean success;
    User user = userDao.searchUser(currentUser.getAccount());
    if (user.getUserType().equals(UserType.User_Ban)) {

    } else if (user != null) { // 已註冊
      UserType currentUserType = currentUser.getUserType();
      if (currentUserType.equals(UserType.Customer)
          && user.getUserType().equals(UserType.Customer_and_Deliver.toString())) {
        // 食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)
        success = userDao
            .modifyUserType(user.getUserId(), UserType.Customer_and_Deliver.toString());
        result = HttpCommonAction.generateStatusResponse(success, "食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)");
      }
    } else { // 未註冊
      // 註冊資料 都有填
      if (!currentUser.getUserName().equals("") && currentUser.getUserName() != null
          && currentUser.getUserType() != null
          && !currentUser.getAccount().equals("") && currentUser.getAccount() != null
          && !currentUser.getPassword().equals("") && currentUser.getPassword() != null
          && !currentUser.getEmail().equals("") && currentUser.getEmail() != null && !currentUser
          .getLastAddress().equals("") && currentUser.getLastAddress() != null
          && !currentUser.getPhoneNumber().equals("") && currentUser.getPhoneNumber() != null
          && currentUser.getUserNow() != null) {
        success = userDao.addUser(currentUser);
        result = HttpCommonAction.generateStatusResponse(success, "未註冊 的 目前直接註冊");
      }
    }
    userDao = null;
    return result;
  }

  /**
   * <p>
   * 登出.
   * </p>
   * <p>
   * 設計登出後，各使用者 的 訂單問題.
   * </p>
   *
   * @param userHashMap servlet context 裡面的 ConcurrentHashMap
   * @param httpSession 請求中的 session
   */
  public Object logout(ConcurrentHashMap userHashMap, HttpSession httpSession) {
    User user = (User) httpSession.getAttribute("user"); // current request User
    Long userID = user.getUserId();
    userDao = new UserDaoImpl();
    userHashMap.remove(userID);
    boolean success = userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString()); // 設定狀態為 下線
    httpSession.removeAttribute("login");
    httpSession.removeAttribute("userID");
    httpSession.removeAttribute("user");
    //httpSession.invalidate(); // 註銷 該 session
    Object result = HttpCommonAction.generateStatusResponse(success, "logout!!");
    userDao = null;
    return result;
  }

  public List<String> showUsers() {
    userDao = new UserDaoImpl();
    List<String> result = userDao.searchUseraccount();
    userDao = null;
    return result;
  }

  /**
   * <p>
   * 依 value 找map 中符合的 keys.
   * </p>
   *
   * @param map Map 物件
   * @param value map中的 value
   */
  public static Object getKey(Map map, Object value) {
    List<Object> keyList = new ArrayList<>();
    for (Object key : map.keySet()) {
      if (map.get(key).equals(value)) {
        keyList.add(key);
      }
    }
    return keyList;
  }
}
