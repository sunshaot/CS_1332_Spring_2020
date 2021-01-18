import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit06 {

    private static final int TIMEOUT = 200;

    /* Add other Student unit tests here, uncomment to activate
    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends ExternalChainingHashMapStudentTest {
    }

    public static class MyKatreJUnit06 extends KatreJUnit06 {
    }
     */

    public static class TestExternalChainingHashMapNoArgConstructor {
        private ExternalChainingHashMap<Integer, String> echm;

        @Before
        public void setup() {
            echm = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT)
        public void testConstructor() {
            assertEquals("size", 0, echm.size());
            assertArrayEquals("backingArray",
                new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY], echm.getTable());
        }
    }

    public static class TestExternalChainingHashMapCapacityConstructor {
        private ExternalChainingHashMap<Integer, String> echm;

        @Test(timeout = TIMEOUT)
        public void testConstructorCapacityOne() {
            echm = new ExternalChainingHashMap<>(1);
            assertEquals("size", 0, echm.size());
            assertArrayEquals("table", new ExternalChainingMapEntry[1], echm.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorCapacityLarge() {
            echm = new ExternalChainingHashMap<>(ExternalChainingHashMap.INITIAL_CAPACITY + 1);
            assertEquals("size", 0, echm.size());
            assertArrayEquals("table",
                new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY + 1], echm.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            echm = new ExternalChainingHashMap<>(1);
            echm.clear();
            assertEquals("size", 0, echm.size());
            assertArrayEquals("table",
                new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY], echm.getTable());
        }
    }

    public static class TestExternalChainingHashMap {
        private ExternalChainingHashMap<Integer, String> echm;

        @Before
        public void setup() {
            echm = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT)
        public void testPutZero() {
            assertNull("should be returning null for add", echm.put(0, "1"));
            for (int i = 0; i < ExternalChainingHashMap.INITIAL_CAPACITY; ++i) {
                if (i == 0) {
                    assertEntryEqual(0, 0, 0, "1", true);
                } else {
                    assertEntryNull(i);
                }
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPutNullKey() {
            echm.put(null, "");
        }

        @Test(timeout = TIMEOUT)
        public void testPutNullKeyCheckNoChanges() {
            try {
                echm.put(null, "");
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPutNullValue() {
            echm.put(1, null);
        }

        @Test(timeout = TIMEOUT)
        public void testPutNullValueCheckNoChanges() {
            try {
                echm.put(1, null);
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPutNullBoth() {
            echm.put(null, null);
        }

        @Test(timeout = TIMEOUT)
        public void testPutNullBothCheckNoChanges() {
            try {
                echm.put(null, null);
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testPutGrowing() {
            buildUnderLoad(true);
            echm.put(8, "8");
            assertEquals(9, echm.size());
            assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY * 2 + 1, echm.getTable().length);
            for (int i = 0; i < ExternalChainingHashMap.INITIAL_CAPACITY * 2 + 1; ++i) {
                if (i < 9) {
                    assertEntryEqual(i, 0, i, "" + i, true);
                } else {
                    assertEntryNull(i);
                }
            }
        }

        @Test(timeout = TIMEOUT)
        public void testPutChaining() {
            buildLongChain(true);
            int offset = ExternalChainingHashMap.INITIAL_CAPACITY;
            for (int z = 0; z < ExternalChainingHashMap.INITIAL_CAPACITY; ++z) {
                if (z == 0) {
                    for (int j = 0; j < 8; ++j) {
                        int i = 7 - j;
                        assertEntryEqual(0, j, i * offset, "" + i, j == 7);
                    }
                } else {
                    assertEntryNull(z);
                }
            }
        }

        @Test(timeout = TIMEOUT)
        public void testPutChainingGrowing() {
            buildLongChain(false);
            int offset = ExternalChainingHashMap.INITIAL_CAPACITY;
            assertNull("should be returning null for add", echm.put(8 * offset, "8"));
            // Should no longer be a single chain, with each in a different table entry
            assertEquals(9, echm.size());
            assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY * 2 + 1, echm.getTable().length);
            for (int i = 0; i < ExternalChainingHashMap.INITIAL_CAPACITY * 2 + 1; ++i) {
                if (i == 0) {
                    assertEntryEqual(0, 0, 0, "0", true);
                } else if (i == 10) {
                    assertEntryEqual(10, 0, 91, "7", true);
                } else if (i == 11) {
                    assertEntryEqual(11, 0, 65, "5", true);
                } else if (i == 12) {
                    assertEntryEqual(12, 0, 39, "3", true);
                } else if (i == 13) {
                    assertEntryEqual(13, 0, 13, "1", true);
                } else if (i == 23) {
                    assertEntryEqual(23, 0, 104, "8", true);
                } else if (i == 24) {
                    assertEntryEqual(24, 0, 78, "6", true);
                } else if (i == 25) {
                    assertEntryEqual(25, 0, 52, "4", true);
                } else if (i == 26) {
                    assertEntryEqual(26, 0, 26, "2", true);
                } else {
                    assertEntryNull(i);
                }
            }
        }

        @Test(timeout = TIMEOUT)
        public void testPutReplaceValueOne() {
            assertNull("should be returning null for add", echm.put(0, "0"));
            assertEquals("0", echm.put(0, "1"));
            assertEntryEqual(0, 0, 0, "1", true);
        }

        @Test(timeout = TIMEOUT)
        public void testPutReplaceValueTwoChained() {
            int second = ExternalChainingHashMap.INITIAL_CAPACITY;
            echm.put(0, "0");
            echm.put(second, "a");
            assertEquals("a", echm.put(second, "b"));
            assertEntryEqual(0, 0, second, "b", false);
            assertEntryEqual(0, 1, 0, "0", true);
            assertEquals("0", echm.put(0, "x"));
            assertEntryEqual(0, 0, second, "b", false);
            assertEntryEqual(0, 1, 0, "x", true);
        }

        @Test(timeout = TIMEOUT)
        public void testPutReplaceValueThreeChained() {
            int second = ExternalChainingHashMap.INITIAL_CAPACITY;
            int third = second * 2;
            echm.put(0, "0");
            echm.put(second, "1");
            echm.put(third, "a");
            assertEquals("a", echm.put(third, "b"));
            assertEntryEqual(0, 0, third, "b", false);
            assertEntryEqual(0, 1, second, "1", false);
            assertEntryEqual(0, 2, 0, "0", true);
            assertEquals("1", echm.put(second, "x"));
            assertEntryEqual(0, 0, third, "b", false);
            assertEntryEqual(0, 1, second, "x", false);
            assertEntryEqual(0, 2, 0, "0", true);
            assertEquals("0", echm.put(0, "1"));
            assertEntryEqual(0, 0, third, "b", false);
            assertEntryEqual(0, 1, second, "x", false);
            assertEntryEqual(0, 2, 0, "1", true);
        }

        @Test(timeout = TIMEOUT)
        public void testPutOddResizingCondition() {
            // Read the javadocs carefully.
            buildUnderLoad(false);
            echm.put(0, "x");
            assertEquals(8, echm.size());
            assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY * 2 + 1, echm.getTable().length);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveEmpty() {
            echm.remove(0);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveEmptyCheckNoChanges() {
            try {
                echm.remove(0);
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveOne() {
            echm.put(0, "0");
            assertEquals("0", echm.remove(0));
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveUnderLoad() {
            buildUnderLoad(false);
            assertEquals("4", echm.remove(4));
            for (int i = 0; i < ExternalChainingHashMap.INITIAL_CAPACITY; ++i) {
                if (i == 4) {
                    assertEntryNull(4);
                } else if (i < 8) {
                    assertEntryEqual(i, 0, i, "" + i, true);
                }
            }
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveLongChainFirst() {
            buildLongChain(false);
            assertEquals("0", echm.remove(0));
            assertEquals("0=[91=7,78=6,65=5,52=4,39=3,26=2,13=1]\n1=null\n2=null\n3=null\n4=null\n5=null\n6=null\n"
                + "7=null\n8=null\n9=null\n10=null\n11=null\n12=null\n", stringify());
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveLongChainMiddle() {
            buildLongChain(false);
            echm.remove(39);
            assertEquals("0=[91=7,78=6,65=5,52=4,26=2,13=1,0=0]\n1=null\n2=null\n3=null\n4=null\n5=null\n6=null\n"
                + "7=null\n8=null\n9=null\n10=null\n11=null\n12=null\n", stringify());
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveLongChainLast() {
            buildLongChain(false);
            echm.remove(91);
            assertEquals("0=[78=6,65=5,52=4,39=3,26=2,13=1,0=0]\n1=null\n2=null\n3=null\n4=null\n5=null\n6=null\n"
                + "7=null\n8=null\n9=null\n10=null\n11=null\n12=null\n", stringify());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveNull() {
            echm.remove(null);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveNullCheckNoChanges() {
            try {
                echm.remove(null);
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetEmpty() {
            echm.get(0);
        }

        @Test(timeout = TIMEOUT)
        public void testGetOne() {
            echm.put(0, "0");
            assertEquals("0", echm.get(0));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetOneNotFound() {
            echm.put(0, "0");
            echm.get(1);
        }

        @Test(timeout = TIMEOUT)
        public void testGetUnderLoad() {
            buildUnderLoad(false);
            for (int i = 0; i < 8; ++i) {
                assertEquals("" + i, echm.get(i));
            }
        }

        @Test(timeout = TIMEOUT)
        public void testGetLongChain() {
            buildLongChain(false);
            for (int i = 0; i < 8; ++i) {
                assertEquals("" + i, echm.get(i * ExternalChainingHashMap.INITIAL_CAPACITY));
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testGetNull() {
            echm.get(null);
        }

        @Test(timeout = TIMEOUT)
        public void testGetNullCheckNoChanges() {
            try {
                echm.get(null);
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testContainsKeyEmpty() {
            assertFalse(echm.containsKey(0));
        }

        @Test(timeout = TIMEOUT)
        public void testContainsKeyOne() {
            echm.put(0, "0");
            assertTrue(echm.containsKey(0));
            assertFalse(echm.containsKey(1));
        }

        @Test(timeout = TIMEOUT)
        public void testContainsKeyUnderLoad() {
            buildUnderLoad(false);
            for (int i = 0; i < 8; ++i) {
                assertTrue(echm.containsKey(i));
            }
            assertFalse(echm.containsKey(-1));
            assertFalse(echm.containsKey(9));
        }

        @Test(timeout = TIMEOUT)
        public void testContainsKeyLongChain() {
            buildLongChain(false);
            for (int i = 0; i < 8; ++i) {
                assertTrue(echm.containsKey(i * ExternalChainingHashMap.INITIAL_CAPACITY));
            }
            assertFalse(echm.containsKey(-1));
            assertFalse(echm.containsKey(9));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testContainsKeyNull() {
            echm.containsKey(null);
        }

        @Test(timeout = TIMEOUT)
        public void testContainsKeyNullCheckNoChanges() {
            try {
                echm.containsKey(null);
            } catch (Exception ex) {
            }
            asserttEmpty(ExternalChainingHashMap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testKeySetOfUnderLoad() {
            buildUnderLoad(false);
            Set<Integer> keys = echm.keySet();
            assertEquals(8, keys.size());
            for (int e:new int[] {7, 6, 5, 4, 3, 2, 1, 0}) {
                assertTrue(keys.contains(e));
            }
        }

        @Test(timeout = TIMEOUT)
        public void testKeySetLongChain() {
            buildLongChain(false);
            Set<Integer> keys = echm.keySet();
            assertEquals(8, keys.size());
            for (int e:new int[] {91, 78, 65, 52, 39, 26, 13, 0}) {
                assertTrue(keys.contains(e));
            }
        }

        @Test(timeout = TIMEOUT)
        public void testValuesOfUnderLoad() {
            buildUnderLoad(false);
            List<String> values = echm.values();
            assertEquals(8, values.size());
            assertArrayEquals(new String[] {"0", "1", "2", "3", "4", "5", "6", "7"}, values.toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testValuesLongChain() {
            buildLongChain(false);
            List<String> values = echm.values();
            assertEquals(8, values.size());
            assertArrayEquals(new String[] {"7", "6", "5", "4", "3", "2", "1", "0"}, values.toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testResizeBackingTableEmptyShrinking() {
            echm.resizeBackingTable(1);
            asserttEmpty(1);
        }

        @Test(timeout = TIMEOUT)
        public void testResizeBackingTableEmptyGrowing() {
            int size = ExternalChainingHashMap.INITIAL_CAPACITY * 2 - 3;
            echm.resizeBackingTable(size);
            asserttEmpty(size);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testResizeBackingTableBelowSize() {
            echm.put(0, "0");
            echm.put(1, "1");
            echm.resizeBackingTable(1);
        }

        @Test(timeout = TIMEOUT)
        public void testResizeBackingTableBelowSizeCheckNoChanges() {
            echm.put(0, "0");
            echm.put(1, "1");
            try {
                echm.resizeBackingTable(1);
            } catch (Exception ex) {
            }
            assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, echm.getTable().length);
        }

        /**
         * Checks that the ExternalChainingHashMap is empty with a capacity
         * @param capacity Expected capacity of the ExternalChainingHashMap
         */
        private void asserttEmpty(int capacity) {
            assertEquals("size", 0, echm.size());
            assertArrayEquals("table", new ExternalChainingMapEntry[capacity], echm.getTable());
        }

        /**
         * Tests if a given index into the table, and offset into the chain is equal to the given values
         * @param index Index into the backing table
         * @param offset Offset down the chain
         * @param key Expected key
         * @param value Expected value
         * @param nextNull Check if the next values is or is not null
         */
        private void assertEntryEqual(int index, int offset, Integer key, String value, boolean nextNull) {
            ExternalChainingMapEntry<Integer, String>[] table = echm.getTable();
            assertNotNull("table[" + index + "] should not be null", table[index]);
            ExternalChainingMapEntry<Integer, String> entry = table[index];
            for (int i = 0; i < offset; ++i) {
                entry = entry.getNext();
            }
            assertEquals("table[" + index + "," + offset + "].key", key, entry.getKey());
            assertEquals("table[" + index + "," + offset + "].value", value, entry.getValue());
            if (nextNull) {
                assertNull("table[" + index + "," + offset + "].next", entry.getNext());
            } else {
                assertNotNull("table[" + index + "," + offset + "].next", entry.getNext());
            }
        }

        /**
         * Asserts that an entry in the table is null
         * @param index index in table to check
         */
        private void assertEntryNull(int index) {
            ExternalChainingMapEntry<Integer, String>[] table = echm.getTable();
            assertNull("table[" + index + "]", table[index]);
        }

        /**
         * Builds a just under load capacity HashMap with each element by itself
         * @param checks True if to assert that things are as they should be
         */
        private void buildUnderLoad(boolean checks) {
            for (int i = 0; i < 8; ++i) {
                String v = echm.put(i, "" + i);
                if (checks) {
                    assertNull(v);
                    assertEquals(i + 1, echm.size());
                    assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, echm.getTable().length);
                }
            }
        }

        /**
         * Build a HashMap that has a single long chain, just less than the load factor length
         *
         * @param checks True if to assert that things are as they should be
         */
        private void buildLongChain(boolean checks) {
            // causes the HashMap to create a single chain just less than the default load factor
            int offset = ExternalChainingHashMap.INITIAL_CAPACITY;
            for (int i = 0; i < 8; ++i) {
                String v = echm.put(i * offset, "" + i);
                if (checks) {
                    assertNull(v);
                    assertEquals(i + 1, echm.size());
                    assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, echm.getTable().length);
                }
            }
        }

        /**
         * Convert the backing table to a string for easier comparison
         * @return Semi human readable string version of the backing table
         */
        private String stringify() {
            StringBuilder sb = new StringBuilder();
            ExternalChainingMapEntry<Integer, String>[] table = echm.getTable();
            for (int i = 0; i < table.length; ++i) {
                sb.append(i);
                sb.append('=');
                if (table[i] == null) {
                    sb.append("null\n");
                } else {
                    sb.append('[');
                    boolean first = true;
                    ExternalChainingMapEntry<Integer, String> entry = table[i];
                    while (entry != null) {
                        if (!first) {
                            sb.append(',');
                        } else {
                            first = false;
                        }
                        sb.append(entry.getKey().toString());
                        sb.append('=');
                        sb.append(entry.getValue());
                        entry = entry.getNext();
                    }
                    sb.append("]\n");
                }
            }
            return sb.toString();
        }
    }
}
