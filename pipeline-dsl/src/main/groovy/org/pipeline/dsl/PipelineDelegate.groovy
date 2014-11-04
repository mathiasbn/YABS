package org.pipeline.dsl

import org.pipeline.model.Pipeline

class PipelineDelegate {
    private Pipeline pipeline
    List strategies = []

    def PipelineDelegate(Pipeline pipeline) {
        this.pipeline = pipeline

        StageDelegateStrategy stageDelegateStrategy = new StageDelegateStrategy()
        stageDelegateStrategy.apply(pipeline)
        strategies << stageDelegateStrategy

        PluginDelegateStrategy pluginDelegateStrategy = new PluginDelegateStrategy()
        pluginDelegateStrategy.apply(pipeline)
        strategies << pluginDelegateStrategy
    }

    def methodMissing(String name, args) {
        strategies.each {
            strategy ->
            if(strategy.respondsTo(name, args)) {
                strategy.invokeMethod(name, args)
            }
        }
        if(strategies.any {
            it.respondsTo(name, args)
        }) {
            return
        }
        if(args.size() == 1 && args[0] instanceof Closure) {
            /*
            stage build {
            }
             */
            Closure c = args[0]
            return new NamedClosure(name, c)
        }
        if(args.size() == 2 && args[1] instanceof Closure) {
            /*
            stage build(type: Something, someOtherKey: 'foo') {
            }
             */
            return new NamedClosure(name, args[1], args[0])
        }

        throw new IllegalStateException("Unhandled configuration block $name")
    }

    def propertyMissing(String name) {
        return pipeline.stages[name]
    }
}


