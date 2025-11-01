package org.example.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.graph.Graph;

import java.io.File;

public class JsonGraphIO {

    private static final ObjectMapper M = new ObjectMapper();

    public static Graph read(String path) throws Exception {
        JsonNode root = M.readTree(new File(path));
        int n = root.get("n").asInt();
        Graph g = new Graph(n);
        for (JsonNode e : root.withArray("edges")) {
            int u = e.get("u").asInt();
            int v = e.get("v").asInt();
            int w = e.has("w") ? e.get("w").asInt() : 1;
            g.addEdge(u, v, w);
        }
        return g;
    }
}