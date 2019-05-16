package member.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import member.model.javabean.MemberSetting;
import member.model.javabean.UserStatus;
import order.controller.websocket.PushOrderWebSocket;
import tool.javabean.CommonRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class MemberService {

    // 管理員 更改使用者狀態
    public static void modifyMemberStatus(CommonRequest commonRequest){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String cmd = commonRequest.getQuery().getCommand();
        List<Object> resultBeans = commonRequest.getResult();
        List<User> userList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            userList.add(gson.fromJson(jsonObject.toString(),User.class)); // 使用者物件
        }

        switch (cmd){
            case MemberSetting.Command.USER_BAN:{ // ban 使用者
                System.out.println("ban");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.DELETE:{ // 刪除 使用者
                System.out.println("delete");
                for(User user : userList){
                    UserDAO.delUser(user.getUserID());
                }
                break;
            }
            case MemberSetting.Command.DELIVER_ON:{ // 更改 使用者 為 上線有空
                System.out.println("deliverOn");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.CUSTOMER:{ // 更改 使用者 為 一般使用者
                System.out.println("customer");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.DELIVER_BUSY:{ // 更改 使用者 為 一般使用者
                System.out.println("deliverBusy");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(),cmd);
                }
                break;
            }
            case MemberSetting.Command.DELIVER_OFF:{ // 更改 使用者 為 離線
                System.out.println("deliverOff");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.OFFLINE:{ // 更改 使用者 為 登出
                System.out.println("offline");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.ADMINISTRATOR:{ // 更改 使用者 為 管理員
                System.out.println("admin");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            case MemberSetting.Command.PUSHING:{ // 更改 使用者 為 受到推播訂單
                System.out.println("pushing");
                for(User user : userList){
                    UserDAO.modifyUserStatus(user.getUserID(), cmd);
                }
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
    }

    // 轉換上下線 切換身份
    // 討論！！
    public static void switchStatus(UserStatus userStatus){

        String status = userStatus.getUserStatus();

        switch (status){
            case MemberSetting.UserStatus.DELIVER_ON:{
                String identity = UserDAO.showUserIdentity(userStatus.getUserID());
                if(identity.equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)){
                    UserDAO.modifyUserStatus(userStatus.getUserID(),status);
                }
                break;
            }
            case MemberSetting.UserStatus.DELIVER_OFF:{
                String identity = UserDAO.showUserIdentity(userStatus.getUserID());
                if(identity.equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)){
                    UserDAO.modifyUserStatus(userStatus.getUserID(),status);
                }
                break;
            }
            case MemberSetting.UserStatus.OFFLINE:{
                // 如果目前有接單，收回
                String currentUserStatus = UserDAO.showUserType(userStatus.getUserID());
                if(currentUserStatus.equals(MemberSetting.UserStatus.DELIVER_BUSY)){}
                // 如果目前有推播訂單，收回
//                String currentUserStatus = UserDAO.showUserType(userStatus.getUser_Id());
//                if(currentUserStatus.equals(MemberSetting.UserStatus.DELIVER_ON)){}
                UserDAO.modifyUserStatus(userStatus.getUserID(),status);
                break;
            }
            case MemberSetting.UserStatus.CUSTOMER:{
                UserDAO.modifyUserStatus(userStatus.getUserID(),status);
                break;
            }
            default:{
                break;
            }
        }
    }

    // 註冊
    public static boolean signUp(User user){
        boolean success = false;
        if(UserDAO.searchUser(user.getUserID())){ // 已註冊
            String currentUserType =  UserDAO.showUserType(user.getUserID());
            if(currentUserType.equals(MemberSetting.UserType.CUSTOMER) && user.getUserType().equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)){
                // 食客想變 外送員(成為外送員會 包含食客及外送員兩種身份)
                UserDAO.modifyUserType(user.getUserID(),MemberSetting.UserType.CUSTOMER_AND_DELIVER);
                success = true;
            }
        }else{ // 未註冊
            // 目前直接註冊
            UserDAO.addUser(user);
            success = true;
        }
        return success;
    }
}
