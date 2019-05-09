package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import configurator.GetHttpSessionConfigurator;
import db.demo.javabean.User;
import order.model.javabean.PushResult;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value="/pushOrderEndpoint",configurator= GetHttpSessionConfigurator.class)
public class PushOrderWebSocket {

    public static final Set<Session> sessions = Collections.synchronizedSet(new CopyOnWriteArraySet<Session>());
    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
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