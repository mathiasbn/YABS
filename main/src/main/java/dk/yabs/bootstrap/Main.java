package dk.yabs.bootstrap;

import dk.yabs.event.EventServer;
import dk.yabs.pipelinepresenter.PipelinePresenterServer;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        EventServer eventServer = new EventServer();
        eventServer.start();
        System.out.println(String.format("Eventserver started! (in %s)",
                (System.nanoTime() - startTime) / 1_000_000L));
        startTime = System.nanoTime();
        PipelinePresenterServer presenterServer = new PipelinePresenterServer();
        presenterServer.start();
        System.out.println(String.format("Pipeline presenter started! (in %s)",
                (System.nanoTime() - startTime) / 1_000_000L));
    }
}
