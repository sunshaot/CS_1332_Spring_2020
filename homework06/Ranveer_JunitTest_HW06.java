import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * These test cases are for HW06 with ExternalChainingHashmap
 *
 * If you find any issues, please reply to my Piazza post. Hope this helps.
 */

public class Ranveer_JunitTest_HW06 {

    private static final int TIMEOUT = 200;
    private ExternalChainingHashMap<Integer, String> hashMap;

    @Before
    public void setUp() {
        hashMap = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, hashMap.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], hashMap.getTable());
    }

    @Test
    public void testClear() {
        hashMap.put(0, "0");
        hashMap.put(1, "0");
        hashMap.put(2, "0");
        hashMap.put(13, "0");
        hashMap.clear();
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        assertArrayEquals(exp, hashMap.getTable());
        assertEquals(0, hashMap.size());
    }

    @Test
    public void DuplicatBegofListValSet() {
        assertNull(hashMap.put(0, "Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        hashMap.put(0, "Dup");
        assertEquals(4, hashMap.size());
        List<String> set = new ArrayList<>();
        set.add("Dup");
        set.add("Non-Dup1");
        set.add("Non-Dup2");
        set.add("Non-Dup3");
        assertEquals(set, hashMap.values());
    }

    @Test
    public void DuplicatMidofListValSet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        hashMap.put(1, "Dup");
        assertEquals(4, hashMap.size());
        List<String> set = new ArrayList<>();
        set.add("Non-Dup0");
        set.add("Dup");
        set.add("Non-Dup2");
        set.add("Non-Dup3");
        assertEquals(set, hashMap.values());
    }

    @Test
    public void DuplicatEndofListValSet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Dup3"));
        hashMap.put(3, "Dup");
        assertEquals(4, hashMap.size());
        List<String> set = new ArrayList<>();
        set.add("Non-Dup0");
        set.add("Non-Dup1");
        set.add("Non-Dup2");
        set.add("Dup");
        assertEquals(set, hashMap.values());
    }

    @Test
    public void testPutFrontValSet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(13, "Non-DupFront"));
        assertEquals(5, hashMap.size());
        List<String> set = new ArrayList<>();
        set.add("Non-DupFront");
        set.add("Non-Dup0");
        set.add("Non-Dup1");
        set.add("Non-Dup2");
        set.add("Non-Dup3");
        assertEquals(set, hashMap.values());
    }

    @Test
    public void testPutMidValSet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(14, "Non-DupMid"));
        assertEquals(5, hashMap.size());
        List<String> set = new ArrayList<>();
        set.add("Non-Dup0");
        set.add("Non-DupMid");
        set.add("Non-Dup1");
        set.add("Non-Dup2");
        set.add("Non-Dup3");
        assertEquals(set, hashMap.values());
    }

    @Test
    public void testPutBackValSet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(16, "Non-DupBack"));
        assertEquals(5, hashMap.size());
        List<String> set = new ArrayList<>();
        set.add("Non-Dup0");
        set.add("Non-Dup1");
        set.add("Non-Dup2");
        set.add("Non-DupBack");
        set.add("Non-Dup3");
        assertEquals(set, hashMap.values());
    }

    @Test
    public void DuplicatBegofListKeySet() {
        assertNull(hashMap.put(0, "Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        hashMap.put(0, "Dup");
        assertEquals(4, hashMap.size());
        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(set, hashMap.keySet());
    }

    @Test
    public void DuplicatMidofListKeySet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        hashMap.put(1, "Dup");
        assertEquals(4, hashMap.size());
        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(set, hashMap.keySet());
    }

    @Test
    public void DuplicatEndofListKeySet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Dup3"));
        hashMap.put(3, "Dup");
        assertEquals(4, hashMap.size());
        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(set, hashMap.keySet());
    }

    @Test
    public void testPutFrontKeySet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(13, "Non-DupFront"));
        assertEquals(5, hashMap.size());
        HashSet<Integer> set = new HashSet<>();
        set.add(13);
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(set, hashMap.keySet());
    }

    @Test
    public void testPutMidKeySet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(14, "Non-DupMid"));
        assertEquals(5, hashMap.size());
        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(14);
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(set, hashMap.keySet());
    }

    @Test
    public void testPutBackKeySet() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(16, "Non-DupBack"));
        assertEquals(5, hashMap.size());
        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(16);
        set.add(3);
        assertEquals(set, hashMap.keySet());
    }

    @Test
    public void DuplicatBegofList() {
        assertNull(hashMap.put(0, "Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertEquals("Dup0", hashMap.put(0, "Dup"));
        assertEquals(4, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        exp[0] = new ExternalChainingMapEntry<>(0, "Dup");
        exp[1] = new ExternalChainingMapEntry<>(1, "Non-Dup1");
        exp[2] = new ExternalChainingMapEntry<>(2, "Non-Dup2");
        exp[3] = new ExternalChainingMapEntry<>(3, "Non-Dup3");
        assertArrayEquals(exp, hashMap.getTable());
    }

    @Test
    public void DuplicatMidofList() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertEquals("Dup1", hashMap.put(1, "Dup"));
        assertEquals(4, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        exp[0] = new ExternalChainingMapEntry<>(0, "Non-Dup0");
        exp[1] = new ExternalChainingMapEntry<>(1, "Dup");
        exp[2] = new ExternalChainingMapEntry<>(2, "Non-Dup2");
        exp[3] = new ExternalChainingMapEntry<>(3, "Non-Dup3");
        assertArrayEquals(exp, hashMap.getTable());
    }

    @Test
    public void DuplicatEndofList() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Dup3"));
        assertEquals("Dup3", hashMap.put(3, "Dup"));
        assertEquals(4, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        exp[0] = new ExternalChainingMapEntry<>(0, "Non-Dup0");
        exp[1] = new ExternalChainingMapEntry<>(1, "Non-Dup1");
        exp[2] = new ExternalChainingMapEntry<>(2, "Non-Dup2");
        exp[3] = new ExternalChainingMapEntry<>(3, "Dup");
        assertArrayEquals(exp, hashMap.getTable());
    }

    @Test
    public void testPutFront() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(13, "Non-DupFront"));
        assertEquals(5, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        ExternalChainingMapEntry zeroEntry = new ExternalChainingMapEntry<>(0, "Non-Dup0");
        exp[0] = new ExternalChainingMapEntry<>(13, "Non-DupFront", zeroEntry);
        exp[1] = new ExternalChainingMapEntry<>(1, "Non-Dup1");
        exp[2] = new ExternalChainingMapEntry<>(2, "Non-Dup2");
        exp[3] = new ExternalChainingMapEntry<>(3, "Non-Dup3");
        assertArrayEquals(exp, hashMap.getTable());
    }

    @Test
    public void testPutMid() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(14, "Non-DupMid"));
        assertEquals(5, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        ExternalChainingMapEntry midEntry = new ExternalChainingMapEntry<>(1, "Non-Dup1");
        exp[0] = new ExternalChainingMapEntry<>(0, "Non-Dup0");
        exp[1] = new ExternalChainingMapEntry<>(14, "Non-DupMid", midEntry);
        exp[2] = new ExternalChainingMapEntry<>(2, "Non-Dup2");
        exp[3] = new ExternalChainingMapEntry<>(3, "Non-Dup3");
        assertArrayEquals(exp, hashMap.getTable());
    }

    @Test
    public void testPutBack() {
        assertNull(hashMap.put(0, "Non-Dup0"));
        assertNull(hashMap.put(1, "Non-Dup1"));
        assertNull(hashMap.put(2, "Non-Dup2"));
        assertNull(hashMap.put(3, "Non-Dup3"));
        assertNull(hashMap.put(16, "Non-DupBack"));
        assertEquals(5, hashMap.size());
        ExternalChainingMapEntry<Integer, String>[] exp =
                new ExternalChainingMapEntry[ExternalChainingHashMap
                        .INITIAL_CAPACITY];
        ExternalChainingMapEntry lastEntry = new ExternalChainingMapEntry<>(3, "Non-Dup3");
        exp[0] = new ExternalChainingMapEntry<>(0, "Non-Dup0");
        exp[1] = new ExternalChainingMapEntry<>(1, "Non-Dup1");
        exp[2] = new ExternalChainingMapEntry<>(2, "Non-Dup2");
        exp[3] = new ExternalChainingMapEntry<>(16, "Non-DupBack", lastEntry);
        assertArrayEquals(exp, hashMap.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void getNullKey() {
        hashMap.get(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void containsNullKey() {
        hashMap.containsKey(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void removeNullKey() {
        hashMap.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullKeyAndVal() {
        hashMap.put(null, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullKey() {
        hashMap.put(null, "Key is Null");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullVal() {
        hashMap.put(1, null);
    }
}