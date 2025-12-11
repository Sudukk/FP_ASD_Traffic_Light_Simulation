package simulation;

public class AsciiGraphRenderer {

    public static void printGraph(NodeNameManager nm, int[][] edges, String[] edgeNames, int[] vehicles) {

        System.out.println("\n=== ASCII TRAFFIC JUNCTION ===");

        nm.printNodes();

        System.out.println("\nConnections:");

        for (int i = 0; i < edges.length; i++) {

            int u = edges[i][0];
            int v = edges[i][1]; 

            System.out.println(" " + nm.nameOf(u) + " ---- " + edgeNames[i] +
                    " (" + vehicles[i] + " cars) ----> " + nm.nameOf(v));
        }
    }

    public static void printVehicleTable(String[] edgeNames, int[] vehicles) {
        System.out.println("\n=== VEHICLE TABLE ===");

        System.out.println("+--------+----------------+");
        System.out.println("| Edge   | Num Vehicles   |");
        System.out.println("+--------+----------------+");

        for (int i = 0; i < vehicles.length; i++) {
            System.out.printf("| %-6s | %-14d |\n", edgeNames[i], vehicles[i]);
        }

        System.out.println("+--------+----------------+");
    }

    public static void printDurationTable(String[] edgeNames, int[] vehicles, int[] durations) {

        System.out.println("\n=== EDGE DURATION TABLE ===");

        System.out.println("+--------+----------------+----------------+");
        System.out.println("| Edge   | Num Vehicles   | Duration (sec) |");
        System.out.println("+--------+----------------+----------------+");

        for (int i = 0; i < durations.length; i++) {
            System.out.printf(
                    "| %-6s | %-14d | %-14d |\n",
                    edgeNames[i],  // edge name
                    vehicles[i],   // number of vehicles on that edge
                    durations[i]   // assigned red-light duration
            );
        }

        System.out.println("+--------+----------------+----------------+");
    }
}
