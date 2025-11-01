package org.example.graph;

import java.util.List;

public class GraphUtils {

    public static void printAdjacency(Graph g) {
        for (int u = 0; u < g.n(); u++) {
            List<WeightedEdge> list = g.adj().get(u);
            System.out.print(u + ": ");
            for (WeightedEdge e : list) {
                System.out.print(e.v() + "(" + e.w() + ") ");
            }
            System.out.println();
        }
    }

    public static void printEdges(Graph g) {
        for (int u = 0; u < g.n(); u++) {
            for (WeightedEdge e : g.adj().get(u)) {
                System.out.println(e.u() + " -> " + e.v() + " (w=" + e.w() + ")");
            }
        }
    }
}