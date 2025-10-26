package com.aitbek.assignment3.cli;

import com.aitbek.assignment3.graph.Graph;
import com.aitbek.assignment3.graph.GraphGenerator;
import com.aitbek.assignment3.benchmark.BenchmarkRunner;
import com.aitbek.assignment3.benchmark.BenchmarkResult;
import com.aitbek.assignment3.util.JSONHandler;
import com.aitbek.assignment3.util.InputValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {
    private final Scanner scanner;
    private final GraphGenerator graphGenerator;
    private final BenchmarkRunner benchmarkRunner;

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.graphGenerator = new GraphGenerator();
        this.benchmarkRunner = new BenchmarkRunner();
    }

    public void start() {
        System.out.println("=== MST Algorithm Benchmark System ===");

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    generateInputFiles();
                    break;
                case "2":
                    runBenchmarks();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Generate input files");
        System.out.println("2. Run benchmarks");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private void generateInputFiles() {
        try {
            System.out.println("\nGenerating input files...");

            new java.io.File("resources/input").mkdirs();
            new java.io.File("resources/output").mkdirs();
            new java.io.File("resources/output/results").mkdirs();
            new java.io.File("resources/output/benchmarks").mkdirs();

            System.out.println("Generating small graphs (5 graphs: 10-30 vertices)...");
            List<Graph> smallGraphs = graphGenerator.generateSmallGraphs();
            JSONHandler.saveGraphsToFile(smallGraphs, "resources/input/small.json");

            System.out.println("Generating medium graphs (10 graphs: up to 500 vertices)...");
            List<Graph> mediumGraphs = graphGenerator.generateMediumGraphs();
            JSONHandler.saveGraphsToFile(mediumGraphs, "resources/input/medium.json");

            System.out.println("Generating large graphs (10 graphs: up to 1500 vertices)...");
            List<Graph> largeGraphs = graphGenerator.generateLargeGraphs();
            JSONHandler.saveGraphsToFile(largeGraphs, "resources/input/large.json");

            System.out.println("Generating extra large graphs (8 graphs: up to 3000 vertices)...");
            List<Graph> extraGraphs = graphGenerator.generateExtraLargeGraphs();
            JSONHandler.saveGraphsToFile(extraGraphs, "resources/input/extra.json");

            System.out.println("\n✓ All input files generated successfully!");

        } catch (IOException e) {
            System.err.println("✗ Error generating input files: " + e.getMessage());
        }
    }

    private void runBenchmarks() {
        try {
            System.out.println("\nRunning benchmarks (single execution for accurate comparison)...");

            System.out.println("Loading graphs from input files...");
            List<Graph> smallGraphs = safeLoadGraphs("resources/input/small.json", "Small");
            List<Graph> mediumGraphs = safeLoadGraphs("resources/input/medium.json", "Medium");
            List<Graph> largeGraphs = safeLoadGraphs("resources/input/large.json", "Large");
            List<Graph> extraGraphs = safeLoadGraphs("resources/input/extra.json", "Extra");

            if (!smallGraphs.isEmpty()) {
                System.out.println("\nBenchmarking small graphs...");
                List<BenchmarkResult> smallResults = benchmarkRunner.runBenchmarks(smallGraphs);
                JSONHandler.saveResultsToFile(smallResults, "resources/output/results/small_results.json");
                benchmarkRunner.generateCSVReport(smallResults, "resources/output/benchmarks/small_benchmark.csv");
                printBenchmarkSummary(smallResults, "Small");
            }

            if (!mediumGraphs.isEmpty()) {
                System.out.println("\nBenchmarking medium graphs...");
                List<BenchmarkResult> mediumResults = benchmarkRunner.runBenchmarks(mediumGraphs);
                JSONHandler.saveResultsToFile(mediumResults, "resources/output/results/medium_results.json");
                benchmarkRunner.generateCSVReport(mediumResults, "resources/output/benchmarks/medium_benchmark.csv");
                printBenchmarkSummary(mediumResults, "Medium");
            }

            if (!largeGraphs.isEmpty()) {
                System.out.println("\nBenchmarking large graphs...");
                List<BenchmarkResult> largeResults = benchmarkRunner.runBenchmarks(largeGraphs);
                JSONHandler.saveResultsToFile(largeResults, "resources/output/results/large_results.json");
                benchmarkRunner.generateCSVReport(largeResults, "resources/output/benchmarks/large_benchmark.csv");
                printBenchmarkSummary(largeResults, "Large");
            }

            if (!extraGraphs.isEmpty()) {
                System.out.println("\nBenchmarking extra large graphs...");
                List<BenchmarkResult> extraResults = benchmarkRunner.runBenchmarks(extraGraphs);
                JSONHandler.saveResultsToFile(extraResults, "resources/output/results/extra_results.json");
                benchmarkRunner.generateCSVReport(extraResults, "resources/output/benchmarks/extra_benchmark.csv");
                printBenchmarkSummary(extraResults, "Extra");
            }

            System.out.println("\n✓ Benchmarks completed successfully!");

        } catch (Exception e) {
            System.err.println("✗ Error running benchmarks: " + e.getMessage());
        }
    }

    private List<Graph> safeLoadGraphs(String filename, String category) {
        try {
            List<Graph> graphs = JSONHandler.loadGraphsFromFile(filename);
            System.out.printf("✓ Loaded %d %s graphs\n", graphs.size(), category.toLowerCase());
            return graphs;
        } catch (IOException e) {
            System.err.printf("✗ Error loading %s graphs: %s\n", category, e.getMessage());
            return List.of();
        }
    }

    private void printBenchmarkSummary(List<BenchmarkResult> results, String category) {
        if (results.isEmpty()) return;

        double primTotalTime = 0;
        double kruskalTotalTime = 0;
        int primTotalOps = 0;
        int kruskalTotalOps = 0;

        for (BenchmarkResult result : results) {
            primTotalTime += result.getPrimResult().getExecutionTimeMs();
            kruskalTotalTime += result.getKruskalResult().getExecutionTimeMs();
            primTotalOps += result.getPrimResult().getOperationsCount();
            kruskalTotalOps += result.getKruskalResult().getOperationsCount();
        }

        System.out.printf("%s Graphs Summary (%d graphs):\n", category, results.size());
        System.out.printf("  Prim:    avg time=%.6fms, avg ops=%.0f\n",
                primTotalTime / results.size(), (double) primTotalOps / results.size());
        System.out.printf("  Kruskal: avg time=%.6fms, avg ops=%.0f\n",
                kruskalTotalTime / results.size(), (double) kruskalTotalOps / results.size());
    }
}