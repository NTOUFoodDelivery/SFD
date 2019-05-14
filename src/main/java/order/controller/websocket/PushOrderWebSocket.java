package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import configurator.GetHttpSessionConfigurator;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import member.controller.servlet.LogInServlet;
import member.model.javabean.MemberSetting;
import order.controller.service.OrderService;
import order.model.javabean.OrderSetting;
import order.model.javabean.PushResult;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value="/pushOrderEndpoint",configurator= GetHttpSessionConfigurator.class)
public class PushOrderWebSocket {


    public static Map<HttpSession,Long> httpSessions =  new ConcurrentHashMap<HttpSession,Long>();
    public static Map<Long,Session> sessions =  new ConcurrentHashMap<Long,Session>();

    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("PushOrderWebSocket Server open ::"+session.getId());
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        Long userID = httpSessions.get(httpSession);

        if(UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.DELIVER_ON)){ // 如果外送員 上線且有空 則 連結 websocket
            sessions.put(userID,session);
            System.out.println(userID + " :: DELIVER_ON");
        }else { // 若外送員 已接單 或 離線 則 斷開 websocket連結
            onClose(session);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        List<Long> closeSessions = (List<Long>)LogInServlet.getKey(sessions,session);
        for(Long closeSessionUserID : closeSessions){
            sessions.remove(closeSessionUserID);
        }
    }

    @OnMessage
    public void onMessage(Session session, String msg) {

        // 處理 訂單 接受與否
        synchronized(sessions) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            try {
//                List<Long> msgSessions = (List<Long>)LogInServlet.getKey(sessions,session);
//                for(Long deliverID : msgSessions){
                OrderService.dealOrder(gson.fromJson(msg, PushResult.class));
//                }
            } catch (Exception e) {
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
    }
}