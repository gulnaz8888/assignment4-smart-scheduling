package org.example.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EdgePrinter {

    public static void printEdges(List<WeightedEdge> edges) {
        for (WeightedEdge e : edges) {
            System.out.println(e.u() + " -> " + e.v() + " (w=" + e.w() + ")");
        }
    }

    public static List<Map<String, Object>> toJsonList(List<WeightedEdge> edges) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WeightedEdge e : edges) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("u", e.u());
            row.put("v", e.v());
            row.put("w", e.w());
            list.add(row);
        }
        return list;
    }
}