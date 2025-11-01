package org.example.graph.scc;

import org.example.common.Metrics;
import org.example.graph.Graph;

import java.util.*;

public class TarjanSCC {

    private final Graph g;
    private final Metrics metrics;
    private final int[] disc;
    private final int[] low;
    private final int[] compId;
    private final boolean[] onStack;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private int time = 0;
    private int compCount = 0;
    private final List<List<Integer>> components = new ArrayList<>();

    public TarjanSCC(Graph g, Metrics metrics) {
        this.g = Objects.requireNonNull(g, "graph");
        this.metrics = metrics;
        int n = g.n();
        this.disc = new int[n];
        this.low = new int[n];
        this.compId = new int[n];
        this.onStack = new boolean[n];
        Arrays.fill(disc, -1);
        Arrays.fill(compId, -1);
    }

    public void run() {
        for (int v = 0; v < g.n(); v++) {
            if (disc[v] == -1) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        if (metrics != null) metrics.incDfsCalls();
        disc[v] = low[v] = time++;
        stack.push(v);
        onStack[v] = true;
        for (var e : g.adj().get(v)) {
            int to = e.v();
            if (metrics != null) metrics.incVisitedEdges();
            if (disc[to] == -1) {
                dfs(to);
                low[v] = Math.min(low[v], low[to]);
            } else if (onStack[to]) {
                low[v] = Math.min(low[v], disc[to]);
            }
        }
        if (low[v] == disc[v]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int u = stack.pop();
                onStack[u] = false;
                compId[u] = compCount;
                comp.add(u);
                if (u == v) break;
            }
            components.add(comp);
            compCount++;
        }
    }

    public List<List<Integer>> components() { return components; }
    public int[] compId() { return compId; }
    public int count() { return compCount; }
}