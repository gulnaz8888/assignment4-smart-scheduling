## Assignment 4 — Smart City / Smart Campus Scheduling

This project integrates two key algorithmic topics:
1.	Strongly Connected Components (SCC) and Topological Ordering
2.	Shortest and Longest Paths in Directed Acyclic Graphs (DAG)

The implementation models dependency scheduling for Smart City or Smart Campus tasks such as maintenance, repair, and sensor analytics.
The goal is to detect cyclic dependencies, compress them into SCCs, generate a condensation DAG, and then compute optimal execution orders and critical paths.

---

## Features
- Tarjan’s SCC algorithm for detecting cycles
- Condensation graph construction and topological sort (Kahn)
- Shortest and longest path algorithms on DAGs
- Metrics system for counting DFS visits, relaxations, and execution time
- Modular structure with separate packages for SCC, Topo, and DAG-SP
- JUnit tests for SCC, Topo, and I/O validation

---

## Project Structure
```
src/
├── main/java/org/example/
│   ├── common/          → Metrics, Stopwatch
│   ├── graph/scc/       → TarjanSCC, GraphCondensor
│   ├── graph/topo/      → KahnToposort
│   ├── graph/dagsp/     → DagShortestPaths, DagLongestPath
│   ├── io/              → JsonGraphIO
│   └── Main.java
└── test/java/org/example/tests/ → JUnit tests
data/ → nine test graphs (small / medium / large)
```

---

## How to Build and Run

Build the project:
```
mvn clean package
```
Run the program:
```
java -jar target/assignment4-smart-scheduling-1.0-SNAPSHOT.jar data/small-01.json --source 0
```
Run all tests:
```
mvn test
```

---

## Dataset Summary
```
Category	Files	Vertices (range)	Description
Small	small-01…03.json	6–10	Simple graphs, 1–2 cycles or DAG
Medium	medium-01…03.json	10–20	Mixed structures, several SCCs
Large	large-01…03.json	20–50	Performance and timing tests

All datasets are stored in /data/ and follow the JSON format:

{"directed": true, "n": 8, "edges": [{"u":0,"v":1,"w":3}, ...], "source": 0, "weight_model": "edge"}
```

---

## Results Summary
```
Graph	n	m	SCCs	Time (ms)
small-01	8	7	6	0.11
medium-02	15	22	10	0.12
large-03	40	57	30	0.17
```
All algorithms completed in under 1 ms, confirming linear scalability (O(V + E)).

---

## Environment
- Java 17
- Maven 3.9+
- JUnit 5.10

---

Author Gulnaz Yeskermes
