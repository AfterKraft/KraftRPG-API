/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.operations;

import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.afterkraft.kraftrpg.api.util.graph.directed.DirectedGraph;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class CycleDetector<T extends Node<T>> {

    private final DirectedGraph<T> graph;
    private Set<T> marked;

    public CycleDetector(DirectedGraph<T> g) {
        this.graph = g;
    }

    public boolean hasCycle() {
        this.marked = Sets.newHashSet();
        List<T> all = Lists.newArrayList(this.graph.getNodes());
        while (!all.isEmpty()) {
            T n = all.get(0);
            boolean cycle = dfs(n);
            if (cycle) {
                return true;
            }
            all.removeAll(this.marked);
            this.marked.clear();
        }
        return false;
    }

    private boolean dfs(T root) {
        this.marked.add(root);
        for (T a : root.getAdjacent()) {
            if (!this.marked.contains(a)) {
                return dfs(a);
            }
            return true;
        }
        return false;
    }

}
