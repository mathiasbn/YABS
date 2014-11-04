package org.pipeline.dsl

import org.pipeline.model.Pipeline
import org.pipeline.plugin.Plugin


class PluginDelegateStrategy implements Plugin<Pipeline> {
    private Pipeline pipeline

    void apply(Pipeline pipeline) {
        this.pipeline = pipeline
    }

    def plugins(Closure config) {
        config()
    }

    def id(String id) {
        pipeline.plugins.create(id)
    }
}
