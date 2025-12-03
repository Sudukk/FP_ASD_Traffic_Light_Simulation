package simulation;

public class AsciiGraphRenderer {

    /**
     * Prints an ASCII visualization of the traffic junction.
     *
     * Example output:
     *
     *   === ASCII TRAFFIC JUNCTION ===
     *   [Keputih]
     *   [Semolowaru]
     *   [Manyar]
     *
     *   Connections:
     *    Keputih ---- A1 (10 cars) ----> Semolowaru
     *    Semolowaru ---- A2 (50 cars) ----> Manyar
     *    Keputih ---- A3 (25 cars) ----> Manyar
     *
     *
     * @param nm        NodeNameManager for translating index ↔ name
     * @param edges     2D array: each row is {fromIndex, toIndex}
     * @param edgeNames Automatically generated names (A1, A2, A3…)
     * @param vehicles  Number of vehicles per edge
     */
    public static void printGraph(NodeNameManager nm, int[][] edges, String[] edgeNames, int[] vehicles) {

        System.out.println("\n=== ASCII TRAFFIC JUNCTION ===");

        // Print node names in simple vertical list format
        nm.printNodes();

        // Now print connections between nodes
        System.out.println("\nConnections:");

        // Loop through every edge
        for (int i = 0; i < edges.length; i++) {

            int u = edges[i][0];  // start node index
            int v = edges[i][1];  // end node index

            /**
             * Print:
             *   <nodeName> ---- <edgeName> (<cars> cars) ----> <nodeName>
             */
            System.out.println(" " + nm.nameOf(u) + " ---- " + edgeNames[i] +
                    " (" + vehicles[i] + " cars) ----> " + nm.nameOf(v));
        }
    }



    /**
     * Prints a table listing each edge and its number of vehicles.
     *
     * Example:
     *
     *   +--------+----------------+
     *   | Edge   | Num Vehicles   |
     *   +--------+----------------+
     *   | A1     | 10             |
     *   | A2     | 50             |
     *   | A3     | 25             |
     *   +--------+----------------+
     *
     *
     * @param edgeNames  Edge identifiers (A1, A2, A3...)
     * @param vehicles   Vehicles per edge
     */
    public static void printVehicleTable(String[] edgeNames, int[] vehicles) {
        System.out.println("\n=== VEHICLE TABLE ===");

        // Table headers
        System.out.println("+--------+----------------+");
        System.out.println("| Edge   | Num Vehicles   |");
        System.out.println("+--------+----------------+");

        // Print one row per edge
        for (int i = 0; i < vehicles.length; i++) {

            // %-6s  = left align text in 6 spaces
            // %-14d = left align number in 14 spaces
            System.out.printf("| %-6s | %-14d |\n", edgeNames[i], vehicles[i]);
        }

        System.out.println("+--------+----------------+");
    }



    /**
     * Prints a table showing:
     * - Edge name
     * - Number of vehicles on that edge
     * - Duration (seconds) for the traffic light
     *
     * Example:
     *
     *   +--------+----------------+----------------+
     *   | Edge   | Num Vehicles   | Duration(sec) |
     *   +--------+----------------+----------------+
     *   | A1     | 10             | 10             |
     *   | A2     | 50             | 30             |
     *   | A3     | 25             | 30             |
     *   +--------+----------------+----------------+
     *
     *
     * @param edgeNames Edge identifiers (A1, A2, ...)
     * @param vehicles  Number of vehicles per edge
     * @param durations Duration assigned to each edge
     */
    public static void printDurationTable(String[] edgeNames, int[] vehicles, int[] durations) {

        System.out.println("\n=== EDGE DURATION TABLE ===");

        // Table header
        System.out.println("+--------+----------------+----------------+");
        System.out.println("| Edge   | Num Vehicles   | Duration (sec) |");
        System.out.println("+--------+----------------+----------------+");

        // Print row for each edge
        for (int i = 0; i < durations.length; i++) {
            System.out.printf(
                    "| %-6s | %-14d | %-14d |\n",
                    edgeNames[i],  // edge name (A1, A2…)
                    vehicles[i],   // number of vehicles on that edge
                    durations[i]   // assigned red-light duration
            );
        }

        // End of table
        System.out.println("+--------+----------------+----------------+");
    }
}
