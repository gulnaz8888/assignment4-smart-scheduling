package org.example.tests;

import org.example.graph.Graph;
import org.example.io.JsonGraphIO;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class JsonGraphIOTest {

    @Test
    public void loadSimpleGraphFromJson() throws Exception {
        String json = """
        {
          "directed": true,
          "n": 4,
          "edges": [
            {"u": 0, "v": 1, "w": 2},
            {"u": 1, "v": 2, "w": 3},
            {"u": 2, "v": 3, "w": 1}
          ]
        }
        """;

        Path temp = Files.createTempFile("graph-test", ".json");
        try (FileWriter writer = new FileWriter(temp.toFile())) {
            writer.write(json);
        }

        Graph g = JsonGraphIO.read(temp.toString());
        assertEquals(4, g.n());
        assertEquals(3, g.m());
    }
}