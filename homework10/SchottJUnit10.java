import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * This set of JUnits assumes you have already passed all tests in the StudentTest
 * provided tests.
 *
 * If any tests do not work as expected, please let me know! Email me at tschott6@gatech.edu or
 * reply to my Piazza post.
 *
 *
 * TESTS INCLUDED:
 * Exception handling for every method that can throw an exception
 * Cases when graph size = 1
 * 3 different graphs:
 *  With a node linking to itself (all 3 algs)
 *  With a disconnected portion (all 3 algs)
 *  Only directional links with a disconnect in one direction (first 2 algs)
 *
 * I relied on my algorithms for some of the expected cases, so let me know if you get something different.
 *
 * NOTE:
 * There are multiple inner classes that can be run separately or together. There is one for each set
 * of tests I could think of. They should work with the given imports, but may require more.
 * Tested for IntelliJ.
 *
 *
 * @author Tyler Schott
 * @version 1.0
 */

@RunWith(SchottJUnit10.class)
@Suite.SuiteClasses({ SchottJUnit10.TestExceptions.class, SchottJUnit10.TestSize1.class,
        SchottJUnit10.TestGraph1.class, SchottJUnit10.TestGraph2Disconnected.class,
        SchottJUnit10.TestGraph3Disconnected.class})
public class SchottJUnit10 extends Suite {

