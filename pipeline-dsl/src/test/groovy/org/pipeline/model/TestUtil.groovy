package org.pipeline.model

import org.pipeline.dsl.ModelBuilder


class TestUtil {
    static Pipeline buildModel(String pipeline) {
        ModelBuilder builder = new ModelBuilder(pipeline)
        Pipeline model = builder.buildModel()
        model.metaClass.assert = new Asserter(model)
        model
    }

    static Pipeline buildModel(Closure pipeline) {
        ModelBuilder builder = new ModelBuilder(pipeline)
        Pipeline model = builder.buildModel()
        model.metaClass.assert = new Asserter(model)
        model
    }
}
