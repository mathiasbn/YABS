package org.pipeline.model

import org.pipeline.model.exception.StageAlreadyDefinedException

class StageContainer implements Iterable<Stage> {
    StageFactory stageFactory;
    Set<Stage> stages = []

    StageContainer(StageFactory stageFactory) {
        this.stageFactory = stageFactory
    }

    @Override
    Iterator<Stage> iterator() {
        stages.iterator()
    }

    Stage create(closure, Map config) {
        if (exists(config.name)) {
            throw new StageAlreadyDefinedException("a stage with name $config.name already exists");
        }

        Stage stage = stageFactory.create(closure, config)
        stages.add(stage)
        stage
    }

    Stage getAt(String index) {
        stages.find {
            it.name == index
        }
    }

    boolean exists(String name) {
        stages.any {
            it.name == name
        }
    }

    int size() {
        stages.size()
    }

    boolean contains(String name) {
        stages.any {
            it.name == name
        }
    }

    boolean valid() {
        stages.inject(true) {
            acc, val ->
            acc && val.valid()
        }
    }
}
