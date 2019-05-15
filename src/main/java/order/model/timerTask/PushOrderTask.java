package order.model.timerTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
import member.model.javabean.MemberSetting;
import order.controller.service.OrderService;
import order.controller.websocket.PushOrderWebSocket;
import order.model.javabean.Order;
import order.model.javabean.OrderSetting;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class PushOrderTask extends TimerTask {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    private void pushOrder() { // 去資料庫 修改訂單狀態成"已推播"


        // 推播 idle 訂單 給 空閒的 外送員
        synchronized(PushOrderWebSocket.sessions){
            // Iterate over the connected sessions
            // and broadcast the received message

            if(PushOrderWebSocket.sessions.size() > 0 && OrderService.pushOrders.size() > 0){ // 如果有在線的外送員的話 且有空閒訂單
//                try {
//                    PushOrderWebSocket.sessions.get(3L).getBasicRemote().sendText("213");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                List<Long> idleDeliverList = UserDAO.searchIdleDeliver(); // 找 閒置的外送員
//                List<JsonObject> idleOrderList = OrderDAO.searchIdelOrder();// 找 閒置的訂單

                List<Order> entryList = new ArrayList<>(OrderService.pushOrders.values());
                // sort idle order value 大到小
                Collections.sort(entryList, new Comparator<Order>() {
                    @Override
                    public int compare(Order order1, Order order2) {
                        return order2.getValue() - order1.getValue();
                    }
                });

                for(Order order : entryList){
                    System.out.println(order.getOrderID());
                    System.out.println(order.getValue());

                    // 如果訂單為空閒
                    if(order.getOrderStatus().equals(OrderSetting.OrderStatus.WAIT)) {
                        for (Long idleDeliverID : idleDeliverList) {
                            User deliver = OrderService.onlineDelivers.get(idleDeliverID);
                            if(deliver.getUserStatus().equals(MemberSetting.UserStatus.DELIVER_ON)) {
                                Session idleDeliverSession = PushOrderWebSocket.sessions.get(idleDeliverID);
                                try {
                                    idleDeliverSession.getBasicRemote().sendObject(order);
                                    order.setOrderStatus(OrderSetting.OrderStatus.PUSHING);
                                    deliver.setUserStatus(MemberSetting.UserStatus.PUSHING);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (EncodeException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }
                }


//                //idle order
//                for(JsonObject jsonObject : idleOrderList){
//                    Order order = gson.fromJson(jsonObject.toString(),Order.class);
//                    // idle deliver
//                    for(Long idleDeliverID : idleDeliverList){
//                        Session idleDeliverSession = PushOrderWebSocket.sessions.get(idleDeliverID);
//
//                        OrderService.pushOrders.get(order.getOrderID());
//                        try {
//                            Date date = new Date();
//                            idleDeliverSession.getBasicRemote().sendText(gson.toJson(jsonObject));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }


            }

        }
    }
    public void run() {
        pushOrder();
    }
}
