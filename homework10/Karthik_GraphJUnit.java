import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class Karthik_GraphJUnit {
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {

    }

    ///////////////////////////////
    //BFS Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bfsNullVertex() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        Graph<Integer> gr = new Graph<Integer>(vertices, edges);
        GraphAlgorithms.bfs(null, gr);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bfsNullGraph() {
        Vertex<Integer> v = new Vertex<>(1);
        GraphAlgorithms.bfs(v, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bfsVertexNotInGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        Graph<Integer> gr = new Graph<Integer>(vertices, edges);
        GraphAlgorithms.bfs(new Vertex<Integer>(3), gr);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bfsVertexNotInGraphBiggerGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        Graph<Integer> g = new Graph<>(vertices, edges);
        GraphAlgorithms.bfs(new Vertex<Integer>(8), g);
    }

    @Test(timeout = TIMEOUT)
    public void bfsOneVertexCycle() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(5));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(5), 0));
        Graph<Integer> g = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<Integer>(5));
        assertEquals(expected, GraphAlgorithms.bfs(new Vertex<>(5), g));
    }

    @Test(timeout = TIMEOUT)
    public void bfsOneVertexNoEdges() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(5));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        Graph<Integer> g = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(5));
        assertEquals(expected, GraphAlgorithms.bfs(new Vertex<>(5), g));
    }

    @Test(timeout = TIMEOUT)
    public void normalBFS() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(3), 0));
        Graph<Integer> graph = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(3));
        assertEquals(expected, GraphAlgorithms.bfs(new Vertex<>(1),
                graph));
    }

    @Test(timeout = TIMEOUT)
    public void bfsLargeGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        vertices.add(new Vertex<>(8));
        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(7), 0));


        Graph<Integer> graph = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<Vertex<Integer>>();
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(5));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(7));
        expected.add(new Vertex<>(6));
        expected.add(new Vertex<>(8));
        assertEquals(expected, GraphAlgorithms.bfs(new Vertex<>(1), graph));
    }

    @Test(timeout = TIMEOUT)
    public void bfsDiGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(6), 0));
        edges.add(new Edge<Integer>(new Vertex<>(6), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(6), new Vertex<>(1), 0));
        Graph<Integer> diGraph = new Graph<Integer>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<Integer>(1));
        expected.add(new Vertex<Integer>(2));
        expected.add(new Vertex<Integer>(3));
        expected.add(new Vertex<Integer>(6));
        expected.add(new Vertex<Integer>(5));
        expected.add(new Vertex<Integer>(4));
        assertEquals(expected, GraphAlgorithms.bfs(new Vertex<Integer>(1),
                diGraph));
    }

    @Test(timeout = TIMEOUT)
    public void bfsUnconnectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(4), 0));
        Graph<Integer> unconnectedGraph = new Graph<>(vertices, edges);
        ArrayList<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(5));
        List<Vertex<Integer>> actual = GraphAlgorithms.bfs(new Vertex<>(2),
                unconnectedGraph);
        assertEquals(expected,
                actual);
        assertTrue("Your array contains a 6",
                !actual.contains(new Vertex<>(6)));
        assertTrue("Your array contains a 7",
                !actual.contains(new Vertex<>(7)));
    }

    @Test(timeout = TIMEOUT)
    public void bfsUnconnectedGraphIsolation() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(4), 0));
        Graph<Integer> unconnectedGraph = new Graph<>(vertices, edges);
        ArrayList<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(6));
        List<Vertex<Integer>> actual = GraphAlgorithms.bfs(new Vertex<>(6),
                unconnectedGraph);
        assertEquals(expected,
                actual);
        assertTrue("Your array is containing more than 1 element.",
                actual.size() == 1);
        assertTrue("Your array should not be empty", actual.size() != 0);
        ArrayList<Vertex<Integer>> expected2 = new ArrayList<>();
        expected2.add(new Vertex<>(7));
        actual = GraphAlgorithms.bfs(new Vertex<>(7), unconnectedGraph);
        assertEquals(expected2,
                actual);
        assertTrue("Your array is containing more than 1 element.",
                actual.size() == 1);
        assertTrue("Your array should not be empty", actual.size() != 0);
    }

    ////////////////////////////////////////////////
    //DFS Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dfsNullVertex() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        Graph<Integer> gr = new Graph<Integer>(vertices, edges);
        GraphAlgorithms.dfs(null, gr);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dfsNullGraph() {
        Vertex<Integer> v = new Vertex<>(1);
        GraphAlgorithms.dfs(v, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dfsVertexNotInGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        Graph<Integer> gr = new Graph<Integer>(vertices, edges);
        GraphAlgorithms.dfs(new Vertex<Integer>(3), gr);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dfsVertexNotInGraphBiggerGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        Graph<Integer> g = new Graph<>(vertices, edges);
        GraphAlgorithms.dfs(new Vertex<Integer>(8), g);
    }

    @Test(timeout = TIMEOUT)
    public void dfsOneVertexCycle() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(5));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(5), 0));
        Graph<Integer> g = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<Integer>(5));
        assertEquals(expected, GraphAlgorithms.dfs(new Vertex<>(5), g));
    }

    @Test(timeout = TIMEOUT)
    public void dfsOneVertexNoEdges() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(5));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        Graph<Integer> g = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(5));
        assertEquals(expected, GraphAlgorithms.dfs(new Vertex<>(5), g));
    }

    @Test(timeout = TIMEOUT)
    public void normalDFS() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(3), 0));
        Graph<Integer> graph = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(4));
        assertEquals(expected, GraphAlgorithms.dfs(new Vertex<>(1),
                graph));
    }

    @Test(timeout = TIMEOUT)
    public void biggerGraphDFS() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        vertices.add(new Vertex<>(8));
        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(7), 0));


        Graph<Integer> graph = new Graph<>(vertices, edges);
        List<Vertex<Integer>> expected = new ArrayList<Vertex<Integer>>();
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(5));
        expected.add(new Vertex<>(7));
        expected.add(new Vertex<>(6));
        expected.add(new Vertex<>(8));
        assertEquals(expected, GraphAlgorithms.dfs(new Vertex<>(1), graph));
    }

    @Test(timeout = TIMEOUT)
    public void dfsUnconnectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(3), 0));
        Graph<Integer> unconnectedGraph = new Graph<>(vertices, edges);
        ArrayList<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(5));
        List<Vertex<Integer>> actual = GraphAlgorithms.dfs(new Vertex<>(2),
                unconnectedGraph);
        assertEquals(expected,
                actual);
        assertTrue("Your array contains a 6",
                !actual.contains(new Vertex<>(6)));
        assertTrue("Your array contains a 7",
                !actual.contains(new Vertex<>(7)));
    }

    @Test(timeout = TIMEOUT)
    public void dfsUnconnectedGraphIsolation() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(5), 0));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(1), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(4), 0));
        Graph<Integer> unconnectedGraph = new Graph<>(vertices, edges);
        ArrayList<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(6));
        List<Vertex<Integer>> actual = GraphAlgorithms.dfs(new Vertex<>(6),
                unconnectedGraph);
        assertEquals(expected,
                actual);
        assertTrue("Your array is containing more than 1 element.",
                actual.size() == 1);
        assertTrue("Your array should not be empty", actual.size() != 0);
        ArrayList<Vertex<Integer>> expected2 = new ArrayList<>();
        expected2.add(new Vertex<>(7));
        actual = GraphAlgorithms.dfs(new Vertex<>(7), unconnectedGraph);
        assertEquals(expected2,
                actual);
        assertTrue("Your array is containing more than 1 element.",
                actual.size() == 1);
        assertTrue("Your array should not be empty", actual.size() != 0);
    }

    ////////////////////////////////////////////////////
    //Djikstra's Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dijkstraNullVertex() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        Graph<Integer> gr = new Graph<Integer>(vertices, edges);
        GraphAlgorithms.dijkstras(null, gr);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dijkstraNullGraph() {
        Vertex<Integer> v = new Vertex<>(1);
        GraphAlgorithms.dijkstras(v, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dijkstraVertexNotInGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        Graph<Integer> gr = new Graph<Integer>(vertices, edges);
        GraphAlgorithms.bfs(new Vertex<Integer>(3), gr);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void dijkstraVertexNotInGraphBiggerGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        Graph<Integer> g = new Graph<>(vertices, edges);
        GraphAlgorithms.dijkstras(new Vertex<Integer>(8), g);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstraOneVertexNoEdges() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        vertices.add(new Vertex<>(5));
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        Graph<Integer> g = new Graph<>(vertices, edges);
        Map<Vertex<Integer>, Integer> expected = new HashMap<Vertex<Integer>,
                Integer>();
        expected.put(new Vertex<>(5), 0);
        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<>(5), g));
    }

    @Test(timeout = TIMEOUT)
    public void dijkstrasSmallGraph() {
        Set<Vertex<String>> placeList = new HashSet<Vertex<String>>();
        Set<Edge<String>> edges = new HashSet<Edge<String>>();
        placeList.add(new Vertex<>("Student Center"));
        placeList.add(new Vertex<>("College of Computing"));
        placeList.add(new Vertex<>("West Village"));
        placeList.add(new Vertex<>("North Ave"));
        placeList.add(new Vertex<>("CRC"));
        edges.add(new Edge<String>(new Vertex<>("College of Computing"),
                new Vertex<>("Student Center"), 9));
        edges.add(new Edge<String>(new Vertex<>("Student Center"),
                new Vertex<>("College of Computing"), 9));

        edges.add(new Edge<String>(new Vertex<>("College of Computing"),
                new Vertex<>("West Village"), 10));
        edges.add(new Edge<String>(new Vertex<>("West Village"),
                new Vertex<>("College of Computing"), 10));

        edges.add(new Edge<String>(new Vertex<>("West Village"),
                new Vertex<>("North Ave"), 23));
        edges.add(new Edge<String>(new Vertex<>("North Ave"),
                new Vertex<>("West Village"), 23));

        edges.add(new Edge<String>(new Vertex<>("College of Computing"),
                new Vertex<>("North Ave"), 16));
        edges.add(new Edge<String>(new Vertex<>("North Ave"),
                new Vertex<>("College of Computing"), 16));

        edges.add(new Edge<String>(new Vertex<>("College of Computing"),
                new Vertex<>("CRC"), 10));
        edges.add(new Edge<String>(new Vertex<>("CRC"),
                new Vertex<>("College of Computing"), 10));

        edges.add(new Edge<String>(new Vertex<>("CRC"),
                new Vertex<>("West Village"), 8));
        edges.add(new Edge<String>(new Vertex<>("West Village"),
                new Vertex<>("CRC"), 8));

        edges.add(new Edge<String>(new Vertex<>("CRC"),
                new Vertex<>("North Ave"), 17));
        edges.add(new Edge<String>(new Vertex<>("North Ave"),
                new Vertex<>("CRC"), 17));

        edges.add(new Edge<String>(new Vertex<>("CRC"),
                new Vertex<>("Student Center"), 7));
        edges.add(new Edge<String>(new Vertex<>("Student Center"),
                new Vertex<>("CRC"), 7));

        edges.add(new Edge<String>(new Vertex<>("Student Center"),
                new Vertex<>("North Ave"), 11));
        edges.add(new Edge<String>(new Vertex<>("North Ave"),
                new Vertex<>("Student Center"), 11));

        edges.add(new Edge<String>(new Vertex<>("Student Center"),
                new Vertex<>("West Village"), 15));
        edges.add(new Edge<String>(new Vertex<>("West Village"),
                new Vertex<>("Student Center"), 15));
        Graph<String> gatech = new Graph<>(placeList, edges);
        Map<Vertex<String>, Integer> expected = new HashMap<Vertex<String>,
                Integer>();
        expected.put(new Vertex<>("Student Center"), 7);
        expected.put(new Vertex<>("CRC"), 0);
        expected.put(new Vertex<>("North Ave"), 17);
        expected.put(new Vertex<>("College of Computing"), 10);
        expected.put(new Vertex<>("West Village"), 8);
        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<>("CRC"),
                gatech));
        assertTrue("The shortest distance from where you start to itself "
                        + "cannot be a positive integer ",
                GraphAlgorithms.dijkstras(new Vertex<>("CRC"), gatech).get(new Vertex<>("CRC")) == 0);
    }

    @Test(timeout = TIMEOUT)
    public void djikstraBiggerGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        vertices.add(new Vertex<>(8));
        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 3));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(1), 3));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 5));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 5));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(2), 7));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 7));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 9));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(2), 9));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 6));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(3), 6));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 8));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(3), 8));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(5), 11));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 11));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(7), 13));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(4), 13));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 15));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(5), 15));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(6), 17));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(5), 17));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 19));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 19));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(8), 2));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(6), 2));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 21));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(7), 21));


        Graph<Integer> graph = new Graph<>(vertices, edges);
        Map<Vertex<Integer>, Integer> distances = new HashMap<Vertex<Integer>
                , Integer>();
        Random rand = new Random();
        int randNum = rand.nextInt(8) + 1;
        if (randNum <= 4) {
            distances.put(new Vertex<>(1), 0);
            distances.put(new Vertex<>(2), 3);
            distances.put(new Vertex<>(3), 5);
            distances.put(new Vertex<>(4), 11);
            distances.put(new Vertex<>(5), 13);
            distances.put(new Vertex<>(6), 30);
            distances.put(new Vertex<>(7), 24);
            distances.put(new Vertex<>(8), 32);
            assertEquals(distances, GraphAlgorithms.dijkstras(new Vertex<>(1)
                    , graph));
            assertTrue("Distance from start vertex to itself cannot be a "
                            + "positive integer",
                    GraphAlgorithms.dijkstras(new Vertex<>(1), graph).get(new Vertex<>(1)) == 0);
        } else if (randNum > 4 && randNum <= 6) {
            distances.put(new Vertex<>(1), 11);
            distances.put(new Vertex<>(2), 9);
            distances.put(new Vertex<>(3), 6);
            distances.put(new Vertex<>(4), 0);
            distances.put(new Vertex<>(5), 11);
            distances.put(new Vertex<>(6), 28);
            distances.put(new Vertex<>(7), 13);
            distances.put(new Vertex<>(8), 30);
            assertEquals(distances, GraphAlgorithms.dijkstras(new Vertex<>(4)
                    , graph));
            assertTrue("Distance from start vertex to itself cannot be a "
                            + "positive integer",
                    GraphAlgorithms.dijkstras(new Vertex<>(4), graph).get(new Vertex<>(4)) == 0);
        } else {
            distances.put(new Vertex<>(1), 32);
            distances.put(new Vertex<>(2), 34);
            distances.put(new Vertex<>(3), 27);
            distances.put(new Vertex<>(4), 30);
            distances.put(new Vertex<>(5), 19);
            distances.put(new Vertex<>(6), 2);
            distances.put(new Vertex<>(7), 21);
            distances.put(new Vertex<>(8), 0);
            assertEquals(distances, GraphAlgorithms.dijkstras(new Vertex<>(8)
                    , graph));
            assertTrue("Distance from start vertex to itself cannot be a "
                            + "positive integer",
                    GraphAlgorithms.dijkstras(new Vertex<>(8), graph).get(new Vertex<>(8)) == 0);
        }
    }

    @Test(timeout = TIMEOUT)
    public void djikstraUnconnectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(5), 3));
        edges.add(new Edge<Integer>(new Vertex<>(5), new Vertex<>(1), 3));
        edges.add(new Edge<Integer>(new Vertex<>(1), new Vertex<>(2), 4));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(1), 4));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(4), 6));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(2), 6));
        edges.add(new Edge<Integer>(new Vertex<>(2), new Vertex<>(3), 12));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(2), 12));
        edges.add(new Edge<Integer>(new Vertex<>(3), new Vertex<>(4), 9));
        edges.add(new Edge<Integer>(new Vertex<>(4), new Vertex<>(3), 9));
        Graph<Integer> unconnectedGraph = new Graph<>(vertices, edges);
        Map<Vertex<Integer>, Integer> expected = new HashMap<Vertex<Integer>,
                Integer>();
        expected.put(new Vertex<>(5), 0);
        expected.put(new Vertex<>(1), 3);
        expected.put(new Vertex<>(2), 7);
        expected.put(new Vertex<>(4), 13);
        expected.put(new Vertex<>(3), 19);
        expected.put(new Vertex<>(6), Integer.MAX_VALUE);
        expected.put(new Vertex<>(7), Integer.MAX_VALUE);
        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<>(5),
                unconnectedGraph));
        Map<Vertex<Integer>, Integer> expected2 = new HashMap<Vertex<Integer>,
                Integer>();
        expected2.put(new Vertex<>(5), Integer.MAX_VALUE);
        expected2.put(new Vertex<>(1), Integer.MAX_VALUE);
        expected2.put(new Vertex<>(2), Integer.MAX_VALUE);
        expected2.put(new Vertex<>(4), Integer.MAX_VALUE);
        expected2.put(new Vertex<>(3), Integer.MAX_VALUE);
        expected2.put(new Vertex<>(6), 0);
        expected2.put(new Vertex<>(7), Integer.MAX_VALUE);
        assertEquals(expected2, GraphAlgorithms.dijkstras(new Vertex<>(6),
                unconnectedGraph));
        Map<Vertex<Integer>, Integer> expected3 = new HashMap<Vertex<Integer>,
                Integer>();
        expected3.put(new Vertex<>(5), Integer.MAX_VALUE);
        expected3.put(new Vertex<>(1), Integer.MAX_VALUE);
        expected3.put(new Vertex<>(2), Integer.MAX_VALUE);
        expected3.put(new Vertex<>(4), Integer.MAX_VALUE);
        expected3.put(new Vertex<>(3), Integer.MAX_VALUE);
        expected3.put(new Vertex<>(6), Integer.MAX_VALUE);
        expected3.put(new Vertex<>(7), 0);
        assertEquals(expected3, GraphAlgorithms.dijkstras(new Vertex<>(7),
                unconnectedGraph));
    }
    ///////////////////////////////////////////////////
    //Fun Graphs

    @Test(timeout = TIMEOUT)
    public void linkedListGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        vertices.add(new Vertex<>(8));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 1));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(1), 1));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 2));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(2), 2));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 3));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(3), 3));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(5), 4));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 4));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(6), 5));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(5), 5));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 6));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 6));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 7));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(7), 7));
        Graph<Integer> graph = new Graph<>(vertices, edges);
        ArrayList<Vertex<Integer>> expected = new ArrayList<>();
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(5));
        expected.add(new Vertex<>(6));
        expected.add(new Vertex<>(7));
        expected.add(new Vertex<>(8));
        assertEquals(expected, GraphAlgorithms.bfs(new Vertex<>(1), graph));
        assertEquals(expected, GraphAlgorithms.dfs(new Vertex<>(1), graph));
        ArrayList<Vertex<Integer>> expected2 = new ArrayList<>();
        expected2.add(new Vertex<>(4));
        expected2.add(new Vertex<>(3));
        expected2.add(new Vertex<>(5));
        expected2.add(new Vertex<>(2));
        expected2.add(new Vertex<>(6));
        expected2.add(new Vertex<>(1));
        expected2.add(new Vertex<>(7));
        expected2.add(new Vertex<>(8));
        assertEquals(expected2, GraphAlgorithms.bfs(new Vertex<>(4), graph));
        ArrayList<Vertex<Integer>> expected3 = new ArrayList<>();
        expected3.add(new Vertex<>(4));
        expected3.add(new Vertex<>(3));
        expected3.add(new Vertex<>(2));
        expected3.add(new Vertex<>(1));
        expected3.add(new Vertex<>(5));
        expected3.add(new Vertex<>(6));
        expected3.add(new Vertex<>(7));
        expected3.add(new Vertex<>(8));
        assertEquals(expected3, GraphAlgorithms.dfs(new Vertex<>(4), graph));
        Map<Vertex<Integer>, Integer> distances =
                new HashMap<Vertex<Integer>, Integer>();
        distances.put(new Vertex<>(1), 0);
        distances.put(new Vertex<>(2), 1);
        distances.put(new Vertex<>(3), 3);
        distances.put(new Vertex<>(4), 6);
        distances.put(new Vertex<>(5), 10);
        distances.put(new Vertex<>(6), 15);
        distances.put(new Vertex<>(7), 21);
        distances.put(new Vertex<>(8), 28);
        assertEquals(distances, GraphAlgorithms.dijkstras(new Vertex<>(1),
                graph));
        assertTrue("The furthest distance from the start vertex of 1 is not"
                        + " mathematically correct. Your answer was " + GraphAlgorithms.dijkstras(new Vertex<>(1), graph).get(new Vertex<>(8)),
                GraphAlgorithms.dijkstras(new Vertex<>(1), graph).get(new Vertex<>(8)) == ((vertices.size() * (vertices.size() - 1)) / 2));

    }
    @Test(timeout = TIMEOUT)
    public void binaryTreeGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        vertices.add(new Vertex<>(3));
        vertices.add(new Vertex<>(4));
        vertices.add(new Vertex<>(5));
        vertices.add(new Vertex<>(6));
        vertices.add(new Vertex<>(7));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 1));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(1), 1));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 2));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 2));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 3));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(2), 3));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(5), 4));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(2), 4));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(6), 5));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(3), 5));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(7), 6));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(3), 6));
        Graph<Integer> graph = new Graph<>(vertices, edges);
        ArrayList<Vertex<Integer>> bfsExpected = new ArrayList<>();
        bfsExpected.add(new Vertex<>(1));
        bfsExpected.add(new Vertex<>(3));
        bfsExpected.add(new Vertex<>(2));
        bfsExpected.add(new Vertex<>(6));
        bfsExpected.add(new Vertex<>(7));
        bfsExpected.add(new Vertex<>(5));
        bfsExpected.add(new Vertex<>(4));
        assertEquals(bfsExpected, GraphAlgorithms.bfs(new Vertex<>(1), graph));
        ArrayList<Vertex<Integer>> dfsExpected = new ArrayList<>();
        dfsExpected.add(new Vertex<>(1));
        dfsExpected.add(new Vertex<>(3));
        dfsExpected.add(new Vertex<>(6));
        dfsExpected.add(new Vertex<>(7));
        dfsExpected.add(new Vertex<>(2));
        dfsExpected.add(new Vertex<>(5));
        dfsExpected.add(new Vertex<>(4));
        assertEquals(dfsExpected, GraphAlgorithms.dfs(new Vertex<>(1), graph));
        Map<Vertex<Integer>, Integer> distances = new HashMap<>();
        distances.put(new Vertex<>(1), 0);
        distances.put(new Vertex<>(2), 1);
        distances.put(new Vertex<>(3), 2);
        distances.put(new Vertex<>(4), 4);
        distances.put(new Vertex<>(5), 5);
        distances.put(new Vertex<>(6), 7);
        distances.put(new Vertex<>(7), 8);
        assertEquals(distances, GraphAlgorithms.dijkstras(new Vertex<>(1),
                graph));
    }


}
