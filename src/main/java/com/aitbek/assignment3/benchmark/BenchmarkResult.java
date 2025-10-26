package com.aitbek.assignment3.benchmark;

import com.aitbek.assignment3.algorithms.MSTResult;

public class BenchmarkResult {
    private final int graphId;
    private final int vertices;
    private final int edges;
    private final MSTResult primResult;
    private final MSTResult kruskalResult;

    public BenchmarkResult(int graphId, int vertices, int edges,
                           MSTResult primResult, MSTResult kruskalResult) {
        this.graphId = graphId;
        this.vertices = vertices;
        this.edges = edges;
        this.primResult = primResult;
        this.kruskalResult = kruskalResult;
    }

    public int getGraphId() { return graphId; }
    public int getVertices() { return vertices; }
    public int getEdges() { return edges; }
    public MSTResult getPrimResult() { return primResult; }
    public MSTResult getKruskalResult() { return kruskalResult; }

    public double getDensity() {
        int maxEdges = vertices * (vertices - 1) / 2;
        return (double) edges / maxEdges;
    }

    @Override
    public String toString() {
        return String.format("Graph %d: %d vertices, %d edges, Density: %.3f\n  %s\n  %s",
                graphId, vertices, edges, getDensity(),
                primResult, kruskalResult);
    }
}