package org.example;

import org.example.common.BasicMetrics;
import org.example.common.Stopwatch;
import org.example.graph.Graph;
import org.example.graph.dagsp.DagLongestPath;
import org.example.graph.dagsp.DagShortestPaths;
import org.example.graph.scc.GraphCondensor;
import org.example.graph.scc.TarjanSCC;
import org.example.graph.topo.KahnToposort;
import org.example.io.JsonGraphIO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar assignment4.jar <path-to-json> [--source <id>]");
            return;
        }

        String path = args[0];
        int source = 0;
        for (int i = 1; i + 1 < args.length; i++) {
            if (args[i].equals("--source")) {
                source = Integer.parseInt(args[i + 1]);
            }
        }

        Graph g = JsonGraphIO.read(path);
        System.out.println("Loaded graph: n=" + g.n() + " m=" + g.m());
        System.out.println("Starting algorithms...\n");

        BasicMetrics metrics = new BasicMetrics();
        Stopwatch timer = new Stopwatch();

        timer.start();
        TarjanSCC tarjan = new TarjanSCC(g, metrics);
        tarjan.run();
        double sccTime = timer.elapsedMillis();
        System.out.println("SCC components: " + tarjan.count() + " (" + sccTime + " ms)");
        System.out.println("DFS calls: " + metrics.getDfsCalls() + ", edges visited: " + metrics.getVisitedEdges());
        System.out.println("SCC list: " + tarjan.components());

        Graph dag = GraphCondensor.build(g, tarjan.compId(), tarjan.count());
        System.out.println("\nCondensation graph: " + dag.n() + " vertices, " + dag.m() + " edges");

        metrics.reset();
        List<Integer> topo = KahnToposort.order(dag, metrics);
        System.out.println("Topological order (components): " + topo);
        System.out.println("Pushes: " + metrics.getPushes() + ", pops: " + metrics.getPops());

        List<Integer> taskOrder = new ArrayList<>();
        for (int c : topo) {
            taskOrder.addAll(tarjan.components().get(c));
        }
        System.out.println("Derived order of original tasks: " + taskOrder);

        if (dag.n() == 0) {
            System.out.println("\nEmpty condensation DAG. Done.");
            return;
        }
        if (source < 0 || source >= dag.n()) {
            System.out.println("\nSource out of range for DAG, using 0");
            source = 0;
        }

        metrics.reset();
        timer.start();
        DagShortestPaths sp = new DagShortestPaths(dag, topo, metrics);
        DagShortestPaths.Result sr = sp.run(source);
        double spTime = timer.elapsedMillis();
        System.out.println("\nShortest paths from source " + source + " (" + spTime + " ms), relaxations: " + metrics.getRelaxations());
        int shortTarget = topo.get(topo.size() - 1);
        System.out.println("Distances:");
        for (int i = 0; i < dag.n(); i++) {
            System.out.println("  " + source + " -> " + i + " = " + sr.dist()[i]);
        }
        System.out.println("One shortest path to last in topo (" + shortTarget + "): " + sr.pathTo(shortTarget));

        metrics.reset();
        timer.start();
        DagLongestPath lp = new DagLongestPath(dag, topo, metrics);
        DagLongestPath.Result lr = lp.run(source);
        double lpTime = timer.elapsedMillis();
        long max = Long.MIN_VALUE;
        int end = -1;
        for (int i = 0; i < dag.n(); i++) {
            if (lr.dist()[i] > max) {
                max = lr.dist()[i];
                end = i;
            }
        }
        System.out.println("\nCritical path (longest) from source " + source + " (" + lpTime + " ms), relaxations: " + metrics.getRelaxations());
        System.out.println("Critical path length: " + max);
        System.out.println("Critical path: " + lr.pathTo(end));

        System.out.println("\nAll computations finished.");
    }
}