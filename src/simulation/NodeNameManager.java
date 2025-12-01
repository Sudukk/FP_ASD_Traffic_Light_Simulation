package simulation;

import java.util.*;

public class NodeNameManager {

    private final HashMap<String, Integer> map = new HashMap<>();
    private final ArrayList<String> names = new ArrayList<>();

    public void add(String name) {
        map.put(name, names.size());
        names.add(name);
    }

    public int indexOf(String name) {
        return map.get(name);
    }

    public String nameOf(int index) {
        return names.get(index);
    }

    public void printNodes() {
        System.out.println("\n=== LIST OF NODES ===");
        for (String n : names)
            System.out.println("[" + n + "]");
    }
}
