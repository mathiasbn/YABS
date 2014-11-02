package dk.yabs.event;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class EventServer {
    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("buildevent", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                System.out.println(String.format("Sending: %s", data));
                server.getBroadcastOperations().sendEvent("buildevent", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
