package order.model.timerTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import order.controller.websocket.PushOrderWebSocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class PushOrderTask extends TimerTask {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    private void pushOrder() { // 去資料庫 修改訂單狀態成"已推播"


        // 推播 idle 訂單 給 空閒的 外送員
//        synchronized(PushOrderWebSocket.sessions){
//            // Iterate over the connected sessions
//            // and broadcast the received message
//
//            if(PushOrderWebSocket.sessions.size() > 0){
//                try {
//
//                    PushOrderWebSocket.sessions.get(3L).getBasicRemote().sendText("213");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                List<Long> idleDeliver = UserDAO.searchIdleDeliver();
//                // idle order
//                List<JsonObject> idleOrderList = OrderDAO.searchIdelOrder();
//                for(JsonObject jsonObject : idleOrderList){
//                    // idle deliver
//                    for(Long idleDeliverID : idleDeliver){
//                        Session idleDeliverSession = PushOrderWebSocket.sessions.get(idleDeliverID);
//                        if(idleDeliverSession != null)
//                        {
//                            try {
//                                Date date = new Date();
//                                idleDeliverSession.getBasicRemote().sendText(gson.toJson(jsonObject));
//                            } catch (IOException e) {
//
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
    }
    public void run() {
        pushOrder();
    }
}
