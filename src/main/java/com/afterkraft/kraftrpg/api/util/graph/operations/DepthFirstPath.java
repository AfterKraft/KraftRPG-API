/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.operations;

import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public class DepthFirstPath<T extends Node<T>> {

    private final T rootNode;
    private Set<T> marked;
    private Map<T, T> edgeTo;

    public DepthFirstPath(T r) {
        this.rootNode = r;
    }

    @Nullable
    public Iterable<T> path(T target) {
        this.marked = Sets.newHashSet();
        this.edgeTo = Maps.newHashMap();
        dfs(this.rootNode);
        if (!this.edgeTo.containsKey(target)) {
            return null;
        }
        ArrayDeque<T> path = Queues.newArrayDeque();
        for (T n = target; n != this.rootNode; n = this.edgeTo.get(n)) {
            path.push(n);
        }
        path.push(this.rootNode);
        return path;
    }

    private void dfs(T root) {
        this.marked.add(root);
        for (T a : root.getAdjacent()) {
            if (!this.marked.contains(a)) {
                this.edgeTo.put(a, root);
                dfs(a);
            }
        }
    }

}
