package order.controller.service;

import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import member.model.javabean.MemberSetting;

public class DeliverOrderService {

    // 拿到 外送員 當前訂單
    public static JsonObject getCurrentOrder(Long userID) {
        JsonObject currentOrder = null;

        if (UserDAO.showUserType(userID).equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)) {
            // 查詢 外送員 當前訂單
            currentOrder = OrderDAO.searchDeliverOrder(userID);
        }
        return currentOrder;
    }

    // 拿到 外送員 歷史訂單
    public static JsonObject getHistoryOrder(Long userID) {
        JsonObject historyOrder = null;

        if(UserDAO.showUserType(userID).equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)) {
            // 查詢 外送員 歷史訂單
            historyOrder = OrderDAO.searchDeliverHistoryOrder(userID);
        }
        return historyOrder;
    }
}
