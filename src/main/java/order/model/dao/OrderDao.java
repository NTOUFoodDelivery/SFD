package order.model.dao;

import order.model.javabean.Order;

import java.util.ArrayList;
import java.util.List;

public interface OrderDao {
    // 把訂單加入資料庫
    boolean addOrder(Order order);
    // 刪除 訂單------------------- 修改
    boolean delOrder(Long orderID);
    // 找出 未推播 訂單 並回傳詳細資訊
    ArrayList<Order> searchIdleOrder();
    // 查詢 食客 當前訂單
    ArrayList<Order> searchEaterOrder(Long userID);
    // 傳出 ORDER 資料到HISTORY!!!
    boolean ordertoHistory(Long OrderID);
    //訂單建立後新增DELIVER_ID到OCD
    boolean insertDtoOCD(Order order);
    // 查詢 食客 歷史訂單
    List<Order> searchEaterHistoryOrder(Long userID);
    // 查詢 外送員 當前訂單
    List searchDeliverOrder(Long userID);
    // 查詢 外送員 歷史訂單
    List searchDeliverHistoryOrder(Long deliverId);
    // 更改 orderStatus
    boolean modifyOrderStatus(Long orderID, String OrderStatus);
    // 拿到 orderStatus
    String getOrderStatus(Long orderID);
    // 更改 訂單權重
    boolean modifyOrderCastingPrio(Long orderID,int castingPrio);
    // 拿到 訂單權重
    int getOrderCastingPrio(Long orderID);
    //拿到所有歷史訂單
    List<Order> searchallHistoryOrder();
    //拿到所有訂單
    List<Order> searchallOrder();
}
