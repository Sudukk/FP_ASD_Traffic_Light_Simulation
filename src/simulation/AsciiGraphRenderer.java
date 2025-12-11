package simulation;

public class AsciiGraphRenderer {

    // ============================================================
    //   HELPER → Sort seluruh array terkait berdasarkan vehicles DESC
    //   (TIDAK dipakai lagi pada versi terbaru, tapi tetap disimpan)
    //   Tujuan: menjaga array edgeNames, vehicles, edges, durations tetap sinkron
    // ============================================================
    private static void sortByVehiclesDescending(
            String[] edgeNames,
            int[] vehicles,
            int[][] edges,
            int[] durations
    ) {
        // Salin array vehicles lalu sort descending
        int[] sorted = MergeSort.sortDescending(vehicles.clone());

        // Sinkronkan setiap elemen yang memiliki nilai sama
        for (int i = 0; i < sorted.length; i++) {
            for (int j = 0; j < vehicles.length; j++) {

                // Ketika nilai sama ditemukan → lakukan swapping
                if (sorted[i] == vehicles[j]) {

                    // swap jumlah kendaraan
                    int tempV = vehicles[i];
                    vehicles[i] = vehicles[j];
                    vehicles[j] = tempV;

                    // swap durasi
                    int tempD = durations[i];
                    durations[i] = durations[j];
                    durations[j] = tempD;

                    // swap endpoints edge
                    int[] tempE = edges[i];
                    edges[i] = edges[j];
                    edges[j] = tempE;

                    // swap nama edge
                    String tempName = edgeNames[i];
                    edgeNames[i] = edgeNames[j];
                    edgeNames[j] = tempName;

                    break;
                }
            }
        }
    }


    // ============================================================
    //   HELPER → Sort berdasarkan DURASI (DESC)
    //   (TIDAK dipakai lagi pada versi terbaru)
    // ============================================================
    private static void sortByDurationsDescending(
            String[] edgeNames,
            int[] vehicles,
            int[][] edges,
            int[] durations
    ) {
        // Sort array durasi secara descending
        int[] sorted = MergeSort.sortDescending(durations.clone());

        for (int i = 0; i < sorted.length; i++) {
            for (int j = 0; j < durations.length; j++) {

                // Ketika nilai cocok → sinkronkan semua array
                if (sorted[i] == durations[j]) {

                    int tempD = durations[i];
                    durations[i] = durations[j];
                    durations[j] = tempD;

                    int tempV = vehicles[i];
                    vehicles[i] = vehicles[j];
                    vehicles[j] = tempV;

                    int[] tempE = edges[i];
                    edges[i] = edges[j];
                    edges[j] = tempE;

                    String tempName = edgeNames[i];
                    edgeNames[i] = edgeNames[j];
                    edgeNames[j] = tempName;

                    break;
                }
            }
        }
    }



    // ============================================================
    //   PRINT ASCII GRAPH (tidak ada sorting)
    //   Menampilkan hubungan node dan edge dalam bentuk ASCII
    // ============================================================
    public static void printGraph(NodeNameManager nm, int[][] edges, String[] edgeNames, int[] vehicles) {

        System.out.println("\n=== ASCII TRAFFIC JUNCTION ===");

        // Print daftar nama node
        nm.printNodes();

        System.out.println("\nConnections:");

        // Loop seluruh edge dan tampilkan koneksinya
        for (int i = 0; i < edges.length; i++) {

            int u = edges[i][0];   // node asal
            int v = edges[i][1];   // node tujuan

            System.out.println(" " + nm.nameOf(u) + " ---- " + edgeNames[i] +
                    " (" + vehicles[i] + " cars) ----> " + nm.nameOf(v));
        }
    }



