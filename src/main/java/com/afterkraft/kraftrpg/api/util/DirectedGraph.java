/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api.util;

import java.util.ArrayList;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.afterkraft.kraftrpg.api.CircularDependencyException;

/**
 * This is a somewhat simple Graph data structure that has directed edges and allows for nodes to be
 * removed without having to specifically loosing all the children nodes of the given node.
 * Performance is gained by utilizing LinkedHashMaps as iterations are performed in virtually all
 * operations.  This type of graph does not allow for cycles and actively checks for cycles when
 * <ul> <li>{@link #addEdge(Object, Object)}</li> <li>{@link #doesCycleExist()}</li> </ul>
 *
 * @param <T> The type of graph
 */
public class DirectedGraph<T> {
    /**
     * Color used to mark unvisited nodes
     */
    public static final int VISIT_COLOR_WHITE = 1;

    /**
     * Color used to mark nodes as they are first visited in DFS order
     */
    public static final int VISIT_COLOR_GREY = 2;

    /**
     * Color used to mark nodes after descendants are completely visited
     */
    public static final int VISIT_COLOR_BLACK = 3;

    protected final Map<T, Vertex<T>> vertexes;

    /**
     * Creates a new directed graph.
     */
    public DirectedGraph() {
        this.vertexes = Maps.newLinkedHashMap();
    }

    /**
     * Adds the given vertex to this graph.
     *
     * @param data The data
     */
    public void addVertex(T data) {
        checkNotNull(data);
        this.vertexes.put(data, new Vertex<>(data));
    }

    /**
     * Removes the vertex from this graph. This performs a full check of all edges for removing
     * linkage to the given vertex.
     *
     * @param data The data to remove
     */
    public void removeVertex(T data) {
        checkNotNull(data);
        Vertex<T> vertex = this.vertexes.get(data);
        for (Map.Entry<Vertex<T>, Edge<T>> entry : vertex.fromNodes
                .entrySet()) {
            Vertex<T> parent = entry.getKey();
            parent.toNodes.remove(vertex);
            // Here we have to re-assign the previous children of the node
            // being deleted to each of the parent nodes of this node.
            for (Map.Entry<Vertex<T>, Edge<T>> childEntries : vertex.toNodes
                    .entrySet()) {
                parent.addEdge(childEntries.getKey());
            }
        }
        for (Map.Entry<Vertex<T>, Edge<T>> entry : vertex.toNodes.entrySet()) {
            Vertex<T> child = entry.getKey();
            child.toNodes.remove(vertex);
        }
        this.vertexes.remove(data);
    }

    /**
     * Adds an edge between the two vertexes.
     *
     * @param from The vertex being depended upon
     * @param to   The vertex depending on the from vertex
     *
     * @return True if successful
     * @throws CircularDependencyException If there is a circular dependency occuring, an exception
     *                                     is thrown
     */
    public boolean addEdge(T from, T to) throws CircularDependencyException {
        checkNotNull(from);
        checkNotNull(to);
        Vertex<T> vertex = this.vertexes.get(from);
        if (vertex == null) {
            vertex = new Vertex<>(from);
        }
        if (!this.vertexes.containsKey(to)) {
            this.vertexes.put(to, new Vertex<>(to));
        }
        vertex.addEdge(new Vertex<>(to));
        this.vertexes.put(from, vertex);
        if (doesCycleExist()) { // We can't allow the creation of a cycle
            throw new CircularDependencyException(
                    "Could not add " + from.toString()
                            + " as a parent of " + to.toString());
        }
        return true;
    }

    /**
     * Removes the given dependency between the two edges.
     *
     * @param from The vertex being depended on
     * @param to   The vertex depending on the from vertex
     */
    public void removeEdge(T from, T to) {
        checkNotNull(from);
        checkNotNull(to);
        if (this.vertexes.get(from) == null || this.vertexes.get(to) == null) {
            return;
        }
        Vertex<T> vertex = this.vertexes.get(from);
        vertex.removeEdge(this.vertexes.get(to));
        this.vertexes.put(from, vertex);
    }

