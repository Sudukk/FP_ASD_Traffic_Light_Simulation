package simulation;

import graph.Graph;
import java.util.List;

public class TrafficLightSimulator {

    // Graph object that holds all nodes + edges + Dijkstra logic
    private final Graph graph;

    // Number of edges in the graph (each edge is a road segment)
    private final int numEdges;

    // Raw input: number of vehicles per edge (unsorted)
    private final int[] vehicles;

    // Sorted version of vehicle counts (used only for analysis)
    private final int[] sortedVehicles;

    // Duration of each traffic light edge (based on each edge's vehicle count)
    private final int[] durations;


    /**
     * Constructor for the simulator.
     *
     * @param numNodes  Number of intersections (nodes)
     * @param vehicles  Array representing number of vehicles per edge, in original order
     */
    public TrafficLightSimulator(int numNodes, int[] vehicles) {

        // Create a new weighted graph with the given number of nodes
        this.graph = new Graph(numNodes);

        // Save number of edges based on the length of vehicles input
        this.numEdges = vehicles.length;

        // Save original vehicle data
        this.vehicles = vehicles;

        // Make a sorted copy of vehicles (merge sort)
        // Sorting is for analysis only, NOT for assigning durations
        this.sortedVehicles = MergeSort.sort(vehicles.clone());

        // Prepare durations array (one duration per edge)
        this.durations = new int[numEdges];

        /**
         * Assign RED LIGHT durations based on the ACTUAL number of vehicles on each edge.
         *
         * IMPORTANT:
         *   - We DO NOT use sortedVehicles here because duration must match
         *     the real traffic on each road segment.
         *
         * Duration Rule:
         *   - If vehicles[i] > 5 → duration = 30 seconds
         *   - Else               → duration = 10 seconds
         */
        for (int i = 0; i < numEdges; i++) {
            durations[i] = vehicles[i] > 5 ? 30 : 10;
        }
    }


    /**
     * Builds the graph by adding edges.
     *
     * @param edges 2D array where each row = {fromNode, toNode}
     *
     * Each edge uses the duration calculated earlier as the weight.
     */
    public void buildGraph(int[][] edges) {
        for (int i = 0; i < edges.length; i++) {

            // Add edge to graph:
            // edges[i][0] = start node index
            // edges[i][1] = end node index
            // durations[i] = weight (time delay caused by red light)
            graph.addEdge(edges[i][0], edges[i][1], durations[i]);
        }
    }


    /**
     * Uses Dijkstra’s algorithm to compute the shortest path between two nodes.
     *
     * @param start Starting node index
     * @param end   Destination node index
     * @return      List of node indexes representing the shortest path
     */
    public List<Integer> shortestPath(int start, int end) {

        // Parent array used internally by Dijkstra for reconstructing the path
        int[] parent = new int[durations.length + 5];

        // Run Dijkstra to compute shortest distances
        graph.dijkstra(start, parent);

        // Build the actual path from start → end
        return graph.buildPath(start, end, parent);
    }


    /**
     * Returns the total travel duration for the shortest path
     * (sum of weights along the path)
     */
    public int totalDuration(int start, int end) {

        // Parent array used for path reconstruction
        int[] parent = new int[durations.length + 5];

        // dist[] contains shortest travel time from start to every node
        int[] dist = graph.dijkstra(start, parent);

        // Return travel time to the destination node
        return dist[end];
    }


    /**
     * Returns the array of durations for each edge (display purposes)
     */
    public int[] getDurations() {
        return durations;
    }
}
