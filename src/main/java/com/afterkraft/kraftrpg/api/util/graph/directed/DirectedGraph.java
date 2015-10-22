/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph.directed;

import com.afterkraft.kraftrpg.api.util.graph.DataNode;
import com.afterkraft.kraftrpg.api.util.graph.IGraph;
import com.afterkraft.kraftrpg.api.util.graph.Node;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class DirectedGraph<T extends Node<T>> implements IGraph<T> {
    
    protected final List<T> nodes = Lists.newArrayList();

    public DirectedGraph() {

    }

    @Override
    public int getNodeCount() {
        return this.nodes.size();
    }

    @Override
    public int getEdgeCount() {
        int count = 0;
        for (T n : this.nodes) {
            count += n.getEdgeCount();
        }
        return count;
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
    }

    @Override
    public Iterable<T> getNodes() {
        return this.nodes;
    }

    public DirectedGraph<T> reverse() {
        DirectedGraph<T> rev = new DirectedGraph<>();
        Map<T, T> siblings = Maps.newHashMap();
        for (T n : this.nodes) {
            T b = n.clone();
            siblings.put(n, b);
        }
        for (T n : this.nodes) {
            for (T b : n.getAdjacent()) {
                rev.addEdge(siblings.get(b), siblings.get(n));
            }
        }
        return rev;
    }

    @Override
    public void add(T n) {
        this.nodes.add(n);
    }

    @Override
    public void delete(T n) {
        for (T b : this.nodes) {
            b.deleteEdge(n);
        }
        this.nodes.remove(n);
    }

    @Override
    public String toString() {
        String s = getNodeCount() + "\n" + getEdgeCount() + "\n";
        for (T n : this.nodes) {
            if (n instanceof DataNode) {
                s += ((DataNode<?>) n).getData().toString() + " ";
            } else {
                s += this.nodes.indexOf(n) + " ";
            }
            for (T a : n.getAdjacent()) {
                s += this.nodes.indexOf(a) + " ";
            }
            s += "\n";
        }
        return s;
    }

}
