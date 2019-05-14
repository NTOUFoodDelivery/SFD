package order.controller.service;

import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import member.model.javabean.MemberSetting;

public class CommonUserOrderService {

    // 拿到 食客 當前訂單
    public static JsonObject getCurrentOrder(Long userID) {
        JsonObject currentOrder = null;

        if (UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.CUSTOMER)) {
            // 查詢 食客 當前訂單
            currentOrder = OrderDAO.searchEaterOrder(userID);
        }
        return currentOrder;
    }

    // 拿到 食客 歷史訂單
    public static JsonObject getHistoryOrder(Long userID) {
        JsonObject historyOrder = null;

        if(UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.CUSTOMER)) {
            // 查詢 食客 歷史訂單
            historyOrder = OrderDAO.searchEaterHistoryOrder(userID);
        }
        return historyOrder;
    }

}
