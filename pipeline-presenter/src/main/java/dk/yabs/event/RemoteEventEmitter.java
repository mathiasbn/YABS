package dk.yabs.event;

import dk.yabs.EventReceiver;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoteEventEmitter {

    private EventReceiver receiver;

    public RemoteEventEmitter(EventReceiver receiver) {
        this.receiver = receiver;
    }

    public void start() throws Exception {
        Server server = new Server(8015);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new RemoteEventEmitterServlet()),"/emit*");

        server.start();
        server.join();

    }
}
