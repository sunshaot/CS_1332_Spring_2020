import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.*;

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
 * Cases when size = 0 and size = 1
 * Chaining and resizing
 *
 * NOTE:
 * There are multiple inner classes that can be run separately or together. There is one for each set
 * of tests I could think of. They should work with the given imports, but may require more.
 * Tested for IntelliJ.
 *
 *
 * @author Tyler Schott
 * @version 1.3
 */


@RunWith(SchottJUnit06.class)
@Suite.SuiteClasses({ SchottJUnit06.TestExceptions.class, SchottJUnit06.TestEmpty.class, SchottJUnit06.TestSize1.class,
        SchottJUnit06.RegularTests.class, SchottJUnit06.FunTests.class })
public class SchottJUnit06 extends Suite {

    public SchottJUnit06(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestExceptions {
        private static final int TIMEOUT = 200;
        private ExternalChainingHashMap<Integer, String> map;

        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddNullKey() {
            map.put(null, "A");
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddNullValue() {
            map.put(1, null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveNullKey() {
            map.remove(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveNotInMap() {
            map.remove(1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testGetNullKey() {
            map.get(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotInMap() {
            map.get(1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testContainsNullKey() {
            map.containsKey(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testResizeTooSmall() {
            map.put(1, "A");
            map.put(2, "B");
            map.put(3, "C");
            map.put(4, "D");
            map.put(5, "E");

            map.resizeBackingTable(3);
        }
    }

    public static class TestEmpty {
        private static final int TIMEOUT = 200;
        private ExternalChainingHashMap<Integer, String> map;

        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            map.put(1, "A");
            map.put(2, "B");

            assertEquals(2, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testContainsKey() {
            assertFalse(map.containsKey(1));
        }

        @Test(timeout = TIMEOUT)
        public void testValues() {
            List<String> expected = new ArrayList<>();
            assertEquals(expected, map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testKeySet() {
            Set<Integer> expected = new HashSet<>();
            assertEquals(expected, map.keySet());
        }

        @Test(timeout = TIMEOUT)
        public void testResize() {
            map.resizeBackingTable(5);
            assertEquals(5, map.getTable().length);
        }
    }

    public static class TestSize1 {
        private static final int TIMEOUT = 200;
        private ExternalChainingHashMap<Integer, String> map;

        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
            map.put(1, "A");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            map.remove(1);
            assertEquals(0, map.size());
            assertFalse(map.containsKey(1));
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            map.clear();
            assertEquals(0, map.size());
            assertFalse(map.containsKey(1));
        }
    }

    public static class RegularTests {
        private static final int TIMEOUT = 200;
        private ExternalChainingHashMap<Integer, String> map;

        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT)
        public void testPut10() {
            Integer[] toAddKeys = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            String[] toAddValues = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
            for (int i = 0; i < 10; i++) {
                map.put(toAddKeys[i], toAddValues[i]);
            }

            List<String> expectedValues = new ArrayList<>(Arrays.asList(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}));
            assertEquals(expectedValues, map.values());

            Set<Integer> expectedKeys = new HashSet<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
            assertEquals(expectedKeys, map.keySet());

            assertEquals(27, map.getTable().length);
            assertEquals(10, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testPut10VarriedOrder() {
            Integer[] toAddKeys = new Integer[]{5, 3, 9, 2, 4, 1, 6, 8, 7, 10};
            String[] toAddValues = new String[]{"E", "C", "I", "B", "D", "A", "F", "H", "G", "J"};
            for (int i = 0; i < 10; i++) {
                map.put(toAddKeys[i], toAddValues[i]);
            }

            List<String> expectedValues = new ArrayList<>(Arrays.asList(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}));
            assertEquals(expectedValues, map.values());

            Set<Integer> expectedKeys = new HashSet<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
            assertEquals(expectedKeys, map.keySet());

            assertEquals(27, map.getTable().length);
            assertEquals(10, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testResize() {
            map.resizeBackingTable(5);
            assertEquals(5, map.getTable().length);
            map.put(1, "A");
            map.put(2, "B");
            map.put(3, "C");
            assertEquals(5, map.getTable().length);

            map.put(4, "D");
            assertEquals(11, map.getTable().length);
            assertEquals(4, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testResizeOnDuplicate() {
            map.resizeBackingTable(5);
            assertEquals(5, map.getTable().length);
            map.put(1, "A");
            map.put(2, "B");
            map.put(3, "C");
            assertEquals(5, map.getTable().length);

            map.put(3, "D");
            assertEquals(11, map.getTable().length);
            assertEquals(3, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testDuplicates() {
            map.put(1, "A");
            map.put(2, "B");
            map.put(3, "C");

            assertEquals("B", map.put(2, "D"));
            assertEquals(3, map.size());

            assertEquals("A", map.put(1, "E"));
            map.put(3, "F");
            assertEquals(3, map.size());

            List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"E", "D", "F"}));
            assertEquals(expected, map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testChaining() {
            //Index 1
            map.put(1, "A");
            map.put(14, "B");
            map.put(27, "C");

            //Index 2
            map.put(2, "D");
            map.put(15, "E");
            map.put(28, "F");

            assertEquals(6, map.size());

            List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"C", "B", "A", "F", "E", "D"}));
            assertEquals(expected, map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testChainingResize() {
            //Index 1 original size
            map.put(1, "A");
            map.put(14, "B");
            map.put(27, "C");

            //Index 2 original size
            map.put(2, "D");
            map.put(15, "E");
            map.put(28, "F");

            //Index 3 original size
            map.put(3, "G");
            map.put(16, "H");
            map.put(29, "I");

            /**
             * Array Length: 27 (13 * 2 + 1)
             *
             * Index 0: (27, "C")
             * Index 1: (28, "F"), (1, "A")
             * Index 2: (29, "I"), (2, "D")
             * Index 3: (3, "G")
             * Index 14: (14, "B")
             * Index 15: (15, "E")
             * Index 16: (16, "H")
             */

            assertEquals(9, map.size());

            List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"C", "F", "A", "I", "D", "G", "B", "E", "H"}));
            assertEquals(expected, map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveInChain() {
            map.put(1, "A");
            map.put(14, "B");
            map.put(40, "C");
            map.put(27, "D");

            // Index 1: D -> C -> B -> A
            map.remove(40);
            List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"D", "B", "A"}));
            assertEquals(expected, map.values());

            // Index 1: D -> B -> A
            map.remove(27);
            expected = new ArrayList<>(Arrays.asList(new String[]{"B", "A"}));
            assertEquals(expected, map.values());
            // Index 1: B -> A
        }

        @Test(timeout=TIMEOUT)
        public void testReplaceInChain() {
            map.put(1, "A");
            map.put(14, "B");
            map.put(27, "C");
            map.put(40, "D");

            assertEquals("C", map.put(27, "E"));
            assertEquals(4, map.size());

            List<String> expected = new ArrayList<>(Arrays.asList(new String[]{"D", "E", "B", "A"}));
            assertEquals(expected, map.values());
        }

        @Test(timeout=TIMEOUT)
        public void testMinMaxIntegerHash() {
            map.resizeBackingTable(5);
            map.put(Integer.MIN_VALUE, "A");
            map.put(Integer.MAX_VALUE, "B");

            assertEquals("A" ,map.getTable()[3].getValue());
            assertEquals("B" ,map.getTable()[2].getValue());
        }

        @Test(timeout=TIMEOUT)
        public void testExactResize() {
            map.resizeBackingTable(100);

            for (int i = 0; i < 67; i++) {
                map.put(i, "A" + i);
            }

            assertEquals(100, map.getTable().length);

            map.put(67, "A67");
            assertEquals(201, map.getTable().length);
        }

        @Test(timeout=TIMEOUT)
        public void testRemoveInChainBack() {
            map.resizeBackingTable(10);
            map.put(2, "A");
            map.put(12, "B");
            map.put(13, "C");
            map.put(23, "D");

            assertEquals("A", map.remove(2));
            assertEquals("D", map.remove(23));
            assertEquals("C", map.remove(13));

            assertEquals(1, map.size());
        }
    }

    /**
     * May not work great or be easy to debug, but are a little fun :)
     */
    public static class FunTests {
        private static final int TIMEOUT = 200;
        private ExternalChainingHashMap<Integer, String> map;

        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
        }

        // This test should work but don't worry too much about debugging it. Look at other tests first :)
        @Test(timeout = TIMEOUT)
        public void testLotsOfData() {
            final int numData = 90;
            for (int i = 0; i < numData; i++) {
                map.put(i, "A" + i);
                map.put(i * 6, "A" + i);
                if (i % 2 == 0) {
                    assertEquals("A" + i, map.remove(i));
                }
            }

            assertEquals(223, map.getTable().length);
            assertEquals(120, map.size());

            assertEquals("[null, (1, A1), null, (3, A3), (450, A75), (228, A38), null, (7, A7), null, (9, A9), (456, A76), (234, A39), null, (13, A13), null, (15, A15), (462, A77), (240, A40), null, (19, A19), null, (21, A21), (468, A78), (246, A41), null, (25, A25), null, (27, A27), (474, A79), (252, A42), null, (31, A31), null, (33, A33), (480, A80), (258, A43), null, (37, A37), null, (39, A39), (486, A81), (264, A44), null, (43, A43), null, (45, A45), (492, A82), (270, A45), null, (49, A49), null, (51, A51), (498, A83), (276, A46), null, (55, A55), null, (57, A57), (504, A84), (59, A59), null, (61, A61), null, (63, A63), (510, A85), (65, A65), null, (67, A67), null, (69, A69), (516, A86), (71, A71), null, (73, A73), null, (75, A75), (522, A87), (77, A77), null, (79, A79), null, (81, A81), (528, A88), (83, A83), null, (85, A85), null, (87, A87), (534, A89), (89, A89), (90, A15), null, null, null, null, (318, A53), (96, A16), null, null, null, null, (324, A54), (102, A17), null, null, null, null, (330, A55), (108, A18), null, null, null, null, (336, A56), (114, A19), null, null, null, null, (342, A57), (120, A20), null, null, null, null, (348, A58), (126, A21), null, null, null, null, (354, A59), (132, A22), null, null, null, null, (360, A60), (138, A23), null, null, null, null, (366, A61), (144, A24), null, null, null, null, (372, A62), (150, A25), null, null, null, null, (378, A63), (156, A26), null, null, null, null, (384, A64), (162, A27), null, null, null, null, (390, A65), (168, A28), null, null, null, null, (396, A66), (174, A29), null, null, null, null, (402, A67), (180, A30), null, null, null, null, (408, A68), (186, A31), null, null, null, null, (414, A69), (192, A32), null, null, null, null, (420, A70), (198, A33), null, null, null, null, (426, A71), (204, A34), null, null, null, null, (432, A72), (210, A35), null, null, null, null, (438, A73), (216, A36), null, null, null, null, (444, A74), (222, A37)]",
                    Arrays.toString(map.getTable()));
        }
    }
}