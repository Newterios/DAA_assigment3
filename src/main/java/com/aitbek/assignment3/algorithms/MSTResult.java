package com.aitbek.assignment3.algorithms;

import com.aitbek.assignment3.graph.Edge;
import java.util.List;

public class MSTResult {
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final int operationsCount;
    private final double executionTimeMs;
    private final String algorithmName;

    public MSTResult(List<Edge> mstEdges, int totalCost, int operationsCount,
                     double executionTimeMs, String algorithmName) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
        this.algorithmName = algorithmName;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public int getOperationsCount() { return operationsCount; }
    public double getExecutionTimeMs() { return executionTimeMs; }
    public String getAlgorithmName() { return algorithmName; }

    @Override
    public String toString() {
        return String.format("%s: cost=%d, operations=%d, time=%.6fms",
                algorithmName, totalCost, operationsCount, executionTimeMs);
    }
}