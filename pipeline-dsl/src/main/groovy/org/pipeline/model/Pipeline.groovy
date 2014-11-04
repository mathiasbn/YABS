package org.pipeline.model

import org.pipeline.plugin.PluginContainer

class Pipeline {
    StageContainer stages = new StageContainer(new StageFactory())
    PluginContainer plugins = new PluginContainer()

    boolean valid() {
        stages.valid()
    }
}
