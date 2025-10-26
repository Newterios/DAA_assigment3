package com.aitbek.assignment3.algorithms;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;
import com.aitbek.assignment3.util.PerformanceTracker;

import java.util.*;

public class PrimMST implements MSTAlgorithm {
    private final PerformanceTracker tracker;

    public PrimMST() {
        this.tracker = new PerformanceTracker();
    }

    @Override
    public MSTResult findMST(Graph graph) {
        tracker.startTimer();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        if (graph.getVertices().isEmpty()) {
            tracker.stopTimer();
            return new MSTResult(mstEdges, 0, tracker.getTotalOperations(),
                    tracker.getElapsedTimeMillis(), getAlgorithmName());
        }

        String startVertex = graph.getVertices().get(0);
        visited.add(startVertex);

        for (Edge edge : graph.getAdjacentEdges(startVertex)) {
            pq.offer(edge);
            tracker.incrementEdgeProcessing();
        }

        while (!pq.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = pq.poll();
            tracker.incrementComparisons(1);

            String nextVertex = null;
            if (visited.contains(edge.getFrom()) && !visited.contains(edge.getTo())) {
                nextVertex = edge.getTo();
            } else if (visited.contains(edge.getTo()) && !visited.contains(edge.getFrom())) {
                nextVertex = edge.getFrom();
            }

            if (nextVertex != null) {
                visited.add(nextVertex);
                mstEdges.add(edge);
                totalCost += edge.getWeight();

                for (Edge adjEdge : graph.getAdjacentEdges(nextVertex)) {
                    tracker.incrementEdgeProcessing();
                    if (!visited.contains(adjEdge.getTo())) {
                        pq.offer(adjEdge);
                    }
                }
            }
        }

        tracker.stopTimer();
        return new MSTResult(mstEdges, totalCost, tracker.getTotalOperations(),
                tracker.getElapsedTimeMillis(), getAlgorithmName());
    }

    @Override
    public String getAlgorithmName() {
        return "Prim";
    }
}