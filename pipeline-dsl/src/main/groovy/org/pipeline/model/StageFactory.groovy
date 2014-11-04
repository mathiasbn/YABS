package org.pipeline.model

import org.pipeline.model.exception.CyclicStageDependencyException

class StageFactory {
    Stage create(Closure closure, Map config) {
        Stage stage
        if (config['type'])
            stage = (config['type'] as Class<Stage>).newInstance()
        else
            stage = DefaultStage.newInstance()

        if(config['dependsOn']) {
            config['dependsOn'].each {
                stage.addDependency(it)
            }
        }

        stage.name = config.name
        stage.configure(closure)

//        if(contains(stage.dependencies, stage))
//            throw new CyclicStageDependencyException("Cycle detected")

        stage
    }

}
