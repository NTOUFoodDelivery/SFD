package order.controller.service;


import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import order.model.daoImpl.OrderDaoImpl;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;
import order.model.javabean.PushResult;
import util.HttpCommonAction;

public class OrderService {

    private UserDaoImpl userDao;
    private OrderDaoImpl orderDao;

    //  將訂單存入資料庫
    public Object addOrder(Order order){
        boolean success;
        orderDao = new OrderDaoImpl();
        order.getOrder().setCastingPrio(0);
        order.getOrder().setOrderStatus(OrderSetting.OrderStatus.WAIT);
        success = orderDao.addOrder(order); // 訂單加入資料庫
        orderDao = null;
        String msg;
        if(success){
            msg = "訂單加入資料庫";
        }else {
            msg = "訂單加入資料庫 失敗！！";
        }
        return  HttpCommonAction.generateStatusResponse(success,msg);
    }

    // 外送員 食客
    // 完成訂單
     public synchronized Object confirmOrder(User user, Long orderID){
        UserType userType = user.getUserType();
        String msg = null;
        switch (userType){
            case Customer_and_Deliver:{
                msg = deliverConfirmOrder(orderID);
                break;
            }
            case Customer:{
                msg = eaterConfirmOrder(orderID);
                break;
            }
        }
        return HttpCommonAction.generateStatusResponse(true,msg);
    }

    // 食客 按下確認
    private String eaterConfirmOrder(Long orderID){
        orderDao = new OrderDaoImpl();
        String status = orderDao.getOrderStatus(orderID); // 訂單狀態
        String msg = null;
        switch (status){
            case OrderSetting.OrderStatus.DEALING:{ // 沒人 按下  確認
                orderDao.modifyOrderStatus(orderID,OrderSetting.OrderStatus.CUSTOMER_CONFIRMING);
                msg = "等待外送員按確認";
                break;
            }
            case OrderSetting.OrderStatus.CUSTOMER_CONFIRMING:{ // 自己 已按過 確認
                msg = "自己已按過確認";
                break;
            }
            case OrderSetting.OrderStatus.DELIVER_CONFIRMING:{ // 外送員 已按過 確認
                orderDao.ordertoHistory(orderID);
                msg = "雙方皆按下確認";
                break;
            }
        }
        orderDao = null;
        return msg;
    }

    // 外送員 按下確認
    private String deliverConfirmOrder(Long orderID){
        orderDao = new OrderDaoImpl();
        String status = orderDao.getOrderStatus(orderID); // 訂單狀態
        String msg = null;
        switch (status){
            case OrderSetting.OrderStatus.DEALING:{ // 沒人 按下  確認
                orderDao.modifyOrderStatus(orderID,OrderSetting.OrderStatus.DELIVER_CONFIRMING);
                msg = "等待食客按確認";
                break;
            }
            case OrderSetting.OrderStatus.CUSTOMER_CONFIRMING:{ // 食客 已按過 確認
                orderDao.ordertoHistory(orderID);
                msg = "雙方皆按下確認";
                break;
            }
            case OrderSetting.OrderStatus.DELIVER_CONFIRMING:{ // 自己 已按過 確認
                msg = "自己已按過確認";
                break;
            }
        }
        orderDao = null;
        return msg;
    }

    // 處理訂單 接受與否 以及 外送員、訂單狀態 轉換
    public void dealOrder(PushResult pushResult,User deliver){
        userDao = new UserDaoImpl();
        orderDao = new OrderDaoImpl();
        Long orderID = pushResult.getOrderID();
        Long deliverID = deliver.getUserID();
        pushResult.setDeliverID(deliverID);
        boolean isAccept = pushResult.isAccept();
        if (isAccept) { // 訂單被接受
            Order order = new Order();
            order.getDeliver().setUserID(deliverID);
            order.getOrder().setOrderID(orderID);
            orderDao.insertDtoOCD(order); // 在資料庫 綁定 食客 、 訂單 與 外送員
            order = null;
            // 去資料庫 修改訂單狀態成"被接受之類的"
            orderDao.modifyOrderStatus(orderID, OrderSetting.OrderStatus.DEALING);
            // 將 外送員狀態 改成"已接受訂單之類的"
            userDao.modifyUserStatus(deliverID, UserStatus.DELIVER_BUSY.toString());
            deliver.setUserStatus(UserStatus.DELIVER_BUSY);
        } else {  // 訂單取消
            // 更改 訂單 權重
            orderDao.modifyOrderCastingPrio(orderID,orderDao.getOrderCastingPrio(orderID)+OrderSetting.ORDERSTAGE);
            // 去資料庫 修改訂單狀態成"未推播"
            orderDao.modifyOrderStatus(orderID,OrderSetting.OrderStatus.WAIT);
            // 將外送員狀態 改成"空閒之類的"
            userDao.modifyUserStatus(deliverID,UserStatus.DELIVER_ON.toString());
            deliver.setUserStatus(UserStatus.DELIVER_ON);
        }
        userDao = null;
        orderDao = null;
    }


    public Object showCurrentOrder(User currentUser){
        orderDao = new OrderDaoImpl();
        UserType userType = currentUser.getUserType();
        Object result = null;
        switch (userType) {
            case Customer: {
                result = orderDao.searchEaterOrder(currentUser.getUserID());
                break;
            }
            case Customer_and_Deliver: {
                result = orderDao.searchDeliverOrder(currentUser.getUserID());
                break;
            }
            case Administrator:{

                break;
            }
        }
        orderDao = null;
        return result;
    }

    public Object showHistoryOrder(User currentUser){
        orderDao = new OrderDaoImpl();
        UserType userType = currentUser.getUserType();
        Object result = null;
        switch (userType) {
            case Customer: {
                result = orderDao.searchEaterHistoryOrder(currentUser.getUserID());
                break;
            }
            case Customer_and_Deliver: {
                result = orderDao.searchDeliverHistoryOrder(currentUser.getUserID());
                break;
            }
            case Administrator:{

                break;
            }
        }
        orderDao = null;
        return result;
    }

}
