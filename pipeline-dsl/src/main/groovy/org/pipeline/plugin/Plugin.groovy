package org.pipeline.plugin

public interface Plugin<T> {
    void apply(T t)
}