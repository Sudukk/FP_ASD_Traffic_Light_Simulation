import simulation.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //  1. READ NUMBER OF NODES (JUNCTIONS)
        System.out.print("Enter number of nodes: ");
        int numNodes = sc.nextInt();
        sc.nextLine();
        NodeNameManager nm = new NodeNameManager();


        //   2. READ NODE (JUNCTION) NAMES FROM THE USER
        System.out.println("\nEnter node names:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print((i + 1) + ". ");
            nm.add(sc.nextLine().trim());
        }

        // 3. READ NUMBER OF EDGES (ROADS)

        System.out.print("\nEnter number of edges: ");
        int numEdges = sc.nextInt();
        sc.nextLine();

        int[][] edges = new int[numEdges][2];

        int[] vehicles = new int[numEdges];

        String[] edgeNames = new String[numEdges];

        // 4. READ EDGE CONNECTIONS (using node names)

        System.out.println("\nEnter edge connections (u v):");
        for (int i = 0; i < numEdges; i++) {
            System.out.print("Edge " + (i+1) + ": ");

            String u = sc.next();
            String v = sc.next();  

            edges[i][0] = nm.indexOf(u); 
            edges[i][1] = nm.indexOf(v); 

            edgeNames[i] = "Edge " + (i+1);
        }

        // 5. READ NUMBER OF VEHICLES ON EACH EDGE

        System.out.println("\nEnter vehicles per edge:");
        for (int i = 0; i < numEdges; i++) {
            System.out.print(edgeNames[i] + " vehicles: ");
            vehicles[i] = sc.nextInt();
        }

        // 6. READ SOURCE AND DESTINATION NODE (BY NAME)

        System.out.print("\nEnter source node: ");
        String s = sc.next();

        System.out.print("Enter destination node: ");
        String d = sc.next();

        int source = nm.indexOf(s);
        int dest = nm.indexOf(d);

        // 7. INITIALIZE TRAFFIC LIGHT SIMULATOR

        TrafficLightSimulator sim = new TrafficLightSimulator(numNodes, vehicles);

        // Build the graph with edge directions + durations
        sim.buildGraph(edges);

        // 8. DISPLAY ASCII GRAPH + VEHICLES + DURATIONS

        AsciiGraphRenderer.printGraph(nm, edges, edgeNames, vehicles);

        AsciiGraphRenderer.printVehicleTable(edgeNames, vehicles);

        AsciiGraphRenderer.printDurationTable(edgeNames, vehicles, sim.getDurations());

        // 9. COMPUTE AND DISPLAY SHORTEST PATH

        System.out.println("\n=== SHORTEST PATH RESULT ===");

        var path = sim.shortestPath(source, dest);

        System.out.print("Path: ");
        for (int node : path)
            System.out.print(nm.nameOf(node) + " ");
        System.out.println();

        System.out.println("Total Duration: " + sim.totalDuration(source, dest) + " secs");
    }
}
