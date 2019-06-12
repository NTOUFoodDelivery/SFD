package order.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import member.model.javabean.User;
import order.controller.service.OrderService;
import order.controller.timertask.PushOrderTask;
import order.model.javabean.PushResult;
import util.configurator.GetHttpSessionConfigurator;

@ServerEndpoint(value = "/pushOrderEndpoint", configurator = GetHttpSessionConfigurator.class)
public class PushOrderWebSocket {



  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
  private OrderService orderService = new OrderService();

  /**
   * <p>開啟 session.</p>
   *
   * @param session 當前 session
   */
  @OnOpen
  public void open(Session session, EndpointConfig config) {
    System.out.println("PushOrderWebSocket Server open ::" + session.getId());

    HttpSession httpSession = (HttpSession) config.getUserProperties()
        .get(HttpSession.class.getName());

    User user = (User) httpSession.getAttribute("user");
    if (user != null) {
      PushOrderTask.sessions.put(session, user);
    }
  }

  /**
   * <p>關閉 session.</p>
   *
   * @param session 當前 session
   */
  @OnClose
  public void onClose(Session session) {
    System.out.println("PushOrderWebSocket Server close ::" + session.getId());
    PushOrderTask.sessions.remove(session);
  }

  /**
   * <p>處理 訂單 接受與否 ,
   * lock websocket sessions 以免推播訂單時 ,拿到不完全的 用戶、訂單資訊.</p>
   *
   * @param session 當前 session
   * @param msg client 回傳資訊
   */
  @OnMessage
  public void onMessage(Session session, String msg) {
    synchronized (PushOrderTask.sessions) {
      User user = PushOrderTask.sessions.get(session); // deliver id
      PushResult pushResult = gson.fromJson(msg, PushResult.class); // 訂單 處理 訊息
      orderService.dealOrder(pushResult, user.getUserId()); // 處理 訂單
    }
  }

  @OnError
  public void onError(Throwable t) {
    System.out.println(t.getMessage());
  }
}