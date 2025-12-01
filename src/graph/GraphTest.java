package graph;

public class GraphTest {
	
 public static void main(String[] args) {

     Graph g = new Graph(6);

     // Add edges (use positive weights only if testing Dijkstra)
     g.addEdge(0, 1, 7);
     g.addEdge(0, 2, 9);
     g.addEdge(0, 5, 14);
     g.addEdge(1, 2, 10);
     g.addEdge(1, 3, 15);
     g.addEdge(2, 3, 11);
     g.addEdge(2, 5, 2);
     g.addEdge(3, 4, 6);
     g.addEdge(5, 4, 9);

     int start = 0;
     int end = 4;

     // Testing Dijkstra's Algorithm
     int[] parentD = new int[6];
     int[] distD = g.dijkstra(start, parentD);

     for (int i = 0; i < distD.length; i++)
         System.out.println("To " + i + " = " + distD[i]);

     System.out.println("\nDijkstra Path " + start + " → " + end + ":");
     System.out.println(g.buildPath(start, end, parentD));


     // Testing Bellman-Ford
     int[] parentB = new int[6];
     try {
         int[] distB = g.bellmanFord(start, parentB);

         for (int i = 0; i < distB.length; i++)
             System.out.println("To " + i + " = " + distB[i]);

         System.out.println("\nBellman-Ford Path " + start + " → " + end + ":");
         System.out.println(g.buildPath(start, end, parentB));

     } catch (RuntimeException e) {
         System.out.println("\n" + e.getMessage());
     }
 }
}