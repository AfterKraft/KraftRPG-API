/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.operations;

import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;

public class BreadthFirstSearch<T extends Node<T>> {

    private final T root;
    private Set<T> marked;

    public BreadthFirstSearch(T r) {
        this.root = r;
    }

    public Iterable<T> search() {
        this.marked = Sets.newHashSet();
        this.marked.add(this.root);
        List<T> result = Lists.newArrayList();
        ArrayDeque<T> queue = Queues.newArrayDeque();
        result.add(this.root);
        queue.push(this.root);
        while (!queue.isEmpty()) {
            T n = queue.poll();
            for (T a : n.getAdjacent()) {
                if (!this.marked.contains(a)) {
                    this.marked.add(a);
                    queue.add(a);
                    result.add(a);
                }
            }
        }
        return result;
    }

}
