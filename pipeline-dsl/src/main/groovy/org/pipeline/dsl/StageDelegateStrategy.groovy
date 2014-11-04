package org.pipeline.dsl

import org.pipeline.model.Pipeline
import org.pipeline.model.StageContainer
import org.pipeline.plugin.Plugin

class StageDelegateStrategy implements Plugin<Pipeline> {
    StageContainer stages

    @Override
    void apply(Pipeline pipelineModel) {
        stages = pipelineModel.stages
    }

    def stage(NamedClosure s) {
        stages.create(s.closure, s.config)
    }
}
