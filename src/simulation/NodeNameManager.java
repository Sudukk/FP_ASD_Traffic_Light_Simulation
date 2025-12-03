package simulation;

import java.util.*;

public class NodeNameManager {

    /**
     * A mapping from node name → index.
     *
     * Example:
     *   "Keputih" → 0
     *   "Semolowaru" → 1
     *
     * Used to convert user input strings into numeric node indexes
     * that the graph internally requires.
     */
    private final HashMap<String, Integer> map = new HashMap<>();


    /**
     * Reverse mapping: index → node name.
     *
     * Example:
     *   index 0 → "Keputih"
     *   index 1 → "Semolowaru"
     *
     * Used when printing paths, edges, and ASCII graph output.
     */
    private final ArrayList<String> names = new ArrayList<>();


    /**
     * Adds a new node name to both maps.
     * Called when user enters the list of intersection names.
     *
     * @param name The human-readable junction name entered by the user.
     */
    public void add(String name) {
        // Store name → index (index = current size of names list)
        map.put(name, names.size());

        // Store index → name
        names.add(name);
    }


    /**
     * Converts a node name into its assigned numeric index.
     * This index is used internally by the Graph and Dijkstra.
     *
     * @param name Junction name (string)
     * @return     Index associated with the name
     */
    public int indexOf(String name) {
        return map.get(name);
    }


    /**
     * Converts an index back into a human-readable junction name.
     *
     * @param index Numeric index
     * @return      Node name at that index
     */
    public String nameOf(int index) {
        return names.get(index);
    }


    /**
     * Prints all node names in the order they were added.
     *
     * Output Example:
     *   === LIST OF NODES ===
     *   [Keputih]
     *   [Semolowaru]
     *   [Manyar]
     *
     * This is used when rendering the ASCII traffic junction map.
     */
    public void printNodes() {
        System.out.println("\n=== LIST OF NODES ===");

        for (String n : names)
            System.out.println("[" + n + "]");
    }
}
