package member.controller.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
  public boolean modifyMember(Long userID, MemberCommand memberCommand) {

    boolean result = false;
    if (memberCommand != null) { // 有這個指令
      userDao = new UserDaoImpl();
      if (memberCommand.equals(MemberCommand.USER_BAN)) {
        result = userDao.modifyUserType(userID, memberCommand.toString());
      } else if (memberCommand.equals(MemberCommand.DELETE)) {
        result = userDao.delUser(userID);
      }
      userDao = null;
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
  public Validate switchType(User currentUser, UserType userType) {

    Validate validate;
    if (userType != null) {
      userDao = new UserDaoImpl();
      UserType currentUserType = currentUser.getUserNow();
      UserType currentUserSignUpType = currentUser.getUserType();
      System.out.println(currentUser);
      switch (currentUserType) {
        case Deliver: {
          if (switchCustomerType(currentUser, userType)) {
            validate = Validate.SUCCESS;
          } else {
            validate = Validate.ERROR;
          }
          break;
        }
        case Customer: {
          if (currentUserSignUpType.equals(UserType.Customer_and_Deliver) && switchDeliverType(currentUser, userType)) {
            validate = Validate.SUCCESS;
          } else {
            validate = Validate.ERROR;
          }
          break;
        }
        default: {
          validate = Validate.ERROR;
          break;
        }
      }
      userDao = null;
    } else {
      validate = Validate.ERROR;
    }
    return validate;
  }

  /**
   * <p>
   * 轉換 使用者 狀態.
   * </p>
   *
   * @param currentUser 使用者 User 物件
   * @param userStatus 使用者 狀態
   */
  public Validate switchStatus(User currentUser, UserStatus userStatus) {

    Validate validate;
    if (userStatus != null) {
      userDao = new UserDaoImpl();
      UserType currentUserType = currentUser.getUserNow();
      switch (currentUserType) {
        case Deliver: {
          if (switchDeliverStatus(currentUser, userStatus)) {
            validate = Validate.SUCCESS;
          } else {
            validate = Validate.ERROR;
          }
          break;
        }
        case Customer: {
          if (switchCustomerStatus(currentUser, userStatus)) {
            validate = Validate.SUCCESS;
          } else {
            validate = Validate.ERROR;
          }
          break;
        }
        default: {
          validate = Validate.ERROR;
          break;
        }
      }
      userDao = null;
    } else {
      validate = Validate.ERROR;
    }
    return validate;
  }

  // 轉換上下線 切換身份
  // 討論！！
  private boolean switchDeliverStatus(User currentUser, UserStatus userStatus) {
    boolean success = false;
    UserStatus currentUserStatus = currentUser.getUserStatus();
    Long currentUserId = currentUser.getUserId();
    if (!currentUserStatus.equals(userStatus)) { // 不同狀態 才要變
      // 如果目前有接單 或 目前有推播訂單，收回接單
      if (currentUserStatus.equals(UserStatus.DELIVER_BUSY)
          || currentUserStatus.equals(UserStatus.PUSHING)) {
        // ---------------------------收回接單-------
        currentUser.setUserStatus(UserStatus.DELIVER_ON); // 更新 session / hash map user 的狀態 為可推播
        success = userDao.modifyUserStatus(currentUserId,
            UserStatus.DELIVER_ON.toString()); // 更新 資料庫 user 狀態
      } else {
        currentUser.setUserStatus(userStatus); // 更新 session / hash map  user 的狀態
        success = userDao
            .modifyUserStatus(currentUserId, userStatus.toString()); // 更新 資料庫 user 狀態
      }
    }
    return success;
  }

  private boolean switchCustomerStatus(User currentUser, UserStatus userStatus) {
    boolean success = false;
    if (!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
      currentUser.setUserStatus(userStatus); // 更新 session / hash map  user 的狀態
      success = userDao
          .modifyUserStatus(currentUser.getUserId(), userStatus.toString()); // 更新 資料庫 user 狀態
    }
    return success;
  }

  // 轉換上下線 切換身份
  // 討論！！
  private boolean switchDeliverType(User currentUser, UserType userType) {
    System.out.println(currentUser);
    boolean success = false;
    Long userId = currentUser.getUserId();
    if (!currentUser.getUserNow().equals(userType)) { // 不同身份 才要變
      if (userDao.modifyUserNow(userId, userType.toString()) && userDao.modifyUserStatus(userId,
          UserStatus.DELIVER_OFF.toString())) {
        currentUser.setUserNow(UserType.Deliver); // 更新 session user 的狀態 為可推播
        success = true;
      } else { // 還原
        userDao.modifyUserNow(userId, currentUser.getUserNow().toString());
        userDao.modifyUserStatus(userId,
            currentUser.getUserStatus().toString());
      }
    }
    return success;
  }

  private boolean switchCustomerType(User currentUser, UserType userType) {
    boolean success = false;
    Long userId = currentUser.getUserId();
    if (!currentUser.getUserNow().equals(userType)) { // 不同狀態 才要變
      if (userDao.modifyUserNow(userId, userType.toString()) && userDao.modifyUserStatus(userId,
          UserStatus.CUSTOMER.toString())) {
        currentUser.setUserNow(UserType.Customer); // 更新 session user 的狀態 為可推播
        success = true;
      } else { // 還原
        userDao.modifyUserNow(userId, currentUser.getUserNow().toString());
        userDao.modifyUserStatus(userId,
            currentUser.getUserStatus().toString());
      }
    }
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
              if (login(session, userHashMap, user)) { // 如果 資料庫 登入成功
                validate = Validate.SUCCESS;
              } else { // 如果 資料庫 登入失敗
                validate = Validate.ERROR;
              }
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

  private boolean login(HttpSession session, ConcurrentHashMap userHashMap, User user) {
    Long currentUserID = user.getUserId();
    UserType signUserType = user.getUserType();
    userDao = new UserDaoImpl();
    boolean result = userDao.modifyUserNow(user.getUserId(), signUserType.toString());
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
    return result;
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
  public Validate signUp(User currentUser) {

    Validate validate;
    if (checkSignUpKeyIn(currentUser)) {
      userDao = new UserDaoImpl();
      User user = userDao.searchUser(currentUser.getAccount());
      if (user != null) { // 已註冊
        UserType userType = user.getUserType();
        if (userType.equals(UserType.User_Ban)) { // 如果使用者 被BAN
          validate = Validate.USER_IS_BAN;
        } else { // 如果使用者 沒有 被BAN
          if (userType.equals(UserType.Customer) // 他是食客
              && currentUser.getUserType().equals(UserType.Customer_and_Deliver)) { // 但現在想 註冊 外送員
            if (userDao
                .modifyUserType(user.getUserId(),
                    UserType.Customer_and_Deliver.toString())) { // 資料庫 加入成功
              validate = Validate.SUCCESS;
            } else { // 資料庫 加入失敗
              validate = Validate.ERROR;
            }
          } else { // 他是 外送員 想註冊
            validate = Validate.ERROR;
          }
        }
      } else { // 未註冊
        // 註冊資料 都有填
        if (userDao.addUser(currentUser)) { // 資料庫 加入成功
          validate = Validate.SUCCESS;
        } else { // 資料庫 加入失敗
          validate = Validate.ERROR;
        }
      }
      userDao = null;
    } else {
      validate = Validate.KEY_IN_ERROR;
    }
    return validate;
  }

  private boolean checkSignUpKeyIn(User user) {
    if (user.getUserName().equals("") || user.getUserName() == null
        || user.getUserType() == null
        || user.getAccount().equals("") || user.getAccount() == null
        || user.getPassword().equals("") || user.getPassword() == null
        || user.getEmail().equals("") || user.getEmail() == null
        || user.getLastAddress().equals("") || user.getLastAddress() == null
        || user.getPhoneNumber().equals("") || user.getPhoneNumber() == null
        || user.getUserNow() == null) {
      return false;
    }
    return true;
  }

  /**
   * <p>
   * 登出.
   * </p>
   * <p>
   * 設計登出後，各使用者 的 訂單問題.
   * </p>
   *
   * @param request HttpServletRequest
   */
  public Validate logout(HttpServletRequest request) {

    ConcurrentHashMap userHashMap = (ConcurrentHashMap) request.getServletContext()
        .getAttribute("userHashMap");
    HttpSession httpSession = request.getSession();

    Validate validate = Validate.ERROR;
    User user = (User) httpSession.getAttribute("user"); // current request User
    if(user != null) {
      Long userID = user.getUserId();
      userDao = new UserDaoImpl();
      if (userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString())) { // 設定狀態為 下線
        userHashMap.remove(userID); // 移除 hash map User 物件
        httpSession.removeAttribute("login"); // 移除 session login
        httpSession.removeAttribute("userID"); // 移除 session userID
        httpSession.removeAttribute("user"); // 移除 session User 物件
        //httpSession.invalidate(); // 註銷 該 session
        validate = Validate.SUCCESS;
      }
    }
    userDao = null;
    return validate;
  }

  public List<User> showUsers() {
    userDao = new UserDaoImpl();
    List<User> result = userDao.searchUseraccount();
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
    Set set = map.entrySet();
    @SuppressWarnings("unchecked")
    Iterator<Entry<Object, Object>> iterator = set.iterator();
    List<Object> keyList = new ArrayList<>();
    while (iterator.hasNext()) {
      Map.Entry<Object, Object> entry = iterator.next();
      if (entry.getValue().equals(value)) {
        keyList.add(entry.getKey());
      }
    }
    return keyList;
  }
}
