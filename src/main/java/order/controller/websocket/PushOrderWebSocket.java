package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.controller.service.OrderService;
import order.model.javabean.PushResult;
import util.configurator.GetHttpSessionConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value="/pushOrderEndpoint",configurator= GetHttpSessionConfigurator.class)
public class PushOrderWebSocket {


    public static Map<Session,Long> sessions =  new ConcurrentHashMap<>(); // 紀錄 所有 連接 websocket 外送員 的 websocket session

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    private OrderService orderService = new OrderService();

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("PushOrderWebSocket Server open ::"+session.getId());

        HttpSession httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());

        Long userID = (Long)httpSession.getAttribute("userID");
        if(userID != null){
            sessions.put(session,userID);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        // 處理 訂單 接受與否
        // lock websocket sessions
        // 以免推播訂單時 拿到不完全的 用戶、訂單資訊
        synchronized(sessions) {
            Long userID = sessions.get(session); // deliver id
            PushResult pushResult = gson.fromJson(msg,PushResult.class); // 訂單 處理 訊息
            orderService.dealOrder(pushResult,userID); // 處理 訂單
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }
}