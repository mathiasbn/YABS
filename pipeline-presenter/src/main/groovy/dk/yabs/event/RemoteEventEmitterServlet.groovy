package dk.yabs.event

import dk.yabs.EventReceiver
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RemoteEventEmitterServlet extends HttpServlet {

    private EventReceiver receiver

    RemoteEventEmitterServlet(EventReceiver receiver) {
        this.receiver = receiver
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        def eventMsg = req.inputStream.text
        def json = new JsonSlurper().parseText(eventMsg)
        if (json.pipelineCreated) {
            receiver.create(new JsonBuilder( json.pipelineCreated ).toPrettyString())
            resp.setStatus(resp.SC_OK)
            resp.writer << "moinz"
        }else
            throw new RuntimeException()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.writer << "MOINZ! You should probably use post"
    }
}
