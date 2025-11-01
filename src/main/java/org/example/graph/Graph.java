package org.example.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int n;
    private final List<List<WeightedEdge>> adj;

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public int n() {
        return n;
    }

    public List<List<WeightedEdge>> adj() {
        return adj;
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new WeightedEdge(u, v, w));
    }

    public int m() {
        int m = 0;
        for (var list : adj) m += list.size();
        return m;
    }
}