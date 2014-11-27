package dk.yabs.event

import dk.yabs.EventReceiver
import spock.lang.Specification

import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest

class RemoteEventEmitterServletTest extends Specification {
    private EventReceiver receiver = Mock(EventReceiver.class)
    private RemoteEventEmitterServlet servlet = new RemoteEventEmitterServlet(receiver)

    def "create event"() {
        given:
        String json = '{createPipeline : {name:pipelineName}}'

        when:
        postEvent(json)

        then:
        1 * receiver.create()
    }

    private postEvent(String json) {
        servlet.doPost({
            inputStream:
            ({
                text:
                {
                    println "moinz!!! $json"
                    json
                }
            } as ServletInputStream)
        } as HttpServletRequest, null)
    }
}
