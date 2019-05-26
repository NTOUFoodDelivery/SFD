package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import member.model.javabean.User;
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


    public static Map<Long,Session> sessions =  new ConcurrentHashMap<Long,Session>(); // 紀錄 所有 連接 websocket 外送員 的 websocket session
    public static Map<Session,HttpSession> httpSessions =  new ConcurrentHashMap<Session,HttpSession>(); // 紀錄 所有 連接 websocket 外送員 的 http session
    private Session session;
    private HttpSession httpSession;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    private OrderService orderService = new OrderService();

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("PushOrderWebSocket Server open ::"+session.getId());
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());

        User user = (User)httpSession.getAttribute("User");

        sessions.put(user.getUserID(),session);
        httpSessions.put(session,httpSession);

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        User user = (User)httpSessions.get(session).getAttribute("User");
        Long userID = user.getUserID();
        sessions.remove(userID);
        httpSessions.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        // 處理 訂單 接受與否
        synchronized(sessions) {
            User user = (User)httpSessions.get(session).getAttribute("User"); // deliver
            PushResult pushResult = gson.fromJson(msg,PushResult.class); // 訂單 處理 訊息
            orderService.dealOrder(pushResult,user);
        }
    }

    @OnError
    public void onError(Throwable t) {
    }
}