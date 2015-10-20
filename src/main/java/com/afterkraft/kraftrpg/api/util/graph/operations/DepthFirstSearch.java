/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.operations;

import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class DepthFirstSearch<T extends Node<T>> {

    private final T rootNode;
    private Set<T> marked;

    public DepthFirstSearch(T r) {
        this.rootNode = r;
    }

    public List<T> search() {
        List<T> result = Lists.newArrayList();
        this.marked = Sets.newHashSet();
        dfs(result, this.rootNode);
        return result;
    }

    private void dfs(List<T> r, T root) {
        this.marked.add(root);
        r.add(root);
        for (T a : root.getAdjacent()) {
            if (!this.marked.contains(a)) {
                dfs(r, a);
            }
        }
    }

}
