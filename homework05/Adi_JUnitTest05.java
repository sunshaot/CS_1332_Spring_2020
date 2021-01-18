import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

/**
 * Adi Singh's JUnit Tests for HW05: Max Heaps
 * @author Adi Singh
 * @version 1.0
 *
 * These include numerous test cases for each method and each exception potentially thrown by the method. Some cases
 * also look at handling adding/removing from a left child versus a right child. Please let me know on Piazza if you
 * find any issues. Hope this helps.
 */
public class Adi_JUnitTest05 {
    private MaxHeap<Integer> heap, heap2;

    @Before
    public void setUp() {
        heap = new MaxHeap<>();
    }

    @Test
    public void testInitialization() {
        assertEquals(0, heap.size());
        assertArrayEquals(new Comparable[MaxHeap.INITIAL_CAPACITY], heap.getBackingArray());
    }

    @Test
    public void testBuildHeap() {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(4);
        nums.add(5);
        nums.add(6);
        nums.add(10);
        nums.add(12);
        nums.add(7);
        heap2 = new MaxHeap<>(nums);

        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 12;
        expected[2] = 10;
        expected[3] = 7;
        expected[4] = 4;
        expected[5] = 5;
        expected[6] = 6;
        assertArrayEquals(expected, heap2.getBackingArray());
    }

    @Test
    public void testBuildHeapAscendingOrderData() {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
        nums.add(6);
        heap2 = new MaxHeap<>(nums);

        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 6;
        expected[2] = 5;
        expected[3] = 3;
        expected[4] = 4;
        expected[5] = 2;
        expected[6] = 1;
        assertArrayEquals(expected, heap2.getBackingArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyConstructorWithNullList() {
        ArrayList<Integer> nums = null;
        heap2 = new MaxHeap<>(nums);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyConstructorWithNullData() {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(4);
        nums.add(5);
        nums.add(null);
        nums.add(10);
        nums.add(12);
        heap2 = new MaxHeap<>(nums);
    }

    //Add Tests
    @Test
    public void addToEmptyHeap() {
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 5;

        heap.add(5);
        assertArrayEquals(expected, heap.getBackingArray());
    }

    /*
              12                  12
             /  \                /  \
           7      10    ->      7     10
         /  \                  / \   /
        1    5                1   5  4
     */
    @Test
    public void addToRegularHeapEndOnLeftChild() {
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 12;
        expected[2] = 7;
        expected[3] = 10;
        expected[4] = 1;
        expected[5] = 5;
        expected[6] = 4;

        heap.add(1);
        heap.add(7);
        heap.add(4);
        heap.add(12);
        heap.add(5);
        heap.add(10);

        assertArrayEquals(expected, heap.getBackingArray());
    }

    /*
              12                     12
             /  \                   /  \
           7      10    ->        7     10
         /  \     /              / \   /  \
        1    5   4              1   5  4   6
     */
    @Test
    public void addToRegularHeapEndOnRightChild() {
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 12;
        expected[2] = 7;
        expected[3] = 10;
        expected[4] = 1;
        expected[5] = 5;
        expected[6] = 4;
        expected[7] = 6;

        heap.add(1);
        heap.add(7);
        heap.add(4);
        heap.add(12);
        heap.add(5);
        heap.add(10);
        heap.add(6);

        assertArrayEquals(expected, heap.getBackingArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullData() {
        heap.add(12);
        heap.add(null);
        heap.add(7);
    }


    @Test
    public void addToResizeHeap() {
        heap.add(19);
        heap.add(4);
        heap.add(8);
        heap.add(2);
        heap.add(12);
        heap.add(34);
        heap.add(32);
        heap.add(9);
        heap.add(11);
        heap.add(84);
        heap.add(1);
        heap.add(3);

        heap.add(22);

        assertEquals(13, heap.size());
    }

    //Remove Tests
    @Test
    public void removeFromSize1Heap() {
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        heap.add(3);
        heap.remove();
        assertArrayEquals(expected, heap.getBackingArray());
    }

    /*
              12                  10
             /  \                /  \
           7      10    ->      7    4
         /  \    /             / \
        1    5   4            1   5
     */
    @Test
    public void removeFromLeftChild() {
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 10;
        expected[2] = 7;
        expected[3] = 4;
        expected[4] = 1;
        expected[5] = 5;

        heap.add(1);
        heap.add(7);
        heap.add(4);
        heap.add(12);
        heap.add(5);
        heap.add(10);

        heap.remove();
        assertArrayEquals(expected, heap.getBackingArray());
    }

    /*
              12                  10
             /  \                /  \
          10      7    ->       5     7
         /  \    /  \          / \   /
        4    5   6   3        4   3  6
     */
    @Test
    public void removeFromRightChild() {
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 10;
        expected[2] = 5;
        expected[3] = 7;
        expected[4] = 4;
        expected[5] = 3;
        expected[6] = 6;

        heap.add(7);
        heap.add(4);
        heap.add(12);
        heap.add(5);
        heap.add(10);
        heap.add(6);
        heap.add(3);

        heap.remove();
        assertArrayEquals(expected, heap.getBackingArray());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyHeap() {
        heap = new MaxHeap<>();
        heap.remove();
    }

    //getMax tests
    @Test
    public void getMaxTest() {
        heap.add(4);
        heap.add(10);
        heap.add(7);
        heap.add(12);

        assertEquals(12, heap.getMax().intValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void getMaxOfEmptyList() {
        heap = new MaxHeap<>();
        heap.getMax();
    }

    @Test
    public void getMaxOfSize1Test() {
        heap.add(1);
        assertEquals(1, heap.getMax().intValue());
    }

    //isEmpty tests
    @Test
    public void isEmpty() {
        assertTrue(heap.isEmpty());
        heap.add(4);
        heap.add(8);
        heap.add(2);
        heap.add(12);
        assertFalse(heap.isEmpty());
    }

    @Test
    public void isEmptyOnEmptyList() {
        assertTrue(heap.isEmpty());
    }

    //clear tests
    @Test
    public void clearTest() {
        assertTrue(heap.isEmpty());
        heap.add(3);
        heap.add(19);
        heap.clear();
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        assertArrayEquals(expected, heap.getBackingArray());
    }

    @Test
    public void clearAfterResizing() {
        heap.add(19);
        heap.add(4);
        heap.add(8);
        heap.add(2);
        heap.add(12);
        heap.add(34);
        heap.add(32);
        heap.add(9);
        heap.add(11);
        heap.add(84);
        heap.add(35);
        heap.add(3);
        assertEquals(12, heap.size());

        heap.add(1);
        heap.clear();

        assertEquals(0, heap.size());
        assertArrayEquals(new Comparable[MaxHeap.INITIAL_CAPACITY], heap.getBackingArray());
    }
}
