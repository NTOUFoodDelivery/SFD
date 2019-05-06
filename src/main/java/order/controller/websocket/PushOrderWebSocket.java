package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.model.javabean.PushResult;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/pushOrderEndpoint")
public class PushOrderWebSocket {

    public static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("PushOrderWebSocket Server open ::"+session.getId());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {

        synchronized(sessions) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            try {

                PushResult pushResult = gson.fromJson(msg, PushResult.class);
                String orderID = pushResult.getOrderID();

                for(Session s: sessions){
                    if(s.getId() != session.getId())
                        s.getBasicRemote().sendText(orderID);
                }
                Boolean isAccept = pushResult.isAccept();
                if (isAccept) { // 訂單被接受
                    // 去資料庫 修改訂單狀態成"被接受之類的"
                } else {  // 訂單被拒絕
                    // 去資料庫 修改訂單狀態成"未推播"
                }
            } catch (Exception e) {
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
    }
}