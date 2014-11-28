package dk.yabs.event

import dk.yabs.EventReceiver
import spock.lang.Specification

class RemoteEventEmitterServletTest extends Specification {
    private EventReceiver receiver = Mock(EventReceiver.class)
    private RemoteEventEmitterServlet servlet = new RemoteEventEmitterServlet(receiver)

    def "create event"() {
        when:
        postEvent('{"pipelineCreated" : {"name":"pipelineName"}}')
        then:
        1 * receiver.create('"name":"pipelineName"++')
    }

    private postEvent(String json) {
        servlet.doPost(new StubRequest(json), null)
    }
}
