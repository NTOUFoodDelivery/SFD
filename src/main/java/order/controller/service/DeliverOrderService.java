package order.controller.service;

import db.demo.dao.OrderDAO;

import java.util.List;

public class DeliverOrderService {

    // 拿到 外送員 當前訂單
    public static List getCurrentOrder(Long userID) {
        List currentOrder = null;

//        if (UserDAO.showUserType(userID).equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)) {
            // 查詢 外送員 當前訂單
            currentOrder = OrderDAO.searchDeliverOrder(userID);
//        }
        return currentOrder;
    }

    // 拿到 外送員 歷史訂單
    public static List getHistoryOrder(Long userID) {
        List historyOrder = null;

//        if(UserDAO.showUserType(userID).equals(MemberSetting.UserType.CUSTOMER_AND_DELIVER)) {
            // 查詢 外送員 歷史訂單
            historyOrder = OrderDAO.searchDeliverHistoryOrder(userID);
//        }
        return historyOrder;
    }
}
