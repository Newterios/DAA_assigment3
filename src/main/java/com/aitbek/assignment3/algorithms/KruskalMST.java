package com.aitbek.assignment3.algorithms;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;
import com.aitbek.assignment3.util.PerformanceTracker;
import com.aitbek.assignment3.util.UnionFind;

import java.util.*;

public class KruskalMST implements MSTAlgorithm {
    private final PerformanceTracker tracker;

    public KruskalMST() {
        this.tracker = new PerformanceTracker();
    }

    @Override
    public MSTResult findMST(Graph graph) {
        tracker.startTimer();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        tracker.incrementComparisons(edges.size() * (int)(Math.log(edges.size()) / Math.log(2)));

        List<String> vertices = graph.getVertices();
        Map<String, Integer> vertexIndexMap = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            vertexIndexMap.put(vertices.get(i), i);
        }

        UnionFind uf = new UnionFind(vertices.size(), tracker);

        for (Edge edge : edges) {
            tracker.incrementEdgeProcessing();

            int u = vertexIndexMap.get(edge.getFrom());
            int v = vertexIndexMap.get(edge.getTo());

            if (!uf.connected(u, v)) {
                uf.union(u, v);
                mstEdges.add(edge);
                totalCost += edge.getWeight();

                if (mstEdges.size() == vertices.size() - 1) {
                    break;
                }
            }
        }

        tracker.stopTimer();
        return new MSTResult(mstEdges, totalCost, tracker.getTotalOperations(),
                tracker.getElapsedTimeMillis(), getAlgorithmName());
    }

    @Override
    public String getAlgorithmName() {
        return "Kruskal";
    }
}