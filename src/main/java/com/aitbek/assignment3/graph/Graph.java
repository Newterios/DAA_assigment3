package com.aitbek.assignment3.graph;

import java.util.*;

public class Graph {
    private final List<String> vertices;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
        this.adjacencyList = new HashMap<>();
        buildAdjacencyList();
    }

    private void buildAdjacencyList() {
        for (String vertex : vertices) {
            adjacencyList.put(vertex, new ArrayList<>());
        }

        for (Edge edge : edges) {
            if (adjacencyList.containsKey(edge.getFrom()) && adjacencyList.containsKey(edge.getTo())) {
                adjacencyList.get(edge.getFrom()).add(edge);
                adjacencyList.get(edge.getTo()).add(
                        new Edge(edge.getTo(), edge.getFrom(), edge.getWeight())
                );
            }
        }
    }

    public List<String> getVertices() { return Collections.unmodifiableList(vertices); }
    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
    public List<Edge> getAdjacentEdges(String vertex) {
        return Collections.unmodifiableList(adjacencyList.getOrDefault(vertex, new ArrayList<>()));
    }
    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return edges.size(); }

    public boolean containsVertex(String vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public String toString() {
        return String.format("Graph{vertices=%d, edges=%d}", vertices.size(), edges.size());
    }
}