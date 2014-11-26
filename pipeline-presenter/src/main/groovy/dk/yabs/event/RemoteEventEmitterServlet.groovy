package dk.yabs.event

import groovy.json.JsonSlurper

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RemoteEventEmitterServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        def json = new JsonSlurper().parseText(req.inputStream.text)
        println "moinz"
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.writer << "MOINZ! You should probably use post"
    }
}
