import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ExtChainHashMapJUnitTest {
    private ExternalChainingHashMap<Integer, String> hashMap;
    @Before
    public void setUp() {
        hashMap = new ExternalChainingHashMap<Integer, String>();
    }

    @Test
    public void testInitialization() {
        assertEquals(0, hashMap.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], hashMap.getTable());
    }

    @Test
    public void hashMapCustomSize() {
        hashMap = new ExternalChainingHashMap<Integer, String>(5);
        assertArrayEquals(new ExternalChainingMapEntry[5], hashMap.getTable());
    }
    @Test
    public void hashMapCustomSizeRandInt() {
        Random randGen = new Random();
        int randInt = randGen.nextInt(20);
        hashMap = new ExternalChainingHashMap<Integer, String>(randInt);
        assertArrayEquals(new ExternalChainingMapEntry[randInt], hashMap.getTable());
    }
    ///////////////////////////////////////////////////////////
    //Add entry tests
    @Test(expected = IllegalArgumentException.class)
    public void addNullKeyValPair() {
        hashMap.put(null, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNullKeyValTwo() {
        hashMap.put(1, "A");
        hashMap.put(2, "B");
        hashMap.put(null, null);
        hashMap.put(3, "C");
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNullKey() {
        hashMap.put(1, "A");
        hashMap.put(2, "B");
        hashMap.put(null, "D");
        hashMap.put(3, "C");
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNullValue() {
        hashMap.put(1, "A");
        hashMap.put(2, "B");
        hashMap.put(34, null);
        hashMap.put(3, "C");
    }
    @Test
    public void testPut() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));

        assertEquals(5, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
    }
    @Test
    public void putDuplicate() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        assertEquals("E", hashMap.put(4, "F"));
        assertEquals(5, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "F");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
    }
    @Test
    public void basicExceedsLoadFactor() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        assertNull(hashMap.put(5, "F"));
        assertNull(hashMap.put(6, "G"));
        assertNull(hashMap.put(7, "H"));
        assertNull(hashMap.put(8, "I")); //resize occurs here. LF = 9/13 > 0.67
        ExternalChainingMapEntry<Integer, String>[] expected = (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[2*ExternalChainingHashMap.INITIAL_CAPACITY + 1];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        expected[5] = new ExternalChainingMapEntry<>(5, "F");
        expected[6] = new ExternalChainingMapEntry<>(6, "G");
        expected[7] = new ExternalChainingMapEntry<>(7, "H");
        expected[8] = new ExternalChainingMapEntry<>(8, "I");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(27, hashMap.getTable().length);
        assertEquals(9, hashMap.size());
    }
    @Test
    public void addWithSmallCollision() {
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(13, "B"));
        assertNull(hashMap.put(26, "C"));
        assertEquals(3, hashMap.size());
        assertNull(hashMap.put(1, "D"));
        assertNull(hashMap.put(2, "E"));
        assertEquals(5, hashMap.size());
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("C");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("D");
        expectedValues.add("E");
        assertEquals(expectedValues, hashMap.values());
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(26);
        expectedKeys.add(13);
        expectedKeys.add(0);
        expectedKeys.add(1);
        expectedKeys.add(2);
        assertEquals(expectedKeys, hashMap.keySet());
    }
    @Test
    public void addPairsEasyOrder() {
        Integer[] keysToAdd = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("A");
        expectedValues.add("B");
        expectedValues.add("C");
        expectedValues.add("D");
        expectedValues.add("E");
        expectedValues.add("F");
        expectedValues.add("G");
        expectedValues.add("H");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("K");
        expectedValues.add("L");
        expectedValues.add("M");
        assertEquals(expectedValues, hashMap.values());
    }
    @Test
    public void addPairsJumbled() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
    }
    @Test
    public void everyPairInOneColumn() {
        Integer[] keysToAdd = {1,14,27,40};
        String[] valueToAdd = {"A", "C", "D", "B"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valueToAdd[i]));
        }
        assertEquals(4, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(40);
        expectedKeys.add(27);
        expectedKeys.add(14);
        expectedKeys.add(1);
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("B");
        expectedVals.add("D");
        expectedVals.add("C");
        expectedVals.add("A");
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
    }
    @Test
    public void mixedAdd() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(26);
        expectedKeys.add(13);
        expectedKeys.add(16);
        expectedKeys.add(3);
        expectedKeys.add(30);
        expectedKeys.add(6);
        expectedKeys.add(47);
        expectedKeys.add(12);
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("C");
        expectedVals.add("A");
        expectedVals.add("F");
        expectedVals.add("B");
        expectedVals.add("L");
        expectedVals.add("G");
        expectedVals.add("I");
        expectedVals.add("M");
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
        assertEquals(13, hashMap.getTable().length);
        assertEquals(8, hashMap.size());
    }
    //////////////////////////////////
    //remove test
    @Test(expected = IllegalArgumentException.class)
    public void removeNullKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));

        assertEquals(5, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        hashMap.remove(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyHashMap() {
        assertEquals(0, hashMap.size());
        assertArrayEquals((ExternalChainingMapEntry<Integer, String>[])
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY], hashMap.getTable());
        hashMap.remove(5);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptySlot() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        hashMap.remove(6);
    }
    @Test
    public void basicRemove() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertEquals("C", hashMap.remove(2));
        assertEquals(4, hashMap.size());
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(0);
        expectedKeys.add(1);
        expectedKeys.add(3);
        expectedKeys.add(4);
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("A");
        expectedVals.add("B");
        expectedVals.add("D");
        expectedVals.add("E");
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
    }
    @Test(expected = NoSuchElementException.class)
    public void couldNotRemoveBasic() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertEquals("C", hashMap.remove(2));
        assertEquals(4, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(0);
        expectedKeys.add(1);
        expectedKeys.add(3);
        expectedKeys.add(4);
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("A");
        expectedVals.add("B");
        expectedVals.add("D");
        expectedVals.add("E");
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
        hashMap.remove(6);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeWithResizeCouldNotFind() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        hashMap.remove(14);
    }
    @Test
    public void removeWithResize() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
        assertEquals("J", hashMap.remove(9));
        assertEquals(12, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys2 = new HashSet<>();
        expectedKeys2.add(1);
        expectedKeys2.add(2);
        expectedKeys2.add(3);
        expectedKeys2.add(4);
        expectedKeys2.add(5);
        expectedKeys2.add(6);
        expectedKeys2.add(7);
        expectedKeys2.add(8);
        expectedKeys2.add(10);
        expectedKeys2.add(11);
        expectedKeys2.add(12);
        expectedKeys2.add(13);
        assertEquals(expectedKeys2, hashMap.keySet());
        ArrayList<String> expectedValues2 = new ArrayList<>();
        expectedValues2.add("F");
        expectedValues2.add("D");
        expectedValues2.add("B");
        expectedValues2.add("A");
        expectedValues2.add("G");
        expectedValues2.add("L");
        expectedValues2.add("C");
        expectedValues2.add("I");
        expectedValues2.add("M");
        expectedValues2.add("H");
        expectedValues2.add("E");
        expectedValues2.add("K");
        assertEquals(expectedValues2, hashMap.values());
        assertEquals(12, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
    }
    @Test
    public void removeWithDuplicateAdd() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(26);
        expectedKeys.add(13);
        expectedKeys.add(16);
        expectedKeys.add(3);
        expectedKeys.add(30);
        expectedKeys.add(6);
        expectedKeys.add(47);
        expectedKeys.add(12);
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("C");
        expectedVals.add("A");
        expectedVals.add("F");
        expectedVals.add("B");
        expectedVals.add("L");
        expectedVals.add("G");
        expectedVals.add("I");
        expectedVals.add("M");
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
        assertEquals(13, hashMap.getTable().length);
        assertEquals(8, hashMap.size());
        assertEquals("G", hashMap.remove(6));
        assertEquals(7, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        HashSet<Integer> expectedKeys2 = new HashSet<>();
        expectedKeys2.add(26);
        expectedKeys2.add(13);
        expectedKeys2.add(16);
        expectedKeys2.add(3);
        expectedKeys2.add(30);
        expectedKeys2.add(47);
        expectedKeys2.add(12);
        ArrayList<String> expectedVals2 = new ArrayList<>();
        expectedVals2.add("C");
        expectedVals2.add("A");
        expectedVals2.add("F");
        expectedVals2.add("B");
        expectedVals2.add("L");
        expectedVals2.add("I");
        expectedVals2.add("M");
        assertEquals(expectedKeys2, hashMap.keySet());
        assertEquals(expectedVals2, hashMap.values());
    }
    @Test
    public void multipleRemoves() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(26);
        expectedKeys.add(13);
        expectedKeys.add(16);
        expectedKeys.add(3);
        expectedKeys.add(30);
        expectedKeys.add(6);
        expectedKeys.add(47);
        expectedKeys.add(12);
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("C");
        expectedVals.add("A");
        expectedVals.add("F");
        expectedVals.add("B");
        expectedVals.add("L");
        expectedVals.add("G");
        expectedVals.add("I");
        expectedVals.add("M");
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
        assertEquals(13, hashMap.getTable().length);
        assertEquals(8, hashMap.size());
        assertEquals("G", hashMap.remove(6));
        assertEquals(7, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        HashSet<Integer> expectedKeys2 = new HashSet<>();
        expectedKeys2.add(26);
        expectedKeys2.add(13);
        expectedKeys2.add(16);
        expectedKeys2.add(3);
        expectedKeys2.add(30);
        expectedKeys2.add(47);
        expectedKeys2.add(12);
        ArrayList<String> expectedVals2 = new ArrayList<>();
        expectedVals2.add("C");
        expectedVals2.add("A");
        expectedVals2.add("F");
        expectedVals2.add("B");
        expectedVals2.add("L");
        expectedVals2.add("I");
        expectedVals2.add("M");
        assertEquals(expectedKeys2, hashMap.keySet());
        assertEquals(expectedVals2, hashMap.values());
        assertEquals("C", hashMap.remove(26));
        assertEquals(6, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        HashSet<Integer> expectedKeys3 = new HashSet<>();
        expectedKeys3.add(13);
        expectedKeys3.add(16);
        expectedKeys3.add(3);
        expectedKeys3.add(30);
        expectedKeys3.add(47);
        expectedKeys3.add(12);
        ArrayList<String> expectedVals3 = new ArrayList<>();
        expectedVals3.add("A");
        expectedVals3.add("F");
        expectedVals3.add("B");
        expectedVals3.add("L");
        expectedVals3.add("I");
        expectedVals3.add("M");
        assertEquals(expectedKeys3, hashMap.keySet());
        assertEquals(expectedVals3, hashMap.values());
        assertEquals("B", hashMap.remove(3));
        assertEquals(5, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        HashSet<Integer> expectedKeys4 = new HashSet<>();
        expectedKeys4.add(13);
        expectedKeys4.add(16);
        expectedKeys4.add(30);
        expectedKeys4.add(47);
        expectedKeys4.add(12);
        ArrayList<String> expectedVals4 = new ArrayList<>();
        expectedVals4.add("A");
        expectedVals4.add("F");
        expectedVals4.add("L");
        expectedVals4.add("I");
        expectedVals4.add("M");
        assertEquals(expectedKeys4, hashMap.keySet());
        assertEquals(expectedVals4, hashMap.values());
    }
    @Test
    public void insertAllRemoveAll() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertEquals("C", hashMap.remove(2));
        assertEquals("A", hashMap.remove(0));
        assertEquals("D", hashMap.remove(3));
        assertEquals("E", hashMap.remove(4));
        assertEquals("B", hashMap.remove(1));
        assertEquals(0, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] expected2 =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        assertArrayEquals(expected2, hashMap.getTable());
    }
    //////////////////////////////////////////////////////
    //get tests
    @Test(expected = IllegalArgumentException.class)
    public void getNull() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        hashMap.get(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void getFromEmpty() {
        assertEquals(0, hashMap.size());
        hashMap.get(5);
    }
    @Test(expected = NoSuchElementException.class)
    public void couldNotGetBasic() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        hashMap.get(6);
    }
    @Test(expected = NoSuchElementException.class)
    public void couldNotGetResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
        hashMap.get(16);
    }
    @Test
    public void basicGet() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertEquals("D", hashMap.get(3));
    }
    @Test(expected = NoSuchElementException.class)
    public void getFromEmptySlot() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        hashMap.get(6);
    }
    @Test
    public void getTestWithResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
        for (int i = 0; i < keysToAdd.length; i++) {
            assertEquals(valuesToAdd[i], hashMap.get(keysToAdd[i]));
        }
    }
    //////////////////////////////////////////////////////
    //contains
    @Test(expected = IllegalArgumentException.class)
    public void containNull() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        hashMap.containsKey(null);
    }
    @Test
    public void containsInEmpty() {
        assertEquals(0, hashMap.size());
        assertFalse(hashMap.containsKey(5));
    }
    @Test
    public void notContainsBasic() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertFalse(hashMap.containsKey(6));
    }
    @Test
    public void doesNotContainResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
        assertFalse(hashMap.containsKey(16));
    }
    @Test
    public void basicContains() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertTrue(hashMap.containsKey(3));
    }
    @Test
    public void containsInEmptySlot() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
        assertEquals(5, hashMap.size());
        assertFalse(hashMap.containsKey(6));
    }
    @Test
    public void containsWithResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
        for (int i = 0; i < keysToAdd.length; i++) {
            assertTrue(hashMap.containsKey(keysToAdd[i]));
        }
    }
    /////////////////////////////////////////
    //Retrieving all keys tests
    @Test
    public void emptyHashMapKeys() {
        HashSet<Integer> expected = new HashSet<>();
        assertEquals(expected, hashMap.keySet());
        assertEquals(0, hashMap.size());
    }
    @Test
    public void keysBasic() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        HashSet<Integer> expected = new HashSet<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, hashMap.keySet());
        assertEquals(5, hashMap.size());
    }
    @Test
    public void keySetResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        expectedKeys.add(4);
        expectedKeys.add(5);
        expectedKeys.add(6);
        expectedKeys.add(7);
        expectedKeys.add(8);
        expectedKeys.add(9);
        expectedKeys.add(10);
        expectedKeys.add(11);
        expectedKeys.add(12);
        expectedKeys.add(13);
        assertEquals(expectedKeys, hashMap.keySet());
    }
    @Test
    public void makeSureNoDuplicateKeys() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        HashSet<Integer> expectedKeys = new HashSet<>();
        expectedKeys.add(26);
        expectedKeys.add(13);
        expectedKeys.add(16);
        expectedKeys.add(3);
        expectedKeys.add(30);
        expectedKeys.add(6);
        expectedKeys.add(47);
        expectedKeys.add(12);
        assertEquals(expectedKeys, hashMap.keySet());
        assertEquals(8, hashMap.size());
    }
    /////////////////////////////////////////
    //Retrieving all values tests
    @Test
    public void emptyHashMapValues() {
        ArrayList<String> expected = new ArrayList<>();
        assertEquals(expected, hashMap.values());
        assertEquals(0, hashMap.size());
    }
    @Test
    public void ValuesBasic() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ArrayList<String> expected = new ArrayList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, hashMap.values());
        assertEquals(5, hashMap.size());
    }
    @Test
    public void valuesResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("F");
        expectedValues.add("D");
        expectedValues.add("B");
        expectedValues.add("A");
        expectedValues.add("G");
        expectedValues.add("L");
        expectedValues.add("C");
        expectedValues.add("I");
        expectedValues.add("J");
        expectedValues.add("M");
        expectedValues.add("H");
        expectedValues.add("E");
        expectedValues.add("K");
        assertEquals(expectedValues, hashMap.values());
    }
    @Test
    public void duplicateValues() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "B", "F", "D", "M", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("M");
        expectedVals.add("B");
        expectedVals.add("F");
        expectedVals.add("B");
        expectedVals.add("L");
        expectedVals.add("G");
        expectedVals.add("I");
        expectedVals.add("M");
        assertEquals(expectedVals, hashMap.values());
        assertEquals(13, hashMap.getTable().length);
        assertEquals(8, hashMap.size());
    }
    ////////////////////////////////////
    //clear hashMap tests
    @Test
    public void clearEmptyHashMap() {
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        hashMap.clear();
        assertArrayEquals(expected, hashMap.getTable());
    }
    @Test
    public void basicClear() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        hashMap.clear();
        assertArrayEquals(expected, hashMap.getTable());
    }
    @Test
    public void clearResized() {
        Integer[] keysToAdd = {3,4,1,2,7,6,5,10,9,8,13,12,11}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "A", "F", "D", "C", "L", "G", "M", "J", "I", "K", "E", "H"};
        for (int i = 0; i < keysToAdd.length; i++) {
            assertNull(hashMap.put(keysToAdd[i], valuesToAdd[i]));
        }
        assertEquals(13, hashMap.size());
        assertEquals(27, hashMap.getTable().length);
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        hashMap.clear();
        assertArrayEquals(expected, hashMap.getTable());
    }
    @Test
    public void clearDuplicatedValues() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "B", "F", "D", "M", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        assertEquals(8, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        hashMap.clear();
        assertArrayEquals(expected, hashMap.getTable());
    }
    ///////////////////////////////////////////////////////
    //resize tests
    @Test(expected = IllegalArgumentException.class)
    public void badResize() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "B", "F", "D", "M", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        assertEquals(8, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        hashMap.resizeBackingTable(1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void badResize2() {
        Integer[] keysToAdd = {3,    13,  16,   6,  26,  30,   6,   12,   47}; //resize occurred since load factor was exceeded
        String[] valuesToAdd = {"B", "B", "F", "D", "M", "L", "G", "M",  "I"};
        for (int i = 0; i < keysToAdd.length; i++) {
            hashMap.put(keysToAdd[i], valuesToAdd[i]);
        }
        assertEquals(8, hashMap.size());
        assertEquals(13, hashMap.getTable().length);
        hashMap.resizeBackingTable(7);
    }
    @Test
    public void resizeToYourself() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        HashSet<Integer> expected = new HashSet<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, hashMap.keySet());
        ArrayList<String> expectedVals = new ArrayList<>();
        expectedVals.add("A");
        expectedVals.add("B");
        expectedVals.add("C");
        expectedVals.add("D");
        expectedVals.add("E");
        assertEquals(expectedVals, hashMap.values());
        assertEquals(5, hashMap.size());
        hashMap.resizeBackingTable(13);
        assertEquals(expected, hashMap.keySet());
        assertEquals(expectedVals, hashMap.values());
        assertEquals(5, hashMap.size());
    }
    @Test
    public void resizeToSize() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(hashMap.put(0, "A"));
        assertNull(hashMap.put(1, "B"));
        assertNull(hashMap.put(2, "C"));
        assertNull(hashMap.put(3, "D"));
        assertNull(hashMap.put(4, "E"));
        assertEquals(5, hashMap.size());

        // [(0, A), (1, B), (2, C), (3, D), (4, E)]
        hashMap.resizeBackingTable(5);

        assertEquals(5, hashMap.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[5];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, hashMap.getTable());
    }
}
