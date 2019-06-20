package order.controller.service;

import java.util.ArrayList;
import java.util.List;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import order.model.daoImpl.OrderDaoImpl;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;
import order.model.javabean.PushResult;
import order.util.setting.OrderConfirm;

public class OrderService {

  private UserDaoImpl userDao;
  private OrderDaoImpl orderDao;

  /**
   * <p>將訂單存入資料庫.</p>
   *
   * @param order 訂單
   */
  public boolean addOrder(Order order) {

    orderDao = new OrderDaoImpl();
    order.getOrder().setCastingPrio(0); // 權重 歸零
    order.getOrder().setOrderStatus(OrderSetting.OrderStatus.WAIT); // 設定 訂單 初始狀態 --WAIT
    boolean result = false;
    if (orderDao.addOrder(order)) {
      result = true;
    }
    orderDao = null;
    return result;
  }

  /**
   * <p>外送員 食客 執行 完成訂單 確認動作.</p>
   *
   * @param userID 使用者ID
   * @param orderID 訂單ID
   */
  public synchronized OrderConfirm confirmOrder(User currentUser, Long orderID) {
    userDao = new UserDaoImpl();
    UserType userType = currentUser.getUserType();
    OrderConfirm result = OrderConfirm.ERROR;
    switch (userType) {
      case Customer_and_Deliver: {
        result = deliverConfirmOrder(orderID);
        break;
      }
      case Customer: {
        result = eaterConfirmOrder(orderID);
        break;
      }
      default: {
        break;
      }
    }
    userDao = null;
    return result;
  }

  /**
   * <p>食客 按下確認.</p>
   *
   * @param orderID 訂單ID
   */
  private OrderConfirm eaterConfirmOrder(Long orderID) {
    orderDao = new OrderDaoImpl();
    String status = orderDao.getOrderStatus(orderID); // 訂單狀態
    OrderConfirm orderConfirm = OrderConfirm.ERROR;
    switch (status) {
      case OrderSetting.OrderStatus.DEALING: { // 沒人 按下  確認
        if (orderDao.modifyOrderStatus(orderID, OrderSetting.OrderStatus.CUSTOMER_CONFIRMING)) {
          orderConfirm = OrderConfirm.FIRST_CONFIRM;
        }
        break;
      }
      case OrderSetting.OrderStatus.CUSTOMER_CONFIRMING: { // 自己 已按過 確認
        orderConfirm = OrderConfirm.REPEAT_CONFIRM;
        break;
      }
      case OrderSetting.OrderStatus.DELIVER_CONFIRMING: { // 外送員 已按過 確認
        if (orderDao.ordertoHistory(orderID)) {
          orderConfirm = OrderConfirm.FINISH;
        }
        break;
      }
      default: {
        break;
      }
    }
    orderDao = null;
    return orderConfirm;
  }

  /**
   * <p>外送員 按下確認.</p>
   *
   * @param orderID 訂單ID
   */
  private OrderConfirm deliverConfirmOrder(Long orderID) {
    orderDao = new OrderDaoImpl();
    String status = orderDao.getOrderStatus(orderID); // 訂單狀態
    OrderConfirm orderConfirm = OrderConfirm.ERROR;
    switch (status) {
      case OrderSetting.OrderStatus.DEALING: { // 沒人 按下  確認
        if (orderDao.modifyOrderStatus(orderID, OrderSetting.OrderStatus.DELIVER_CONFIRMING)) {
          orderConfirm = OrderConfirm.FIRST_CONFIRM;
        }
        break;
      }
      case OrderSetting.OrderStatus.CUSTOMER_CONFIRMING: { // 食客 已按過 確認
        if (orderDao.ordertoHistory(orderID)) {
          orderConfirm = OrderConfirm.FINISH;
        }
        break;
      }
      case OrderSetting.OrderStatus.DELIVER_CONFIRMING: { // 自己 已按過 確認
        orderConfirm = OrderConfirm.REPEAT_CONFIRM;
        break;
      }
      default: {
        break;
      }
    }
    orderDao = null;
    return orderConfirm;
  }

  /**
   * <p>處理訂單 接受與否 以及 外送員、訂單狀態 轉換.</p>
   *
   * @param pushResult 推播結果
   * @param deliverID 外送員ID
   */
  public void dealOrder(PushResult pushResult, Long deliverID) {
    userDao = new UserDaoImpl();
    orderDao = new OrderDaoImpl();
    Long orderID = pushResult.getOrderID();
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
    } else {  // 訂單取消
      // 更改 訂單 權重
      orderDao.modifyOrderCastingPrio(orderID,
          orderDao.getOrderCastingPrio(orderID) + OrderSetting.ORDERSTAGE);
      // 去資料庫 修改訂單狀態成"未推播"
      orderDao.modifyOrderStatus(orderID, OrderSetting.OrderStatus.WAIT);
      // 將外送員狀態 改成"空閒之類的"
      userDao.modifyUserStatus(deliverID, UserStatus.DELIVER_ON.toString());
    }
    userDao = null;
    orderDao = null;
  }


  /**
   * <p>使用者 得到 目前訂單.</p>
   *
   * @param userID 使用者ID
   */
  public List<Order> showCurrentOrder(User currentUser) {
    orderDao = new OrderDaoImpl();
    userDao = new UserDaoImpl();
    UserType userType = currentUser.getUserType();
    List<Order> result;
    switch (userType) {
      case Customer: {
        result = orderDao.searchEaterOrder(currentUser.getUserId());
        break;
      }
      case Customer_and_Deliver: {
        result = orderDao.searchDeliverOrder(currentUser.getUserId());
        break;
      }
      case Administrator: {
        result = orderDao.searchallOrder();
        break;
      }
      default: {
        result = new ArrayList<>();
        break;
      }
    }
    userDao = null;
    orderDao = null;
    return result;
  }

  /**
   * <p>使用者 得到 歷史訂單.</p>
   *
   * @param userID 使用者ID
   */
  public List<Order> showHistoryOrder(User currentUser) {
    orderDao = new OrderDaoImpl();
    userDao = new UserDaoImpl();
    UserType userType = currentUser.getUserNow();
    List<Order> result;
    switch (userType) {
      case Customer: {
        result = orderDao.searchEaterHistoryOrder(currentUser.getUserId());
        break;
      }
      case Deliver: {
        result = orderDao.searchDeliverHistoryOrder(currentUser.getUserId());
        break;
      }
      case Administrator: {
        result = orderDao.searchallHistoryOrder();
        break;
      }
      default: {
        result = new ArrayList<>();
        break;
      }
    }
    userDao = null;
    orderDao = null;
    return result;
  }

}
