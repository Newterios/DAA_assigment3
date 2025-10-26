package com.aitbek.assignment3.util;

public class UnionFind {
    private int[] parent;
    private int[] rank;
    private final PerformanceTracker tracker;

    public UnionFind(int size, PerformanceTracker tracker) {
        this.parent = new int[size];
        this.rank = new int[size];
        this.tracker = tracker;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        tracker.incrementFindOperations();
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        tracker.incrementUnionOperations();
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}