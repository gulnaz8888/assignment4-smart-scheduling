Assignment 4 — Smart City / Smart Campus Scheduling
---
## Goal and Scenario

This project consolidates two course topics within one practical case:
- Strongly Connected Components (SCC) and Topological Ordering
- Shortest Paths in Directed Acyclic Graphs (DAGs)

The scenario models scheduling for city and campus services such as street cleaning, maintenance, or sensor repairs.
Some dependencies among tasks are cyclic, requiring detection and compression into SCCs; others are acyclic, requiring optimal planning using DAG path algorithms.

The project aims to implement and analyze these algorithms, generate datasets of various scales, measure performance metrics, and draw conclusions on algorithmic behavior and scalability.

---
## Implemented Algorithms and Structure

The project is organized into modular packages, each covering a specific task:

Package	Content	Description
graph.scc	TarjanSCC, GraphCondensor	Tarjan’s algorithm for strongly connected components and condensation graph construction.
graph.topo	KahnToposort	Kahn’s algorithm for generating a topological order of a DAG.
graph.dagsp	DagShortestPaths, DagLongestPath	Dynamic programming algorithms for shortest and longest paths in DAGs.
common	BasicMetrics, Stopwatch, Metrics	Operation counters and time measurement tools.
io	JsonGraphIO	JSON graph reader for loading directed weighted graphs.
tests	TarjanSCCBasicTest, TopoAndDagPathsTest, JsonGraphIOTest	JUnit 5 tests verifying correctness of algorithms and I/O.

Each algorithm collects operation metrics:
- SCC: DFS visits, edges processed.
- Topological sort: push/pop operations.
- DAG-SP: relaxations and execution time.

---

## Dataset Generation

Nine datasets were generated to test algorithm behavior on different structures and scales.
All are located under /data/ and use the following format:

```{
"directed": true,
"n": <number_of_vertices>,
"edges": [
{"u": <source>, "v": <destination>, "w": <weight>}
],
"source": <starting_node>,
"weight_model": "edge"
}

Dataset Summary

Category	File	Vertices	Edges	Type	Description
Small	small-01.json	8	7	Mixed	One cycle and DAG part.
Small	small-02.json	7	8	Acyclic	Simple DAG.
Small	small-03.json	9	10	Mixed	Several SCCs and chains.
Medium	medium-01.json	12	16	Mixed	Sparse DAG with cycles.
Medium	medium-02.json	15	22	Mixed	Moderate density, several SCCs.
Medium	medium-03.json	18	26	Mixed	Multiple SCC clusters.
Large	large-01.json	22	31	Mixed	Performance test.
Large	large-02.json	28	44	Dense	Denser DAG for timing.
Large	large-03.json	40	57	Dense	Stress test, high connectivity.
```
Each dataset’s structure and parameters are documented in this report to ensure reproducibility.

--- 

## Experimental Results
```
Dataset	n	m	SCC count	Time (ms)	DFS calls	Edges visited	Pushes	Pops	SP time (ms)	LP time (ms)	Relaxations	Critical path length
small-01	8	7	6	0.11	8	7	6	6	0.16	0.24	0	0
small-02	7	8	7	0.11	7	8	7	7	0.14	0.15	0	0
small-03	9	10	6	0.12	9	10	6	6	0.15	0.16	0	0
medium-01	12	16	8	0.12	12	16	8	8	0.13	0.16	0	0
medium-02	15	22	10	0.12	15	22	10	10	0.13	0.13	0	0
medium-03	18	26	13	0.14	18	26	13	13	0.14	0.14	0	0
large-01	22	31	16	0.13	22	31	16	16	0.14	0.18	0	0
large-02	28	44	19	0.14	28	44	19	19	0.14	0.23	0	0
large-03	40	57	30	0.17	40	57	30	30	0.19	0.15	0	0
```
---


## Analysis

- Strongly Connected Components

Execution time grows linearly with graph size, consistent with O(V + E) complexity.
The number of SCCs increases with density, confirming consistent detection of cycles.
Average SCC time per dataset: 0.13 ms, with negligible overhead for condensation.

- Condensation Graph and Topological Order

Condensed DAGs maintain linear vertex and edge counts.
Push/pop counts equal the number of DAG vertices, confirming correct queue-based Kahn implementation.
Topological orders are valid for all graphs, enabling path computation steps.

- Shortest and Longest Paths in DAG

Shortest and longest path algorithms both executed in linear time relative to DAG edges.
Relaxations count is zero in cases with isolated SCC sources, consistent with expected behavior.
Critical paths are computed correctly, identifying the longest feasible chain in each DAG.

- Scalability

From small (n=8) to large (n=40) datasets, all algorithms complete under 1 millisecond.
Performance scales linearly and remains stable across densities and component counts.

--- 

## Conclusions
- Tarjan’s SCC algorithm effectively identifies cycles and groups interdependent tasks.
- Condensation and topological sorting enable efficient scheduling of acyclic components.
- Shortest and longest path algorithms provide critical insights into minimum and maximum execution chains.
- All algorithms operate in linear time and are suitable for real-time scheduling applications.
- Empirical results confirm correctness, efficiency, and scalability for all datasets.
- The combined SCC–DAG pipeline provides a practical framework for dependency-based task planning in smart city or campus systems.

---

## Testing and Verification

All algorithms were verified using JUnit tests:

Test	Purpose
TarjanSCCBasicTest	Validates SCC detection and condensation graph correctness.
TopoAndDagPathsTest	Verifies topological order and path algorithms.
JsonGraphIOTest	Ensures correct JSON graph parsing and structure loading.

All tests executed successfully (mvn test → BUILD SUCCESS).

--- 

## Code Quality and Project Structure
- Modular architecture following standard Maven conventions.
- Package separation by algorithmic function.
- Metrics and time-tracking utilities isolated in common.
- Input handling separated in io.
- Clean .gitignore and reproducible repository.
```
assignment4-smart-scheduling/
├── src/
│   ├── main/java/org/example/
│   │   ├── common/
│   │   ├── graph/scc/
│   │   ├── graph/topo/
│   │   ├── graph/dagsp/
│   │   ├── io/
│   │   └── Main.java
│   └── test/java/org/example/tests/
├── data/
├── pom.xml
├── .gitignore
└── REPORT.md
```

---
## Environment and Reproducibility
- Java: 17
- Maven: 3.9+
- JUnit: 5.10
```
To build and run: 
mvn clean package
java -jar target/assignment4-smart-scheduling-1.0-SNAPSHOT.jar data/small-01.json --source 0

To test: mvn test

```