package member.controller.service;

import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.MemberCommand;
import member.util.setting.UserStatus;
import member.util.setting.UserType;

public class MemberService {

    private UserDaoImpl userDao;
    // 管理員 更改使用者狀態
    public boolean modifyMemberStatus(User user){
        userDao = new UserDaoImpl();

        boolean success = false;
        MemberCommand memberCommand = MemberCommand.getMemberCommand(user.getUserStatus()); // 要變的 狀態
        switch (memberCommand){
            case USER_BAN:{ // ban 使用者
                System.out.println("ban");
                userDao.modifyUserStatus(user.getUserID(), user.getUserStatus());
                success = true;
                break;
            }
            case DELETE:{ // 刪除 使用者
                System.out.println("delete");
                userDao.delUser(user.getUserID());
                success = true;
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
        userDao = null;
        return success;
    }

    // 轉換上下線 切換身份
    // 討論！！
    public boolean switchDeliverStatus(User currentUser,String userStatus){
        userDao = new UserDaoImpl();
        boolean success = false;
        UserStatus status = UserStatus.getUserStatus(userStatus); // 要變的 狀態
        if(status != null){ // 目前提供的狀態裡 有這個狀態
            if(!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
                // 如果目前有接單 或 目前有推播訂單，收回接單
                if(currentUser.getUserType().equals(UserStatus.DELIVER_BUSY.toString())
                        || currentUser.getUserType().equals(UserStatus.PUSHING.toString())){
                    // ---------------------------收回接單-------
                    currentUser.setUserStatus(UserStatus.DELIVER_ON.toString()); // 更新 session user 的狀態 為可推播
                    userDao.modifyUserStatus(currentUser.getUserID(), UserStatus.DELIVER_ON.toString()); // 更新 資料庫 user 狀態
                }else{
                    currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
                    userDao.modifyUserStatus(currentUser.getUserID(), userStatus); // 更新 資料庫 user 狀態
                }
            }
            success = true;
        }
        userDao = null;
        return success;
    }

    public boolean switchCustomerStatus(User currentUser,String userStatus){
        boolean success = false;
        userDao = new UserDaoImpl();
        UserStatus status = UserStatus.getUserStatus(userStatus); // 要變的 狀態
        if(status != null) { // 目前提供的狀態裡 有這個狀態
            if(!currentUser.getUserStatus().equals(userStatus)) { // 不同狀態 才要變
                currentUser.setUserStatus(userStatus); // 更新 session user 的狀態
                userDao.modifyUserStatus(currentUser.getUserID(), userStatus); // 更新 資料庫 user 狀態
            }
            success = true;
        }
        userDao = null;
        return success;
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
}
