package dk.yabs.event;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import dk.yabs.EventReceiver;

public class EventServer implements EventReceiver {

    private SocketIOServer server;

    public static void main(String[] args) throws InterruptedException {
        new EventServer().start();
    }

    public void start() throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        server = new SocketIOServer(config);
        server.addEventListener("buildevent", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                server.getBroadcastOperations().sendEvent("buildevent", data);
            }
        });
        server.addEventListener("pipelineCreated", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                System.out.println("Sending pipelineCreated event");
                server.getBroadcastOperations().sendEvent("pipelineCreated", data);
            }
        });
        server.start();
    }

    @Override
    public void create(String json) {
        System.out.println("creating pipline: " + json);
        server.getBroadcastOperations().sendEvent("pipelineCreated", json);
    }
}
