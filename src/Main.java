import simulation.*;
import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        NodeNameManager nm = new NodeNameManager();

        // ---------------------------------------------------------------------
        // 1. MEMBACA JUMLAH NODE (Minimum = 10, harus integer)
        // ---------------------------------------------------------------------
        int numNodes = readIntWithMin("Enter number of junction (min 10): ", 10);

        // ---------------------------------------------------------------------
        // 2. MEMBACA NAMA-NAMA NODE (tidak boleh kosong)
        // ---------------------------------------------------------------------
        System.out.println("\nEnter junction names:");
        for (int i = 0; i < numNodes; i++) {
            String name;

            while (true) {
                System.out.print((i + 1) + ". ");
                name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println("Junction name cannot be empty. Try again.");
                } else if (nm.indexOf(name) != -1) {
                    System.out.println("Duplicate name. Use unique names.");
                } else {
                    nm.add(name);
                    break;
                }
            }
        }

        // ---------------------------------------------------------------------
        // 3. MEMBACA JUMLAH EDGE (harus connect semua node → min = nodes - 1)
        // ---------------------------------------------------------------------
        int minEdges = numNodes - 1;
        int numEdges = readIntWithMin(
                "\nEnter number of roads (minimum " + minEdges + "): ",
                minEdges
        );

        int[][] edges = new int[numEdges][2];
        int[] vehicles = new int[numEdges];
        String[] edgeNames = new String[numEdges];

        // ---------------------------------------------------------------------
        // 4. MEMBACA KONEKSI EDGE (validasi keberadaan node)
        // ---------------------------------------------------------------------
        System.out.println("\nEnter junction connections (u v):");

        for (int i = 0; i < numEdges; i++) {
            while (true) {
                System.out.print("Road " + (i + 1) + ": ");

                String u = sc.next();
                String v = sc.next();
                sc.nextLine(); // konsumsi newline

                int uIndex = nm.indexOf(u);
                int vIndex = nm.indexOf(v);

                if (uIndex == -1) {
                    System.out.println("Junction '" + u + "' does not exist. Try again.");
                    continue;
                }
                if (vIndex == -1) {
                    System.out.println("Junction '" + v + "' does not exist. Try again.");
                    continue;
                }
                if (uIndex == vIndex) {
                    System.out.println("Road cannot connect a node to itself. Try again.");
                    continue;
                }

                // Simpan endpoint edge
                edges[i][0] = uIndex;
                edges[i][1] = vIndex;

                // Nama edge dalam bentuk A ---- B
                edgeNames[i] = "Road " + u + " ---- " + v;

                break;
            }
        }

        // ---------------------------------------------------------------------
        // 5. MEMBACA JUMLAH KENDARAAN PER EDGE (≥ 0)
        // ---------------------------------------------------------------------
        System.out.println("\nEnter vehicles per edge:");
        for (int i = 0; i < numEdges; i++) {
            vehicles[i] = readIntWithMin(edgeNames[i] + " vehicles: ", 0);
        }

        // ---------------------------------------------------------------------
        // 6. MEMBACA NODE SUMBER & TUJUAN (harus valid)
        // ---------------------------------------------------------------------
        int source = readValidNode("Enter source node: ", nm);
        int dest = readValidNode("Enter destination node: ", nm);

        // ---------------------------------------------------------------------
        // 7. MEMBANGUN SIMULATOR DAN GRAPH
        // ---------------------------------------------------------------------
        TrafficLightSimulator sim = new TrafficLightSimulator(numNodes, vehicles);
        sim.buildGraph(edges);

        // ---------------------------------------------------------------------
        // 8. MENAMPILKAN ASCII GRAPH DAN TABEL KENDARAAN
        // ---------------------------------------------------------------------
        AsciiGraphRenderer.printGraph(nm, edges, edgeNames, vehicles);
        AsciiGraphRenderer.printVehicleTable(edgeNames, edges, vehicles, sim.getDurations(), sim.getSortedVehiclesDescending());
        AsciiGraphRenderer.printDurationTable(nm, edges, edgeNames, vehicles, sim.getDurations(), sim.getSortedDurationsDescending());



        // ---------------------------------------------------------------------
        // 9. OUTPUT SHORTEST PATH
        // ---------------------------------------------------------------------
        System.out.println("\n=== SHORTEST PATH RESULT ===");

        var path = sim.shortestPath(source, dest);
        int numShortestPathNode = path.size();

        System.out.print("Path: ");
        for (int node : path) {
            System.out.print(nm.nameOf(node) + " ");
        }
        System.out.println();

        // Hitung total durasi
        int totalDuration = sim.totalDuration(source, dest);
        System.out.println("Total Duration: " + totalDuration + " secs");

        // ---------------------------------------------------------------------
        // 10. EVALUASI EFEKTIVITAS (0–100 jika bagus, negatif jika lambat)
        // ---------------------------------------------------------------------
        final double AVG_DURATION = 120.0;
        double total_avg_duration = AVG_DURATION * numShortestPathNode;
        double effectiveness;

        if (totalDuration <= total_avg_duration) {
            effectiveness = ((total_avg_duration - totalDuration) / total_avg_duration) * 100.0;
        } else {
            effectiveness = -((totalDuration - total_avg_duration) / total_avg_duration) * 100.0;
        }

        System.out.println("\n=== TRAFFIC LIGHT EFFECTIVENESS ===");
        System.out.printf("Average Traffic Light Duration for This Route: %.2f secs\n", total_avg_duration);
        System.out.printf("Your Route Duration: %d secs\n", totalDuration);
        System.out.printf("Effectiveness: %.2f%%\n", effectiveness);

        // Penilaian kualitas (opsional)
        if (effectiveness == 0) {
            System.out.println("Performance: AVERAGE — Matches typical traffic timing.");
        } else if (effectiveness > 0 && effectiveness < 40) {
            System.out.println("Performance: GOOD — Slightly faster than average.");
        } else if (effectiveness >= 40 && effectiveness < 80) {
            System.out.println("Performance: GREAT — Much faster than average.");
        } else if (effectiveness >= 80) {
            System.out.println("Performance: EXCELLENT — Extremely efficient route!");
        } else if (effectiveness > -40) {
            System.out.println("Performance: BELOW AVERAGE — Slightly slower than typical.");
        } else if (effectiveness > -100) {
            System.out.println("Performance: POOR — Noticeably slower than average.");
        } else {
            System.out.println("Performance: VERY POOR — Highly inefficient route.");
        }

    }

    // =====================================================================
    // METHOD UTILITAS
    // =====================================================================

    // Membaca integer dengan nilai minimum
    private static int readIntWithMin(String msg, int min) {
        int value;
        while (true) {
            System.out.print(msg);
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine();
                continue;
            }
            value = sc.nextInt();
            sc.nextLine();

            if (value < min) {
                System.out.println("Value must be ≥ " + min + ". Try again.");
            } else {
                return value;
            }
        }
    }

    // Membaca nama node dan validasi keberadaannya
    private static int readValidNode(String msg, NodeNameManager nm) {
        while (true) {
            System.out.print(msg);
            String name = sc.next();
            sc.nextLine();

            int idx = nm.indexOf(name);
            if (idx == -1) {
                System.out.println("Junction does not exist. Try again.");
            } else {
                return idx;
            }
        }
    }



}
