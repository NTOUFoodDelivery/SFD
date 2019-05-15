package order.controller.service;

import db.demo.connect.JdbcUtils;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import member.model.javabean.MemberSetting;
import order.controller.websocket.PushOrderWebSocket;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;
import order.model.javabean.PushResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {

//    public static Map<Long, User> onlineDelivers =  new ConcurrentHashMap<Long,User>();
//    public static Map<Long, Order> pushOrders =  new ConcurrentHashMap<Long,Order>();

    // 產生訂單編號 將訂單存入資料庫
    public static void addOrder(Order order){
        order.setOrderID(JdbcUtils.generateID()); // 產生訂單 ID
        OrderDAO.addOrder(order); // 訂單加入資料庫
//        pushOrders.put(order.getOrderID(),order);
    }

    // 雙方 完成訂單
    public static void confirmOrder(){

    }

    // 處理訂單 接受與否 以及 外送員、訂單狀態 轉換
    public static void dealOrder(PushResult pushResult){
        Long orderID = pushResult.getOrderID();
        Long deliverID = pushResult.getDeliverID();
        boolean isAccept = pushResult.isAccept();
        if (isAccept) { // 訂單被接受
//            pushOrders.remove(orderID);
            // 去資料庫 修改訂單狀態成"被接受之類的"
            OrderDAO.modifyOrderStauts(orderID, OrderSetting.OrderStatus.DEALING);
            // 將 外送員狀態 改成"已接受訂單之類的"
            UserDAO.modifyUserStatus(deliverID, MemberSetting.UserStatus.DELIVER_BUSY);
        } else {  // 訂單取消
//            Order order = pushOrders.get(orderID);
//            order.setValue(order.getValue()+OrderSetting.ORDERSTAGE);
//            order.setOrderStatus(OrderSetting.OrderStatus.WAIT);
//            onlineDelivers.get(order.getDeliverID()).setUserStatus(MemberSetting.UserStatus.DELIVER_ON);
            // 去資料庫 修改訂單狀態成"未推播"
            OrderDAO.modifyOrderStauts(orderID,OrderSetting.OrderStatus.WAIT);
            // 將外送員狀態 改成"空閒之類的"
            UserDAO.modifyUserStatus(deliverID,MemberSetting.UserStatus.DELIVER_ON);
        }
    }
}
