package com.aitbek.assignment3.util;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;

import java.util.HashSet;
import java.util.Set;

public class InputValidator {

    public static ValidationResult validateGraph(Graph graph) {
        Set<String> vertices = new HashSet<>(graph.getVertices());

        for (Edge edge : graph.getEdges()) {
            if (!vertices.contains(edge.getFrom())) {
                return new ValidationResult(false,
                        "Vertex '" + edge.getFrom() + "' in edge not found in graph vertices");
            }
            if (!vertices.contains(edge.getTo())) {
                return new ValidationResult(false,
                        "Vertex '" + edge.getTo() + "' in edge not found in graph vertices");
            }
        }

        for (Edge edge : graph.getEdges()) {
            if (edge.getWeight() < 0) {
                return new ValidationResult(false,
                        "Negative weight found in edge: " + edge);
            }
        }

        return new ValidationResult(true, "Graph is valid");
    }

    public static class ValidationResult {
        private final boolean valid;
        private final String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
    }
}