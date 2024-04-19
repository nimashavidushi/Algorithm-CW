import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GraphImplementation {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.parse("C:\\Algorithm CW\\src\\inputGraph.txt", false);
        Scanner input = new Scanner(System.in);
        String option;
        do {
            try {
                System.out.println("-------Console Menu-------");
                System.out.println("P.  print graph");
                System.out.println("S.  find sink");
                System.out.println("R.  remove vertex");
                System.out.println("T.  get the graph type cyclic or acyclic");
                System.out.println("C.  get the cycle");
                System.out.println("E.  exit");
                System.out.println("Choose an option");
                option = input.nextLine().toUpperCase();
                switch (option){
                    case "P":
                        System.out.println(graph.printGraph());
                        break;
                    case "S":
                        long startTime = System.nanoTime(); // Record start time
                        graph.findSink();
                        long endTime = System.nanoTime(); // Record end time
                        long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
                        System.out.println("Elapsed Time: " + elapsedTime + " nanoseconds");
                        break;
                    case "R":
                        System.out.println("Enter a vertex to delete:");
                        String vertex = input.next();
                        graph.removeVertex(vertex);
                        break;
                    case "T":
                        boolean isGraphAcyclic = graph.isAcyclic();
                        System.out.println("Is the graph acyclic? " + isGraphAcyclic);
                        break;
                    case "C":
                        List cycle = graph.findCycle();
                        if (cycle != null) {
                            Collections.reverse(cycle);
                            System.out.println("Cycle found: " + cycle);
                        } else {
                            System.out.println("No cycle found.");
                        }
                        break;
                    case "E":
                        System.exit(0);
                        break;
                }
            }catch (Exception e){
                System.out.println("Invalid input");
                input.nextLine();
            }
        }while (true);
    }
}
