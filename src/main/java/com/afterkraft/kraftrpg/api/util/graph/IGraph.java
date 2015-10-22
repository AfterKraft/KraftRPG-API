/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph;

public interface IGraph<T extends Node<T>> {

    int getNodeCount();

    int getEdgeCount();

    void add(T n);

    void delete(T n);

    void addEdge(T a, T b);

    Iterable<T> getNodes();

}
