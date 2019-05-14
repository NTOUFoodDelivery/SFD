package order.model.timerTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import order.controller.service.OrderService;
import order.controller.websocket.PushOrderWebSocket;
import order.model.javabean.Order;

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

            if(PushOrderWebSocket.sessions.size() > 0){ // 如果有在線的外送員的話
//                try {
//                    PushOrderWebSocket.sessions.get(3L).getBasicRemote().sendText("213");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                List<Long> idleDeliverList = UserDAO.searchIdleDeliver(); // 找 閒置的外送員
//                List<JsonObject> idleOrderList = OrderDAO.searchIdelOrder();// 找 閒置的訂單

                List<Map.Entry> entryList = new ArrayList<>(OrderService.pushOrders.entrySet());
                Comparator< Map.Entry> sortByValue = (e1,e2)->{ return ((Integer)e2.getValue()).compareTo( (Integer)e1.getValue()); };
                Collections.sort(entryList, sortByValue );
                // sort idle order value 大到小
                for(Map.Entry entry : entryList){
                    System.out.println(entry.getValue());
                    entry.getKey();
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
