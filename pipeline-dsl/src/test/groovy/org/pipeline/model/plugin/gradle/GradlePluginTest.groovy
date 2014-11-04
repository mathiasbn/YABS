package org.pipeline.model.plugin.gradle

import org.pipeline.model.Pipeline
import spock.lang.Specification

import static org.pipeline.model.TestUtil.buildModel


class GradlePluginTest extends Specification {
    private Closure pipeline

    def "the gradle plugin can be configured"() {
        pipeline = {
            plugins {
                id 'gradle'
            }

            gradleConfiguration {
                buildFile = file('some/dir/build.gradle')
            }
        }

        Pipeline model = buildModel(pipeline)

        expect:
        model.valid()
    }
}