    /**
     * Checks whether this graph contains a path that cycles itself.
     *
     * <p>The performance costs of this method involves a full depth first search.</p>
     *
     * @return True if there is a cycle that exists
     */
    public boolean doesCycleExist() {
        ArrayList<Edge<T>> cycleEdges = Lists.newArrayList();
        // Mark all verticies as white
        for (Map.Entry<T, Vertex<T>> entry : this.vertexes.entrySet()) {
            Vertex<T> v = entry.getValue();
            v.setMarkState(VISIT_COLOR_WHITE);
        }
        // Then search for the cycles
        for (Map.Entry<T, Vertex<T>> entry : this.vertexes.entrySet()) {
            Vertex<T> v = entry.getValue();
            visit(v, cycleEdges);
        }

        return !cycleEdges.isEmpty();
    }

    private void visit(Vertex<T> v, ArrayList<Edge<T>> cycleEdges) {
        checkNotNull(v);
        checkNotNull(cycleEdges);
        v.setMarkState(VISIT_COLOR_GREY);
        for (Map.Entry<Vertex<T>, Edge<T>> entry : v.toNodes.entrySet()) {
            Edge<T> e = entry.getValue();
            Vertex<T> u = entry.getKey();
            if (u.getMarkState() == VISIT_COLOR_GREY) {
                // A cycle Edge<T>
                cycleEdges.add(e);
            } else if (u.getMarkState() == VISIT_COLOR_WHITE) {
                visit(u, cycleEdges);
            }
        }
        v.setMarkState(VISIT_COLOR_BLACK);
    }

    static class Vertex<T> {
        protected final T data;
        protected final String name;
        protected final Map<Vertex<T>, Edge<T>> fromNodes;
        protected final Map<Vertex<T>, Edge<T>> toNodes;
        private boolean mark;
        private int markState;

        Vertex(T data) {
            checkNotNull(data);
            this.data = data;
            this.name = data.toString();
            this.fromNodes = Maps.newLinkedHashMap();
            this.toNodes = Maps.newLinkedHashMap();
        }

        public Vertex<T> addEdge(Vertex<T> vertex) {
            checkNotNull(vertex);
            Edge<T> edge = new Edge<>(this, vertex);
            this.toNodes.put(vertex, edge);
            vertex.fromNodes.put(this, edge);
            return this;
        }

        public Vertex<T> removeEdge(Vertex<T> vertex) {
            checkNotNull(vertex);
            this.toNodes.remove(vertex);
            vertex.fromNodes.remove(this);
            return this;
        }

        /**
         * Has this vertex been marked during a visit
         *
         * @return true is visit has been called
         */
        boolean visited() {
            return this.mark;
        }

        /**
         * Get the mark state value.
         *
         * @return the mark state
         */
        int getMarkState() {
            return this.markState;
        }

        /**
         * Set the mark state to state.
         *
         * @param state the state
         */
        void setMarkState(int state) {
            this.markState = state;
        }

        /**
         * Visit the vertex and set the mark flag to true.
         */
        void visit() {
            mark();
        }

        /**
         * Set the vertex mark flag.
         */
        void mark() {
            this.mark = true;
        }

        /**
         * Clear the visited mark flag.
         */
        void clearMark() {
            this.mark = false;
        }

        @Override
        public int hashCode() {
            return this.data.hashCode();
        }

        public String toString() {
            return this.name;
        }

    }

    static class Edge<T> {
        protected Vertex<T> from;
        protected Vertex<T> to;

        Edge(Vertex<T> from, Vertex<T> to) {
            checkNotNull(from);
            checkNotNull(to);
            this.from = from;
            this.to = to;
        }

        public String toString() {
            return this.from.toString() + " ==> " + this.to.toString();
        }
    }

}
