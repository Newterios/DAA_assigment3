package assignment3.graph;

import com.aitbek.assignment3.graph.Edge;
import com.aitbek.assignment3.graph.Graph;

import java.util.*;

public class GraphGeneratorSimple {
    private static final Random random = new Random();

    // Быстрая генерация только маленьких графов для тестирования
    public List<Graph> generateTestGraphs() {
        System.out.println("Generating test graphs (small only)...");
        List<Graph> graphs = new ArrayList<>();
        int[] sizes = {10, 15, 20}; // Только маленькие для теста

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("  Test graph %d/%d: %d vertices\n", i+1, sizes.length, sizes[i]);
            graphs.add(generateConnectedGraphFast(sizes[i], 0.3));
        }
        return graphs;
    }

    private Graph generateConnectedGraphFast(int vertexCount, double density) {
        List<String> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add("V" + i);
        }

        List<Edge> edges = new ArrayList<>();

        // 1. Spanning tree для связности
        for (int i = 1; i < vertexCount; i++) {
            int parent = random.nextInt(i);
            edges.add(new Edge(vertices.get(parent), vertices.get(i), random.nextInt(100) + 1));
        }

        // 2. Ограниченное количество дополнительных рёбер для скорости
        int maxAdditionalEdges = Math.min(vertexCount * 2, vertexCount * (vertexCount - 1) / 2 - edges.size());
        int additionalEdges = Math.min(maxAdditionalEdges, (int)(density * vertexCount * vertexCount));

        for (int i = 0; i < additionalEdges; i++) {
            int u = random.nextInt(vertexCount);
            int v = random.nextInt(vertexCount);
            if (u != v) {
                String from = vertices.get(u);
                String to = vertices.get(v);
                // Простая проверка без сложных структур
                boolean exists = edges.stream().anyMatch(e ->
                        (e.getFrom().equals(from) && e.getTo().equals(to)) ||
                                (e.getFrom().equals(to) && e.getTo().equals(from)));

                if (!exists) {
                    edges.add(new Edge(from, to, random.nextInt(100) + 1));
                }
            }
        }

        return new Graph(vertices, edges);
    }
}