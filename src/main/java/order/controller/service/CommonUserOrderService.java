package order.controller.service;

import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import member.model.javabean.MemberSetting;
import order.model.javabean.Order;

import java.util.List;

public class CommonUserOrderService {

    // 拿到 食客 當前訂單
    public static List getCurrentOrder(Long userID) {
        List currentOrderList = null;
//        if (UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.CUSTOMER)) {
            // 查詢 食客 當前訂單
            currentOrderList = OrderDAO.searchEaterOrder(userID);
//        }
        return currentOrderList;
    }

    // 拿到 食客 歷史訂單
    public static List getHistoryOrder(Long userID) {
        List historyOrder = null;

//        if(UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.CUSTOMER)) {
            // 查詢 食客 歷史訂單
            historyOrder = OrderDAO.searchEaterHistoryOrder(userID);
//        }
        return historyOrder;
    }

}
