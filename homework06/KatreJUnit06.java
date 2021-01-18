import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RunWith(Enclosed.class)
public class KatreJUnit06 {
    private static ExternalChainingHashMap<Integer, String> map;

    /**
     * PUBLIC HELPER METHOD
     * Checks the external chain since assertArrayExternalChainEquals does not
     *
     * @param expected the expected values
     * @param actual   the actual values
     */
    public static void assertArrayExternalChainEquals(
            ExternalChainingMapEntry<Integer, String>[] expected,
            ExternalChainingMapEntry<Integer, String>[] actual
    ) {
        assertArrayEquals(expected, actual);
        for (int i = 0; i < expected.length; i++) {
            ExternalChainingMapEntry<Integer, String> expectedEntry = expected[i];
            ExternalChainingMapEntry<Integer, String> actualEntry = actual[i];

            int chainIndex = 0;
            while (expectedEntry != null && actualEntry != null) {
                Integer expectedEntryKey = expectedEntry.getKey();
                Integer actualEntryKey = actualEntry.getKey();
                if (!actualEntryKey.equals(expectedEntryKey)) {
                    Assert.fail(
                            "chain entry key differed at chain index "
                                    + chainIndex
                                    + ", expected.key="
                                    + expectedEntryKey
                                    + " actual.key"
                                    + actualEntryKey
                    );
                }

                String expectedEntryValue = expectedEntry.getValue();
                String actualEntryValue = actualEntry.getValue();
                if (!actualEntryValue.equals(expectedEntryValue)) {
                    Assert.fail(
                            "chain entry value differed at chain index "
                                    + chainIndex
                                    + ", expected.value="
                                    + expectedEntryValue
                                    + " actual.value"
                                    + actualEntryValue
                    );
                }

                expectedEntry = expectedEntry.getNext();
                actualEntry = actualEntry.getNext();
                chainIndex++;
            }
        }
    }

    /**
     * Outputs a visualization of the HashMap
     */
    public static void print() {
        System.out.print("\tCurrent HashMap state:");
        for (int i = 0; i < map.getTable().length; i++) {
            System.out.print("\n\t\t[" + i + "]");
            ExternalChainingMapEntry<Integer, String> current = map.getTable()[i];
            while (current != null) {
                System.out.print(" -> <" + current.getKey() + ", " + current.getValue() + ">");
                current = current.getNext();
            }
        }

        System.out.println();
    }

    public static class TestPut {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testPutNullKey() {
            map.put(null, "A");
        }

        @Test(expected = IllegalArgumentException.class)
        public void testPutNullValue() {
            map.put(0, null);
        }

        @Test
        public void testPutBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(5, map.size());
        }

        @Test
        public void testPutNegativeKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(-1, "B");
            print();
            assertEquals(2, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(-1, "B");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testPutExternalChaining() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            assertEquals(2, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(5, "B");
            expected[0].setNext(new ExternalChainingMapEntry<>(0, "A"));
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        public void testPutResize() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(11, map.getTable().length);
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testPutDuplicateKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(0, "B");
            print();
            assertEquals(1, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "B");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testPutDuplicateKeyFurtherInChain() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(0, "B");
            print();
            assertEquals(1, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "B");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testPutDuplicateKeyReturn() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            assertEquals("A", map.put(0, "C"));
            print();
            assertEquals(2, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(5, "B");
            expected[0].setNext(new ExternalChainingMapEntry<>(0, "C"));
            assertArrayExternalChainEquals(expected, map.getTable());
        }
    }

    public static class TestRemove {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testRemoveNullKey() {
            map.remove(null);
        }

        @Test(expected = NoSuchElementException.class)
        public void testRemoveNonexistentKey() {
            map.remove(0);
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testRemoveBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map = new ExternalChainingHashMap<>(5);
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.remove(0);
            print();
            assertEquals(2, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            expected[2] = new ExternalChainingMapEntry<>(2, "C");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        public void testRemoveNegativeKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map = new ExternalChainingHashMap<>(5);
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(-2, "C");
            print();
            map.remove(-2);
            print();
            assertEquals(2, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testRemoveFurtherInChain() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            map.put(10, "C");
            print();
            map.remove(5);
            print();
            assertEquals(2, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(10, "C");
            expected[0].setNext(new ExternalChainingMapEntry<>(0, "A"));
            assertArrayExternalChainEquals(expected, map.getTable());
        }
    }

    public static class TestGet {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testGetNullKey() {
            map.get(null);
        }

        @Test(expected = NoSuchElementException.class)
        public void testGetNonexistentKey() {
            map.get(0);
        }

        @Test
        public void testGetBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            assertEquals("A", map.get(0));
            assertEquals(1, map.size());
        }

        @Test
        public void testGetNegativeKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map = new ExternalChainingHashMap<>(5);
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(-2, "C");
            print();
            assertEquals(3, map.size());

            assertEquals("C", map.get(-2));
            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            expected[2] = new ExternalChainingMapEntry<>(-2, "C");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testGetKeyFurtherInChain() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            map.put(10, "C");
            print();
            assertEquals("B", map.get(5));
            assertEquals(3, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(10, "C");
            expected[0].setNext(new ExternalChainingMapEntry<>(5, "B"));
            expected[0].getNext().setNext(new ExternalChainingMapEntry<>(0, "A"));
            assertArrayExternalChainEquals(expected, map.getTable());
        }
    }

    public static class TestContains {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testContainsNullKey() {
            map.containsKey(null);
        }

        @Test
        public void testContainsBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            assertEquals(1, map.size());
            assertTrue(map.containsKey(0));
        }

        @Test
        public void testContainsNegativeKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(-2, "C");
            print();
            assertEquals(3, map.size());
            assertTrue(map.containsKey(-2));
        }

        @Test
        public void testContainsKeyFurtherInChain() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            map.put(10, "C");
            print();
            assertEquals(3, map.size());
            assertTrue(map.containsKey(5));
        }
    }

