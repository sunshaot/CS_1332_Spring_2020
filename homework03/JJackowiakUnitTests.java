import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for ArrayQueue, LinkedQueue, ArrayStack, LinkedStack
 * Tests for exceptions, resizing and moving the front to the right position in ArrayQueue
 *
 * @author Jakub Jackowiak
 * @version 1.0
 */

public class JJackowiakUnitTests {
    private static final int TIMEOUT = 200;
    private ArrayQueue<String> arrayQueue;
    private LinkedQueue<String> linkedQueue;
    private ArrayStack<String> arrayStack;
    private LinkedStack<String> linkedStack;

    @Before
    public void setup() {
        arrayQueue = new ArrayQueue<>();
        linkedQueue = new LinkedQueue<>();
        linkedStack = new LinkedStack<>();
        arrayStack = new ArrayStack<>();
    }

    //ArrayQueue tests

    @Test(timeout = TIMEOUT)
    public void testResizingArrayQueue() {
        String[] expected = new String[9];

        for (int i = 0; i < 9; i++) {
            expected[i] = Integer.toString(i);
            arrayQueue.enqueue(Integer.toString(i));
        }

        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals("0", arrayQueue.peek());


        assertEquals("0", arrayQueue.dequeue());
        assertEquals("1", arrayQueue.dequeue());
        assertEquals("2", arrayQueue.dequeue());
        expected[0] = null;
        expected[1] = null;
        expected[2] = null;

        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(6, arrayQueue.size());
        assertEquals("3", arrayQueue.peek());


        arrayQueue.enqueue("9");
        arrayQueue.enqueue("10");
        arrayQueue.enqueue("11");
        expected[0] = "9";
        expected[1] = "10";
        expected[2] = "11";

        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals("3", arrayQueue.peek());


        String[] expected_after_resizing = new String[18];
        for (int i = 0; i < 12; i++) {
            expected_after_resizing[i] = Integer.toString(3 + i);
        }

        arrayQueue.enqueue("12");
        arrayQueue.enqueue("13");
        arrayQueue.enqueue("14");
        assertArrayEquals(expected_after_resizing, arrayQueue.getBackingArray());
        assertEquals(12, arrayQueue.size());
        assertEquals("3", arrayQueue.peek());

        assertEquals("3", arrayQueue.dequeue());
        assertEquals("4", arrayQueue.dequeue());
        assertEquals("5", arrayQueue.peek());


    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayQueueEnqueueNullData() {
        arrayQueue.enqueue("0");
        arrayQueue.enqueue(null);

    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueueMoveFront() {
        for (int i = 0; i < 9; i++) {
            arrayQueue.enqueue(Integer.toString(i));
        }
        for (int i = 0; i < 8; i++) {
            arrayQueue.dequeue();
            arrayQueue.enqueue(Integer.toString(i) + "new");
        }
        assertEquals("8", arrayQueue.dequeue());
        assertEquals("0new", arrayQueue.dequeue());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayQueueDequeueFromEmpty() {
        arrayQueue.enqueue("0");
        arrayQueue.enqueue("1");
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueuePeek() {
        arrayQueue.enqueue("0");
        arrayQueue.enqueue("1");
        assertEquals("0", arrayQueue.peek());

        arrayQueue.enqueue("2");
        arrayQueue.enqueue("3");
        arrayQueue.enqueue("4");
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue("5");
        arrayQueue.enqueue("6");
        arrayQueue.enqueue("7");
        assertEquals("3", arrayQueue.peek());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayQueuePeekEmpty() {
        arrayQueue.enqueue("1");
        arrayQueue.enqueue("2");
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.peek();

    }

    //LinkedQueue Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedQueueEnqueueNull() {
        linkedQueue.enqueue("1");
        linkedQueue.enqueue(null);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedQueueEnqueue() {
        linkedQueue.enqueue("0");
        linkedQueue.enqueue("1");
        linkedQueue.enqueue("2");
        linkedQueue.enqueue("3");
        linkedQueue.enqueue("4");
        assertEquals(5, linkedQueue.size());
        assertEquals("4", linkedQueue.getTail().getData());
        assertEquals("0", linkedQueue.getHead().getData());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedQueueDequeueFromEmpty() {
        linkedQueue.enqueue("0");
        assertEquals("0", linkedQueue.dequeue());
        linkedQueue.dequeue();
    }

    @Test(timeout = TIMEOUT)
    public void testLiknedQueueDequeue() {
        linkedQueue.enqueue("0");
        linkedQueue.enqueue("1");
        linkedQueue.enqueue("2");
        linkedQueue.enqueue("3");
        linkedQueue.enqueue("4");
        assertEquals("0", linkedQueue.dequeue());
        assertEquals("1", linkedQueue.dequeue());
        linkedQueue.enqueue("5");
        linkedQueue.enqueue("6");
        linkedQueue.enqueue("7");
        assertEquals("2", linkedQueue.dequeue());
        assertEquals("3", linkedQueue.dequeue());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedQueuePeekFromEmpty() {
        linkedQueue.enqueue("1");
        linkedQueue.dequeue();
        linkedQueue.peek();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedQueuePeek() {
        linkedQueue.enqueue("0");
        linkedQueue.enqueue("1");
        linkedQueue.enqueue("2");
        linkedQueue.enqueue("3");
        linkedQueue.dequeue();
        assertEquals("1", linkedQueue.peek());
        linkedQueue.dequeue();
        assertEquals("2", linkedQueue.peek());
        linkedQueue.dequeue();
        assertEquals("3", linkedQueue.peek());
    }

    //Test ArrayStack
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayStackPushNull() {
        arrayStack.push(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayStackPushResizing() {
        String[] expected = new String[9];
        String[] expectedAfterResizing = new String[18];
        for (int i = 0; i < 9; i++) {
            arrayStack.push(Integer.toString(i));
            expected[i] = Integer.toString(i);
            expectedAfterResizing[i] = Integer.toString(i);
        }
        assertEquals(9, arrayStack.size());
        assertArrayEquals(expected, arrayStack.getBackingArray());

        arrayStack.push("9");
        arrayStack.push("10");
        arrayStack.push("11");
        expectedAfterResizing[9] = "9";
        expectedAfterResizing[10] = "10";
        expectedAfterResizing[11] = "11";
        assertArrayEquals(expectedAfterResizing, arrayStack.getBackingArray());
        assertEquals(12, arrayStack.size());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayStackPopFromEmpty() {
        arrayStack.pop();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayStackPop() {
        String[] expected = new String[9];
        for (int i = 0; i < 9; i++) {
            arrayStack.push(Integer.toString(i));
            expected[i] = Integer.toString(i);
        }
        assertEquals(9, arrayStack.size());
        expected[7] = null;
        expected[8] = null;
        assertEquals("8", arrayStack.pop());
        assertEquals("7", arrayStack.pop());
        assertEquals(7, arrayStack.size());
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayStackPeekEmpty() {
        arrayStack.push("1");
        arrayStack.pop();
        arrayStack.peek();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayStackPeek() {
        arrayStack.push("0");
        arrayStack.push("1");
        arrayStack.push("2");
        assertEquals("2", arrayStack.peek());
        arrayStack.pop();
        assertEquals("1", arrayStack.peek());
    }


    //Test LinkedStack
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedStackPushNull() {
        linkedStack.push(null);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedStackPush() {
        linkedStack.push("0");
        assertEquals(1, linkedStack.size());
        linkedStack.push("1");
        linkedStack.push("2");
        assertEquals(3, linkedStack.size());
        assertEquals("2", linkedStack.getHead().getData());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedStackPopEmpty() {
        linkedStack.push("2");
        linkedStack.pop();
        linkedStack.pop();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedStackPop() {
        linkedStack.push("0");
        linkedStack.push("1");
        linkedStack.push("2");
        linkedStack.push("3");
        assertEquals("3", linkedStack.pop());
        assertEquals(3, linkedStack.size());
        assertEquals("2", linkedStack.pop());
        assertEquals(2, linkedStack.size());
        assertEquals("1", linkedStack.getHead().getData());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedStackPeekEmpty() {
        linkedStack.push("0");
        linkedStack.pop();
        linkedStack.peek();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedStackPeek() {
        linkedStack.push("0");
        linkedStack.push("1");
        linkedStack.push("2");
        assertEquals("2", linkedStack.peek());
        linkedStack.pop();
        assertEquals("1", linkedStack.peek());
        linkedStack.pop();
        assertEquals("0", linkedStack.peek());

    }

}