package org.example.common;

public class BasicMetrics implements Metrics {

    private long dfsCalls;
    private long visitedEdges;
    private long pushes;
    private long pops;
    private long relaxations;

    @Override
    public void incDfsCalls() {
        dfsCalls++;
    }

    @Override
    public void incVisitedEdges() {
        visitedEdges++;
    }

    @Override
    public void incPushes() {
        pushes++;
    }

    @Override
    public void incPops() {
        pops++;
    }

    @Override
    public void incRelaxations() {
        relaxations++;
    }

    @Override
    public long getDfsCalls() {
        return dfsCalls;
    }

    @Override
    public long getVisitedEdges() {
        return visitedEdges;
    }

    @Override
    public long getPushes() {
        return pushes;
    }

    @Override
    public long getPops() {
        return pops;
    }

    @Override
    public long getRelaxations() {
        return relaxations;
    }

    @Override
    public void reset() {
        dfsCalls = 0;
        visitedEdges = 0;
        pushes = 0;
        pops = 0;
        relaxations = 0;
    }
}