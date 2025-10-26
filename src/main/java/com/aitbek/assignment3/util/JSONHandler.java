package com.aitbek.assignment3.util;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.Edge;
import com.aitbek.assignment3.benchmark.BenchmarkResult;
import com.aitbek.assignment3.algorithms.MSTResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class JSONHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static class GraphInput {
        public int id;
        public List<String> nodes;
        public List<EdgeInput> edges;
    }

    public static class EdgeInput {
        public String from;
        public String to;
        public int weight;
    }

    public static class OutputWrapper {
        public List<GraphOutput> results = new ArrayList<>();
    }

    public static class GraphOutput {
        public int graph_id;
        public InputStats input_stats;
        public AlgorithmResult prim;
        public AlgorithmResult kruskal;
    }

    public static class InputStats {
        public int vertices;
        public int edges;

        public InputStats(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
        }
    }

    public static class AlgorithmResult {
        public List<EdgeOutput> mst_edges;
        public int total_cost;
        public int operations_count;
        public double execution_time_ms;

        public AlgorithmResult(MSTResult result) {
            this.mst_edges = new ArrayList<>();
            for (Edge edge : result.getMstEdges()) {
                this.mst_edges.add(new EdgeOutput(edge));
            }
            this.total_cost = result.getTotalCost();
            this.operations_count = result.getOperationsCount();
            this.execution_time_ms = result.getExecutionTimeMs();
        }
    }

    public static class EdgeOutput {
        public String from;
        public String to;
        public int weight;

        public EdgeOutput(Edge edge) {
            this.from = edge.getFrom();
            this.to = edge.getTo();
            this.weight = edge.getWeight();
        }
    }

    public static void saveGraphsToFile(List<Graph> graphs, String filename) throws IOException {
        List<GraphInput> graphInputs = new ArrayList<>();

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            GraphInput input = new GraphInput();
            input.id = i + 1;
            input.nodes = graph.getVertices();
            input.edges = new ArrayList<>();

            for (Edge edge : graph.getEdges()) {
                EdgeInput edgeInput = new EdgeInput();
                edgeInput.from = edge.getFrom();
                edgeInput.to = edge.getTo();
                edgeInput.weight = edge.getWeight();
                input.edges.add(edgeInput);
            }
            graphInputs.add(input);
        }

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("graphs", graphInputs);

        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(wrapper, writer);
        }
    }

    public static List<Graph> loadGraphsFromFile(String filename) throws IOException {
        try (Reader reader = new FileReader(filename)) {
            Type mapType = new TypeToken<Map<String, List<GraphInput>>>(){}.getType();
            Map<String, List<GraphInput>> wrapper = gson.fromJson(reader, mapType);

            List<GraphInput> graphInputs = wrapper.get("graphs");
            List<Graph> graphs = new ArrayList<>();

            for (GraphInput input : graphInputs) {
                List<Edge> edges = new ArrayList<>();
                for (EdgeInput edgeInput : input.edges) {
                    edges.add(new Edge(edgeInput.from, edgeInput.to, edgeInput.weight));
                }
                graphs.add(new Graph(input.nodes, edges));
            }
            return graphs;
        }
    }

    public static void saveResultsToFile(List<BenchmarkResult> results, String filename) throws IOException {
        OutputWrapper output = new OutputWrapper();

        for (BenchmarkResult result : results) {
            GraphOutput graphOutput = new GraphOutput();
            graphOutput.graph_id = result.getGraphId();
            graphOutput.input_stats = new InputStats(result.getVertices(), result.getEdges());
            graphOutput.prim = new AlgorithmResult(result.getPrimResult());
            graphOutput.kruskal = new AlgorithmResult(result.getKruskalResult());
            output.results.add(graphOutput);
        }

        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(output, writer);
        }
    }
}