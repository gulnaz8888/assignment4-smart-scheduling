package org.example.tests;

import org.example.common.BasicMetrics;
import org.example.graph.Graph;
import org.example.graph.topo.KahnToposort;
import org.example.graph.dagsp.DagShortestPaths;
import org.example.graph.dagsp.DagLongestPath;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopoAndDagPathsTest {

    @Test
    public void simpleDag_topoOrderCorrect() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 1);
        List<Integer> topo = KahnToposort.order(g, new BasicMetrics());
        assertEquals(List.of(0, 1, 2, 3), topo);
    }

    @Test
    public void singleSource_shortestPathWorks() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 3, 3);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 5);

        List<Integer> topo = KahnToposort.order(g, new BasicMetrics());
        DagShortestPaths sp = new DagShortestPaths(g, topo, new BasicMetrics());
        DagShortestPaths.Result res = sp.run(0);

        assertEquals(0, res.dist()[0]);
        assertEquals(2, res.dist()[1]);
        assertEquals(4, res.dist()[2]);
        assertEquals(5, res.dist()[3]); // via 0→1→3
        assertEquals(10, res.dist()[4]);
    }

    @Test
    public void longestPath_identifiesCriticalRoute() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(2, 3, 4);
        g.addEdge(1, 4, 5);

        List<Integer> topo = KahnToposort.order(g, new BasicMetrics());
        DagLongestPath lp = new DagLongestPath(g, topo, new BasicMetrics());
        DagLongestPath.Result res = lp.run(0);

        long[] dist = res.dist();
        assertTrue(dist[3] >= dist[4]);
        assertTrue(dist[3] == 9 || dist[4] == 7);
    }
}