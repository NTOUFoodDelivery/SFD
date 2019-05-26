package order.controller.timerTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.model.daoImpl.UserDaoImpl;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import order.controller.websocket.PushOrderWebSocket;
import order.model.daoImpl.OrderDaoImpl;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;

public class PushOrderTask extends TimerTask {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    private UserDaoImpl userDao = new UserDaoImpl();
    private OrderDaoImpl orderDao = new OrderDaoImpl();

    // 推播 idle 訂單 給 idle 外送員
    private void pushOrder() {

        // lock websocket session
        // 推播訂單 給 這個時候之前的 空閒外送員
        synchronized (PushOrderWebSocket.sessions) {

            if(PushOrderWebSocket.sessions.size() > 0){ // 如果有 外送員在線上

                List<User> idleDeliverList = userDao.searchIdleDeliverUser(); // 到資料庫 找 閒置的外送員
                List<Order> idleOrderList = orderDao.searchIdleOrder();// 到資料庫  找 閒置的訂單

                if (idleDeliverList.size() > 0 && idleOrderList.size() > 0) { // 如果有 在線 閒置 的外送員的話 且有空閒訂單

                    // sort idle order 權重 大到小
                    Collections.sort(idleOrderList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            return order2.getOrder().getCastingPrio() - order1.getOrder().getCastingPrio();
                        }
                    });

                    for (Order order : idleOrderList) { // 尋訪每個 閒置 訂單
                        for (User deliver : idleDeliverList) { // 尋訪每個 閒置 外送員
                            if (deliver.getUserStatus().equals(UserStatus.DELIVER_ON)) {
                                Session idleDeliverSession = PushOrderWebSocket.sessions.get(deliver.getUserID()); // 拿 websocket session 物件 with user id
                                HttpSession httpSession = PushOrderWebSocket.httpSessions.get(idleDeliverSession); // 拿 http session 物件 with websocket session
                                if (httpSession != null) { // 如果該 空閒外送員 在推播的過程中 斷開連線 ( websocket on close() ) 則不推播給他
                                    User idleDeliver = (User) httpSession.getAttribute("User"); // 拿 http session 裡的 user 物件
                                    try {
                                        idleDeliverSession.getBasicRemote().sendText(gson.toJson(order)); // push order with websocket !!
                                        orderDao.modifyOrderStatus(order.getOrder().getOrderID(), OrderSetting.OrderStatus.PUSHING); // 改動 資料庫的 order status
                                        userDao.modifyUserStatus(deliver.getUserID(), UserStatus.PUSHING.toString()); // 改動 資料庫的 user status
                                        order.getOrder().setOrderStatus(OrderSetting.OrderStatus.PUSHING); // 普通物件裡的 oder
                                        deliver.setUserStatus(UserStatus.PUSHING); // 普通物件裡的 user
                                        idleDeliver.setUserStatus(UserStatus.PUSHING); // http session 裡的 user
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void run () {
        pushOrder();
    }
}
