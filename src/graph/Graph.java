package graph;

import java.util.*;


// Edge Class
class Edge {
    int from, to, weight;

    Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

// Graph Class
public class Graph {
    private final int n;
    private final List<List<Edge>> adj;   // for Dijkstra
    private final List<Edge> edges;       // for Bellman–Ford

    public Graph(int n) {
        this.n = n;
        adj = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
    }

    // Add directed edge u -> v
    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(u, v, weight));
        edges.add(new Edge(u, v, weight));
    }

    // Dijkstra's Algorithm
    public int[] dijkstra(int start, int[] parent) {
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[start] = 0;

        PriorityQueue<int[]> pq = 
            new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge e : adj.get(u)) {
                int v = e.to;
                int w = e.weight;

                if (!visited[v] && dist[u] != Integer.MAX_VALUE &&
                    dist[u] + w < dist[v]) {

                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        return dist;
    }

    // Bellman-Ford Algorithm
    public int[] bellmanFord(int start, int[] parent) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[start] = 0;

        // Relax V-1 times
        for (int i = 0; i < n - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.from] != Integer.MAX_VALUE &&
                    dist[e.from] + e.weight < dist[e.to]) {

                    dist[e.to] = dist[e.from] + e.weight;
                    parent[e.to] = e.from;
                }
            }
        }

        // Detect negative cycle
        for (Edge e : edges) {
            if (dist[e.from] != Integer.MAX_VALUE &&
                dist[e.from] + e.weight < dist[e.to]) {
                throw new RuntimeException("Graph contains a negative-weight cycle");
            }
        }

        return dist;
    }

    // Path reconstruction
    public List<Integer> buildPath(int start, int end, int[] parent) {
        List<Integer> path = new ArrayList<>();

        if (parent[end] == -1 && start != end)
            return path; // No path

        for (int at = end; at != -1; at = parent[at])
            path.add(at);

        Collections.reverse(path);
        return path;
    }
}

