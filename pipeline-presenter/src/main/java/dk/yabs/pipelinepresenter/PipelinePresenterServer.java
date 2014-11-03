package dk.yabs.pipelinepresenter;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class PipelinePresenterServer {
    public static void main(String[] args) throws Exception {
        new PipelinePresenterServer().start();
    }

    public void start() throws Exception {
        Server server = new Server(8010);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        resourceHandler.setResourceBase("pipeline-presenter/src/main/resources/app/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()});
        server.setHandler(handlers);
        server.start();
    }
}
