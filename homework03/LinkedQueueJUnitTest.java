import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class LinkedQueueJUnitTest {
    private LinkedQueue<Integer> linkedQueue;
    @Before
    public void setup() {
        linkedQueue = new LinkedQueue<Integer>();
    }
    @Test
    public void testInitialization() {
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
    }
    @Test(expected = IllegalArgumentException.class)
    public void enqueueNullData() {
        linkedQueue.enqueue(null);
    }
    @Test
    public void enqueueSizeOne() {
        linkedQueue.enqueue(7);
        assertEquals(1, linkedQueue.size());
        assertEquals(7, linkedQueue.getHead().getData().intValue());
        assertNull(linkedQueue.getHead().getNext());
    }
    @Test
    public void basicEnqueueTest() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, linkedQueue.size());
        LinkedNode<Integer> current = linkedQueue.getHead();
        for (int i = 0; i < linkedQueue.size(); i++) {
            assertEquals(i + 1, current.getData().intValue());
            current = current.getNext();
        }
        assertEquals(5, linkedQueue.getTail().getData().intValue());
        assertEquals(1, linkedQueue.getHead().getData().intValue());
    }


    @Test(expected = NoSuchElementException.class)
    public void dequeueFromEmpty() {
        linkedQueue.dequeue();
    }
    @Test
    public void basicDequeueTest() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, linkedQueue.size());
        for (int i = 1; i <=5; i++) {
            assertEquals(i, linkedQueue.dequeue().intValue());
        }
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
        assertNull(linkedQueue.getTail());

    }
    @Test
    public void dequeueTestTwo() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        linkedQueue.enqueue(6); //[1,2,3,4,5,6]
        linkedQueue.enqueue(7); //[1,2,3,4,5,6,7]
        linkedQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        linkedQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        linkedQueue.enqueue(10); //[1,2,3,4,5,6,7,8,9,10]
        assertEquals(10, linkedQueue.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, linkedQueue.dequeue().intValue());
        }
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
        assertNull(linkedQueue.getTail());
    }
    @Test
    public void dequeueFromSizeOne() {
        linkedQueue.enqueue(36);
        assertEquals(36, linkedQueue.dequeue().intValue());
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
        assertNull(linkedQueue.getTail());
    }
    @Test (expected = NoSuchElementException.class)
    public void peekFromEmpty() {
        linkedQueue.peek();
    }
    @Test
    public void basicPeekTest() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, linkedQueue.size());
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, linkedQueue.getHead().getData().intValue());
            assertEquals(i, linkedQueue.peek().intValue());
            assertEquals(i, linkedQueue.dequeue().intValue());
        }
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
        assertNull(linkedQueue.getTail());
    }
    @Test
    public void peekTestTwo() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        linkedQueue.enqueue(6); //[1,2,3,4,5,6]
        linkedQueue.enqueue(7); //[1,2,3,4,5,6,7]
        linkedQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        linkedQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        linkedQueue.enqueue(10); //resize will occur
        assertEquals(10, linkedQueue.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, linkedQueue.getHead().getData().intValue());
            assertEquals(i, linkedQueue.peek().intValue());
            assertEquals(i, linkedQueue.dequeue().intValue());
        }
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
        assertNull(linkedQueue.getTail());
    }
    @Test
    public void getEmptySize() {
        assertEquals(0, linkedQueue.size());
    }
    @Test
    public void getSize() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        assertEquals(5, linkedQueue.size());
    }
    @Test
    public void getSizeTwo() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        linkedQueue.enqueue(6); //[1,2,3,4,5,6]
        linkedQueue.enqueue(7); //[1,2,3,4,5,6,7]
        linkedQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        linkedQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        linkedQueue.enqueue(10); //resize will occur
        assertEquals(10, linkedQueue.size());
    }
    @Test
    public void peekAndDequeue() {
        linkedQueue.enqueue(1); //[1]
        linkedQueue.enqueue(2); //[1,2]
        linkedQueue.enqueue(3); //[1,2,3]
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.enqueue(5); //[1,2,3,4,5]
        linkedQueue.enqueue(6); //[1,2,3,4,5,6]
        linkedQueue.enqueue(7); //[1,2,3,4,5,6,7]
        linkedQueue.enqueue(8); //[1,2,3,4,5,6,7,8]
        linkedQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        linkedQueue.enqueue(10); //resize will occur
        assertEquals(10, linkedQueue.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, linkedQueue.getHead().getData().intValue());
            assertEquals(i, linkedQueue.peek().intValue());
            linkedQueue.dequeue();
        }
        assertEquals(0, linkedQueue.size());
    }
    @Test
    public void enqueueAndDequeue() {
        linkedQueue.enqueue(1);
        linkedQueue.enqueue(2);
        linkedQueue.enqueue(3);
        linkedQueue.enqueue(4); //[1,2,3,4]
        linkedQueue.dequeue(); //[2,3,4]
        assertEquals(2, linkedQueue.getHead().getData().intValue());
        assertEquals(3, linkedQueue.size());
        linkedQueue.enqueue(5); //[2,3,4,5]
        assertEquals(4, linkedQueue.size());
        assertEquals(5, linkedQueue.getTail().getData().intValue());
    }
    @Test
    public void complexEnqueueAndDequeue() {
        linkedQueue.enqueue(1);
        linkedQueue.enqueue(2);
        linkedQueue.enqueue(3);
        linkedQueue.enqueue(4);
        linkedQueue.enqueue(5);
        linkedQueue.enqueue(6); //[1,2,3,4,5,6]
        linkedQueue.dequeue(); //[2,3,4,5,6]
        assertEquals(2, linkedQueue.getHead().getData().intValue());
        linkedQueue.enqueue(7);
        linkedQueue.enqueue(8);
        linkedQueue.enqueue(9);
        linkedQueue.enqueue(10); //[2,3,4,5,6,7,8,9,10]
        assertEquals(9, linkedQueue.size());
        assertEquals(10, linkedQueue.getTail().getData().intValue());
        assertEquals(3, linkedQueue.getHead().getNext().getData().intValue());
    }
    @Test
    public void moreEnqueueAndDequeue() {
        linkedQueue.enqueue(1);
        linkedQueue.enqueue(2);
        linkedQueue.enqueue(3);
        linkedQueue.enqueue(4);
        linkedQueue.enqueue(5);
        linkedQueue.enqueue(6); //[1,2,3,4,5,6]
        linkedQueue.dequeue();
        linkedQueue.dequeue(); //[3,4,5,6]
        linkedQueue.enqueue(7);
        linkedQueue.enqueue(8);
        linkedQueue.enqueue(9);
        linkedQueue.enqueue(10);
        linkedQueue.enqueue(11); //[3,4,5,6,7,8,9,10,11]
        assertEquals(9, linkedQueue.size());
        assertEquals(11, linkedQueue.getTail().getData().intValue());
        assertEquals(3, linkedQueue.getHead().getData().intValue());
        assertEquals(4, linkedQueue.getHead().getNext().getData().intValue());
    }
    @Test
    public void enqueueAndDequeueFrontNotZero() {
        linkedQueue.enqueue(1);
        linkedQueue.enqueue(2);
        linkedQueue.enqueue(3);
        linkedQueue.enqueue(4);
        linkedQueue.enqueue(5);
        linkedQueue.enqueue(6);
        linkedQueue.enqueue(7);
        linkedQueue.enqueue(8);
        linkedQueue.enqueue(9); //[1,2,3,4,5,6,7,8,9]
        for (int i = 1; i <= 8; i++) {
            linkedQueue.dequeue();
        }
        assertEquals(1, linkedQueue.size());
        assertEquals(9, linkedQueue.getHead().getData().intValue());
        assertEquals(9, linkedQueue.getTail().getData().intValue());
        linkedQueue.dequeue();
        assertEquals(0, linkedQueue.size());
        linkedQueue.enqueue(10);
        linkedQueue.enqueue(11);
        assertEquals(2, linkedQueue.size());
        assertEquals(10, linkedQueue.getHead().getData().intValue());
        assertEquals(11, linkedQueue.getHead().getNext().getData().intValue());
    }
    @Test
    public void enqueueAndDequeueSizeOne() {
        linkedQueue.enqueue(5);
        assertEquals(1, linkedQueue.size());
        assertEquals(5, linkedQueue.getHead().getData().intValue());
        assertEquals(5, linkedQueue.getTail().getData().intValue());
        linkedQueue.dequeue();
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getTail());
        assertNull(linkedQueue.getHead());
    }
}