    public SchottJUnit10(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestExceptions {

        private static final int TIMEOUT = 200;
        private Graph<Character> graph;
        private Set<Vertex<Character>> vertices;
        private Set<Edge<Character>> edges;

        @Before
        public void setup() {
            vertices = new HashSet<>();
            for (int i = 65; i <= 70; i++) {
                vertices.add(new Vertex<>((char) i));
            }

            edges = new LinkedHashSet<>();
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 3));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 2));

            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 3));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('B'), 4));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 7));

            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 6));

            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 2));
            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 10));

            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 7));
            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 10));
            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 4));

            edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 4));

            graph = new Graph<Character>(vertices, edges);
        }

        @Test(timeout = TIMEOUT)
        public void testNullBFS() {
            int exceptions = 0;

            try {
                GraphAlgorithms.bfs(null, graph);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }
            try {
                GraphAlgorithms.bfs(new Vertex<Character>('A'), null);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }

            assertEquals(2, exceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testNullDFS() {
            int exceptions = 0;

            try {
                GraphAlgorithms.dfs(null, graph);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }
            try {
                GraphAlgorithms.dfs(new Vertex<Character>('A'), null);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }

            assertEquals(2, exceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testNullDij() {
            int exceptions = 0;

            try {
                GraphAlgorithms.dijkstras(null, graph);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }
            try {
                GraphAlgorithms.dijkstras(new Vertex<Character>('A'), null);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }

            assertEquals(2, exceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testVertexNotExistBFS() {
            int exceptions = 0;

            try {
                GraphAlgorithms.bfs(new Vertex<Character>('T'), graph);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }

            assertEquals(1, exceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testVertexNotExistDFS() {
            int exceptions = 0;

            try {
                GraphAlgorithms.dfs(new Vertex<Character>('T'), graph);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }

            assertEquals(1, exceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testVertexNotExistDij() {
            int exceptions = 0;

            try {
                GraphAlgorithms.dijkstras(new Vertex<Character>('T'), graph);
            } catch (IllegalArgumentException e) {
                exceptions++;
            }

            assertEquals(1, exceptions);
        }
    }

    //Also tests the vertex linking to itself
    public static class TestSize1 {

        private Graph graph;
        private static final int TIMEOUT = 200;
        private Set<Vertex<Character>> vertices;
        private Set<Edge<Character>> edges;

        @Before
        public void setup() {
            vertices = new HashSet<>();
            vertices.add(new Vertex<>('A'));

            edges = new LinkedHashSet<>();
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 3));

            graph = new Graph<Character>(vertices, edges);
        }

        @Test(timeout = TIMEOUT)
        public void testBFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            expected.add(new Vertex<Character>('A'));
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testDFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            expected.add(new Vertex<Character>('A'));
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testDij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();
            expected.put(new Vertex<Character>('A'), 0);
            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }
    }

    //Normal graph with a vertex linking to itself
    public static class TestGraph1 {

        private static final int TIMEOUT = 200;
        private Graph<Character> graph;
        private Set<Vertex<Character>> vertices;
        private Set<Edge<Character>> edges;

        @Before
        public void setup() {
            vertices = new HashSet<>();
            for (int i = 65; i <= 70; i++) {
                vertices.add(new Vertex<>((char) i));
            }

            edges = new LinkedHashSet<>();
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 3));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 2));

            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 3));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('B'), 4));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 7));

            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 6));

            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 2));
            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 10));

            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 7));
            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 10));
            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 4));

            edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 4));

            graph = new Graph<Character>(vertices, edges);
        }

        @Test(timeout = TIMEOUT)
        public void testA_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testA_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'A', 'B', 'E', 'D', 'F', 'C'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testA_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
            //Array of values
            int[] values = new int[]{0, 3, 5, 2, 10, 14};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testB_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'B', 'A', 'E', 'C', 'D', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('B'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testB_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'B', 'A', 'C', 'D', 'E', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('B'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testB_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
            //Array of values
            int[] values = new int[]{3, 0, 8, 5, 7, 11};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('B'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testC_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'C', 'A', 'D', 'B', 'E', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('C'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testC_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'C', 'A', 'B', 'E', 'D', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('C'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testC_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
            //Array of values
            int[] values = new int[]{5, 8, 0, 6, 15, 19};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('C'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testF_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'F', 'E', 'B', 'D', 'A', 'C'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('F'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testF_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'F', 'E', 'B', 'A', 'C', 'D'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('F'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testF_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
            //Array of values
            int[] values = new int[]{14, 11, 19, 14, 4, 0};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('F'), graph);

            assertEquals(expected, actual);
        }
    }

    //Disconnected graph
    public static class TestGraph2Disconnected {

        private static final int TIMEOUT = 200;
        private Graph<Character> graph;
        private Set<Vertex<Character>> vertices;
        private Set<Edge<Character>> edges;

        @Before
        public void setup() {
            vertices = new HashSet<>();
            for (int i = 65; i <= 71; i++) {
                vertices.add(new Vertex<>((char) i));
            }

            edges = new LinkedHashSet<>();
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 4));

            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 8));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 5));

            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 4));
            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 8));
            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 3));

            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 3));
            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 5));

            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 1));
            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 2));

            edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 1));

            edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 2));

            graph = new Graph<Character>(vertices, edges);
        }

        @Test(timeout = TIMEOUT)
        public void testA_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'A', 'B', 'C', 'D'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testA_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'A', 'B', 'C', 'D'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testA_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
            //Array of values
            int[] values = new int[]{0, 2, 4, 7, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testC_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'C', 'A', 'B', 'D'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('C'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testC_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'C', 'A', 'B', 'D'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('C'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testC_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
            //Array of values
            int[] values = new int[]{4, 6, 0, 3, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('C'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testG_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'G', 'E', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('G'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testG_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'G', 'E', 'F'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('G'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testG_Dij() {
            Map<Vertex<Character>, Integer> expected = new HashMap<>();

            //Array of keys (as characters)
            char[] keys = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
            //Array of values
            int[] values = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2, 3, 0};

            for (int i = 0; i < keys.length; i++) {
                expected.put(new Vertex<Character>(keys[i]), values[i]);
            }

            Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(new Vertex<Character>('G'), graph);

            assertEquals(expected, actual);
        }
    }

    //Disconnected graph (only in one direction)
    public static class TestGraph3Disconnected {

        private static final int TIMEOUT = 200;
        private Graph<Character> graph;
        private Set<Vertex<Character>> vertices;
        private Set<Edge<Character>> edges;

        @Before
        public void setup() {
            vertices = new HashSet<>();
            for (int i = 65; i <= 73; i++) {
                vertices.add(new Vertex<>((char) i));
            }

            edges = new LinkedHashSet<>();
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1));
            edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('G'), 1));

            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 1));
            edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 1));

            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1));
            edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 1));

            edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));

            edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 1));

            edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 1));
            edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 1));

            edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 1));
            edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('I'), 1));

            edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 1));

            edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('H'), 1));

            graph = new Graph<Character>(vertices, edges);
        }

        @Test(timeout = TIMEOUT)
        public void testA_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'A', 'B', 'C', 'G', 'F', 'D', 'I', 'H', 'E'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testA_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'H'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('A'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testD_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'D', 'E', 'A', 'B', 'C', 'G', 'F', 'I', 'H'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('D'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testD_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'D', 'E', 'A', 'B', 'C', 'F', 'G', 'I', 'H'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('D'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testF_BFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'F', 'G', 'H', 'I'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.bfs(new Vertex<Character>('F'), graph);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testF_DFS() {
            List<Vertex<Character>> expected = new ArrayList<>();
            char[] toAdd = new char[]{'F', 'G', 'I', 'H'};
            for (char c : toAdd) {
                expected.add(new Vertex<Character>(c));
            }
            List<Vertex<Character>> actual = GraphAlgorithms.dfs(new Vertex<Character>('F'), graph);

            assertEquals(expected, actual);
        }
    }
}
