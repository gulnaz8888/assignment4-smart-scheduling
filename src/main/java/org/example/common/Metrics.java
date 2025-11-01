package org.example.common;

public interface Metrics {

    void incDfsCalls();
    void incVisitedEdges();
    void incPushes();
    void incPops();
    void incRelaxations();

    long getDfsCalls();
    long getVisitedEdges();
    long getPushes();
    long getPops();
    long getRelaxations();

    void reset();
}