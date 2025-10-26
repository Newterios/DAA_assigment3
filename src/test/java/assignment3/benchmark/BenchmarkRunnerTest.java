package assignment3.benchmark;

import com.aitbek.assignment3.benchmark.BenchmarkResult;
import com.aitbek.assignment3.benchmark.BenchmarkRunner;
import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.GraphGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BenchmarkRunnerTest {

    @Test
    void testBenchmarkRunnerWithSmallGraph() {
        GraphGenerator generator = new GraphGenerator();
        List<Graph> graphs = generator.generateSmallGraphs().subList(0, 1);
        BenchmarkRunner runner = new BenchmarkRunner();

        List<BenchmarkResult> results = runner.runBenchmarks(graphs);

        assertEquals(1, results.size());
        BenchmarkResult result = results.get(0);

        // Both algorithms should find MST with same cost
        assertEquals(result.getPrimResult().getTotalCost(),
                result.getKruskalResult().getTotalCost());

        // MST should have V-1 edges
        assertEquals(result.getVertices() - 1,
                result.getPrimResult().getMstEdges().size());
        assertEquals(result.getVertices() - 1,
                result.getKruskalResult().getMstEdges().size());

        // Execution times should be non-negative
        assertTrue(result.getPrimResult().getExecutionTimeMs() >= 0);
        assertTrue(result.getKruskalResult().getExecutionTimeMs() >= 0);

        // Operation counts should be positive
        assertTrue(result.getPrimResult().getOperationsCount() > 0);
        assertTrue(result.getKruskalResult().getOperationsCount() > 0);
    }

    @Test
    void testBenchmarkRunnerWithMultipleGraphs() {
        GraphGenerator generator = new GraphGenerator();
        List<Graph> graphs = generator.generateSmallGraphs().subList(0, 3);
        BenchmarkRunner runner = new BenchmarkRunner();

        List<BenchmarkResult> results = runner.runBenchmarks(graphs);

        assertEquals(3, results.size());

        for (BenchmarkResult result : results) {
            // For each graph, both algorithms should agree on MST cost
            assertEquals(result.getPrimResult().getTotalCost(),
                    result.getKruskalResult().getTotalCost(),
                    "MST cost mismatch for graph " + result.getGraphId());

            // Validate MST properties
            assertEquals(result.getVertices() - 1,
                    result.getPrimResult().getMstEdges().size());
            assertEquals(result.getVertices() - 1,
                    result.getKruskalResult().getMstEdges().size());
        }
    }

    @Test
    void testCSVReportGeneration() {
        GraphGenerator generator = new GraphGenerator();
        List<Graph> graphs = generator.generateSmallGraphs().subList(0, 2);
        BenchmarkRunner runner = new BenchmarkRunner();

        List<BenchmarkResult> results = runner.runBenchmarks(graphs);

        // Test CSV generation doesn't throw exceptions
        assertDoesNotThrow(() -> {
            runner.generateCSVReport(results, "test_benchmark.csv");
        });

        // Clean up
        new java.io.File("test_benchmark.csv").delete();
    }

    @Test
    void testBenchmarkWithSingleVertexGraph() {
        List<String> vertices = List.of("A");
        List<com.aitbek.assignment3.graph.Edge> edges = List.of();
        Graph graph = new Graph(vertices, edges);

        BenchmarkRunner runner = new BenchmarkRunner();
        List<BenchmarkResult> results = runner.runBenchmarks(List.of(graph));

        assertEquals(1, results.size());
        BenchmarkResult result = results.get(0);

        // MST cost should be 0 for single vertex
        assertEquals(0, result.getPrimResult().getTotalCost());
        assertEquals(0, result.getKruskalResult().getTotalCost());

        // No edges in MST for single vertex
        assertEquals(0, result.getPrimResult().getMstEdges().size());
        assertEquals(0, result.getKruskalResult().getMstEdges().size());
    }
}