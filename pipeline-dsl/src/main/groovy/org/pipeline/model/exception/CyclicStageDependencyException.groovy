package org.pipeline.model.exception

class CyclicStageDependencyException extends RuntimeException {
    def CyclicStageDependencyException(String s) {
        super(s);
    }
}
