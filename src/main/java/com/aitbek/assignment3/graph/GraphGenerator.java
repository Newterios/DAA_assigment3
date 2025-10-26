package com.aitbek.assignment3.graph;

import java.util.*;

public class GraphGenerator {
    private static final Random random = new Random();

    public List<Graph> generateSmallGraphs() {
        System.out.println("Generating small graphs...");
        List<Graph> graphs = new ArrayList<>();
        int[] sizes = {10, 15, 20, 25, 30};

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("  Small graph %d/%d: %d vertices\n", i+1, sizes.length, sizes[i]);
            graphs.add(generateConnectedGraph(sizes[i], 0.3 + 0.4 * random.nextDouble()));
        }
        return graphs;
    }

    public List<Graph> generateMediumGraphs() {
        System.out.println("Generating medium graphs...");
        List<Graph> graphs = new ArrayList<>();
        int[] sizes = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("  Medium graph %d/%d: %d vertices\n", i+1, sizes.length, sizes[i]);
            graphs.add(generateConnectedGraph(sizes[i], 0.2 + 0.3 * random.nextDouble()));
        }
        return graphs;
    }

    public List<Graph> generateLargeGraphs() {
        System.out.println("Generating large graphs...");
        List<Graph> graphs = new ArrayList<>();
        int[] sizes = {600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("  Large graph %d/%d: %d vertices\n", i+1, sizes.length, sizes[i]);
            graphs.add(generateConnectedGraph(sizes[i], 0.1 + 0.2 * random.nextDouble()));
        }
        return graphs;
    }

    public List<Graph> generateExtraLargeGraphs() {
        System.out.println("Generating extra large graphs...");
        List<Graph> graphs = new ArrayList<>();
        int[] sizes = {1600, 1800, 2000, 2200, 2400, 2600, 2800, 3000};

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("  Extra large graph %d/%d: %d vertices\n", i+1, sizes.length, sizes[i]);
            graphs.add(generateConnectedGraph(sizes[i], 0.05 + 0.15 * random.nextDouble()));
        }
        return graphs;
    }

    private Graph generateConnectedGraph(int vertexCount, double density) {
        List<String> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add("V" + i);
        }

        List<Edge> edges = new ArrayList<>();

        for (int i = 1; i < vertexCount; i++) {
            int parent = random.nextInt(i);
            int weight = random.nextInt(100) + 1;
            edges.add(new Edge(vertices.get(parent), vertices.get(i), weight));
        }

        int maxPossibleEdges = vertexCount * (vertexCount - 1) / 2;
        int targetEdges = Math.max(vertexCount - 1,
                Math.min(maxPossibleEdges, (int)(density * maxPossibleEdges)));

        if (edges.size() < targetEdges) {
            int additionalEdgesNeeded = targetEdges - edges.size();
            List<Edge> possibleEdges = generateAllPossibleEdges(vertices, edges);
            Collections.shuffle(possibleEdges, random);

            int edgesToAdd = Math.min(additionalEdgesNeeded, possibleEdges.size());
            edges.addAll(possibleEdges.subList(0, edgesToAdd));
        }

        return new Graph(vertices, edges);
    }

    private List<Edge> generateAllPossibleEdges(List<String> vertices, List<Edge> existingEdges) {
        List<Edge> possibleEdges = new ArrayList<>();
        Set<String> existingEdgeSet = new HashSet<>();

        for (Edge edge : existingEdges) {
            existingEdgeSet.add(getEdgeKey(edge.getFrom(), edge.getTo()));
        }

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                String from = vertices.get(i);
                String to = vertices.get(j);
                String edgeKey = getEdgeKey(from, to);

                if (!existingEdgeSet.contains(edgeKey)) {
                    int weight = random.nextInt(100) + 1;
                    possibleEdges.add(new Edge(from, to, weight));
                }
            }
        }

        return possibleEdges;
    }

    private String getEdgeKey(String from, String to) {
        return from.compareTo(to) < 0 ? from + ":" + to : to + ":" + from;
    }
}