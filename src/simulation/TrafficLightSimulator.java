package simulation;

import graph.Graph;
import java.util.List;

public class TrafficLightSimulator {

    private final Graph graph; //Graph utama
    private final int numEdges; //Jumlah edge
    private final int[] vehicles; //Data jumlah kendaraan
    private final int[] durations; //Durasi hasil perhitungan

    public TrafficLightSimulator(int numNodes, int[] vehicles) {

        this.graph = new Graph(numNodes); //Inisialisasi graph

        this.numEdges = vehicles.length; //Banyaknya edge
        this.vehicles = vehicles; //Simpan data kendaraan

        this.durations = new int[numEdges]; //Array durasi edge

        for (int i = 0; i < numEdges; i++) {
            durations[i] = vehicles[i] / 2; //Durasi=half dari vehicles
        }
    }

    public void buildGraph(int[][] edges) {
        for (int i = 0; i < edges.length; i++) {
            graph.addEdge(edges[i][0], edges[i][1], durations[i]); //Tambah edge
        }
    }

    public List<Integer> shortestPath(int start, int end) {
        int[] parent = new int[durations.length + 5]; //Array parent
        graph.dijkstra(start, parent); //Jalankan Dijkstra
        return graph.buildPath(start, end, parent); //Bangun path
    }

    public int totalDuration(int start, int end) {
        int[] parent = new int[durations.length + 5]; //Array parent
        int[] dist = graph.dijkstra(start, parent); //Jalankan Dijkstra
        return dist[end]; //Return total durasi
    }

    public int[] getDurations() {
        return durations; //Getter durasi
    }

    public int[] getSortedVehiclesDescending() {
        return MergeSort.sortDescending(vehicles.clone()); //Sort vehicles
    }

    public int[] getSortedDurationsDescending() {
        return MergeSort.sortDescending(durations.clone()); //Sort durations
    }
}
