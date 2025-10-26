package assignment3.algorithms;

import com.aitbek.assignment3.algorithms.MSTResult;
import com.aitbek.assignment3.algorithms.PrimMST;
import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimMSTTest {

    @Test
    void testPrimMSTWithSmallGraph() {
        List<String> vertices = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("B", "D", 5)
        );
        Graph graph = new Graph(vertices, edges);
        PrimMST prim = new PrimMST();

        MSTResult result = prim.findMST(graph);

        assertEquals(6, result.getTotalCost());
        assertEquals(3, result.getMstEdges().size());
        assertEquals("Prim", result.getAlgorithmName());
    }

    @Test
    void testPrimMSTWithSingleVertex() {
        List<String> vertices = Arrays.asList("A");
        List<Edge> edges = Arrays.asList();
        Graph graph = new Graph(vertices, edges);
        PrimMST prim = new PrimMST();

        MSTResult result = prim.findMST(graph);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getMstEdges().size());
    }
}