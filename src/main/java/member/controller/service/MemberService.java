package member.controller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import member.controller.servlet.LoginServlet;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.MemberCommand;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
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
          System.out.println("ban");
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
          System.out.println("delete");
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
   * 轉換 使用者 狀態.
   * </p>
   *
   * @param userID 使用者ID
   * @param userStatus 使用者 狀態
   */
  public Object switchStatus(Long userID, UserStatus userStatus) {

    Object result = null;
    String msg = "command :: " + userStatus;
    boolean success = false;
    if (userStatus != null) {
      userDao = new UserDaoImpl();
      User currentUser = userDao.showUser(userID);
      UserType currentUserType = currentUser.getUserType();
      switch (currentUserType) {
        case Customer_and_Deliver: {
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
      if (currentUser.getUserType().equals(UserStatus.DELIVER_BUSY)
          || currentUser.getUserType().equals(UserStatus.PUSHING)) {
        // ---------------------------收回接單-------
        currentUser.setUserStatus(UserStatus.DELIVER_ON); // 更新 session user 的狀態 為可推播
        success = userDao.modifyUserStatus(currentUser.getUserID(),
            UserStatus.DELIVER_ON.toString()); // 更新 資料庫 user 狀態
      } else {
        currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
        success = userDao
            .modifyUserStatus(currentUser.getUserID(), userStatus.toString()); // 更新 資料庫 user 狀態
      }
    }
    return success;
  }

  private boolean switchCustomerStatus(User currentUser, UserStatus userStatus) {
    boolean success = false;
    if (!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
      currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
      success = userDao
          .modifyUserStatus(currentUser.getUserID(), userStatus.toString()); // 更新 資料庫 user 狀態
    }
    return success;
  }

  /**
   * <p>
   * 登入.
   * </p>
   *
   * @param session 請求中的 session
   * @param account 使用者 key in 的 帳號
   * @param password 使用者 key in 的 密碼
   * @param userType 使用者 key in 的 使用者 種類
   */

  public Object login(ConcurrentHashMap userHashMap,HttpSession session, String account, String password, String userType) {

    Object result;
    if (session.getAttribute("login") == null) { // 這個session 沒登入過 任何 user
      List<String> info = new ArrayList<>(); // 錯誤訊息

      if (account == null || "".equals(account)) { // account不能空著喔
        info.add("account不能空著喔");
      }

      if (password == null || "".equals(password)) { // password不能空著喔
        info.add("password不能空著喔");
      }

      if (userType == null || "".equals(userType)) { // userType不能空著喔
        info.add("userType不能空著喔");
      }

      if (info.size() == 0) { // 使用者欄位 輸入正常
        userDao = new UserDaoImpl();
        User user = userDao.loginUser(account, password, userType); // 檢查 資料庫 有無此 帳密 使用者 --------
        if (user != null) { // 資料庫 有這個使用者
          session.setAttribute("login", "login"); // login 保存進 session
          UserType currentUserType = user.getUserType();
          switch (currentUserType) { // 設定 登入 初始狀態
            case Customer: {
              userHashMap.put(session.getId(), user.getUserID()); // 食客 session map
              session.setAttribute("userID", user.getUserID()); // User id 保存進 session
              info.add("CUSTOMER");
              break;
            }
            case Customer_and_Deliver: {
              userHashMap.put(session.getId(), user.getUserID()); // 外送員 session map
              session.setAttribute("userID", user.getUserID()); // User id 保存進 session
              info.add("CUSTOMER_AND_DELIVER");
              break;
            }
            case Administrator: {
              userHashMap.put(session.getId(), user.getUserID()); // 管理員 session map
              session.setAttribute("userID", user.getUserID()); // User id 保存進 session
              info.add("ADMINISTRATOR");
              break;
            }
            default: {
              break;
            }
          }
        } else {
          info.add("登入失敗，錯誤的帳號、密碼或userType");
          info.add(0, "error");
        }
        userDao = null;
      } else { // 使用者欄位 輸入異常
        info.add(0, "error");
      }
      result = info;
    } else { // 這個session 已經有 user 登入了 ---------- 紀錄用 應該不會發生  ---- 因為 在 LoginFilter 會擋掉
      result = HttpCommonAction.generateStatusResponse(false, "這個session 已經有 user 登入了");
    }
    return result;
  }

  /**
   * <p>
   * 註冊.
   * </p>
   *
   * @param user 使用者
   */
  public Object signUp(User user) {
    userDao = new UserDaoImpl();
    Object result = null;
    boolean success;
    if (userDao.searchUser(user.getUserID())) { // 已註冊
      String currentUserType = userDao.showUserType(user.getUserID());
      if (currentUserType.equals(UserType.Customer.toString())
          && user.getUserType().equals(UserType.Customer_and_Deliver.toString())) {
        // 食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)
        success = userDao
            .modifyUserType(user.getUserID(), UserType.Customer_and_Deliver.toString());
        result = HttpCommonAction.generateStatusResponse(success, "食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)");
      }
    } else { // 未註冊
      // 目前直接註冊
      success = userDao.addUser(user);
      result = HttpCommonAction.generateStatusResponse(success, "未註冊 的 目前直接註冊");
    }
    userDao = null;
    return result;
  }

  /**
   * <p>
   * 登出.
   * </p>
   *
   * @param httpSession 請求中的 session
   */
  public Object logout(ConcurrentHashMap userHashMap,HttpSession httpSession) {
    Long userID = (Long) httpSession.getAttribute("userID"); // current request user id
    userDao = new UserDaoImpl();
    User user = userDao.showUser(userID);
    boolean success = false;
    switch (user.getUserType()) {
      case Administrator: {
        userHashMap.remove(httpSession.getId());
        List<String> adminSessionList = (List<String>) MemberService
            .getKey(userHashMap, userID);
        if (adminSessionList.size() == 0) { // 沒有其他 session 登這個 user 了
          success = userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString()); // 設定狀態為 下線
        }
        break;
      }
      case Customer_and_Deliver: {
        userHashMap.remove(httpSession.getId());
        List<String> deliverSessionList = (List<String>) MemberService
            .getKey(userHashMap, userID);
        if (deliverSessionList.size() == 0) { // 沒有其他 session 登這個 user 了
          success = userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString()); // 設定狀態為 下線
        }
        break;
      }
      case Customer: {
        userHashMap.remove(httpSession.getId());
        List<String> eaterSessionList = (List<String>) MemberService
            .getKey(userHashMap, userID);
        if (eaterSessionList.size() == 0) { // 沒有其他 session 登這個 user 了
          success = userDao.modifyUserStatus(userID, UserStatus.OFFLINE.toString()); // 設定狀態為 下線
        }
        break;
      }
      default: {
        break;
      }
    }
    httpSession.removeAttribute("login");
    httpSession.removeAttribute("userID");
    //httpSession.invalidate(); // 註銷 該 session
    Object result = HttpCommonAction.generateStatusResponse(success, "logout!!");
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
