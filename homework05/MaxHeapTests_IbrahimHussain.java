import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class MaxHeapTests_IbrahimHussain {
    private MaxHeap<Integer> heap;
    @Before
    public void setup() {
        heap = new MaxHeap<Integer>();
    }

    @Test
    public void testInitialization(){
        assertEquals(0, heap.size());
        assertArrayEquals(new Comparable[MaxHeap.INITIAL_CAPACITY], heap.getBackingArray());
        assertTrue(heap.isEmpty());
    }

    //======================= Exceptions Test ====================================================================
    @Test(expected = IllegalArgumentException.class)
    public void addNullElement() {
        heap.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullArrayList() {
        ArrayList<Integer> list = null;
        heap = new MaxHeap<>(list);

    }
    @Test(expected = IllegalArgumentException.class)
    public void addArrayOneNull() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(89);
        list.add(32);
        list.add(12);
        list.add(null);
        list.add(97);
        heap = new MaxHeap<>(list);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmpty() {
        heap.remove();
    }
    @Test(expected = NoSuchElementException.class)
    public void getMaxEmpty() {
        heap.getMax();
    }

    //==============================================================================================================

    @Test
    public void testResize() {

        //check original capacity
        Comparable[] arr = (Comparable[])heap.getBackingArray();
        assertEquals(13, arr.length);

        heap.add(8236);
        heap.add(2109);
        heap.add(2109120);
        heap.add(21221);
        heap.add(2211);
        heap.add(10);
        heap.add(940);
        heap.add(20239);
        heap.add(3393);
        heap.add(39999);
        heap.add(101012);
        heap.add(3832);
        // --resize--
        heap.add(10019);
        heap.add(54213);
        heap.add(1239450);
        heap.add(4582);
        heap.add(4);
        heap.add(84252);
        heap.add(52362);
        heap.add(84522);
        heap.add(3281036);
        heap.add(294652);

        //check size
        assertEquals(22, heap.size());
        //check capacity is double the original (13 * 2 = 26)
        arr = (Comparable[])heap.getBackingArray();
        assertEquals(26, arr.length);
        //check 0 index is null
        assertEquals(null, arr[0]);
    }

    @Test
    public void arrayListInitialization() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(45);
        list.add(75);
        list.add(46);
        list.add(13);
        list.add(90);
        list.add(73);
        list.add(59);
        list.add(80);
        list.add(27);
        //size of list = 9

        heap = new MaxHeap<>(list);

        //test for capacity = 2n + 1:
        Comparable[] arr = (Comparable[])heap.getBackingArray();
        assertEquals(19, arr.length);


        /*

        After heapify:
                  90
              /        \
             80         73
           /   \      /    \
          45   75    46    59
          / \
         13  27

         */

        Comparable[] expected = {null, 90, 80, 73, 45, 75, 46, 59, 13, 27,
                null, null, null, null, null, null, null, null, null};
        arr = (Comparable[])heap.getBackingArray();
        assertArrayEquals(expected, arr);

    }

    @Test
    public void testAdd() {

        heap.add(45);
        heap.add(75);
        heap.add(46);
        heap.add(13);
        heap.add(90);
        heap.add(73);
        heap.add(59);
        heap.add(80);
        heap.add(27);
        //size of heap = 9

        assertEquals(9, heap.size());

        /*

        Expected:
                  90
              /        \
             80         73
           /   \      /    \
          75   45    46    59
          / \
         13  27

         */

        Comparable[] expected = {null, 90, 80, 73, 75, 45, 46, 59, 13, 27,
                null, null, null};
        Comparable[] arr = (Comparable[])heap.getBackingArray();
        assertArrayEquals(expected, arr);

        heap.add(97);
        heap.add(1000);
        heap.add(798);
        // -- resize --
        heap.add(741);
        heap.add(761);
        heap.add(167);
        heap.add(391);
        heap.add(516);
        heap.add(943);

        /* size = 18

           capacity = 26

           Expected:
                              1000
                        /              \
                      943               798
                    /      \           /      \
                   516      90        741      761
                  /   \     / \       /  \     /  \
                97    391  45  80    46  73   59   167
              /  \    /
             13  75  27


         */
        arr = (Comparable[])heap.getBackingArray();
        Comparable[] expected2 = {null, 1000, 943, 798, 516, 90, 741, 761, 97, 391,
                45, 80, 46, 73, 59, 167, 13, 75, 27, null, null, null, null, null,
                null, null};
        assertEquals(26, arr.length);
        assertEquals(18, heap.size());
        assertArrayEquals(expected2, arr);


    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveAndClear() {

        heap.add(45);
        heap.add(75);
        heap.add(46);
        heap.add(13);
        heap.add(90);
        heap.add(73);
        heap.add(59);
        heap.add(80);
        heap.add(27);
        heap.add(97);
        heap.add(1000);
        heap.add(798);
        heap.add(741);
        heap.add(761);
        heap.add(167);
        heap.add(391);
        heap.add(516);
        heap.add(943);

        /*

            size = 18

            capacity = 26

            Starting heap:
                              1000
                        /              \
                      943               798
                    /      \           /      \
                   516      90        741      761
                  /   \     / \       /  \     /  \
                97    391  45  80    46  73   59   167
              /  \    /
             13  75  27


         */

        assertEquals((Integer)1000, heap.remove());
        assertEquals((Integer)943, heap.remove());
        assertEquals((Integer)798, heap.remove());
        assertEquals((Integer)761, heap.remove());
        assertEquals((Integer)741, heap.remove());
        assertEquals((Integer)516, heap.remove());


        Comparable[] arr = (Comparable[])heap.getBackingArray();
        Comparable[] expected = {null, 391, 97, 167, 59, 90, 73, 75, 13, 27,
                45, 80, 46, null, null, null, null, null, null, null, null, null, null, null,
                null, null};
        assertEquals(26, arr.length);
        assertEquals(12, heap.size());
        assertArrayEquals(expected, arr);

        //now clear heap and try to remove (expect exception)

        heap.clear();
        assertEquals(0, heap.size());
        arr = (Comparable[])heap.getBackingArray();
        assertEquals(13, arr.length);
        assertNull(arr[0]);
        assertNull(arr[7]);
        assertTrue(heap.isEmpty());

        //size is 0, this should throw an exception
        heap.remove();

    }

    @Test (expected = NoSuchElementException.class)
    public void getMaxTest() {
        heap.add(100);
        heap.add(200);
        heap.add(300);
        heap.add(400);
        heap.add(500);
        heap.add(600);
        heap.add(700);
        heap.add(800);

        assertEquals((Integer)800, heap.getMax());

        //make sure item was not removed

        assertEquals(8, heap.size());

        //clear all
        heap.clear();

        //heap of size one
        heap.add(75);

        assertEquals((Integer)75, heap.getMax());

        heap.remove();

        //heap is empty

        //should throw an exception
        heap.getMax();

    }

    //worst-case scenario
    @Test
    public void minHeapTest (){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(45);
        list.add(65);
        list.add(75);
        list.add(98);
        list.add(100);
        list.add(150);
        list.add(200);
        list.add(250);
        list.add(269);
        list.add(290);
        list.add(300);
        list.add(320);
        list.add(340);
        list.add(360);
        list.add(400);
        list.add(410);
        list.add(460);
        list.add(470);
        list.add(480);
        list.add(500);
        list.add(530);
        list.add(550);
        list.add(560);
        list.add(590);
        list.add(630);
        list.add(670);
        list.add(690);
        list.add(700);
        list.add(720);
        list.add(740);
        list.add(760);
        list.add(780);
        list.add(790);
        list.add(810);

        /* expected:
        size = 34
        capacity = 2(34) + 1 = 69

         */

        Integer[] expected = {null, 810, 790, 760, 780, 560, 690, 740, 460, 480, 530, 550, 630, 670, 720, 400,
        410, 250, 470, 269, 500, 290, 100, 300, 590, 320, 150, 340, 700, 360, 200, 75, 98, 65, 45, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null};

        heap = new MaxHeap<>(list);

        Comparable[] arr = (Comparable[])heap.getBackingArray();

        assertEquals(69, arr.length);
        assertEquals(34, heap.size());
        assertArrayEquals(expected, arr);


    }

    @Test
    public void listArgOneElement() {
        //edge case: list has only one element
        ArrayList<Integer> list = new ArrayList<>();
        list.add(65);

        /*
        Expected:
        capacity: 2(1) + 1 = 3
        size: 1
         */

        heap = new MaxHeap<>(list);
        Integer[] expected = {null, 65, null};

        Comparable[] arr = (Comparable[])heap.getBackingArray();

        assertNull(arr[0]);
        assertArrayEquals(expected, arr);
        assertEquals(3, arr.length);
        assertEquals(1, heap.size());

    }



}
