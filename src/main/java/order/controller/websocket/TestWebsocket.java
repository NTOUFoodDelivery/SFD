package order.controller.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/testWebsocket")
public class TestWebsocket {

    public static Session testPushSession;


    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.testPushSession = session;
    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        System.out.println(msg);
    }

    @OnError
    public void onError(Throwable t) {
    }
}
