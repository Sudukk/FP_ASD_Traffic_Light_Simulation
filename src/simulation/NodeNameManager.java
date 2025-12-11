package simulation;

import java.util.*;

public class NodeNameManager {

    private final HashMap<String, Integer> map = new HashMap<>(); // Map nama node -> index
    private final ArrayList<String> names = new ArrayList<>(); // List index -> nama node

    public void add(String name) {
        map.put(name, names.size()); // Simpan mapping nama → index
        names.add(name); // Simpan nama di urutan input
    }

    public int indexOf(String name) {
        return map.getOrDefault(name, -1); // Mengambil index, return -1 jika tidak ada
    }

    public String nameOf(int index) {
        if (index < 0 || index >= names.size()) return null; // Cegah index invalid
        return names.get(index); // Ambil nama berdasarkan index
    }

    public void printNodes() {
        System.out.println("\n=== LIST OF NODES ==="); // Header daftar node
        for (String n : names)// Loop semua node
            System.out.println("[" + n + "]");// Cetak node
    }
}
