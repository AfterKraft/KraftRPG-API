/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class Node<T extends Node<T>> implements Cloneable {

    private final List<T> adj = Lists.newArrayList();

    public Node() {
    }

    public void addEdge(T other) {
        this.adj.add(other);
    }

    public void deleteEdge(T other) {
        this.adj.remove(other);
    }

    public boolean isAdjacent(T other) {
        return this.adj.contains(other);
    }

    public int getEdgeCount() {
        return this.adj.size();
    }

    public Iterable<T> getAdjacent() {
        return this.adj;
    }

    @Override
    public abstract T clone();
}
