package org.pipeline.dsl

import org.codehaus.groovy.control.CompilerConfiguration
import org.pipeline.model.Pipeline

class ModelBuilder {

    Script script
    Closure closureScript
    Pipeline pipelineModel = new Pipeline()

    ModelBuilder(String dsl) {
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = DelegatingScript.class.name
        def shell = new GroovyShell(this.class.classLoader, new Binding(), compilerConfiguration)

        def pipelineScript = new PipelineDelegate(pipelineModel)

        script = shell.parse(dsl)
        script.setDelegate(pipelineScript)
    }

    ModelBuilder(Closure dsl) {
        def pipelineScript = new PipelineDelegate(pipelineModel)
        dsl.setResolveStrategy(Closure.DELEGATE_FIRST)
        dsl.setDelegate(pipelineScript)
        closureScript = dsl;
    }

    Pipeline buildModel() {
        if (script)
            script.run()
        if(closureScript)
            closureScript.run()

        pipelineModel.valid()
        return pipelineModel;
    }
}
