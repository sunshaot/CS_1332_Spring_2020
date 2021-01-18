import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class KatreJUnit03 {
    private ArrayQueue<String> arrayQueue;
    private LinkedQueue<String> linkedQueue;

    private ArrayStack<String> arrayStack;
    private LinkedStack<String> linkedStack;

    // Exceptions

    @Before
    public void setup() {
        arrayQueue = new ArrayQueue<>();
        linkedQueue = new LinkedQueue<>();

        arrayStack = new ArrayStack<>();
        linkedStack = new LinkedStack<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayQueueAddNull() {
        arrayQueue.enqueue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkedQueueAddNull() {
        linkedQueue.enqueue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayStackAddNull() {
        arrayStack.push(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkedStackAddNull() {
        linkedStack.push(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayQueueEmptyDequeue() {
        arrayQueue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedQueueEmptyDequeue() {
        linkedQueue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayStackEmptyPop() {
        arrayStack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedStackEmptyPop() {
        linkedStack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayQueueEmptyPeek() {
        arrayQueue.peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedQueueEmptyPeek() {
        linkedQueue.peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayStackEmptyPeek() {
        arrayStack.peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedStackEmptyPeek() {
        linkedStack.peek();
    }

    // ArrayQueue

    @Test
    public void testArrayQueueMaxCapacity() {
        for (int i = 0; i < ArrayQueue.INITIAL_CAPACITY; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(ArrayQueue.INITIAL_CAPACITY, arrayQueue.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        for (int i = 0; i < ArrayQueue.INITIAL_CAPACITY; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueResize() {
        for (int i = 0; i < 10; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(10, arrayQueue.size());

        Object[] expected = new Object[2 * ArrayQueue.INITIAL_CAPACITY];
        for (int i = 0; i < 10; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueCircularArray() {
        for (int i = 0; i < 5; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(5, arrayQueue.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        for (int i = 0; i < 5; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());

        for (int i = 0; i < 5; i++) {
            assertEquals(i + "a", arrayQueue.dequeue());
        }
        assertEquals(0, arrayQueue.size());

        for (int i = 0; i < 5; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(5, arrayQueue.size());

        expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[0] = "4a";
        expected[5] = "0a";
        expected[6] = "1a";
        expected[7] = "2a";
        expected[8] = "3a";
        assertArrayEquals(expected, arrayQueue.getBackingArray());

        for (int i = 0; i < 5; i++) {
            arrayQueue.dequeue();
        }
        assertEquals(0, arrayQueue.size());
    }

    @Test
    public void testArrayQueueResizeCircularArray() {
        for (int i = 0; i < 5; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(5, arrayQueue.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        for (int i = 0; i < 5; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());

        for (int i = 0; i < 5; i++) {
            assertEquals(i + "a", arrayQueue.dequeue());
        }
        assertEquals(0, arrayQueue.size());

        for (int i = 0; i < 10; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(10, arrayQueue.size());

        expected = new Object[2 * ArrayQueue.INITIAL_CAPACITY];
        for (int i = 0; i < 10; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());

        for (int i = 0; i < 10; i++) {
            assertEquals(i + "a", arrayQueue.peek());
            arrayQueue.dequeue();
        }
        assertEquals(0, arrayQueue.size());
    }

    @Test
    public void testArrayQueueRemovePeek() {
        for (int i = 0; i < 5; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(5, arrayQueue.size());

        for (int i = 0; i < 5; i++) {
            assertEquals(i + "a", arrayQueue.peek());
            arrayQueue.dequeue();
        }
        assertEquals(0, arrayQueue.size());
    }

    @Test
    public void testArrayQueueResizeRemovePeek() {
        for (int i = 0; i < 10; i++) {
            arrayQueue.enqueue(i + "a");
        }
        assertEquals(10, arrayQueue.size());

        for (int i = 0; i < 10; i++) {
            assertEquals(i + "a", arrayQueue.peek());
            arrayQueue.dequeue();
        }
        assertEquals(0, arrayQueue.size());
    }

    // LinkedQueue

    @Test
    public void testLinkedQueueAddFirst() {
        linkedQueue.enqueue("0a");
        assertEquals("0a", linkedQueue.getHead().getData());
        assertEquals("0a", linkedQueue.getTail().getData());
        assertEquals(1, linkedQueue.size());
    }

    @Test
    public void testLinkedQueueAddSecond() {
        linkedQueue.enqueue("0a");
        linkedQueue.enqueue("1a");
        assertEquals("0a", linkedQueue.getHead().getData());
        assertEquals("1a", linkedQueue.getTail().getData());
        assertEquals(2, linkedQueue.size());
    }

    @Test
    public void testLinkedQueueAddThird() {
        linkedQueue.enqueue("0a");
        linkedQueue.enqueue("1a");
        linkedQueue.enqueue("2a");
        assertEquals("0a", linkedQueue.getHead().getData());
        assertEquals("2a", linkedQueue.getTail().getData());
        assertEquals(3, linkedQueue.size());
    }

    @Test
    public void testLinkedQueueRemoveLast() {
        linkedQueue.enqueue("0a");
        linkedQueue.dequeue();
        assertNull(linkedQueue.getHead());
        assertNull(linkedQueue.getTail());
        assertEquals(0, linkedQueue.size());
    }

    @Test
    public void testLinkedQueueRemovePeek() {
        for (int i = 0; i < 5; i++) {
            linkedQueue.enqueue(i + "a");
        }
        assertEquals(5, linkedQueue.size());

        for (int i = 0; i < 5; i++) {
            assertEquals(i + "a", linkedQueue.peek());
            linkedQueue.dequeue();
        }
        assertEquals(0, linkedQueue.size());
    }

    // ArrayStack

    @Test
    public void testArrayStackMaxCapacity() {
        for (int i = 0; i < ArrayStack.INITIAL_CAPACITY; i++) {
            arrayStack.push(i + "a");
        }
        assertEquals(ArrayStack.INITIAL_CAPACITY, arrayStack.size());

        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        for (int i = 0; i < ArrayStack.INITIAL_CAPACITY; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackResize() {
        for (int i = 0; i < 10; i++) {
            arrayStack.push(i + "a");
        }
        assertEquals(10, arrayStack.size());

        Object[] expected = new Object[2 * ArrayStack.INITIAL_CAPACITY];
        for (int i = 0; i < 10; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackRemovePeek() {
        for (int i = 0; i < 5; i++) {
            arrayStack.push(i + "a");
        }
        assertEquals(5, arrayStack.size());

        for (int i = 4; i > -1; i--) {
            assertEquals(i + "a", arrayStack.peek());
            arrayStack.pop();
        }
        assertEquals(0, arrayStack.size());
    }

    @Test
    public void testArrayStackResizeRemovePeek() {
        for (int i = 0; i < 10; i++) {
            arrayStack.push(i + "a");
        }
        assertEquals(10, arrayStack.size());

        for (int i = 9; i > -1; i--) {
            assertEquals(i + "a", arrayStack.peek());
            arrayStack.pop();
        }
        assertEquals(0, arrayStack.size());
    }

    // LinkedStack

    @Test
    public void testLinkedStackAddFirst() {
        linkedStack.push("0a");
        assertEquals("0a", linkedStack.getHead().getData());
        assertEquals(1, linkedStack.size());
    }

    @Test
    public void testLinkedStackAddSecond() {
        linkedStack.push("0a");
        linkedStack.push("1a");
        assertEquals("1a", linkedStack.getHead().getData());
        assertEquals(2, linkedStack.size());
    }

    @Test
    public void testLinkedStackRemoveLast() {
        linkedStack.push("0a");
        linkedStack.pop();
        assertNull(linkedQueue.getHead());
        assertEquals(0, linkedQueue.size());
    }

    @Test
    public void testLinkedStackRemovePeek() {
        for (int i = 0; i < 5; i++) {
            linkedStack.push(i + "a");
        }
        assertEquals(5, linkedStack.size());

        for (int i = 4; i > -1; i--) {
            assertEquals(i + "a", linkedStack.peek());
            linkedStack.pop();
        }
        assertEquals(0, linkedStack.size());
    }
}
