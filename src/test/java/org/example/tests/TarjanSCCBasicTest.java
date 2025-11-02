package org.example.tests;

import org.example.common.BasicMetrics;
import org.example.graph.Graph;
import org.example.graph.scc.GraphCondensor;
import org.example.graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TarjanSCCBasicTest {

    @Test
    public void simpleCycle_singleScc() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        TarjanSCC scc = new TarjanSCC(g, new BasicMetrics());
        scc.run();
        assertEquals(1, scc.count());
        Graph dag = GraphCondensor.build(g, scc.compId(), scc.count());
        assertEquals(1, dag.n());
        assertEquals(0, dag.m());
    }

    @Test
    public void twoCycles_and_tail_multipleSccs() {
        Graph g = new Graph(7);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 2, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 1);
        g.addEdge(5, 6, 1);
        TarjanSCC scc = new TarjanSCC(g, new BasicMetrics());
        scc.run();
        assertEquals(5, scc.count());
        Graph dag = GraphCondensor.build(g, scc.compId(), scc.count());
        assertTrue(dag.n() >= 5);
        assertTrue(dag.m() >= 2);
    }

    @Test
    public void isolatedVertices_noEdges_eachOwnScc() {
        Graph g = new Graph(4);
        TarjanSCC scc = new TarjanSCC(g, new BasicMetrics());
        scc.run();
        assertEquals(4, scc.count());
        Graph dag = GraphCondensor.build(g, scc.compId(), scc.count());
        assertEquals(4, dag.n());
        assertEquals(0, dag.m());
    }
}