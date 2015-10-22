/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph;

public class DataNode<T> extends Node<DataNode<T>> {

    private final T data;

    public DataNode(T obj) {
        this.data = obj;
    }

    public T getData() {
        return this.data;
    }

    @Override
    public DataNode<T> clone() {
        return new DataNode<>(this.data);
    }

}
