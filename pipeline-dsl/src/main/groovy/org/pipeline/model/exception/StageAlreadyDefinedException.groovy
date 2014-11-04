package org.pipeline.model.exception

class StageAlreadyDefinedException extends RuntimeException {
    def StageAlreadyDefinedException(String s) {
        super(s);
    }
}
