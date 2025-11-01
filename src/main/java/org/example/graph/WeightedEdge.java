package org.example.graph;

public class WeightedEdge {
    private final int u;
    private final int v;
    private final int w;

    public WeightedEdge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public int u() {
        return u;
    }

    public int v() {
        return v;
    }

    public int w() {
        return w;
    }

    @Override
    public String toString() {
        return "(" + u + " -> " + v + ", w=" + w + ")";
    }
}