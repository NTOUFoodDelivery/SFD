package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import configurator.GetHttpSessionConfigurator;
import db.demo.dao.UserDAO;
import member.controller.service.LogInServlet;
import member.model.javabean.MemberSetting;
import order.model.javabean.PushResult;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value="/pushOrderEndpoint",configurator= GetHttpSessionConfigurator.class)
public class PushOrderWebSocket {


    public static Map<HttpSession,Integer> httpSessions =  new ConcurrentHashMap<HttpSession,Integer>();
    public static Map<Integer,Session> sessions =  new ConcurrentHashMap<Integer,Session>();

    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        System.out.println("PushOrderWebSocket Server open ::"+session.getId());
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        int userID = httpSessions.get(httpSession);
        System.out.println(userID);
        if(UserDAO.showUserIdentity(userID).equals(MemberSetting.UserStatus.DELIVER_ON)){ // 如果是外送員 連結 websocket
            sessions.put(userID,session);
        }else { // 不是外送員 斷開 websocket連結
            onClose(session);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("PushOrderWebSocket Server close ::"+session.getId());
        List<Integer> closeSessions = (List<Integer>)LogInServlet.getKey(sessions,session);
        for(int closeSessionUserID : closeSessions){
            sessions.remove(closeSessionUserID);
        }
    }

    @OnMessage
    public void onMessage(Session session, String msg) {

        // 處理 訂單 接受與否
        synchronized(sessions) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            try {

                PushResult pushResult = gson.fromJson(msg, PushResult.class);
                String orderID = pushResult.getOrderID();

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