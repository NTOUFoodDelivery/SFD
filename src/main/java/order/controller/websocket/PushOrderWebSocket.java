package order.controller.websocket;

import configurator.GetHttpSessionConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value="/pushOrderEndpoint",configurator= GetHttpSessionConfigurator.class)
public class PushOrderWebSocket {

    //    private static CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<Session>();
    public static Map<HttpSession,Integer> httpSessions =  new ConcurrentHashMap<HttpSession,Integer>();
    public static Map<String,Session> sessions =  new ConcurrentHashMap<String,Session>();
    //    public static Set<Session> sessions = Collections.synchronizedSet(new CopyOnWriteArraySet<Session>());
    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
//        System.out.println("PushOrderWebSocket Server open ::"+session.getId());
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
//        System.out.println("httpSession :: " + httpSession.getId());
        sessions.put(httpSession.getId(),session);
    }

    @OnClose
    public void onClose(Session session) {
//        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {

//        synchronized(sessions) {
//            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
//            try {
//
//                PushResult pushResult = gson.fromJson(msg, PushResult.class);
//                String orderID = pushResult.getOrderID();
//
//                for(Session s: sessions){
//                    if(s.getId() != session.getId())
//                        s.getBasicRemote().sendText(orderID);
//                }
//                Boolean isAccept = pushResult.isAccept();
//                if (isAccept) { // 訂單被接受
//                    // 去資料庫 修改訂單狀態成"被接受之類的"
//                } else {  // 訂單被拒絕
//                    // 去資料庫 修改訂單狀態成"未推播"
//                }
//            } catch (Exception e) {
//            }
//        }
    }

    @OnError
    public void onError(Throwable t) {
    }
}