package org.pipeline.model

public interface Stage {
    def configure(Closure closure)

    def addDependency(Closure dep)
    def addDependency(Stage dep)

    boolean valid()
}