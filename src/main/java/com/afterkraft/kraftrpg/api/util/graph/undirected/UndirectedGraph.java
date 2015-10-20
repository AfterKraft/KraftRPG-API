/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.undirected;

import com.afterkraft.kraftrpg.api.util.graph.IGraph;
import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.google.common.collect.Lists;

import java.util.List;

public class UndirectedGraph<T extends Node<T>> implements IGraph<T> {

    protected final List<T> nodes = Lists.newArrayList();

    public UndirectedGraph() {

    }

    @Override
    public int getNodeCount() {
        return this.nodes.size();
    }

    @Override
    public int getEdgeCount() {
        int c = 0;
        for (T n : this.nodes) {
            int ai = this.nodes.indexOf(n);
            for (T b : n.getAdjacent()) {
                int bi = this.nodes.indexOf(b);
                // Only count edges which are from low to high to avoid counting any edge twice and if the
                // node b is not in the graph its index will be -1 and thus will never be counted
                if (bi >= ai) {
                    c++;
                }
            }
        }
        return c;
    }

    @Override
    public void addEdge(T from, T to) {
        if (!this.nodes.contains(from)) {
            this.nodes.add(from);
        }
        if (!this.nodes.contains(to)) {
            this.nodes.add(to);
        }
        if (!from.isAdjacent(to)) {
            from.addEdge(to);
        }
        if (!to.isAdjacent(from)) {
            to.addEdge(from);
        }
    }

    @Override
    public Iterable<T> getNodes() {
        return this.nodes;
    }

    @Override
    public void add(T node) {
        this.nodes.add(node);
    }

    @Override
    public void delete(T node) {
        for (T b : this.nodes) {
            b.deleteEdge(node);
        }
        this.nodes.remove(node);
    }

}
