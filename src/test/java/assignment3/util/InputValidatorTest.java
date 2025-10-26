package assignment3.util;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;
import com.aitbek.assignment3.util.InputValidator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void testValidGraph() {
        List<String> vertices = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("A", "C", 3)
        );
        Graph graph = new Graph(vertices, edges);

        InputValidator.ValidationResult result = InputValidator.validateGraph(graph);

        assertTrue(result.isValid());
        assertEquals("Graph is valid", result.getMessage());
    }

    @Test
    void testGraphWithNegativeWeight() {
        List<String> vertices = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", -2)
        );
        Graph graph = new Graph(vertices, edges);

        InputValidator.ValidationResult result = InputValidator.validateGraph(graph);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Negative weight"));
    }

    @Test
    void testDisconnectedGraph() {
        List<String> vertices = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("C", "D", 2)
        );
        Graph graph = new Graph(vertices, edges);

        InputValidator.ValidationResult result = InputValidator.validateGraph(graph);

        assertTrue(result.isValid());
    }

    @Test
    void testEmptyGraph() {
        List<String> vertices = List.of();
        List<Edge> edges = List.of();
        Graph graph = new Graph(vertices, edges);

        InputValidator.ValidationResult result = InputValidator.validateGraph(graph);

        assertTrue(result.isValid());
    }

    @Test
    void testSingleVertexGraph() {
        List<String> vertices = List.of("A");
        List<Edge> edges = List.of();
        Graph graph = new Graph(vertices, edges);

        InputValidator.ValidationResult result = InputValidator.validateGraph(graph);

        assertTrue(result.isValid());
    }
}