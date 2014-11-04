package org.pipeline.model

import org.pipeline.model.exception.CyclicStageDependencyException
import org.pipeline.model.exception.StageAlreadyDefinedException
import spock.lang.Specification

import static org.pipeline.model.TestUtil.buildModel

class ModelBuilderTest extends Specification {
    private Closure pipeline

    def "an empty pipeline can be parsed"() {
        def pipeline = ""

        Pipeline model = buildModel(pipeline)

        expect:
        model.assert.valid()
    }

    //see TaskDefinitionScriptTransformer.java
    def "unknown blocks results in errors"() {
        def pipeline = {
            invalidBlock() {

            }
        }

        Pipeline model = buildModel(pipeline)

        expect:
        !model.assert.valid()
    }

    def "given a pipeline with a single stage, the model has the stage"() {
        def pipeline =
                """
                stage build {
                }
                """
        Pipeline model = buildModel(pipeline)

        expect:
        model.assert.valid()
        model.assert.stage('build')
    }

    def "a closure can be used instead of a String script"() {
        pipeline = { stage build {} }

        Pipeline model = buildModel(pipeline)

        expect:
        model.assert.valid()
        model.assert.stage('build')
    }

    def "multiple stages can be added"() {
        pipeline = {
            stage build {}
            stage test {}
        }

        Pipeline model = buildModel(pipeline)

        expect:
        model.assert.valid()
        model.assert.stage('build')
        model.assert.stage('test')
    }

    def "two stages cannot have the same name"() {
        pipeline = {
            stage build {}
            stage build {}
        }

        when:
        buildModel(pipeline)

        then:
        thrown(StageAlreadyDefinedException)
    }

    def "an action can be added to a stage"() {
        pipeline = {
            stage build {
                action {
                    return "actionRun"
                }
            }
        }
        Pipeline model = buildModel(pipeline)

        expect:
        model.stages['build'].actions[0].call() == "actionRun"
    }

    def "multiple actions can be added to a stage"() {
        pipeline = {
            stage build {
                action {
                    return "someAction"
                }
                action {
                    return "anotherAction"
                }
            }
        }
        Pipeline model = buildModel(pipeline)

        expect:
        model.stages['build'].actions[0].call() == "someAction"
        model.stages['build'].actions[1].call() == "anotherAction"
    }

    def "a beforeActions block can be added to a stage"() {
        pipeline = {
            stage build {
                beforeActions {
                    return "beforeActions"
                }
            }
        }
        Pipeline model = buildModel(pipeline)

        expect:
        model.stages['build'].beforeActions.execute() == "beforeActions"
    }

    def "a postActions block can be added to a stage"() {
        pipeline = {
            stage build {
                postActions {
                    return "postActions"
                }
            }
        }
        Pipeline model = buildModel(pipeline)

        expect:
        model.stages['build'].postActions.execute() == "postActions"
    }

    def "a stage block can depend on another"() {
        pipeline = {
            stage build {
            }

            stage test(dependsOn: build) {
            }
        }
        Pipeline model = buildModel(pipeline)

        expect:
        model.stages['test'].dependencies.size() == 1
    }

    def "a stage block can depend on another, even if it is defined first, but only if the stage is wrapped in a closure"() {
        pipeline = {
            stage test(dependsOn: { build }) {
            }

            stage build {
            }
        }
        Pipeline model = buildModel(pipeline)

        expect:
        model.stages['test'].dependencies.size() == 1
        model.stages['test'].dependencies.get(0).name == 'build'
    }

    def "there cannot be cyclic dependencies between stages"() {
        pipeline = {
            stage build(dependsOn: { test }) {
            }

            stage test(dependsOn: build) {
            }
        }
        when:
        buildModel(pipeline)

        then:
        thrown(CyclicStageDependencyException)
    }

    def "the type of a stage can be defined in its parameters"() {
        pipeline = {
            stage build(type: BuildStage) {}
        }

        when:
        Pipeline model = buildModel(this.pipeline)

        then:
        Stage s = model.assert.stage('build')
        s instanceof BuildStage
    }

    def "a plugin can be applied"() {
        pipeline = {
            plugins {
                id 'gradle'
            }
        }

        Pipeline model = buildModel(pipeline)

        expect:
        model.plugins.hasPlugin('gradle')
    }


    boolean valid(Pipeline pipelineModel) {
        pipelineModel != null
    }
}

