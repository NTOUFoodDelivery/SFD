package order.controller.timerTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import member.util.setting.UserStatus;
import order.controller.websocket.PushOrderWebSocket;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class PushOrderTask extends TimerTask {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    private void pushOrder() { // 去資料庫 修改訂單狀態成"已推播"


        // 推播 idle 訂單 給 空閒的 外送員
        synchronized (PushOrderWebSocket.sessions) {
            // Iterate over the connected sessions
            // and broadcast the received message
            if(PushOrderWebSocket.sessions.size() > 0){ // 如果有 外送員在線上
                List<User> idleDeliverList = UserDAO.searchIdleDeliverUser(); // 找 閒置的外送員
                List<Order> idleOrderList = OrderDAO.searchIdleOrder();// 找 閒置的訂單
                if (idleDeliverList.size() > 0 && idleOrderList.size() > 0) { // 如果有在線的外送員的話 且有空閒訂單

//            if(PushOrderWebSocket.sessions.size() > 0 && OrderService.pushOrders.size() > 0){ // 如果有在線的外送員的話 且有空閒訂單

//                List<Order> entryList = new ArrayList<>(OrderService.pushOrders.values());
                    // sort idle order value 大到小
                    Collections.sort(idleOrderList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            return order2.getOrder().getCastingPrio() - order1.getOrder().getCastingPrio();
                        }
                    });

                    for (Order order : idleOrderList) {

                        // 如果訂單為空閒
                        if (order.getOrder().getOrderStatus().equals(OrderSetting.OrderStatus.WAIT)) {

                            for (User deliver : idleDeliverList) {
//                        User deliver = OrderService.onlineDelivers.get(user.getUserID());
                                if (deliver.getUserStatus().equals(UserStatus.DELIVER_ON.toString())) {
                                    Session idleDeliverSession = PushOrderWebSocket.sessions.get(deliver.getUserID());

                                    try {
                                        idleDeliverSession.getBasicRemote().sendText(gson.toJson(order));
//                                    System.out.println(order.getOrderID());
//                                    System.out.println(deliver.getUserID());
                                        OrderDAO.modifyOrderStatus(order.getOrder().getOrderID(), OrderSetting.OrderStatus.PUSHING);
                                        UserDAO.modifyUserStatus(deliver.getUserID(), UserStatus.PUSHING.toString());
                                        order.getOrder().setOrderStatus(OrderSetting.OrderStatus.PUSHING);
                                        deliver.setUserStatus(UserStatus.PUSHING.toString());

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
