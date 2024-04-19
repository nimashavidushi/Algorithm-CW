//Name: S. K. Nimasha Kosgoda
//Student ID: 20210360
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph<T> {
    private Map<T, List<T>> graph = new HashMap<>();
    //method for adding an edge
    public void addEdge(T source, T destination, boolean biDirectional) {
        if (!graph.containsKey(source)) {
            addVertex(source);
        }

        if (!graph.containsKey(destination)) {
            addVertex(destination);
        }

        graph.get(source).add(destination);
        if(biDirectional == true) {
            graph.get(destination).add(source);
        }
    }
    //method for print the graph
    public String printGraph() {
        StringBuilder builder = new StringBuilder();

        for(T vertex : graph.keySet()) {
            builder.append(vertex.toString() + ": ");
            for(T node: graph.get(vertex)) {
                builder.append(node.toString() + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void addVertex(T vertex) {
        graph.put(vertex, new LinkedList<T>());
    }
    //method to find sink nodes
    public List<T> findSink() {
        List<T> sinks = new ArrayList<>();
        for (T vertex : graph.keySet()) {
            if (graph.get(vertex).isEmpty()) {
                boolean isSink = true;
                for (T otherVertex : graph.keySet()) {
                    if (otherVertex.equals(vertex) && graph.get(otherVertex).contains(vertex)) {
                        isSink = false;
                        break;
                    }
                }
                if (isSink) {
                    sinks.add(vertex);
                }
            }
        }
        if (sinks.isEmpty()) {
            System.out.println("The graph does not contain a sink");
        } else {
            System.out.println("The sink(s) of the graph is/are: " + sinks);
        }
        return sinks;
    }
    //method to read the input text file
    public void parse(String filename, boolean biDirectional) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                T source = (T) tokenizer.nextToken();
                T destination = (T) tokenizer.nextToken();
                //calls the addEdge method to add the nodes
                addEdge(source, destination, biDirectional);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    //method to remove vertex
    public void removeVertex(String vertex) {
        if (!graph.containsKey(vertex)) {
            System.out.println("The Graph does not contain " + vertex + " as a vertex");
            return;
        }
        long startTime = System.nanoTime(); // Record start time
        // remove incoming edges
        for (T key : graph.keySet()) {
            graph.get(key).remove(vertex);
        }

        // remove outgoing edges
        graph.remove(vertex);
        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
        System.out.println("Graph after removing vertex"+vertex+":"+"\n"+printGraph());
        System.out.println("Elapsed Time: " + elapsedTime + " nanoseconds");
    }

    public boolean isAcyclic() {
        Set<T> visited = new HashSet<>();
        Set<T> recStack = new HashSet<>();

        for (T vertex : graph.keySet()) {
            if (isCyclicUtil(vertex, visited, recStack)) {
                return false;
            }
        }

        return true;
    }

    private boolean isCyclicUtil(T vertex, Set<T> visited, Set<T> recStack) {
        if (recStack.contains(vertex)) {
            return true;
        }

        if (visited.contains(vertex)) {
            return false;
        }

        visited.add(vertex);
        recStack.add(vertex);

        List<T> neighbors = graph.get(vertex);
        for (T neighbor : neighbors) {
            if (isCyclicUtil(neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack.remove(vertex);

        return false;
    }

    public List<T> findCycle() {
        Set<T> visited = new HashSet<>();
        Set<T> recStack = new HashSet<>();
        List<T> cycle = new ArrayList<>();

        long startTime = System.nanoTime(); // Record start time
        for (T vertex : graph.keySet()) {
            if (findCycleUtil(vertex, visited, recStack, cycle)){
                long endTime = System.nanoTime(); // Record end time
                long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
                System.out.println("Elapsed Time: " + elapsedTime + " nanoseconds");
                return cycle;
            }
        }
        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
        System.out.println("Elapsed Time: " + elapsedTime + " nanoseconds");
        return null;
    }

    private boolean findCycleUtil(T vertex, Set<T> visited, Set<T> recStack, List<T> cycle) {
        if (recStack.contains(vertex)) {
            cycle.add(vertex);
            return true;
        }

        if (visited.contains(vertex)) {
            return false;
        }

        visited.add(vertex);
        recStack.add(vertex);

        List<T> neighbors = graph.get(vertex);
        for (T neighbor : neighbors) {
            if (findCycleUtil(neighbor, visited, recStack, cycle)) {
                cycle.add(vertex);
                return true;
            }
        }

        recStack.remove(vertex);

        return false;
    }
}
