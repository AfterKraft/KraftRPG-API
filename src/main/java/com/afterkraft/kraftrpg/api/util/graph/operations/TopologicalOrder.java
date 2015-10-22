/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.operations;

import com.afterkraft.kraftrpg.api.util.graph.GraphOperations;
import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.afterkraft.kraftrpg.api.util.graph.directed.DirectedGraph;
import com.google.common.collect.Sets;

import java.util.ArrayDeque;
import java.util.Set;

import javax.annotation.Nullable;

public class TopologicalOrder<T extends Node<T>> {

    private final DirectedGraph<T> digraph;
    private Set<T> marked;

    public TopologicalOrder(DirectedGraph<T> graph) {
        this.digraph = graph;
    }

    @Nullable
    public Iterable<T> order(T root) {
        if (GraphOperations.hasCycle(this.digraph)) {
            return null;
        }
        this.marked = Sets.newHashSet();
        ArrayDeque<T> order = new ArrayDeque<>();
        dfs(order, root);

        return order;
    }

    private void dfs(ArrayDeque<T> order, T root) {
        this.marked.add(root);
        for (T n : root.getAdjacent()) {
            if (!this.marked.contains(n)) {
                dfs(order, n);
            }
        }
        order.push(root);
    }

}
