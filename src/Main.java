import simulation.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int numNodes = sc.nextInt();
        sc.nextLine();

        NodeNameManager nm = new NodeNameManager();

        System.out.println("\nEnter node names:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print((i + 1) + ". ");
            nm.add(sc.nextLine().trim());
        }

        System.out.print("\nEnter number of edges: ");
        int numEdges = sc.nextInt();
        sc.nextLine();

        int[][] edges = new int[numEdges][2];
        int[] vehicles = new int[numEdges];
        String[] edgeNames = new String[numEdges];

        System.out.println("\nEnter edge connections (u v):");
        for (int i = 0; i < numEdges; i++) {
            System.out.print("Edge " + (i+1) + ": ");
            String u = sc.next();
            String v = sc.next();
            edges[i][0] = nm.indexOf(u);
            edges[i][1] = nm.indexOf(v);
            edgeNames[i] = "Edge " + (i+1);
        }

        System.out.println("\nEnter vehicles per edge:");
        for (int i = 0; i < numEdges; i++) {
            System.out.print(edgeNames[i] + " vehicles: ");
            vehicles[i] = sc.nextInt();
        }

        System.out.print("\nEnter source node: ");
        String s = sc.next();
        System.out.print("Enter destination node: ");
        String d = sc.next();

        int source = nm.indexOf(s);
        int dest = nm.indexOf(d);

        TrafficLightSimulator sim = new TrafficLightSimulator(numNodes, vehicles);
        sim.buildGraph(edges);

        AsciiGraphRenderer.printGraph(nm, edges, edgeNames, vehicles);
        AsciiGraphRenderer.printVehicleTable(edgeNames, vehicles);
        AsciiGraphRenderer.printDurationTable(edgeNames, vehicles, sim.getDurations());

        System.out.println("\n=== SHORTEST PATH RESULT ===");
        var path = sim.shortestPath(source, dest);

        System.out.print("Path: ");
        for (int node : path)
            System.out.print(nm.nameOf(node) + " ");
        System.out.println();

        System.out.println("Total Duration: " + sim.totalDuration(source, dest) + " secs");
    }
}
