package member.controller.service;

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.MemberCommand;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import order.controller.websocket.PushOrderWebSocket;
import util.HttpCommonAction;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberService {

    private UserDaoImpl userDao;
    // 管理員 更改 使用者 狀態 之類的
    public Object modifyMember(Long userID,MemberCommand memberCommand){
        userDao = new UserDaoImpl();
        Object result = null;
        if (memberCommand != null) { // 有這個指令
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
                    result = HttpCommonAction.generateStatusResponse(success,msg);
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
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
            }
        }else { // 沒有這個指令
            result = HttpCommonAction.generateStatusResponse(false,"Command not found");
        }
        userDao = null;
        return result;
    }

    public Object switchStatus(User currentUser,UserStatus userStatus){
        userDao = new UserDaoImpl();
        UserType currentUserType = currentUser.getUserType();
        Object result = null;
        String msg = "command :: " + userStatus + " "+ currentUserType.toString();
        if(currentUser != null) { // 有提供這個 狀態
            switch (currentUserType) {
                case Customer_and_Deliver: {
                    boolean success;
                    success = switchDeliverStatus(currentUser, userStatus);
                    if (success) {
                        msg = msg + " work!!";
                    } else {
                        msg = msg + " do not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
                case Customer: {
                    boolean success;
                    success = switchCustomerStatus(currentUser, userStatus);
                    if (success) {
                        msg = msg + " work!!";
                    } else {
                        msg = msg + " do not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
            }
        }
        userDao = null;
        return result;
    }

    // 轉換上下線 切換身份
    // 討論！！
    private boolean switchDeliverStatus(User currentUser,UserStatus userStatus){
        boolean success = false;
        if(!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
            // 如果目前有接單 或 目前有推播訂單，收回接單
            if(currentUser.getUserType().equals(UserStatus.DELIVER_BUSY)
                    || currentUser.getUserType().equals(UserStatus.PUSHING)){
                // ---------------------------收回接單-------
                currentUser.setUserStatus(UserStatus.DELIVER_ON); // 更新 session user 的狀態 為可推播
                success = userDao.modifyUserStatus(currentUser.getUserID(), UserStatus.DELIVER_ON.toString()); // 更新 資料庫 user 狀態
            }else{
                currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
                success = userDao.modifyUserStatus(currentUser.getUserID(), userStatus.toString()); // 更新 資料庫 user 狀態
            }
        }
        return success;
    }

    private boolean switchCustomerStatus(User currentUser,UserStatus userStatus){
        boolean success = false;
        if(!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
            currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
            success =  userDao.modifyUserStatus(currentUser.getUserID(), userStatus.toString()); // 更新 資料庫 user 狀態
        }
        return success;
    }

    // 登入
    public Object login(HttpSession session,String account,String password,String userType){
        List<String> info=new ArrayList<>(); // 錯誤訊息

        if(account==null||"".equals(account)){ // account不能空著喔
            info.add("account不能空著喔");
        }

        if(password==null||"".equals(password)){ // password不能空著喔
            info.add("password不能空著喔");
        }

        if(userType==null||"".equals(userType)){ // userType不能空著喔
            info.add("userType不能空著喔");
        }

        if(info.size()==0){
            try {
                UserDaoImpl userDao = new UserDaoImpl();
                User user = userDao.loginUser(account,password,userType);

                if(user != null){ // 有這個使用者
                    // ------判斷使用者登入狀況------- BEGIN
//                    if(PushOrderWebSocket.httpSessions.get(session) == null){ // 單個登入

                    session.setAttribute("login","login");
                    session.setAttribute("User_Id",user.getUserID()); // User_Id 保存進 session 全域變數中
                    session.setAttribute("User",user);
//                    session.setAttribute("Account",user.getAccount()); // Account 保存進 session 全域變數中
//                    session.setAttribute("Password",user.getPassword()); // Password 保存進 session 全域變數中
//                    session.setAttribute("User_Name",user.getUserName()); // User_Name 保存進 session 全域變數中
//                    session.setAttribute("Email",user.getEmail()); // Email 保存進 session 全域變數中
//                    session.setAttribute("Phone_Number",user.getPhoneNumber()); // Phone_Number 保存進 session 全域變數中
//                    session.setAttribute("Last_Address",user.getLastAddress()); // Last_Address 保存進 session 全域變數中
//                    session.setAttribute("User_Type",user.getUserType()); // User_Type 保存進 session 全域變數中
//                    session.setAttribute("User_Status",user.getUserStatus()); // User_Status 保存進 session 全域變數中

                    UserType currentUserType = user.getUserType();
                    switch (currentUserType){
                        case Customer:{
                            user.setUserStatus(UserStatus.CUSTOMER);
                            userDao.modifyUserStatus(user.getUserID(),UserStatus.CUSTOMER.toString());
                            info.add("CUSTOMER");
                            break;
                        }
                        case Customer_and_Deliver:{
                            PushOrderWebSocket.httpSessions.put(session,user);
                            user.setUserStatus(UserStatus.DELIVER_ON);
                            userDao.modifyUserStatus(user.getUserID(),UserStatus.DELIVER_ON.toString());
                            info.add("CUSTOMER_AND_DELIVER");
                            break;
                        }
                        case Administrator:{
                            user.setUserStatus(UserStatus.CUSTOMER);
                            userDao.modifyUserStatus(user.getUserID(),UserStatus.ADMINISTRATOR.toString());
                            info.add("ADMINISTRATOR");
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                    userDao = null;

                    List<HttpSession> keyList = (List<HttpSession>)getKey(PushOrderWebSocket.httpSessions,user);
                    List<HttpSession> oneUserSameKeyList = new ArrayList<>();
                    int count = 0 ;
                    for(HttpSession key : keyList){
                        if(!key.getId().equals(session.getId())){ // 一個使用者 但有多個session，要踢除其他的session
                            PushOrderWebSocket.httpSessions.remove(key);
                            key.invalidate(); // 銷毀 session
                        }else{ // 和目前登入 session 一樣的 放進List 裡
                            oneUserSameKeyList.add(key);
                        }
                        System.out.println(count+" :: "+key.getId());
                        count++;
                    }
//                    if(oneUserSameKeyList.size()>1){ // 如果 相同的 session 登入 多個 同一個使用者
//                        for(int i =0;i<oneUserSameKeyList.size()-1;i++){ // 剔除 其他session
//                            PushOrderWebSocket.httpSessions.remove(oneUserSameKeyList.get(i));
//                            oneUserSameKeyList.get(i).invalidate(); // 銷毀 session
//                        }
//                    }

                    // ------判斷使用者登入狀況------- END
                }else {
                    info.add("登入失敗，錯誤的帳號、密碼或userType");
                    info.add(0,"error");
//                    request.setAttribute("info", info); // 保存錯誤訊息
                    session.invalidate(); // 銷毀 session
                    System.out.println(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            info.add(0,"error");
//            request.setAttribute("info", info); // 保存錯誤訊息
            session.invalidate(); // 銷毀 session
            System.out.println(info);

        }
        return info;
    }
    // 註冊
    public  boolean signUp(User user){
        userDao = new UserDaoImpl();
        boolean success = false;
        if(userDao.searchUser(user.getUserID())){ // 已註冊
            String currentUserType =  userDao.showUserType(user.getUserID());
            if(currentUserType.equals(UserType.Customer.toString())
                    && user.getUserType().equals(UserType.Customer_and_Deliver.toString())){
                // 食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)
                userDao.modifyUserType(user.getUserID(),UserType.Customer_and_Deliver.toString());
                success = true;
            }
        }else{ // 未註冊
            // 目前直接註冊
            userDao.addUser(user);
            success = true;
        }
        userDao = null;
        return success;
    }

    public static Object getKey(Map map, Object value){
        List<Object> keyList = new ArrayList<>();
        for(Object key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList;
    }
}
