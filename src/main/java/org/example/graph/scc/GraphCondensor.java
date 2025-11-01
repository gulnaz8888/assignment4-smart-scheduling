package org.example.graph.scc;

import org.example.graph.Graph;
import org.example.graph.WeightedEdge;

import java.util.HashSet;
import java.util.Set;

public class GraphCondensor {

    public static Graph build(Graph g, int[] compId, int compCount) {
        Graph dag = new Graph(compCount);
        Set<Long> seen = new HashSet<>();

        for (int u = 0; u < g.n(); u++) {
            int cu = compId[u];
            for (WeightedEdge e : g.adj().get(u)) {
                int cv = compId[e.v()];
                if (cu == cv) continue;
                long key = (((long) cu) << 32) ^ (cv & 0xffffffffL);
                if (seen.add(key)) {
                    dag.addEdge(cu, cv, e.w());
                }
            }
        }
        return dag;
    }
}