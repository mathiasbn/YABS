package org.pipeline.plugin

import org.pipeline.model.Pipeline
import org.pipeline.plugin.Plugin


class PluginContainer {
    List<Plugin> plugins = []

    boolean hasPlugin(String plugin) {
        plugins.any {
            it.pluginId == plugin
        }
    }

    def create(String id) {
        def plugin = new Plugin<Pipeline>() {
            String pluginId

            @Override
            void apply(Pipeline pipeline) {

            }
        }

        plugin.setPluginId(id)

        plugins << plugin
        //map to class
        //new instance
        //call apply
    }
}
