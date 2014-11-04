package org.pipeline.model

class Asserter {
    Closure stage = {
        stageName ->
            assert stages.contains(stageName)
            return stages[stageName]
    }
    Closure valid = { getDelegate() != null }

    Asserter(Pipeline model) {
        properties.each {
            if (it.value instanceof Closure) {
                it.value.setDelegate(model)
                it.value.setResolveStrategy(Closure.DELEGATE_FIRST)
            }
        }

    }
}
