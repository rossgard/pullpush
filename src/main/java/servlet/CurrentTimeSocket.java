package servlet;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebSocket
public class CurrentTimeSocket {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentTimeSocket.class);

    private RemoteEndpoint remote;

    public CurrentTimeSocket() {
        LOG.debug("CurrentTimeSocket instantiated");
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOG.debug("WebSocket Opened");
        this.remote = session.getRemote();

        while (true) {
            try {
                LOG.debug("Pushing WebSocket Event");
                remote.sendString(String.valueOf(System.currentTimeMillis()));
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        LOG.debug("Message from Client: " + message);
        try {
            remote.sendString("Hi Client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOG.debug("WebSocket Closed. Code:" + statusCode);
    }
}
