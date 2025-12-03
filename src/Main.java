import simulation.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        /**
         * ==========================================================
         *  1. READ NUMBER OF NODES (JUNCTIONS)
         * ==========================================================
         */
        System.out.print("Enter number of nodes: ");
        int numNodes = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        // NodeNameManager handles name ↔ index conversion
        NodeNameManager nm = new NodeNameManager();


        /**
         * ==========================================================
         *  2. READ NODE (JUNCTION) NAMES FROM THE USER
         * ==========================================================
         */
        System.out.println("\nEnter node names:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print((i + 1) + ". ");
            nm.add(sc.nextLine().trim());   // store name in NodeNameManager
        }


        /**
         * ==========================================================
         *  3. READ NUMBER OF EDGES (ROADS)
         * ==========================================================
         */
        System.out.print("\nEnter number of edges: ");
        int numEdges = sc.nextInt();
        sc.nextLine(); // consume newline

        // 2D array: each edge = [fromIndex, toIndex]
        int[][] edges = new int[numEdges][2];

        // number of vehicles on each edge
        int[] vehicles = new int[numEdges];

        // automatically generated edge names: Edge 1, Edge 2, ...
        String[] edgeNames = new String[numEdges];


        /**
         * ==========================================================
         *  4. READ EDGE CONNECTIONS (using node names)
         * ==========================================================
         *
         * User enters: "Keputih Semolowaru"
         * We convert them to indexes using NodeNameManager:
         *   nm.indexOf("Keputih") → 0
         *   nm.indexOf("Semolowaru") → 1
         */
        System.out.println("\nEnter edge connections (u v):");
        for (int i = 0; i < numEdges; i++) {
            System.out.print("Edge " + (i+1) + ": ");

            String u = sc.next();   // starting node name
            String v = sc.next();   // ending node name

            edges[i][0] = nm.indexOf(u);  // convert name → index
            edges[i][1] = nm.indexOf(v);  // convert name → index

            edgeNames[i] = "Edge " + (i+1); // name edges automatically
        }


        /**
         * ==========================================================
         *  5. READ NUMBER OF VEHICLES ON EACH EDGE
         * ==========================================================
         */
        System.out.println("\nEnter vehicles per edge:");
        for (int i = 0; i < numEdges; i++) {
            System.out.print(edgeNames[i] + " vehicles: ");
            vehicles[i] = sc.nextInt();
        }


        /**
         * ==========================================================
         *  6. READ SOURCE AND DESTINATION NODE (BY NAME)
         * ==========================================================
         */
        System.out.print("\nEnter source node: ");
        String s = sc.next();

        System.out.print("Enter destination node: ");
        String d = sc.next();

        // Convert names → indexes
        int source = nm.indexOf(s);
        int dest = nm.indexOf(d);


        /**
         * ==========================================================
         *  7. INITIALIZE TRAFFIC LIGHT SIMULATOR
         * ==========================================================
         *
         * This class:
         *   - Calculates durations based on vehicle count
         *   - Builds the weighted graph
         *   - Performs Dijkstra’s shortest path
         */
        TrafficLightSimulator sim = new TrafficLightSimulator(numNodes, vehicles);

        // Build the graph with edge directions + durations
        sim.buildGraph(edges);


        /**
         * ==========================================================
         *  8. DISPLAY ASCII GRAPH + VEHICLES + DURATIONS
         * ==========================================================
         */
        AsciiGraphRenderer.printGraph(nm, edges, edgeNames, vehicles);

        AsciiGraphRenderer.printVehicleTable(edgeNames, vehicles);

        AsciiGraphRenderer.printDurationTable(edgeNames, vehicles, sim.getDurations());


        /**
         * ==========================================================
         *  9. COMPUTE AND DISPLAY SHORTEST PATH
         * ==========================================================
         */
        System.out.println("\n=== SHORTEST PATH RESULT ===");

        // Get path using Dijkstra (as list of indexes)
        var path = sim.shortestPath(source, dest);

        // Print readable path (convert index → node name)
        System.out.print("Path: ");
        for (int node : path)
            System.out.print(nm.nameOf(node) + " ");
        System.out.println();

        // Print total travel time (sum of weights/durations)
        System.out.println("Total Duration: " + sim.totalDuration(source, dest) + " secs");
    }
}
