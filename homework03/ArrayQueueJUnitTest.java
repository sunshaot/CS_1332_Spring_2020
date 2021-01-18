import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayQueueJUnitTest {
    private ArrayQueue<Integer> arrayQueue;
    @Before
    public void setup() {
        arrayQueue = new ArrayQueue<Integer>();
    }
    @Test
    public void testInitialization() {
        assertEquals(0, arrayQueue.size());
        assertArrayEquals(new Integer[ArrayStack.INITIAL_CAPACITY],
                arrayQueue.getBackingArray());
    }
    @Test(expected = IllegalArgumentException.class)
    public void enqueueNullData() {
        arrayQueue.enqueue(null);
    }
    @Test
    public void enqueueSizeOne() {
        arrayQueue.enqueue(1);
        assertEquals(1, arrayQueue.size());
        Integer[] expectedList = {1, null, null, null, null, null, null, null, null};
        assertArrayEquals(expectedList, arrayQueue.getBackingArray());
    }
    @Test
    public void basicEnqueueTest() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, arrayQueue.size());
        Integer[] expected = {1,2,3,4,5, null, null, null, null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }
    @Test
    public void enqueueWithResize() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        arrayQueue.enqueue(10); //resize will occur
        assertEquals(10, arrayQueue.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }
    @Test
    public void enqueueWithMultipleResizings() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        assertEquals(9, arrayQueue.size());
        arrayQueue.enqueue(10); //resize will occur
        arrayQueue.enqueue(11);
        arrayQueue.enqueue(12);
        arrayQueue.enqueue(13);
        arrayQueue.enqueue(14);
        arrayQueue.enqueue(15);
        arrayQueue.enqueue(16);
        arrayQueue.enqueue(17);
        arrayQueue.enqueue(18);
        assertEquals(18, arrayQueue.size());
        arrayQueue.enqueue(19); //resize will occur
        arrayQueue.enqueue(20); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19, 20]
        assertEquals(20, arrayQueue.size());
        Integer[] expectedNum = new Integer[36];
        for (int i = 1; i <= 20; i++) {
            expectedNum[i-1] = i;
        }
        assertArrayEquals(expectedNum, arrayQueue.getBackingArray());
    }
    @Test(expected = NoSuchElementException.class)
    public void dequeueFromEmpty() {
        arrayQueue.dequeue();
    }
    @Test
    public void basicDequeueTest() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, arrayQueue.size());
        int expected = 1;
        assertEquals(expected, arrayQueue.dequeue().intValue());
        assertEquals(4, arrayQueue.size());
        Integer[] expectedList = {null, 2, 3, 4, 5, null, null, null, null};
        assertArrayEquals(expectedList, arrayQueue.getBackingArray());
    }
    @Test
    public void dequeueTestWithResize() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        arrayQueue.enqueue(10); //resize will occur
        assertEquals(10, arrayQueue.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        int expectedNum = 0;
        for (int i = 1; i <= 10; i++) {
            expectedNum = i;
            assertEquals(expectedNum, arrayQueue.dequeue().intValue());
            assertEquals(10 - i, arrayQueue.size());
        }
        assertEquals(0, arrayQueue.size());
    }
    @Test
    public void dequeueFromSizeOne() {
        arrayQueue.enqueue(36);
        assertEquals(36, arrayQueue.dequeue().intValue());
        assertEquals(0, arrayQueue.size());
    }
    @Test (expected = NoSuchElementException.class)
    public void peekFromEmpty() {
        arrayQueue.peek();
    }
    @Test
    public void basicPeekTest() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(1, arrayQueue.peek().intValue());
        assertEquals(5, arrayQueue.size());
        Integer[] expected = {1,2,3,4,5,null,null,null,null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }
    @Test
    public void peekTestWithResize() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        arrayQueue.enqueue(10); //resize will occur
        assertEquals(10, arrayQueue.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(1, arrayQueue.peek().intValue());
    }
    @Test
    public void getBackingArrayTest() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        arrayQueue.enqueue(10); //resize will occur
        assertEquals(10, arrayQueue.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }
    @Test
    public void getBackingArray() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        Integer[] expected = {1,2,3,4,5,null,null,null,null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }
    @Test
    public void getEmptySize() {
        assertEquals(0, arrayQueue.size());
    }
    @Test
    public void getSize() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, arrayQueue.size());
    }
    @Test
    public void getSizeWithResize() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        arrayQueue.enqueue(10); //resize will occur
        assertEquals(10, arrayQueue.size());
    }
    @Test
    public void peekAndDequeue() {
        arrayQueue.enqueue(1); //[1]
        arrayQueue.enqueue(2); //[1,2]
        arrayQueue.enqueue(3); //[1,2,3]
        arrayQueue.enqueue(4); //[1,2,3,4]
        arrayQueue.enqueue(5); //[1,2,3,4,5]
        arrayQueue.enqueue(6); //[1,2,3,4,5,6]
        arrayQueue.enqueue(7); //[1,2,3,4,5,6,7]
        arrayQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        arrayQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        arrayQueue.enqueue(10); //resize will occur
        assertEquals(10, arrayQueue.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, arrayQueue.peek().intValue());
            arrayQueue.dequeue();
        }
        assertEquals(0, arrayQueue.size());
        assertArrayEquals(new Integer[18], arrayQueue.getBackingArray());
    }
    @Test
    public void enqueueAndDequeue() {
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.dequeue();
        arrayQueue.enqueue(5);
        Integer[] expected = {null, 2, 3, 4, 5, null, null, null, null};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(4, arrayQueue.size());
    }
    @Test
    public void complexEnqueueAndDequeue() {
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.enqueue(5);
        arrayQueue.enqueue(6);
        arrayQueue.dequeue();
        arrayQueue.enqueue(7);
        arrayQueue.enqueue(8);
        arrayQueue.enqueue(9);
        arrayQueue.enqueue(10);
        Integer[] expected = {10,2,3,4,5,6, 7, 8, 9};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(9, arrayQueue.size());
    }
    @Test
    public void moreEnqueueAndDequeue() {
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.enqueue(5);
        arrayQueue.enqueue(6);
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue(7);
        arrayQueue.enqueue(8);
        arrayQueue.enqueue(9);
        arrayQueue.enqueue(10);
        arrayQueue.enqueue(11);
        Integer[] expected = {10,11,3,4,5,6,7,8,9};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(9, arrayQueue.size());
    }
    @Test
    public void enqueueAndDequeueResize() {
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.enqueue(5);
        arrayQueue.enqueue(6);
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue(7);
        arrayQueue.enqueue(8);
        arrayQueue.enqueue(9);
        arrayQueue.enqueue(10);
        arrayQueue.enqueue(11);
        arrayQueue.enqueue(12);
        arrayQueue.enqueue(13);
        Integer[] expected = {3,4,5,6,7,8,9,10,11,12,13, null, null, null, null, null, null, null}; //[3,4,5,6,7,8,9,10,11,12,13]
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(11, arrayQueue.size());
    }
    @Test
    public void enqueueAndDequeueFrontNotZero() {
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.enqueue(5);
        arrayQueue.enqueue(6);
        arrayQueue.enqueue(7);
        arrayQueue.enqueue(8);
        arrayQueue.enqueue(9);
        for (int i = 1; i <= 8; i++) {
            arrayQueue.dequeue();
        }
        Integer[] expected = {null, null, null, null, null, null, null, null, 9};
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(1, arrayQueue.size());
        arrayQueue.dequeue();
        Integer[] nullArray = new Integer[9];
        assertArrayEquals(nullArray, arrayQueue.getBackingArray());
        assertEquals(0, arrayQueue.size());
        arrayQueue.enqueue(10);
        arrayQueue.enqueue(11);
        Integer[] reAdjusted = {10, 11, null, null, null, null, null, null, null};
        assertArrayEquals(reAdjusted, arrayQueue.getBackingArray());
        assertEquals(2, arrayQueue.size());
    }
    @Test
    public void enqueueAndDequeueSizeOne() {
        arrayQueue.enqueue(5);
        assertEquals(1, arrayQueue.size());
        Integer[] expected = new Integer[9];
        expected[0] = 5;
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        arrayQueue.dequeue();
        assertEquals(0, arrayQueue.size());
        assertArrayEquals(new Integer[9], arrayQueue.getBackingArray());
    }

}
