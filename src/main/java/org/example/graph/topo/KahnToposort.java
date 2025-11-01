package org.example.graph.topo;

import org.example.common.Metrics;
import org.example.graph.Graph;

import java.util.*;

public class KahnToposort {

    public static List<Integer> order(Graph dag, Metrics metrics) {
        int n = dag.n();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (var e : dag.adj().get(u)) {
                indeg[e.v()]++;
            }
        }

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) {
                q.add(i);
                if (metrics != null) metrics.incPushes();
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.remove();
            if (metrics != null) metrics.incPops();
            result.add(u);
            for (var e : dag.adj().get(u)) {
                int v = e.v();
                indeg[v]--;
                if (indeg[v] == 0) {
                    q.add(v);
                    if (metrics != null) metrics.incPushes();
                }
            }
        }

        if (result.size() != n) {
            throw new IllegalStateException("Graph is not a DAG (cycle detected)");
        }

        return result;
    }
}