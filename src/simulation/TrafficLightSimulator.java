package simulation;

import graph.Graph;

import java.util.List;

public class TrafficLightSimulator {

    private final Graph graph;
    private final int numEdges;
    private final int[] vehicles;
    private final int[] sortedVehicles;
    private final int[] durations;

    public TrafficLightSimulator(int numNodes, int[] vehicles) {
        this.graph = new Graph(numNodes);
        this.numEdges = vehicles.length;
        this.vehicles = vehicles;

        this.sortedVehicles = MergeSort.sort(vehicles.clone());
        this.durations = new int[numEdges];

// Duration MUST match each edge’s own vehicles count
        for (int i = 0; i < numEdges; i++) {
            durations[i] = vehicles[i] > 20 ? 30 : 10;
        }

    }

    public void buildGraph(int[][] edges) {
        for (int i = 0; i < edges.length; i++) {
            graph.addEdge(edges[i][0], edges[i][1], durations[i]);
        }
    }

    public List<Integer> shortestPath(int start, int end) {
        int[] parent = new int[durations.length + 5];
        graph.dijkstra(start, parent);
        return graph.buildPath(start, end, parent);
    }

    public int totalDuration(int start, int end) {
        int[] parent = new int[durations.length + 5];
        int[] dist = graph.dijkstra(start, parent);
        return dist[end];
    }

    public int[] getDurations() { return durations; }
}
