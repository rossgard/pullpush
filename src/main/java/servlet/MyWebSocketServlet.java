package servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebSocketServlet extends WebSocketServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MyWebSocketServlet.class);

    @Override
    public void configure(WebSocketServletFactory factory) {
        LOG.debug("Configuring MyWebSocketServlet...");
        factory.getPolicy().setIdleTimeout(600000);
        factory.register(CurrentTimeSocket.class);
    }
}
