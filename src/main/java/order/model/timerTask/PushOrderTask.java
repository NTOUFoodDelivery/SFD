package order.model.timerTask;

import com.google.gson.JsonObject;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import order.controller.websocket.PushOrderWebSocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class PushOrderTask extends TimerTask {

    private void pushOrder() { // 去資料庫 修改訂單狀態成"已推播"

        // 推播 idle 訂單 給 空閒的 外送員
//        synchronized(PushOrderWebSocket.sessions){
//            // Iterate over the connected sessions
//            // and broadcast the received message
//            List<Integer> idleDeliver = UserDAO.searchIdelDeliver();
//            ArrayList<JsonObject> idleOrderList = OrderDAO.searchIdelOrder();
//            // idle order
//            for(JsonObject idleOrder : idleOrderList){
//                // idle deliver
//                for(int idleDeliverID : idleDeliver){
//                    Session idleDeliverSession = PushOrderWebSocket.sessions.get(idleDeliverID);
//                    if(idleDeliverSession != null)
//                    {
//                        try {
//                            Date date = new Date();
//                            idleDeliverSession.getBasicRemote().sendText("[SYS] Test push order timerTask ::generate:: Date is "+date.toString());
//                        } catch (IOException e) {
//
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
    }
    public void run() {
        pushOrder();
    }
}
