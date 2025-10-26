package com.aitbek.assignment3.benchmark;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.algorithms.MSTAlgorithm;
import com.aitbek.assignment3.algorithms.PrimMST;
import com.aitbek.assignment3.algorithms.KruskalMST;
import com.aitbek.assignment3.algorithms.MSTResult;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {

    public List<BenchmarkResult> runBenchmarks(List<Graph> graphs) {
        List<BenchmarkResult> results = new ArrayList<>();
        int graphId = 1;

        for (Graph graph : graphs) {
            System.out.printf("Benchmarking graph %d: %d vertices, %d edges...\n",
                    graphId, graph.getVertexCount(), graph.getEdgeCount());

            MSTAlgorithm prim = new PrimMST();
            MSTResult primResult = prim.findMST(graph);

            MSTAlgorithm kruskal = new KruskalMST();
            MSTResult kruskalResult = kruskal.findMST(graph);

            validateMSTProperties(graph, primResult, kruskalResult, graphId);

            BenchmarkResult result = new BenchmarkResult(
                    graphId, graph.getVertexCount(), graph.getEdgeCount(),
                    primResult, kruskalResult
            );
            results.add(result);

            System.out.printf("  Completed: Prim=%.6fms, Kruskal=%.6fms\n",
                    primResult.getExecutionTimeMs(), kruskalResult.getExecutionTimeMs());

            graphId++;
        }

        return results;
    }

    private void validateMSTProperties(Graph graph, MSTResult primResult, MSTResult kruskalResult, int graphId) {
        if (primResult.getTotalCost() != kruskalResult.getTotalCost()) {
            System.err.printf("WARNING: Cost mismatch for graph %d: Prim=%d, Kruskal=%d\n",
                    graphId, primResult.getTotalCost(), kruskalResult.getTotalCost());
        }

        int expectedEdges = graph.getVertexCount() - 1;
        if (primResult.getMstEdges().size() != expectedEdges) {
            System.err.printf("WARNING: Prim MST has %d edges, expected %d\n",
                    primResult.getMstEdges().size(), expectedEdges);
        }
        if (kruskalResult.getMstEdges().size() != expectedEdges) {
            System.err.printf("WARNING: Kruskal MST has %d edges, expected %d\n",
                    kruskalResult.getMstEdges().size(), expectedEdges);
        }

        if (primResult.getExecutionTimeMs() < 0 || kruskalResult.getExecutionTimeMs() < 0) {
            System.err.printf("WARNING: Negative execution time for graph %d\n", graphId);
        }

        if (primResult.getOperationsCount() < 0 || kruskalResult.getOperationsCount() < 0) {
            System.err.printf("WARNING: Negative operation count for graph %d\n", graphId);
        }
    }

    public void generateCSVReport(List<BenchmarkResult> results, String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
            writer.println("GraphID,Vertices,Edges,Density,Prim_Cost,Prim_Time,Prim_Operations,Kruskal_Cost,Kruskal_Time,Kruskal_Operations");

            for (BenchmarkResult result : results) {
                writer.printf("%d,%d,%d,%.4f,%d,%.6f,%d,%d,%.6f,%d\n",
                        result.getGraphId(),
                        result.getVertices(),
                        result.getEdges(),
                        result.getDensity(),
                        result.getPrimResult().getTotalCost(),
                        result.getPrimResult().getExecutionTimeMs(),
                        result.getPrimResult().getOperationsCount(),
                        result.getKruskalResult().getTotalCost(),
                        result.getKruskalResult().getExecutionTimeMs(),
                        result.getKruskalResult().getOperationsCount()
                );
            }
        } catch (java.io.IOException e) {
            System.err.println("Error writing CSV report: " + e.getMessage());
        }
    }
}