import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.NoSuchElementException;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit03 {

    private static final int TIMEOUT = 200;

    public static class MyQueueStudentTest extends QueueStudentTest {
    }

    public static class MyStackStudentTest extends StackStudentTest {
    }

    /*
    public static class MyKatreJUnit03 extends KatreJUnit03 {
    }

    public static class MyJJackowiakUnitTests extends JJackowiakUnitTests {
    }

    public static class MyKarthikArrayStackJUnit03 extends KarthikArrayStackJUnit03 {
    }

    public static class MyKarthikArrayQueueJUnit03 extends KarthikArrayQueueJUnit03 {
    }

    public static class MyKarthikLinkedStackJUnit03 extends KarthikLinkedStackJUnit03 {
    }

    public static class MyKarthikLinkedQueueJUnit03 extends KarthikLinkedQueueJUnit03 {
    }
    */

    public static class TestArrayQueue {
        private ArrayQueue<String> arrayQueue;

        @Before
        public void setup() {
            arrayQueue = new ArrayQueue<>();
        }

        /**
         * Creates a null filled string array of INITIAL_CAPACITY size.
         * @return null filled array
         */
        private String[] getNullExpected() {
            return getNullExpected(ArrayQueue.INITIAL_CAPACITY);
        }

        /**
         * Creates a null filled string array of the given size.
         * @param size size of array to null fill
         * @return null filled array
         */
        private String[] getNullExpected(int size) {
            return new String[size];
        }

        @Test(timeout = TIMEOUT)
        public void testConstruction() {
            assertEquals(0, arrayQueue.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueOne() {
            String[] expected = getNullExpected();
            expected[0] = "1a";
            arrayQueue.enqueue(expected[0]);
            assertEquals(1, arrayQueue.size());
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
        public void testEnqueueNull() {
            arrayQueue.enqueue(null);
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueNullVerifyNoChange() {
            try {
                arrayQueue.enqueue(null);
            } catch (Exception ex) {
            }
            assertEquals(0, arrayQueue.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testEnquueExpand() {
            String[] expected = getNullExpected(ArrayQueue.INITIAL_CAPACITY * 2);
            for (int i = 0; i < ArrayQueue.INITIAL_CAPACITY + 1; ++i) {
                expected[i] = "a" + i;
                arrayQueue.enqueue(expected[i]);
            }
            assertEquals(ArrayQueue.INITIAL_CAPACITY + 1, arrayQueue.size());
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueTwiceDequeueOnce() {
            String[] expected = getNullExpected();
            expected[1] = "2a";
            arrayQueue.enqueue("1a");
            arrayQueue.enqueue(expected[1]);
            assertEquals("1a", arrayQueue.dequeue());
            assertEquals(1, arrayQueue.size());
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueWrap() {
            String[] expected = getNullExpected();
            arrayQueue.enqueue("aa");
            expected[1] = "a" + ArrayQueue.INITIAL_CAPACITY;
            for (int i = 0; i < ArrayQueue.INITIAL_CAPACITY + 1; ++i) {
                arrayQueue.enqueue("a" + i);
                arrayQueue.dequeue();
            }
            assertEquals(1, arrayQueue.size());
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testDequeueEmpty() {
            arrayQueue.dequeue();
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueEmptyVerifyNoChange() {
            try {
                arrayQueue.dequeue();
            } catch (Exception ex) {
            }
            assertEquals(0, arrayQueue.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testPeekEmpty() {
            arrayQueue.peek();
        }

        @Test(timeout = TIMEOUT)
        public void testPeekSameAfterMultipleEnqueues() {
            arrayQueue.enqueue("aa");
            for (int i = 0; i < 5; ++i) {
                assertEquals("aa", arrayQueue.peek());
                arrayQueue.enqueue("a" + i);
            }
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueResizeResetsFront() {
            // Special check to be sure that when the array is grown that it shifts front to zero
            String[] expected = getNullExpected(ArrayQueue.INITIAL_CAPACITY * 2);
            arrayQueue.enqueue("aa");
            arrayQueue.dequeue();
            for (int i = 0; i < ArrayQueue.INITIAL_CAPACITY + 1; ++i) {
                expected[i] = "a" + i;
                arrayQueue.enqueue(expected[i]);
            }
            assertEquals(ArrayQueue.INITIAL_CAPACITY + 1, arrayQueue.size());
            assertArrayEquals(expected, arrayQueue.getBackingArray());
        }
    }

    public static class TestArrayStack {
        private ArrayStack<String> arrayStack;

        @Before
        public void setup() {
            arrayStack = new ArrayStack<>();
        }

        /**
         * Creates a null filled string array of INITIAL_CAPACITY size.
         * @return null filled array
         */
        private String[] getNullExpected() {
            return getNullExpected(ArrayStack.INITIAL_CAPACITY);
        }

        /**
         * Creates a null filled string array of the given size.
         * @param size size of array to null fill
         * @return null filled array
         */
        private String[] getNullExpected(int size) {
            return new String[size];
        }

        @Test(timeout = TIMEOUT)
        public void testConstruction() {
            assertEquals(0, arrayStack.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPushFirst() {
            String[] expected = getNullExpected();
            expected[0] = "a1";
            arrayStack.push(expected[0]);
            assertEquals(1, arrayStack.size());
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
        public void testPushNull() {
            arrayStack.push(null);
        }

        @Test(timeout = TIMEOUT)
        public void testPushNullVerifyNoChange() {
            try {
                arrayStack.push(null);
            } catch (Exception ex) {
            }
            assertEquals(0, arrayStack.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPushGrow() {
            int expectedSize = ArrayStack.INITIAL_CAPACITY + 1;
            String[] expected = getNullExpected(ArrayStack.INITIAL_CAPACITY * 2);
            for (int i = 0; i < expectedSize; ++i) {
                expected[i] = "a" + i;
                arrayStack.push(expected[i]);
            }
            assertEquals(expectedSize, arrayStack.size());
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPushPop() {
            String expectedData = "a1";
            arrayStack.push(expectedData);
            assertEquals(expectedData, arrayStack.pop());
            assertEquals(0, arrayStack.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPushTwicePop() {
            String expectedData = "a1";
            String[] expected = getNullExpected();
            expected[0] = "aa";
            arrayStack.push(expected[0]);
            arrayStack.push(expectedData);
            assertEquals(expectedData, arrayStack.pop());
            assertEquals(1, arrayStack.size());
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testPopEmpty() {
            arrayStack.pop();
        }

        @Test(timeout = TIMEOUT)
        public void testPopEmptyVerifyNoChange() {
            try {
                arrayStack.pop();
            } catch (Exception ex) {
            }
            assertEquals(0, arrayStack.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPeek() {
            for (int i = 0; i < 10; ++i) {
                String expected = "a" + i;
                arrayStack.push(expected);
                assertEquals(expected, arrayStack.peek());
            }
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testPeekEmpty() {
            arrayStack.peek();
        }

        @Test(timeout = TIMEOUT)
        public void testPeekEmptyVerifyNoChange() {
            try {
                arrayStack.peek();
            } catch (Exception ex) {
            }
            assertEquals(0, arrayStack.size());
            String[] expected = getNullExpected();
            assertArrayEquals(expected, arrayStack.getBackingArray());
        }
    }

    public static class TestLinkedQueue {
        private LinkedQueue<String> linkedQueue;

        @Before
        public void setup() {
            linkedQueue = new LinkedQueue<>();
        }


        @Test(timeout = TIMEOUT)
        public void testConstruction() {
            assertEquals(0, linkedQueue.size());
            assertNull(linkedQueue.getHead());
            assertNull(linkedQueue.getTail());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueOne() {
            String expected = "1a";
            linkedQueue.enqueue(expected);
            assertEquals(1, linkedQueue.size());
            LinkedNode<String> node = linkedQueue.getHead();
            assertEquals(expected, node.getData());
            assertNull(node.getNext());
            assertEquals(node, linkedQueue.getTail());
        }

        @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
        public void testEnqueueNull() {
            linkedQueue.enqueue(null);
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueNullVerifyNoChange() {
            try {
                linkedQueue.enqueue(null);
            } catch (Exception ex) {
            }
            assertEquals(0, linkedQueue.size());
            assertNull(linkedQueue.getHead());
            assertNull(linkedQueue.getTail());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueTwiceDequeueOnce() {
            String expected = "2a";
            linkedQueue.enqueue("1a");
            linkedQueue.enqueue(expected);
            assertEquals("1a", linkedQueue.dequeue());
            assertEquals(1, linkedQueue.size());
            LinkedNode<String> node = linkedQueue.getHead();
            assertEquals("2a", node.getData());
            assertNull(node.getNext());
            assertEquals(node, linkedQueue.getTail());
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testDequeueEmpty() {
            linkedQueue.dequeue();
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueEmptyVerifyNoChange() {
            try {
                linkedQueue.dequeue();
            } catch (Exception ex) {
            }
            assertEquals(0, linkedQueue.size());
            assertNull(linkedQueue.getHead());
            assertNull(linkedQueue.getTail());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueDequeue() {
            // specifically looking to be sure head and tail BOTH go null
            linkedQueue.enqueue("1a");
            linkedQueue.dequeue();
            assertEquals(0, linkedQueue.size());
            assertNull(linkedQueue.getHead());
            assertNull(linkedQueue.getTail());
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testPeekEmpty() {
            linkedQueue.peek();
        }

        @Test(timeout = TIMEOUT)
        public void testPeekSameAfterMultipleEnqueues() {
            linkedQueue.enqueue("aa");
            for (int i = 0; i < 5; ++i) {
                assertEquals("aa", linkedQueue.peek());
                linkedQueue.enqueue("a" + i);
            }
        }
    }

    public static class TestLinkedStack {
        private LinkedStack<String> linkedStack;

        @Before
        public void setup() {
            linkedStack = new LinkedStack<>();
        }


        @Test(timeout = TIMEOUT)
        public void testLinkedStackConstruction() {
            assertEquals(0, linkedStack.size());
            assertNull(linkedStack.getHead());
        }

        @Test(timeout = TIMEOUT)
        public void testPushFirst() {
            String expected = "a1";
            linkedStack.push(expected);
            assertEquals(1, linkedStack.size());
            LinkedNode<String> node = linkedStack.getHead();
            assertEquals(expected, node.getData());
            assertNull(node.getNext());
        }

        @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
        public void testPushNull() {
            linkedStack.push(null);
        }

        @Test(timeout = TIMEOUT)
        public void testPushNullVerifyNoChange() {
            try {
                linkedStack.push(null);
            } catch (Exception ex) {
            }
            assertEquals(0, linkedStack.size());
            assertNull(linkedStack.getHead());
        }

        @Test(timeout = TIMEOUT)
        public void testPushPop() {
            String expectedData = "a1";
            linkedStack.push(expectedData);
            assertEquals(expectedData, linkedStack.pop());
            assertEquals(0, linkedStack.size());
            assertNull(linkedStack.getHead());
        }

        @Test(timeout = TIMEOUT)
        public void testPushTwicePop() {
            String expectedData = "a1";
            String expected = "aa";
            linkedStack.push(expected);
            linkedStack.push(expectedData);
            assertEquals(expectedData, linkedStack.pop());
            assertEquals(1, linkedStack.size());
            LinkedNode<String> node = linkedStack.getHead();
            assertEquals(expected, node.getData());
            assertNull(node.getNext());
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testPopEmpty() {
            linkedStack.pop();
        }

        @Test(timeout = TIMEOUT)
        public void testPopEmptyVerifyNoChange() {
            try {
                linkedStack.pop();
            } catch (Exception ex) {
            }
            assertEquals(0, linkedStack.size());
            assertNull(linkedStack.getHead());
        }

        @Test(timeout = TIMEOUT)
        public void testPeek() {
            for (int i = 0; i < 10; ++i) {
                String expected = "a" + i;
                linkedStack.push(expected);
                assertEquals(expected, linkedStack.peek());
            }
        }

        @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
        public void testPeekEmpty() {
            linkedStack.peek();
        }

        @Test(timeout = TIMEOUT)
        public void testPeekEmptyVerifyNoChange() {
            try {
                linkedStack.peek();
            } catch (Exception ex) {
            }
            assertEquals(0, linkedStack.size());
            assertNull(linkedStack.getHead());
        }
    }
}
