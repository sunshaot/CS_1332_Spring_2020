import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class MaxHeapJUnitTest {
    private MaxHeap<Integer> maxHeap;

    @Before
    public void setup() {
        maxHeap = new MaxHeap<Integer>();
    }
    ///////////////////////////////////////////////////////////////////
    //BuildHeap Tests
    @Test
    public void testInitialization() {
        assertEquals(0, maxHeap.size());
        assertArrayEquals(new Comparable[MaxHeap.INITIAL_CAPACITY], maxHeap.getBackingArray());
    }
    @Test(expected = IllegalArgumentException.class)
    public void nullArrayListBuildHeap() {
        ArrayList<Integer> dataList = null;
        maxHeap = new MaxHeap<Integer>(dataList);
    }
    @Test(expected = IllegalArgumentException.class)
    public void nullEntryBuildHeap() {
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        dataList.add(1);
        dataList.add(8);
        dataList.add(null);
        dataList.add(2);
        maxHeap = new MaxHeap<Integer>(dataList);

    }
    @Test
    public void basicBuildHeap() {
        /*
                 2
               /   \
              1     4
             / \
            6   8

            ->

                 8
               /   \
              6     4
             / \
            2   1
        */

        ArrayList<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(1);
        data.add(4);
        data.add(6);
        data.add(8);
        maxHeap = new MaxHeap<>(data);

        assertEquals(5, maxHeap.size());

        Integer[] expected = new Integer[11];
        expected[1] = 8;
        expected[2] = 6;
        expected[3] = 4;
        expected[4] = 2;
        expected[5] = 1;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void buildHeapDegenerate() {
        ArrayList<Integer> dataList = new ArrayList<>();
        dataList.add(1);
        dataList.add(2);
        dataList.add(3);
        dataList.add(4);
        maxHeap = new MaxHeap<>(dataList);
        assertEquals(4, maxHeap.size());
        Integer[] expected = new Integer[9];
        expected[1] = 4;
        expected[2] = 2;
        expected[3] = 3;
        expected[4] = 1;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void buildHeapSizeOne() {
        ArrayList<Integer> dataList = new ArrayList<>();
        Random randGen = new Random();
        int randInt = randGen.nextInt(10);
        dataList.add(randInt);
        maxHeap = new MaxHeap<>(dataList);
        Integer[] expected = new Integer[3];
        expected[1] = randInt;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(1, maxHeap.size());
    }
    @Test
    public void complexBuildHeapTest() {
        ArrayList<Integer> dataList = new ArrayList<>();
        dataList.add(2);
        dataList.add(1);
        dataList.add(4);
        dataList.add(6);
        dataList.add(8);
        dataList.add(11);
        dataList.add(0);
        dataList.add(20);
        dataList.add(7);
        dataList.add(3);
        /*
                 20
               /   \
              8     11
             / \   /  \
            7   3  4   0
           / \ /
          6  1 2

         */
        maxHeap = new MaxHeap<>(dataList);
        Integer[] expected = new Integer[21];
        expected[1] = 20;
        expected[2] = 8;
        expected[3] = 11;
        expected[4] = 7;
        expected[5] = 3;
        expected[6] = 4;
        expected[7] = 0;
        expected[8] = 6;
        expected[9] = 1;
        expected[10] = 2;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(10, maxHeap.size());
    }
    @Test
    public void buildMaxHeapFromMinHeap() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(18);
        data.add(41);
        data.add(22);
        data.add(47);
        data.add(59);
        data.add(29);
        data.add(31);
        data.add(73);
        maxHeap = new MaxHeap<>(data);
        Integer[] expected = new Integer[17];
        expected[1] = 73;
        expected[2] = 59;
        expected[3] = 31;
        expected[4] = 47;
        expected[5] = 18;
        expected[6] = 29;
        expected[7] = 22;
        expected[8] = 41;
        assertEquals(8, maxHeap.size());
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    ////////////////////////////////////////////////////
    //adding to heap tests
    @Test(expected = IllegalArgumentException.class)
    public void addNullData() {
        maxHeap.add(null);
    }
    @Test
    public void addToHeapSizeOne() {
        maxHeap.add(2);
        assertEquals(1, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 2;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void simpleAddToHeap() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        assertEquals(5, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 8;
        expected[2] = 6;
        expected[3] = 4;
        expected[4] = 2;
        expected[5] = 1;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        maxHeap.add(3);
        Integer[] expected2 = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected2[1] = 8;
        expected2[2] = 6;
        expected2[3] = 4;
        expected2[4] = 2;
        expected2[5] = 1;
        expected2[6] = 3;
        assertEquals(6, maxHeap.size());
        assertArrayEquals(expected2, maxHeap.getBackingArray());
    }
    @Test
    public void addWithResize() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(18);
        maxHeap.add(41);
        maxHeap.add(22);
        maxHeap.add(47);
        maxHeap.add(59);
        maxHeap.add(29);
        maxHeap.add(31);
        maxHeap.add(73); //resize will occur here
        maxHeap.add(100);
        assertEquals(14, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY * 2];
        expected[1] = 100;
        expected[2] = 47;
        expected[3] = 73;
        expected[4] = 22;
        expected[5] = 41;
        expected[6] = 31;
        expected[7] = 59;
        expected[8] = 2;
        expected[9] = 6;
        expected[10] = 1;
        expected[11] = 29;
        expected[12] = 4;
        expected[13] = 18;
        expected[14] = 8;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    /////////////////////////////////////////////////////
    //remove tests
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyHeap() {
        maxHeap.remove();
    }
    @Test
    public void removeFromSizeOne() {
        maxHeap.add(5);
        assertEquals(1, maxHeap.size());
        assertEquals(5, maxHeap.remove().intValue());
        assertEquals(0, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(0, maxHeap.size());
    }
    @Test
    public void basicRemove() {
        maxHeap.add(5);
        maxHeap.add(2);
        maxHeap.add(3);
        maxHeap.add(11);
        maxHeap.add(1);
        assertEquals(5, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 11;
        expected[2] = 5;
        expected[3] = 3;
        expected[4] = 2;
        expected[5] = 1;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(11, maxHeap.remove().intValue());
        assertEquals(4, maxHeap.size());
        Integer[] removed = new Integer[MaxHeap.INITIAL_CAPACITY];
        removed[1] = 5;
        removed[2] = 2;
        removed[3] = 3;
        removed[4] = 1;
        assertArrayEquals(removed, maxHeap.getBackingArray());
    }
    @Test
    public void multipleRemove() {
        maxHeap.add(5);
        maxHeap.add(2);
        maxHeap.add(3);
        maxHeap.add(11);
        maxHeap.add(1);
        assertEquals(5, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 11;
        expected[2] = 5;
        expected[3] = 3;
        expected[4] = 2;
        expected[5] = 1;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(11, maxHeap.remove().intValue());
        assertEquals(4, maxHeap.size());
        Integer[] removed = new Integer[MaxHeap.INITIAL_CAPACITY];
        removed[1] = 5;
        removed[2] = 2;
        removed[3] = 3;
        removed[4] = 1;
        assertArrayEquals(removed, maxHeap.getBackingArray());
        assertEquals(5, maxHeap.remove().intValue());
        Integer[] removed2 = new Integer[MaxHeap.INITIAL_CAPACITY];
        removed2[1] = 3;
        removed2[2] = 2;
        removed2[3] = 1;
        assertArrayEquals(removed2, maxHeap.getBackingArray());
        assertEquals(3, maxHeap.size());
    }
    @Test
    public void resizeAndRemove() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(18);
        maxHeap.add(41);
        maxHeap.add(22);
        maxHeap.add(47);
        maxHeap.add(59);
        maxHeap.add(29);
        maxHeap.add(31);
        maxHeap.add(73); //resize will occur here
        maxHeap.add(100);
        assertEquals(14, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY * 2];
        expected[1] = 100;
        expected[2] = 47;
        expected[3] = 73;
        expected[4] = 22;
        expected[5] = 41;
        expected[6] = 31;
        expected[7] = 59;
        expected[8] = 2;
        expected[9] = 6;
        expected[10] = 1;
        expected[11] = 29;
        expected[12] = 4;
        expected[13] = 18;
        expected[14] = 8;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(100, maxHeap.remove().intValue());
        assertEquals(13, maxHeap.size());
        Integer[] expected2 = new Integer[MaxHeap.INITIAL_CAPACITY * 2];
        expected2[1] = 73;
        expected2[2] = 47;
        expected2[3] = 59;
        expected2[4] = 22;
        expected2[5] = 41;
        expected2[6] = 31;
        expected2[7] = 8;
        expected2[8] = 2;
        expected2[9] = 6;
        expected2[10] = 1;
        expected2[11] = 29;
        expected2[12] = 4;
        expected2[13] = 18;
        assertArrayEquals(expected2, maxHeap.getBackingArray());
    }
    ////////////////////////////////////////////////
    //get maximum element tests
    @Test(expected = NoSuchElementException.class)
    public void getMaxEmpty() {
        maxHeap.getMax();
    }
    @Test
    public void getMaxSizeOne() {
        maxHeap.add(50);
        assertEquals(50, maxHeap.getMax().intValue());
        assertEquals(1, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 50;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void basicGetMax() {
        maxHeap.add(5);
        maxHeap.add(2);
        maxHeap.add(3);
        maxHeap.add(11);
        maxHeap.add(1);
        assertEquals(5, maxHeap.size());
        assertEquals(11, maxHeap.getMax().intValue());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 11;
        expected[2] = 5;
        expected[3] = 3;
        expected[4] = 2;
        expected[5] = 1;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void getMaxResized() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(18);
        maxHeap.add(41);
        maxHeap.add(22);
        maxHeap.add(47);
        maxHeap.add(59);
        maxHeap.add(29);
        maxHeap.add(31);
        maxHeap.add(73); //resize will occur here
        maxHeap.add(100);
        assertEquals(14, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY * 2];
        expected[1] = 100;
        expected[2] = 47;
        expected[3] = 73;
        expected[4] = 22;
        expected[5] = 41;
        expected[6] = 31;
        expected[7] = 59;
        expected[8] = 2;
        expected[9] = 6;
        expected[10] = 1;
        expected[11] = 29;
        expected[12] = 4;
        expected[13] = 18;
        expected[14] = 8;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(100, maxHeap.getMax().intValue());
    }
    @Test
    public void getMaxAndRemove() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(18);
        maxHeap.add(41);
        maxHeap.add(22);
        maxHeap.add(47);
        maxHeap.add(59);
        maxHeap.add(29);
        maxHeap.add(31);
        maxHeap.add(73); //resize will occur here
        maxHeap.add(100);
        assertEquals(14, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY * 2];
        expected[1] = 100;
        expected[2] = 47;
        expected[3] = 73;
        expected[4] = 22;
        expected[5] = 41;
        expected[6] = 31;
        expected[7] = 59;
        expected[8] = 2;
        expected[9] = 6;
        expected[10] = 1;
        expected[11] = 29;
        expected[12] = 4;
        expected[13] = 18;
        expected[14] = 8;
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(100, maxHeap.getMax().intValue());
        assertEquals(100, maxHeap.remove().intValue());
        assertEquals(13, maxHeap.size());
        Integer[] expected2 = new Integer[MaxHeap.INITIAL_CAPACITY * 2];
        expected2[1] = 73;
        expected2[2] = 47;
        expected2[3] = 59;
        expected2[4] = 22;
        expected2[5] = 41;
        expected2[6] = 31;
        expected2[7] = 8;
        expected2[8] = 2;
        expected2[9] = 6;
        expected2[10] = 1;
        expected2[11] = 29;
        expected2[12] = 4;
        expected2[13] = 18;
        assertArrayEquals(expected2, maxHeap.getBackingArray());
    }
    @Test
    public void getMaxFromInitiallyMinHeap() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(18);
        data.add(41);
        data.add(22);
        data.add(47);
        data.add(59);
        data.add(29);
        data.add(31);
        data.add(73);
        maxHeap = new MaxHeap<>(data);
        Integer[] expected = new Integer[17];
        expected[1] = 73;
        expected[2] = 59;
        expected[3] = 31;
        expected[4] = 47;
        expected[5] = 18;
        expected[6] = 29;
        expected[7] = 22;
        expected[8] = 41;
        assertEquals(8, maxHeap.size());
        assertArrayEquals(expected, maxHeap.getBackingArray());
        assertEquals(73, maxHeap.getMax().intValue());
    }
    //////////////////////////////////////////////////////
    //is empty tests
    @Test
    public void emptyHeapTest() {
        assertTrue(maxHeap.isEmpty());
    }
    @Test
    public void notEmptyTest() {
        maxHeap.add(1);
        assertFalse(maxHeap.isEmpty());
    }
    @Test
    public void isEmptyBasicTest() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(18);
        maxHeap.add(41);
        maxHeap.add(22);
        maxHeap.add(47);
        maxHeap.add(59);
        maxHeap.add(29);
        maxHeap.add(31);
        maxHeap.add(73); //resize will occur here
        maxHeap.add(100);
        assertFalse(maxHeap.isEmpty());
    }
    ///////////////////////////////////////////////
    //clear the heap tests
    @Test
    public void clearHeapFromEmpty() {
        maxHeap.clear();
        assertEquals(0, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void basicClearHeap() {
        maxHeap.add(5);
        maxHeap.add(2);
        maxHeap.add(3);
        maxHeap.add(11);
        maxHeap.add(1);
        assertEquals(5, maxHeap.size());
        maxHeap.clear();
        assertEquals(0, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
    @Test
    public void clearResizedHeap() {
        maxHeap.add(4);
        maxHeap.add(2);
        maxHeap.add(6);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(18);
        maxHeap.add(41);
        maxHeap.add(22);
        maxHeap.add(47);
        maxHeap.add(59);
        maxHeap.add(29);
        maxHeap.add(31);
        maxHeap.add(73); //resize will occur here
        maxHeap.add(100);
        maxHeap.clear();
        assertEquals(0, maxHeap.size());
        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
}
