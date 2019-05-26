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

    private void pushOrder() { // 去資料庫 修改訂單狀態成"已推播"


        // 推播 idle 訂單 給 空閒的 外送員
        synchronized (PushOrderWebSocket.sessions) {
            // Iterate over the connected sessions
            // and broadcast the received message
            if(PushOrderWebSocket.sessions.size() > 0){ // 如果有 外送員在線上
                List<User> idleDeliverList = userDao.searchIdleDeliverUser(); // 找 閒置的外送員
                List<Order> idleOrderList = orderDao.searchIdleOrder();// 找 閒置的訂單
                if (idleDeliverList.size() > 0 && idleOrderList.size() > 0) { // 如果有在線 閒置 的外送員的話 且有空閒訂單

                    // sort idle order value 大到小
                    Collections.sort(idleOrderList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            return order2.getOrder().getCastingPrio() - order1.getOrder().getCastingPrio();
                        }
                    });

                    for (Order order : idleOrderList) {
                        for (User deliver : idleDeliverList) {
                            if (deliver.getUserStatus().equals(UserStatus.DELIVER_ON)) {
                                Session idleDeliverSession = PushOrderWebSocket.sessions.get(deliver.getUserID());
                                HttpSession httpSession = (HttpSession)PushOrderWebSocket.httpSessions.get(idleDeliverSession);
                                User idleDeliver = (User)httpSession.getAttribute("User");

                                try {
                                    idleDeliverSession.getBasicRemote().sendText(gson.toJson(order));
                                    orderDao.modifyOrderStatus(order.getOrder().getOrderID(), OrderSetting.OrderStatus.PUSHING);
                                    userDao.modifyUserStatus(deliver.getUserID(), UserStatus.PUSHING.toString());
                                    order.getOrder().setOrderStatus(OrderSetting.OrderStatus.PUSHING);
                                    deliver.setUserStatus(UserStatus.PUSHING);
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

    public void run () {
        pushOrder();
    }
}
