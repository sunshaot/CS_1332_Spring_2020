import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * Your implementation of various graph algorithms.
 *
 * @author Shaotong Sun
 * @version 1.0
 * @userid ssun319
 * @GTID 903453285
 *
 * Collaborators: No
 *
 * Resources: Slide
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Error, start cannot be null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Error, graph cannot be null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!(adjList.containsKey(start))) {
            throw new IllegalArgumentException("Error, start doesn't exist in graph!");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> returnList = new ArrayList<>();
        visitedSet.add(start);
        queue.add(start);
        while (!(queue.isEmpty())) {
            Vertex<T> vertex = queue.remove();
            returnList.add(vertex);
            for (VertexDistance<T> v: adjList.get(vertex)) {
                if (!(visitedSet.contains(v.getVertex()))) {
                    visitedSet.add(v.getVertex());
                    queue.add(v.getVertex());
                }
            }
        }
        return returnList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Error, start cannot be null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Error, graph cannot be null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!(adjList.containsKey(start))) {
            throw new IllegalArgumentException("Error, start doesn't exist in graph!");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> returnList = new ArrayList<>();
        rdfs(start, visitedSet, returnList, adjList);
        return returnList;
    }

    /**
     * Helper method for dfs
     * @param start start
     * @param visitedSet visited set
     * @param returnList return list
     * @param adjList adj list
     * @param <T> data type
     */
    private static <T> void rdfs(Vertex<T> start, Set<Vertex<T>> visitedSet, List<Vertex<T>> returnList,
                                 Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        visitedSet.add(start);
        returnList.add(start);
        for (VertexDistance<T> v: adjList.get(start)) {
            if (!(visitedSet.contains(v.getVertex()))) {
                rdfs(v.getVertex(), visitedSet, returnList, adjList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Error, start cannot be null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Error, graph cannot be null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!(adjList.containsKey(start))) {
            throw new IllegalArgumentException("Error, start doesn't exist in graph!");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>(graph.getVertices().size());
        PriorityQueue<VertexDistance<T>> pqueue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> returnMap = new HashMap<>();
        for (Vertex<T> v: graph.getVertices()) {
            if (v.equals(start)) {
                returnMap.put(start, 0);
            } else {
                returnMap.put(v, Integer.MAX_VALUE);
            }
        }
        pqueue.add(new VertexDistance<>(start, 0));
        while (!(pqueue.isEmpty()) && visitedSet.size() < graph.getVertices().size()) {
            VertexDistance<T> v = pqueue.poll();
            Vertex<T> u = v.getVertex();
            int d = v.getDistance();
            if (!(visitedSet.contains(v))) {
                visitedSet.add(u);
                if (returnMap.get(u) > d) {
                    returnMap.put(u, d);
                }
                for (VertexDistance<T> vd: adjList.get(u)) {
                    if (!(visitedSet.contains(vd.getVertex()))) {
                        pqueue.add(new VertexDistance<>(vd.getVertex(),
                                d + vd.getDistance()));
                    }
                }
            }
        }
        return returnMap;
    }
}
