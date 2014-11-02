package dk.yabs.event;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class EventServer {
    public static void main(String[] args) throws InterruptedException {
        new EventServer().start();
    }

    public void start() throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("buildevent", String.class, (client, data, ackRequest) -> {
            server.getBroadcastOperations().sendEvent("buildevent", data);
        });
        server.start();
    }
}