    // ============================================================
    //   PRINT VEHICLE TABLE (sorted DESCENDING)
    //   Sorting dilakukan dengan mencocokkan urutan sortedVehiclesDesc
    // ============================================================
    public static void printVehicleTable(
            String[] edgeNames,
            int[][] edges,
            int[] vehicles,
            int[] durations,
            int[] sortedVehiclesDesc
    ) {
        System.out.println("\n=== VEHICLE TABLE (SORTED DESCENDING) ===");

        // Mendapatkan urutan indeks hasil sorting
        Integer[] order = getSortOrder(vehicles, sortedVehiclesDesc);

        // Cari panjang kolom maksimal untuk format tabel
        int maxLen = "Edge".length();
        for (String s : edgeNames) maxLen = Math.max(maxLen, s.length());

        String fmt = "%-" + maxLen + "s";
        String border = "+" + "-".repeat(maxLen + 2) + "+----------------+";

        System.out.println(border);
        System.out.printf("| " + fmt + " | %-14s |\n", "Road", "Num Vehicles");
        System.out.println(border);

        // Print berdasarkan urutan sorted
        for (int idx : order) {
            System.out.printf("| " + fmt + " | %-14d |\n", edgeNames[idx], vehicles[idx]);
        }

        System.out.println(border);
    }



    // ============================================================
    //   PRINT DURATION TABLE (sorted descending)
    //   Tabel menampilkan Node → Edge → Kendaraan → Durasi
    // ============================================================
    public static void printDurationTable(
            NodeNameManager nm,
            int[][] edges,
            String[] edgeNames,
            int[] vehicles,
            int[] durations,
            int[] sortedDurationsDesc
    ) {
        System.out.println("\n=== EDGE DURATION TABLE (SORTED DESCENDING) ===");

        // Dapatkan urutan index hasil sorting durasi
        Integer[] order = getSortOrder(durations, sortedDurationsDesc);

        // Hitung panjang kolom "Node" dan "Edge"
        int maxNodeLen = "Node".length();
        int maxEdgeLen = "Edge".length();

        for (int i = 0; i < edges.length; i++) {
            maxNodeLen = Math.max(maxNodeLen, nm.nameOf(edges[i][1]).length());

            String text = nm.nameOf(edges[i][0]) + " ---- " + nm.nameOf(edges[i][1]);
            maxEdgeLen = Math.max(maxEdgeLen, text.length());
        }

        String nodeFmt = "%-" + maxNodeLen + "s";
        String edgeFmt = "%-" + maxEdgeLen + "s";

        String border = "+"
                + "-".repeat(maxNodeLen + 2)
                + "+" + "-".repeat(maxEdgeLen + 2)
                + "+----------------+----------------+";

        System.out.println(border);

        // Header tabel
        System.out.printf(
                "| " + nodeFmt + " | " + edgeFmt + " | %-14s | %-14s |\n",
                "Junction", "Road", "Num Vehicles", "Duration (sec)"
        );

        System.out.println(border);

        // Print isi tabel sesuai urutan sorting
        for (int idx : order) {
            String from = nm.nameOf(edges[idx][0]);
            String to   = nm.nameOf(edges[idx][1]);
            String edgeText = from + " ---- " + to;

            System.out.printf(
                    "| " + nodeFmt + " | " + edgeFmt + " | %-14d | %-14d |\n",
                    to, edgeText, vehicles[idx], durations[idx]
            );
        }

        System.out.println(border);
    }



    // ============================================================
    //   HELPER: Menghasilkan urutan indeks hasil sorting
    //   original = array asli
    //   sortedDesc = array hasil merge sort
    // ============================================================
    private static Integer[] getSortOrder(int[] original, int[] sortedDesc) {

        Integer[] order = new Integer[sortedDesc.length];
        boolean[] used = new boolean[original.length]; // untuk menghindari duplikasi index

        // Loop nilai sorted, cari index aslinya
        for (int i = 0; i < sortedDesc.length; i++) {
            int target = sortedDesc[i];

            for (int j = 0; j < original.length; j++) {

                // Jika nilai cocok dan belum dipakai → simpan index-nya
                if (!used[j] && original[j] == target) {
                    order[i] = j;
                    used[j] = true;
                    break;
                }
            }
        }

        return order;
    }

}
