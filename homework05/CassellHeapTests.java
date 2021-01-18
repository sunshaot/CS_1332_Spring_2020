import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit tests to cover the general cases not covered by the given tests.
 * @author Colin Cassell
 */
public class CassellHeapTests {
    private static final int TIMEOUT = 200;
    private MaxHeap<Integer> heap;

    @Before
    public void setUp() {
        heap = new MaxHeap<>();
    }

    //Tests constructor with even number of entries, as this may cause a null pointer exception in some implementations
    @Test(timeout = TIMEOUT)
    public void testBuildHeap() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(7);
        list.add(1);
        list.add(5);
        list.add(3);
        heap = new MaxHeap<>(list);
        assertEquals(4, heap.size());

        Integer[] expected = new Integer[9];
        expected[1] = 7;
        expected[2] = 3;
        expected[3] = 5;
        expected[4] = 1;
        assertArrayEquals(expected, heap.getBackingArray());
    }



    @Test(timeout = TIMEOUT)
    public void testResize() {
        heap.add(20);
        heap.add(5);
        heap.add(25);
        heap.add(6);
        heap.add(40);
        heap.add(2);
        heap.add(7);
        heap.add(19);
        heap.add(61);
        heap.add(1);
        heap.add(8);
        heap.add(32);

        assertEquals(12, heap.size());

        heap.add(22);

        assertEquals(13, heap.size());


        Integer[] expected = {null, 61, 40, 32, 25, 8, 22, 7, 5, 19, 1, 6, 2,
            20, null, null, null, null, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, heap.getBackingArray());
    }

    //Everything below here checks the exception throwing for each method
    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testBuildHeapEmpty() {
        ArrayList<Integer> list = null;
        heap = new MaxHeap<>(list);
    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testBuildHeapNullElement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(7);
        list.add(null);
        list.add(3);
        heap = new MaxHeap<>(list);
    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testAddNull() {
        heap.add(null);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void testRemoveEmpty() {
        heap.remove();
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void testGetMaxEmpty() {
        heap.getMax();
    }
}
