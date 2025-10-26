package com.aitbek.assignment3.algorithms;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.util.PerformanceTracker;
import java.util.List;

public interface MSTAlgorithm {
    MSTResult findMST(Graph graph);
    String getAlgorithmName();
}