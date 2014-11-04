package org.pipeline.main

import org.pipeline.dsl.ModelBuilder

class PipelineMain {

    public static void main(String[] args) {
        new ModelBuilder(args[0]);
    }

}
