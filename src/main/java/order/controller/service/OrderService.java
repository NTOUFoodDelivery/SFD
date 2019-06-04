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

  /**
   * <p>將訂單存入資料庫.</p>
   *
   * @param order 訂單
   */
  public Object addOrder(Order order) {

    orderDao = new OrderDaoImpl();
    order.getOrder().setCastingPrio(0);
    order.getOrder().setOrderStatus(OrderSetting.OrderStatus.WAIT);
    boolean success = orderDao.addOrder(order); // 訂單加入資料庫
    orderDao = null;
    String msg;
    if (success) {
      msg = "訂單加入資料庫";
    } else {
      msg = "訂單加入資料庫 失敗！！";
    }
    return HttpCommonAction.generateStatusResponse(success, msg);
  }

  /**
   * <p>外送員 食客 執行 完成訂單 確認動作.</p>
   *
   * @param userID 使用者ID
   * @param orderID 訂單ID
   */
  public synchronized Object confirmOrder(Long userID, Long orderID) {
    userDao = new UserDaoImpl();
    User user = userDao.showUser(userID);
    UserType userType = user.getUserType();
    String msg = null;
    switch (userType) {
      case Customer_and_Deliver: {
        msg = deliverConfirmOrder(orderID);
        break;
      }
      case Customer: {
        msg = eaterConfirmOrder(orderID);
        break;
      }
      default: {
        break;
      }
    }
    userDao = null;
    return HttpCommonAction.generateStatusResponse(true, msg);
  }

  /**
   * <p>食客 按下確認.</p>
   *
   * @param orderID 訂單ID
   */
  private String eaterConfirmOrder(Long orderID) {
    orderDao = new OrderDaoImpl();
    String status = orderDao.getOrderStatus(orderID); // 訂單狀態
    String msg = null;
    switch (status) {
      case OrderSetting.OrderStatus.DEALING: { // 沒人 按下  確認
        orderDao.modifyOrderStatus(orderID, OrderSetting.OrderStatus.CUSTOMER_CONFIRMING);
        msg = "等待外送員按確認";
        break;
      }
      case OrderSetting.OrderStatus.CUSTOMER_CONFIRMING: { // 自己 已按過 確認
        msg = "自己已按過確認";
        break;
      }
      case OrderSetting.OrderStatus.DELIVER_CONFIRMING: { // 外送員 已按過 確認
        orderDao.ordertoHistory(orderID);
        msg = "雙方皆按下確認";
        break;
      }
      default: {
        break;
      }
    }
    orderDao = null;
    return msg;
  }

  /**
   * <p>外送員 按下確認.</p>
   *
   * @param orderID 訂單ID
   */
  private String deliverConfirmOrder(Long orderID) {
    orderDao = new OrderDaoImpl();
    String status = orderDao.getOrderStatus(orderID); // 訂單狀態
    String msg = null;
    switch (status) {
      case OrderSetting.OrderStatus.DEALING: { // 沒人 按下  確認
        orderDao.modifyOrderStatus(orderID, OrderSetting.OrderStatus.DELIVER_CONFIRMING);
        msg = "等待食客按確認";
        break;
      }
      case OrderSetting.OrderStatus.CUSTOMER_CONFIRMING: { // 食客 已按過 確認
        orderDao.ordertoHistory(orderID);
        msg = "雙方皆按下確認";
        break;
      }
      case OrderSetting.OrderStatus.DELIVER_CONFIRMING: { // 自己 已按過 確認
        msg = "自己已按過確認";
        break;
      }
      default: {
        break;
      }
    }
    orderDao = null;
    return msg;
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
  public Object showCurrentOrder(Long userID) {
    orderDao = new OrderDaoImpl();
    userDao = new UserDaoImpl();
    User currentUser = userDao.showUser(userID);
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
      case Administrator: {
        break;
      }
      default: {
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
  public Object showHistoryOrder(Long userID) {
    orderDao = new OrderDaoImpl();
    userDao = new UserDaoImpl();
    User currentUser = userDao.showUser(userID);
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
      case Administrator: {
        break;
      }
      default: {
        break;
      }
    }
    userDao = null;
    orderDao = null;
    return result;
  }

}
