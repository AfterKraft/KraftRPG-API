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

public class BreadthFirstPath<T extends Node<T>> {

    private final T root;
    private Set<T> marked;
    private Map<T, T> edgeTo;

    public BreadthFirstPath(T r) {
        this.root = r;
    }

    public Iterable<T> path(T target) {
        this.marked = Sets.newHashSet();
        this.marked.add(this.root);
        ArrayDeque<T> queue = Queues.newArrayDeque();
        this.edgeTo = Maps.newHashMap();
        queue.push(this.root);
        while (!queue.isEmpty()) {
            T n = queue.poll();
            for (T a : n.getAdjacent()) {
                if (!this.marked.contains(a)) {
                    this.edgeTo.put(a, n);
                    this.marked.add(a);
                    queue.add(a);
                }
            }
        }
        ArrayDeque<T> path = Queues.newArrayDeque();
        for (T n = target; n != this.root; n = this.edgeTo.get(n)) {
            path.push((T) n);
        }
        path.push(this.root);
        return path;
    }

}
