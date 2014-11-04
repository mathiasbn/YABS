package org.pipeline.dsl

public class NamedClosure implements GroovyInterceptable {
    Closure closure
    Map config = [:]

    NamedClosure(String name, Closure closure) {
        this.closure = closure
        config.name = name;
    }

    NamedClosure(String name, Closure closure, Map config) {
        this.config = config
        this.closure = closure
        config.name = name;
    }

    def invokeMethod(String name, args) {

    }

    def getProperty(String name) {
        this.@"$name"
    }

    void setProperty(String name, value) {
        this.@"$name" = value
    }
}
