import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class PipelinePresenterServer {
    public static void main(String[] args) throws Exception {
        new PipelinePresenterServer().start();
    }

    private void start() throws Exception {
        Server server = new Server(8010);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setWelcomeFiles(new String[]{"index.html"});

        resource_handler.setResourceBase("pipeline-presenter/src/main/resources/app/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, new DefaultHandler()});
        server.setHandler(handlers);
        server.start();
    }
}