    public static class TestKeySet {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test
        public void testKeySetBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(5, map.size());

            Set<Integer> expected = new HashSet<>();
            expected.add(0);
            expected.add(1);
            expected.add(2);
            expected.add(3);
            expected.add(4);
            assertEquals(expected, map.keySet());
        }

        @Test
        public void testKeySetNegativeKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(-2, "C");
            print();
            assertEquals(3, map.size());

            Set<Integer> expected = new HashSet<>();
            expected.add(0);
            expected.add(1);
            expected.add(-2);
            assertEquals(expected, map.keySet());
        }

        @Test
        public void testKeySetFurtherInChain() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            map.put(10, "C");
            print();
            assertEquals(3, map.size());

            Set<Integer> expected = new HashSet<>();
            expected.add(0);
            expected.add(5);
            expected.add(10);
            assertEquals(expected, map.keySet());
        }
    }

    public static class TestValues {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test
        public void testValuesBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(5, map.size());

            List<String> expected = new LinkedList<>();
            expected.add("A");
            expected.add("B");
            expected.add("C");
            expected.add("D");
            expected.add("E");
            assertEquals(expected, map.values());
        }

        @Test
        public void testValuesFurtherInChain() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            map.put(10, "C");
            print();
            assertEquals(3, map.size());

            List<String> expected = new LinkedList<>();
            expected.add("C");
            expected.add("B");
            expected.add("A");
            assertEquals(expected, map.values());
        }
    }

    public static class TestResize {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testResizeBackingTableSmallerThanAllowed() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.resizeBackingTable(1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testResizeBackingTableZeroLength() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.resizeBackingTable(0);
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testResizeBasic() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            assertEquals(3, map.size());

            map.resizeBackingTable(10);
            print();
            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[10];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            expected[2] = new ExternalChainingMapEntry<>(2, "C");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testResizeNegativeKey() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(-2, "C");
            print();
            assertEquals(3, map.size());

            map.resizeBackingTable(10);
            print();
            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[10];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            expected[2] = new ExternalChainingMapEntry<>(-2, "C");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testResizeBackingTableBeyondLoadFactor() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(5, map.size());

            map.resizeBackingTable(5);
            print();
            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            expected[2] = new ExternalChainingMapEntry<>(2, "C");
            expected[3] = new ExternalChainingMapEntry<>(3, "D");
            expected[4] = new ExternalChainingMapEntry<>(4, "E");
            assertArrayExternalChainEquals(expected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testResizeBackingTableNearLoadFactor() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            assertEquals(3, map.size());

            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[5];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(1, "B");
            expected[2] = new ExternalChainingMapEntry<>(2, "C");
            assertArrayExternalChainEquals(expected, map.getTable());

            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(5, map.size());
            ExternalChainingMapEntry<Integer, String>[] newExpected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[11];
            newExpected[0] = new ExternalChainingMapEntry<>(0, "A");
            newExpected[1] = new ExternalChainingMapEntry<>(1, "B");
            newExpected[2] = new ExternalChainingMapEntry<>(2, "C");
            newExpected[3] = new ExternalChainingMapEntry<>(3, "D");
            newExpected[4] = new ExternalChainingMapEntry<>(4, "E");
            assertArrayExternalChainEquals(newExpected, map.getTable());
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testResizeBackingTableExternalChainDifferentPositions() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(5, "B");
            print();
            map.put(10, "C");
            print();
            map.put(15, "D");
            print();
            map.put(20, "E");
            print();
            assertEquals(5, map.size());

            map.resizeBackingTable(7);
            print();
            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[7];
            expected[0] = new ExternalChainingMapEntry<>(0, "A");
            expected[1] = new ExternalChainingMapEntry<>(15, "D");
            expected[3] = new ExternalChainingMapEntry<>(10, "C");
            expected[5] = new ExternalChainingMapEntry<>(5, "B");
            expected[6] = new ExternalChainingMapEntry<>(20, "E");
            assertArrayExternalChainEquals(expected, map.getTable());
        }
    }

    public static class TestClear {
        @Before
        public void setup() {
            map = new ExternalChainingHashMap<>(5);
        }

        @Test
        @SuppressWarnings("unchecked")
        public void testClear() {
            System.out.println("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            map.put(0, "A");
            print();
            map.put(1, "B");
            print();
            map.put(2, "C");
            print();
            map.put(3, "D");
            print();
            map.put(4, "E");
            print();
            assertEquals(5, map.size());

            map.clear();
            print();
            assertEquals(0, map.size());
            ExternalChainingMapEntry<Integer, String>[] expected =
                    (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[13];
            assertArrayExternalChainEquals(expected, map.getTable());
        }
    }
}
