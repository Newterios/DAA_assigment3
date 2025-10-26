package assignment3.algorithms;

import com.aitbek.assignment3.algorithms.KruskalMST;
import com.aitbek.assignment3.algorithms.MSTResult;
import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KruskalMSTTest {

    @Test
    void testKruskalMSTWithSmallGraph() {
        List<String> vertices = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("B", "D", 5)
        );
        Graph graph = new Graph(vertices, edges);
        KruskalMST kruskal = new KruskalMST();

        MSTResult result = kruskal.findMST(graph);

        assertEquals(6, result.getTotalCost());
        assertEquals(3, result.getMstEdges().size());
        assertEquals("Kruskal", result.getAlgorithmName());
    }

    @Test
    void testKruskalMSTWithSingleVertex() {
        List<String> vertices = Arrays.asList("A");
        List<Edge> edges = Arrays.asList();
        Graph graph = new Graph(vertices, edges);
        KruskalMST kruskal = new KruskalMST();

        MSTResult result = kruskal.findMST(graph);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getMstEdges().size());
    }
}