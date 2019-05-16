package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import configurator.GetHttpSessionConfigurator;
import db.demo.dao.OrderDAO;
import db.demo.dao.UserDAO;
import db.demo.javabean.User;
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


    public static Map<HttpSession, User> httpSessions =  new ConcurrentHashMap<HttpSession,User>();
    public static Map<Long,Session> sessions =  new ConcurrentHashMap<Long,Session>();

    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("PushOrderWebSocket Server open ::"+session.getId());
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());

        User user = httpSessions.get(httpSession);
        System.out.println(user.getUserStatus());
//        OrderService.onlineDelivers.put(user.getUserID(),user);

        if(UserDAO.showUserIdentity(user.getUserID()).equals(MemberSetting.UserStatus.DELIVER_ON)){ // 如果外送員 上線且有空 則 連結 websocket
            sessions.put(user.getUserID(),session);
            System.out.println(user.getUserID() + " :: DELIVER_ON");
        }else { // 若外送員 已接單 或 離線 則 斷開 websocket連結
            onClose(session);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        List<Long> closeSessions = (List<Long>)LogInServlet.getKey(sessions,session);
        for(Long userID : closeSessions){
            sessions.remove(userID);
            UserDAO.modifyUserStatus(userID, MemberSetting.UserStatus.OFFLINE);
//            OrderService.onlineDelivers.remove(user.getUserID());
        }
    }

    @OnMessage
    public void onMessage(Session session, String msg) {

        // 處理 訂單 接受與否
        synchronized(sessions) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            try {
                List<Long> msgSessions = (List<Long>)LogInServlet.getKey(sessions,session);
                for(Long deliverID : msgSessions){
                    PushResult pushResult = gson.fromJson(msg,PushResult.class);
                    pushResult.setDeliverID(deliverID);
                    OrderService.dealOrder(pushResult);
                }
            } catch (Exception e) {
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
    }
}