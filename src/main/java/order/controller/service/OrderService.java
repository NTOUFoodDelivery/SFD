package order.controller.service;

import db.demo.dao.OrderDAO;

import member.model.daoImpl.UserDaoImpl;
import member.util.setting.UserStatus;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;
import order.model.javabean.PushResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {

//    public static Map<Long, User> onlineDelivers =  new ConcurrentHashMap<Long,User>();
    public static Map<Long, Order> pushOrders =  new ConcurrentHashMap<Long,Order>();
    private UserDaoImpl userDao;

    // 產生訂單編號 將訂單存入資料庫
    public void addOrder(Order order){
//        order.setOrderID(JdbcUtils.generateID()); // 產生訂單 ID
        order.getOrder().setCastingPrio(0);
        order.getOrder().setOrderStatus(OrderSetting.OrderStatus.WAIT);
        OrderDAO.addOrder(order); // 訂單加入資料庫
//        pushOrders.put(order.getOrderID(),order);
    }

    // 外送員 食客
    //
    // 完成訂單
    public void confirmOrder(PushResult pushResult){
        Long orderID = pushResult.getOrderID();
        Long deliverID = pushResult.getDeliverID();
        String status = OrderDAO.getOrderStatus(orderID);
        switch (status){
            case OrderSetting.OrderStatus.DEALING:{
                OrderDAO.modifyOrderStatus(orderID,OrderSetting.OrderStatus.CONFIRMING);
                break;
            }
            case OrderSetting.OrderStatus.CONFIRMING:{
                OrderDAO.modifyOrderStatus(orderID,OrderSetting.OrderStatus.FINISH);
                break;
            }
            case OrderSetting.OrderStatus.FINISH:{
                OrderDAO.ordertoHistory(orderID);
                break;
            }
            default:{
                break;
            }
        }
    }

    // 處理訂單 接受與否 以及 外送員、訂單狀態 轉換
    public void dealOrder(PushResult pushResult){
        userDao = new UserDaoImpl();
        Long orderID = pushResult.getOrderID();
        Long deliverID = pushResult.getDeliverID();
        boolean isAccept = pushResult.isAccept();
        if (isAccept) { // 訂單被接受
            Order order = new Order();
            order.getDeliver().setUserID(deliverID);
            order.getOrder().setOrderID(orderID);
            OrderDAO.insertDtoOCD(order);
//            pushOrders.remove(orderID);
            // 去資料庫 修改訂單狀態成"被接受之類的"
            OrderDAO.modifyOrderStatus(orderID, OrderSetting.OrderStatus.DEALING);
            // 將 外送員狀態 改成"已接受訂單之類的"
            userDao.modifyUserStatus(deliverID, UserStatus.DELIVER_BUSY.toString());
        } else {  // 訂單取消
//            Order order = pushOrders.get(orderID);
//            order.setValue(order.getValue()+OrderSetting.ORDERSTAGE);
//            order.setOrderStatus(OrderSetting.OrderStatus.WAIT);
//            onlineDelivers.get(order.getDeliverID()).setUserStatus(MemberSetting.UserStatus.DELIVER_ON);
            OrderDAO.modifyOrderCastingPrio(orderID,OrderDAO.getOrderCastingPrio(orderID)+OrderSetting.ORDERSTAGE);
            // 去資料庫 修改訂單狀態成"未推播"
            OrderDAO.modifyOrderStatus(orderID,OrderSetting.OrderStatus.WAIT);
            // 將外送員狀態 改成"空閒之類的"
            userDao.modifyUserStatus(deliverID,UserStatus.DELIVER_ON.toString());
        }
        userDao = null;
    }
}
