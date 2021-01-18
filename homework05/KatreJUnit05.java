import org.junit.Before;
import org.junit.Test;

import java.util.*;
import static org.junit.Assert.*;

public class KatreJUnit05 {
    private MaxHeap<Integer> heap;

    @Before
    public void setup() {
        heap = new MaxHeap<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInitializeException() {
        heap = new MaxHeap<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayListInitializeException() {
        ArrayList<Integer> test = new ArrayList<>(Arrays.asList(0, 1, null, 3));
        heap = new MaxHeap<>(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullException() {
        heap.add(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmptyException() {
        heap.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testMaxEmptyException() {
        heap.getMax();
    }

    @Test
    public void testBuildHeap() {
        ArrayList<Integer> test = new ArrayList<>(Arrays.asList(4, 8, 35, 48, 17, 5, 9, 1, 20, 49));
        heap = new MaxHeap<>(test);
        Comparable[] expected = new Comparable[]{null, 49, 48, 35, 20, 17, 5, 9, 1, 4, 8, null, null, null, null, null, null, null, null, null, null};
        Comparable[] actual = heap.getBackingArray();
        assertArrayEquals(expected, actual);
        assertEquals(test.size(), heap.size());
    }

    @Test
    public void testAddFirst() {
        heap.add(4);
        assertArrayEquals(new Comparable[]{null, 4, null, null, null, null, null, null, null, null, null, null, null}, heap.getBackingArray());
        assertEquals(1, heap.size());
    }

    @Test
    public void testAddResize() {
        ArrayList<Integer> test = new ArrayList<>(Arrays.asList(4, 8, 35, 48, 17, 5, 9, 1, 20, 49, 21, 16, 26, 45));
        for (Integer i : test) {
            heap.add(i);
        }

        Comparable[] backingArray = heap.getBackingArray();
        assertArrayEquals(new Comparable[]{null, 49, 48, 45, 20, 35, 16, 26, 1, 4, 17, 21, 5, 9, 8, null, null, null, null, null, null, null, null, null, null, null}, backingArray);
        assertEquals(test.size(), heap.size());
        assertEquals(26, backingArray.length);
    }

    @Test
    public void testRemoveLast() {
        heap.add(4);
        assertArrayEquals(new Comparable[]{null, 4, null, null, null, null, null, null, null, null, null, null, null}, heap.getBackingArray());
        assertEquals(1, heap.size());
        heap.remove();
        assertArrayEquals(new Comparable[]{null, null, null, null, null, null, null, null, null, null, null, null, null}, heap.getBackingArray());
        assertEquals(0, heap.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(heap.isEmpty());
    }
}
