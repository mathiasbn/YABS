package org.pipeline.model

import org.pipeline.model.exception.CyclicStageDependencyException

class DefaultStage implements Stage {
    String name
    List<Closure> actions = []
    Action beforeActions
    Action postActions
    List<Closure<Stage>> dependencies = []

    @Override
    def configure(Closure closure) {
        closure.setDelegate(this)
        closure.resolveStrategy = Closure.DELEGATE_FIRST;
        closure.call()
    }

    def addDependency(Closure dep) {
        dependencies << dep
    }

    def addDependency(Stage dep) {
        dependencies << { dep }
    }

    def action(Closure closure) {
        actions << closure
    }

    def beforeActions(Closure closure) {
        beforeActions = new ClosureAction(closure)
    }

    def postActions(Closure closure) {
        postActions = new ClosureAction(closure)
    }

    List<Stage> getDependencies() {
        List<Stage> transformed = dependencies.collect { it() }
        transformed
    }

    boolean valid() {
        if (contains(getDependencies(), this))
            throw new CyclicStageDependencyException("Cycle detected")
        return true
    }

    boolean contains(List<Stage> heyStack, Stage needle) {
        if (heyStack.contains(needle))
            return true
        return heyStack.any {
            contains(it.dependencies, needle)
        }
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        DefaultStage that = (DefaultStage) o

        if (name != that.name) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}
