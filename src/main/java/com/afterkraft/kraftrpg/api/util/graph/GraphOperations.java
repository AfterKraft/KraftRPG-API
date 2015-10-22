/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.util.graph;

import com.afterkraft.kraftrpg.api.util.graph.directed.DirectedGraph;
import com.afterkraft.kraftrpg.api.util.graph.operations.BreadthFirstPath;
import com.afterkraft.kraftrpg.api.util.graph.operations.BreadthFirstSearch;
import com.afterkraft.kraftrpg.api.util.graph.operations.CycleDetector;
import com.afterkraft.kraftrpg.api.util.graph.operations.DepthFirstPath;
import com.afterkraft.kraftrpg.api.util.graph.operations.DepthFirstSearch;
import com.afterkraft.kraftrpg.api.util.graph.operations.TopologicalOrder;
import com.afterkraft.kraftrpg.api.util.graph.undirected.UndirectedGraph;
import com.google.common.collect.Lists;

import java.util.List;

import javax.annotation.Nullable;

public final class GraphOperations {

    @Nullable
    public static <T extends Node<T>> T getMaxDegreeNode(IGraph<T> graph) {
        if (graph.getNodeCount() == 0) {
            return null;
        }
        int max = -1;
        T m = null;
        for (T n : graph.getNodes()) {
            if (n.getEdgeCount() > max) {
                max = n.getEdgeCount();
                m = n;
            }
        }
        return m;
    }

    @Nullable
    public static <T extends Node<T>> T getMinDegreeNode(IGraph<T> g) {
        if (g.getNodeCount() == 0) {
            return null;
        }
        int min = Integer.MAX_VALUE;
        T m = null;
        for (T n : g.getNodes()) {
            if (n.getEdgeCount() < min) {
                min = n.getEdgeCount();
                m = n;
            }
        }
        return m;
    }

    public static double averageDegree(IGraph<?> graph) {
        return 2.0 * graph.getEdgeCount() / graph.getNodeCount();
    }

    public static <T extends Node<T>> Iterable<T> dfs(T root) {
        return new DepthFirstSearch<>(root).search();
    }

    @Nullable
    public static <T extends Node<T>> Iterable<T> dfspath(T root, T target) {
        return new DepthFirstPath<>(root).path(target);
    }

    public static <T extends Node<T>> Iterable<T> bfs(T root) {
        return new BreadthFirstSearch<>(root).search();
    }

    public static <T extends Node<T>> Iterable<T> bfspath(T root, T target) {
        return new BreadthFirstPath<>(root).path(target);
    }

    public static <T extends Node<T>> List<List<T>> components(UndirectedGraph<T> graph) {
        List<T> all = Lists.newArrayList(graph.getNodes());
        List<List<T>> result = Lists.newArrayList();

        while (!all.isEmpty()) {
            T root = all.get(0);
            List<T> connected = new DepthFirstSearch<>(root).search();
            result.add(connected);
            all.removeAll(connected);
        }

        return result;
    }

    public static <T extends Node<T>> boolean hasCycle(DirectedGraph<T> graph) {
        return new CycleDetector<>(graph).hasCycle();
    }

    @Nullable
    public static <T extends Node<T>> Iterable<T> topological(DirectedGraph<T> graph, T root) {
        return new TopologicalOrder<>(graph).order(root);
    }

    private GraphOperations() {

    }
}
