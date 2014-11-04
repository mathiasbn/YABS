package org.pipeline.model

class ClosureAction implements Action {
    private Closure closure

    ClosureAction(Closure closure) {
        this.closure = closure
    }

    @Override
    def execute() {
        closure.call()
    }
}
