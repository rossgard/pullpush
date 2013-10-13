package servlet;

import org.eclipse.jetty.servlets.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//  @WebServlet(urlPatterns = "/events", initParams = {@WebInitParam(name = "heartBeatPeriod", value = "3")}, asyncSupported = true)
public class MYSSEServlet extends EventSourceServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MYSSEServlet.class);

    @Override
    protected EventSource newEventSource(HttpServletRequest request) {
        return new EventSource() {
            @Override
            public void onOpen(final Emitter emitter) throws IOException {
                while (true) {
                    LOG.debug("Pushing SSE event");
                    try {
                        emitter.data(String.valueOf(System.currentTimeMillis()));
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onClose() {
                LOG.debug("Closing SSE");
            }
        };
    }
}

