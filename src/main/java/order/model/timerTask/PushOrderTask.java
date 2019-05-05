package order.model.timerTask;

import order.controller.websocket.PushOrderWebSocket;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

public class PushOrderTask extends TimerTask {

    private void pushOrder() { // 去資料庫 修改訂單狀態成"已推播"

        synchronized(PushOrderWebSocket.sessions){
            // Iterate over the connected sessions
            // and broadcast the received message
            for(Session session : PushOrderWebSocket.sessions){
                if (!session.equals(PushOrderWebSocket.sessions)){
                    try {
                        Date date = new Date();
                        session.getBasicRemote().sendText("[SYS] Test push order timerTask ::generate:: Date is "+date.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void run() {
        pushOrder();
    }
}
