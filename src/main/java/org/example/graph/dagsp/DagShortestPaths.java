package org.example.graph.dagsp;

import org.example.common.Metrics;
import org.example.graph.Graph;
import org.example.graph.WeightedEdge;

import java.util.*;

public class DagShortestPaths {

    private final Graph dag;
    private final List<Integer> topo;
    private final Metrics metrics;

    public DagShortestPaths(Graph dag, List<Integer> topo, Metrics metrics) {
        this.dag = dag;
        this.topo = topo;
        this.metrics = metrics;
    }

    public Result run(int source) {
        int n = dag.n();
        long[] dist = new long[n];
        int[] prev = new int[n];
        Arrays.fill(dist, Long.MAX_VALUE / 4);
        Arrays.fill(prev, -1);
        dist[source] = 0;

        for (int u : topo) {
            if (dist[u] == Long.MAX_VALUE / 4) continue;
            for (WeightedEdge e : dag.adj().get(u)) {
                int v = e.v();
                long nd = dist[u] + e.w();
                if (metrics != null) metrics.incRelaxations();
                if (nd < dist[v]) {
                    dist[v] = nd;
                    prev[v] = u;
                }
            }
        }

        return new Result(dist, prev);
    }

    public static class Result {
        private final long[] dist;
        private final int[] prev;

        public Result(long[] dist, int[] prev) {
            this.dist = dist;
            this.prev = prev;
        }

        public long[] dist() {
            return dist;
        }

        public int[] prev() {
            return prev;
        }

        public List<Integer> pathTo(int target) {
            List<Integer> path = new ArrayList<>();
            for (int v = target; v != -1; v = prev[v]) {
                path.add(v);
            }
            Collections.reverse(path);
            return path;
        }
    }
}