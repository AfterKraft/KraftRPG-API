/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.afterkraft.kraftrpg.api.CircularDependencyException;

/**
 * This is a somewhat simple Graph data structure that has directed edges and
 * allows for nodes to be removed without having to specifically loosing all
 * the children nodes of the given node. Performance is gained by utilizing
 * LinkedHashMaps as iterations are performed in virtually all operations.
 * 
 * This type of graph does not allow for cycles and actively checks for cycles
 * when
 * <ul>
 * <li>{@link #addEdge(Object, Object)}</li>
 * <li>{@link #doesCycleExist()}</li>
 * </ul>
 * 
 * @param <T>
 */
public class DirectedGraph<T> {
    /** Color used to mark unvisited nodes */
    public static final int VISIT_COLOR_WHITE = 1;

    /** Color used to mark nodes as they are first visited in DFS order */
    public static final int VISIT_COLOR_GREY = 2;

    /** Color used to mark nodes after descendants are completely visited */
    public static final int VISIT_COLOR_BLACK = 3;

    protected final LinkedHashMap<T, Vertex<T>> vertexes;

    public DirectedGraph() {
        vertexes = new LinkedHashMap<T, Vertex<T>>();
    }

    public void addVertex(T data) {
        vertexes.put(data, new Vertex<T>(data));
    }

    public void removeVertex(T data) {
        if (data == null) {
            return;
        }
        Vertex<T> vertex = vertexes.get(data);
        for (Map.Entry<Vertex<T>, Edge<T>> entry : vertex.fromNodes.entrySet()) {
            Vertex<T> parent = entry.getKey();
            parent.toNodes.remove(vertex);
            // Here we have to re-assign the previous children of the node
            // being deleted to each of the parent nodes of this node.
            for (Map.Entry<Vertex<T>, Edge<T>> childEntries : vertex.toNodes.entrySet()) {
                parent.addEdge(childEntries.getKey());
            }
        }
        for (Map.Entry<Vertex<T>, Edge<T>> entry : vertex.toNodes.entrySet()) {
            Vertex<T> child = entry.getKey();
            child.toNodes.remove(vertex);
        }
        vertexes.remove(data);
    }

    public boolean addEdge(T from, T to) throws CircularDependencyException {
        Vertex<T> vertex = vertexes.get(from);
        if (vertex == null) {
            vertex = new Vertex<T>(from);
        }
        if (!vertexes.containsKey(to)) {
            vertexes.put(to, new Vertex<T>(to));
        }
        vertex.addEdge(new Vertex<T>(to));
        vertexes.put(from, vertex);
        if (doesCycleExist()) { // We can't allow the creation of a cycle
            throw new CircularDependencyException("Could not add " + from.toString() + " as a parent of " + to.toString());
        }
        return true;
    }

    public void removeEdge(T from, T to) {
        if (vertexes.get(from) == null || vertexes.get(to) == null) {
            return;
        }
        Vertex<T> vertex = vertexes.get(from);
        vertex.removeEdge(vertexes.get(to));
        vertexes.put(from, vertex);
    }

    public boolean doesCycleExist() {
        ArrayList<Edge<T>> cycleEdges = new ArrayList<Edge<T>>();
        // Mark all verticies as white
        for (Map.Entry<T, Vertex<T>> entry : vertexes.entrySet()) {
            Vertex<T> v = entry.getValue();
            v.setMarkState(VISIT_COLOR_WHITE);
        }
        // Then search for the cycles
        for (Map.Entry<T, Vertex<T>> entry : vertexes.entrySet()) {
            Vertex<T> v = entry.getValue();
            visit(v, cycleEdges);
        }

        return !cycleEdges.isEmpty();
    }

    private void visit(Vertex<T> v, ArrayList<Edge<T>> cycleEdges) {
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
        protected final LinkedHashMap<Vertex<T>, Edge<T>> fromNodes;
        protected final LinkedHashMap<Vertex<T>, Edge<T>> toNodes;
        private boolean mark;
        private int markState;

        Vertex(T data) {
            this.data = data;
            this.name = data.toString();
            fromNodes = new LinkedHashMap<Vertex<T>, Edge<T>>();
            toNodes = new LinkedHashMap<Vertex<T>, Edge<T>>();
        }

        public Vertex<T> addEdge(Vertex<T> vertex) {
            Edge<T> edge = new Edge<T>(this, vertex);
            toNodes.put(vertex, edge);
            vertex.fromNodes.put(this, edge);
            return this;
        }

        public Vertex removeEdge(Vertex<T> vertex) {
            toNodes.remove(vertex);
            vertex.fromNodes.remove(this);
            return this;
        }

        /**
         * Has this vertex been marked during a visit
         * 
         * @return true is visit has been called
         */
        boolean visited() {
            return mark;
        }

        /**
         * Get the mark state value.
         * 
         * @return the mark state
         */
        int getMarkState() {
            return markState;
        }

        /**
         * Set the mark state to state.
         * 
         * @param state the state
         */
        void setMarkState(int state) {
            markState = state;
        }

        /**
         * Visit the vertex and set the mark flag to true.
         * 
         */
        void visit() {
            mark();
        }

        /**
         * Set the vertex mark flag.
         * 
         */
        void mark() {
            mark = true;
        }

        /**
         * Clear the visited mark flag.
         * 
         */
        void clearMark() {
            mark = false;
        }

        @Override
        public int hashCode() {
            return this.data.hashCode();
        }

        public String toString() {
            return name;
        }

    }

    static class Edge<T> {
        protected Vertex<T> from, to;

        Edge(Vertex<T> from, Vertex<T> to) {
            this.from = from;
            this.to = to;
        }

        public String toString() {
            return from.toString() + " ==> " + to.toString();
        }
    }

}