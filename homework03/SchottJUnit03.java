import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * This set of JUnits assumes you have already passed all tests in the StudentTest
 * provided tests.
 *
 * If any tests do not work as expected, please let me know! Email me at tschott6@gatech.edu or
 * reply to my Piazza post.
 *
 *
 * TESTS INCLUDED:
 * Exception handling for every method that can throw an exception
 * Edge cases when size = 0
 * Edge cases when size = 1
 * Edge cases when size = 2
 * Edge cases when size = backingArray.length
 * Resizing
 * Other edge cases
 * Some fun tests at the end
 *
 *
 * NOTE:
 * Some of my tests rely on provided linkedArray() method
 * This method converts your linked list into an array
 *
 * I use linkedArray() as it allows easy comparison of the entire linked list
 * and it allows you to more easily visualize any issues. While debugging, you will
 * be able to see the array values for expected and actual to see what part of your
 * list is wrong if a test fails.
 *
 *
 * NOTE 2:
 * There are 2 inner classes that can be run separately or together. There is one for testing queues
 * and another for stacks. It should work with the given imports, but may require more.
 * Tested for IntelliJ.
 *
 *
 * @author Tyler Schott
 * @version 1.3
 */

@RunWith(SchottJUnit03.class)
@Suite.SuiteClasses({ SchottJUnit03.testQueue.class, SchottJUnit03.testStack.class, SchottJUnit03.PhatStacks.class })
public class SchottJUnit03 extends Suite {

    // Ignore this. Allows inner classes to function
    public SchottJUnit03(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    /**
     * Tests for ArrayQueue and LinkedQueue
     */
    public static class testQueue {
        protected static final int TIMEOUT = 200;
        protected ArrayQueue<String> array;
        protected LinkedQueue<String> linked;

        /**
         * Helper method for testing your linked queue
         * Turns your linked list into an array for easier testing and visualization
         *
         * @return your linked queue object as an array
         */
        private Object[] linkedArray() {
            Object[] linkedArray = new Object[linked.size()];
            LinkedNode curr = linked.getHead();
            for (int i = 0; i < linked.size(); i++) {
                linkedArray[i] = curr.getData();
                curr = curr.getNext();
            }
            return linkedArray;
        }

        @Before
        public void setup() {
            array = new ArrayQueue<String>();
            linked = new LinkedQueue<String>();
        }

// Exception handling ------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testEnqueueNull_A() {
            array.enqueue(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testEnqueueNull_L() {
            linked.enqueue(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testDequeueEmpty_A() {
            array.dequeue();
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testDequeueEmpty_L() {
            linked.dequeue();
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPeekEmpty_A() {
            array.peek();
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPeekEmpty_L() {
            linked.peek();
        }

// Tests When Size = 0 ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testEnqueueAtEmpty_A() {
            array.enqueue("a1");

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
            assertEquals(1, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueAtEmpty_L() {
            linked.enqueue("a1");

            assertEquals("a1", linked.getHead().getData());
            assertEquals("a1", linked.getTail().getData());
            assertEquals(null, linked.getHead().getNext());
            assertEquals(1, linked.size());
        }

// Tests When Size = 1 ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testEnqueueAt1_A() {
            array.enqueue("a1");
            array.enqueue("a2");

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            expected[1] = "a2";
            assertArrayEquals(expected, array.getBackingArray());
            assertEquals(2, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueAt1_L() {
            linked.enqueue("a1");
            linked.enqueue("a2");

            assertEquals("a1", linked.getHead().getData());
            assertEquals("a2", linked.getTail().getData());
            assertEquals(2, linked.size());

            Object[] expected = new Object[]{"a1", "a2"};
            Object[] actual = linkedArray();
            assertArrayEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueAt1_A() {
            array.enqueue("a1");
            String returned = array.dequeue();

            assertEquals("a1", returned);
            assertEquals(0, array.size());

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            assertArrayEquals(expected, array.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueAt1_L() {
            linked.enqueue("a1");
            String returned = linked.dequeue();

            assertEquals("a1", returned);
            assertEquals(0, array.size());

            assertEquals(null, linked.getHead());
            assertEquals(null, linked.getTail());
        }

        @Test(timeout = TIMEOUT)
        public void testPeekAt1_A() {
            array.enqueue("a1");
            String returned = array.peek();

            assertEquals("a1", returned);
            assertEquals(1, array.size());

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPeekAt1_L() {
            linked.enqueue("a1");
            String returned = linked.peek();

            assertEquals("a1", returned);
            assertEquals(1, linked.size());

            assertEquals("a1", linked.getHead().getData());
            assertEquals("a1", linked.getTail().getData());
        }

// Tests When Size = 2 ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testDequeueAt2_A() {
            array.enqueue("a1");
            array.enqueue("a2");
            String returned = array.dequeue();

            assertEquals("a1", returned);
            assertEquals(1, array.size());

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[1] = "a2";
            assertArrayEquals(expected, array.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueAt2_L() {
            linked.enqueue("a1");
            linked.enqueue("a2");
            String returned = linked.dequeue();

            assertEquals("a1", returned);
            assertEquals(1, linked.size());

            assertEquals("a2", linked.getHead().getData());
            assertEquals("a2", linked.getTail().getData());
            assertEquals(null, linked.getHead().getNext());
        }

// Resize / Backing Array Full Tests ---------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testEnqueueToResize_A() {
            array.enqueue("a1");
            array.enqueue("a2");
            array.enqueue("a3");
            array.enqueue("a4");
            array.enqueue("a5");
            array.enqueue("a6");
            array.enqueue("a7");
            array.enqueue("a8");
            array.enqueue("a9");

            Object[] expected = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);

            array.enqueue("a10");

            expected = new Object[] {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
                    null, null, null, null, null, null, null, null};
            actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
            assertEquals(10, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testEnqueueToResizeFromMiddle_A() {
            array.enqueue("a-1");
            array.enqueue("a0");
            array.dequeue();
            array.dequeue();
            array.enqueue("a1");
            array.enqueue("a2");
            array.enqueue("a3");
            array.enqueue("a4");
            array.enqueue("a5");
            array.enqueue("a6");
            array.enqueue("a7");
            array.enqueue("a8");
            array.enqueue("a9");


            Object[] expected = {"a8", "a9", "a1", "a2", "a3", "a4", "a5", "a6", "a7"};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);

            array.enqueue("a10");

            expected = new Object[] {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
                    null, null, null, null, null, null, null, null};
            actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
            assertEquals(10, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueAtMaxSize_A() {
            array.enqueue("a1");
            array.enqueue("a2");
            array.enqueue("a3");
            array.enqueue("a4");
            array.enqueue("a5");
            array.enqueue("a6");
            array.enqueue("a7");
            array.enqueue("a8");
            array.enqueue("a9");
            array.dequeue();


            Object[] expected = {null, "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testDequeueWithOnlyLast_A() {
            for (int i = 0; i < 8; i++) {
                array.enqueue("a1");
                array.dequeue();
            }

            array.enqueue("a1");
            Object[] expected = new Object[9];
            expected[8] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
            array.dequeue();

            array.enqueue("a1");
            expected = new Object[9];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
        }

        // Enqueues and dequeues 19 times
        @Test(timeout = TIMEOUT)
        public void testDequeueWithOnlyLastDouble_A() {
            for (int i = 0; i < 17; i++) {
                array.enqueue("a1");
                array.dequeue();
            }

            array.enqueue("a1");
            Object[] expected = new Object[9];
            expected[8] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
            array.dequeue();

            array.enqueue("a1");
            expected = new Object[9];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
        }

// Other Tests ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testMultiEnqueueAndDequeue_A() {
            array.enqueue("a1"); // {"a1"}
            array.enqueue("a2"); // {"a1", "a2"}
            array.enqueue("a3"); // {"a1", "a2", "a3"}
            array.dequeue(); // {null, "a2", "a3"}
            array.enqueue("a4"); // {null, "a2", "a3", "a4"}
            array.enqueue("a5"); // {null, "a2", "a3", "a4", "a5"}
            array.dequeue(); // {null, null, "a3", "a4", "a5"}
            array.dequeue(); // {null, null, null, "a4", "a5"}
            array.enqueue("a6"); // {null, null, null, "a4", "a5", "a6"}

            Object[] expected = new Object[]{null, null, null, "a4", "a5", "a6", null, null, null};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);

            assertEquals(3, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testMultiEnqueueAndDequeue_L() {
            linked.enqueue("a1"); // {"a1"}
            linked.enqueue("a2"); // {"a1", "a2"}
            linked.enqueue("a3"); // {"a1", "a2", "a3"}
            linked.dequeue(); // {"a2", "a3"}
            linked.enqueue("a4"); // {"a2", "a3", "a4"}
            linked.enqueue("a5"); // {"a2", "a3", "a4", "a5"}
            linked.dequeue(); // {"a3", "a4", "a5"}
            linked.dequeue(); // {"a4", "a5"}
            linked.enqueue("a6"); // {"a4", "a5", "a6"}

            Object[] expected = new Object[]{"a4", "a5", "a6"};
            Object[] actual = linkedArray();
            assertArrayEquals(expected, actual);

            assertEquals(3, linked.size());
            assertEquals("a4", linked.getHead().getData());
            assertEquals("a6", linked.getTail().getData());
        }

    }

    /**
     * Tests for ArrayStack and LinkedStack
     */
    public static class testStack {
        private static final int TIMEOUT = 200;
        private ArrayStack<String> array;
        private LinkedStack<String> linked;

        /**
         * Helper method for testing your linked stack
         * Turns your linked list into an array for easier testing and visualization
         *
         * @return your linked stack object as an array
         */
        private Object[] linkedArray() {
            Object[] linkedArray = new Object[linked.size()];
            LinkedNode curr = linked.getHead();
            for (int i = 0; i < linked.size(); i++) {
                linkedArray[i] = curr.getData();
                curr = curr.getNext();
            }
            return linkedArray;
        }

        @Before
        public void setup() {
            array = new ArrayStack<>();
            linked = new LinkedStack<>();
        }

// Exception handling ------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPushNull_A() {
            array.push(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPushNull_L() {
            linked.push(null);
        }


        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPopEmpty_A() {
            array.pop();
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPopEmpty_L() {
            linked.pop();
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPeekEmpty_A() {
            array.peek();
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPeekEmpty_L() {
            linked.peek();
        }

// Tests When Size = 0 ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testPushAtEmpty_A() {
            array.push("a1");

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
            assertEquals(1, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testPushAtEmpty_L() {
            linked.push("a1");

            assertEquals("a1", linked.getHead().getData());
            assertEquals(null, linked.getHead().getNext());
            assertEquals(1, linked.size());
        }

// Tests When Size = 1 ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testPushAt1_A() {
            array.push("a1");
            array.push("a2");

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            expected[1] = "a2";
            assertArrayEquals(expected, array.getBackingArray());
            assertEquals(2, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testPushAt1_L() {
            linked.push("a1");
            linked.push("a2");

            assertEquals("a2", linked.getHead().getData());
            assertEquals(2, linked.size());

            Object[] expected = new Object[]{"a2", "a1"};
            Object[] actual = linkedArray();
            assertArrayEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPopAt1_A() {
            array.push("a1");
            String returned = array.pop();

            assertEquals("a1", returned);
            assertEquals(0, array.size());

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            assertArrayEquals(expected, array.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPopAt1_L() {
            linked.push("a1");
            String returned = linked.pop();

            assertEquals("a1", returned);
            assertEquals(0, array.size());

            assertEquals(null, linked.getHead());
        }

        @Test(timeout = TIMEOUT)
        public void testPeekAt1_A() {
            array.push("a1");
            String returned = array.peek();

            assertEquals("a1", returned);
            assertEquals(1, array.size());

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPeekAt1_L() {
            linked.push("a1");
            String returned = linked.peek();

            assertEquals("a1", returned);
            assertEquals(1, linked.size());

            assertEquals("a1", linked.getHead().getData());
        }

// Tests When Size = 2 ----------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testPopAt2_A() {
            array.push("a1");
            array.push("a2");
            String returned = array.pop();

            assertEquals("a2", returned);
            assertEquals(1, array.size());

            Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
            expected[0] = "a1";
            assertArrayEquals(expected, array.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPopAt2_L() {
            linked.push("a1");
            linked.push("a2");
            String returned = linked.pop();

            assertEquals("a2", returned);
            assertEquals(1, linked.size());

            assertEquals("a1", linked.getHead().getData());
            assertEquals(null, linked.getHead().getNext());
        }

// Resize / Backing Array Full Tests ---------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testPushToResize_A() {
            array.push("a1");
            array.push("a2");
            array.push("a3");
            array.push("a4");
            array.push("a5");
            array.push("a6");
            array.push("a7");
            array.push("a8");
            array.push("a9");

            Object[] expected = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);

            array.push("a10");

            expected = new Object[] {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
                    null, null, null, null, null, null, null, null};
            actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
            assertEquals(10, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testPushToResizeAfterPops_A() {
            array.push("a1");
            array.push("a2");
            array.push("a3");
            array.push("a4");
            array.push("a5");
            array.push("a6");
            array.push("a7");
            array.push("a8");
            array.push("a9");
            array.pop();
            array.pop();
            array.push("a8");
            array.push("a9");

            Object[] expected = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);

            array.push("a10");

            expected = new Object[] {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
                    null, null, null, null, null, null, null, null};
            actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
            assertEquals(10, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testPopAtMaxSize_A() {
            array.push("a1");
            array.push("a2");
            array.push("a3");
            array.push("a4");
            array.push("a5");
            array.push("a6");
            array.push("a7");
            array.push("a8");
            array.push("a9");

            Object[] expected = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);

            array.pop();

            expected = new Object[] {"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", null};
            actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
            assertEquals(8, array.size());
        }

// Other Tests ---------------------------------------------------------------------------------------------------

        @Test(timeout = TIMEOUT)
        public void testMultiPushPop_A() {
            array.push("a1");
            array.push("a2");
            String returned = array.pop();
            assertEquals("a2", returned);
            array.push("a2");
            array.push("a3");
            array.push("a4");
            array.push("a5");
            returned = array.pop();
            assertEquals("a5", returned);
            returned = array.pop();
            assertEquals("a4", returned);
            array.push("a4");
            array.push("a5");
            array.push("a6");

            Object[] expected = new Object[] {"a1", "a2", "a3", "a4", "a5", "a6", null, null, null};
            Object[] actual = array.getBackingArray();
            assertArrayEquals(expected, actual);
            assertEquals(6, array.size());
        }

        @Test(timeout = TIMEOUT)
        public void testMultiPushPop_L() {
            linked.push("a1");
            linked.push("a2");
            String returned = linked.pop();
            assertEquals("a2", returned);
            linked.push("a2");
            linked.push("a3");
            linked.push("a4");
            linked.push("a5");
            returned = linked.pop();
            assertEquals("a5", returned);
            returned = linked.pop();
            assertEquals("a4", returned);
            linked.push("a4");
            linked.push("a5");
            linked.push("a6");

            Object[] expected = new Object[] {"a6", "a5", "a4", "a3", "a2", "a1"};
            Object[] actual = linkedArray();
            assertArrayEquals(expected, actual);
            assertEquals(6, linked.size());
        }
    }

    /**
     * Who doesn't love some Phat Stacks..! (and Queues)
     *
     * While these are just for fun, they should all work correctly. They can effectively
     * catch errors in your code.
     */
    public static class PhatStacks {
        protected static final int TIMEOUT = 200;
        protected ArrayQueue<String> arrayQ;
        protected LinkedQueue<String> linkedQ;
        protected ArrayStack<String> arrayS;
        protected LinkedStack<String> linkedS;

        /**
         * Helper method for testing your linked queue
         * Turns your linked list into an array for easier testing and visualization
         *
         * @return your linked queue object as an array
         */
        private Object[] linkedArrayQ() {
            Object[] linkedArray = new Object[linkedQ.size()];
            LinkedNode curr = linkedQ.getHead();
            for (int i = 0; i < linkedQ.size(); i++) {
                linkedArray[i] = curr.getData();
                curr = curr.getNext();
            }
            return linkedArray;
        }

        /**
         * Helper method for testing your linked stack
         * Turns your linked list into an array for easier testing and visualization
         *
         * @return your linked queue object as an array
         */
        private Object[] linkedArrayS() {
            Object[] linkedArray = new Object[linkedS.size()];
            LinkedNode curr = linkedS.getHead();
            for (int i = 0; i < linkedS.size(); i++) {
                linkedArray[i] = curr.getData();
                curr = curr.getNext();
            }
            return linkedArray;
        }

        @Before
        public void setup() {
            arrayQ = new ArrayQueue<String>();
            linkedQ = new LinkedQueue<String>();

            arrayS = new ArrayStack<String>();
            linkedS = new LinkedStack<String>();
        }


        /**
         * Pushes an item, pushes another item, and pops an item bigSize times
         * Tests that your array stack works correctly over multiple resizes and pops
         */
        @Test(timeout = TIMEOUT)
        public void bigArrayStack() {
            // Sets up array stack object
            int bigSize = 150;
            for (int i = 0; i < bigSize; i++) {
                arrayS.push("a" + i);
                arrayS.push("a" + (i+1));
                arrayS.pop();
            }

            // Finds expected capacity of backing array
            int expectedCapacity = ArrayStack.INITIAL_CAPACITY;
            while (expectedCapacity <= bigSize) {
                expectedCapacity *= 2;
            }

            // Creates expected backing array
            String[] expected = new String[expectedCapacity];
            for (int i = 0; i < bigSize; i++) {
                expected[i] = "a" + i;
            }

            assertArrayEquals(expected, arrayS.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void bigLinkedStack() {
            int bigSize = 150;
            for (int i = 0; i < bigSize; i++) {
                linkedS.push("a" + i);
                linkedS.push("a" + (i+1));
                linkedS.pop();
            }

            String[] expected = new String[bigSize];
            for (int i = bigSize - 1, j = 0; i >= 0; i--, j++) {
                expected[i] = "a" + j;
            }

            assertArrayEquals(expected, linkedArrayS());
        }

        /**
         * Enqueues an item, enqueues another item, and dequeues an item bigSize times
         * Tests that your array queue works correctly over multiple resizes and passes
         */
        @Test(timeout = TIMEOUT)
        public void bigArrayQueue() {
            // Creates an array queue and fills
            int bigSize = 150;
            for (int i = 0; i < bigSize; i++) {
                arrayQ.enqueue("a" + i);
                arrayQ.enqueue("a" + (i+1));
                arrayQ.dequeue();
            }

            // Finds the expected capacity of the backing array and the number of resizings
            int expectedCapacity = ArrayQueue.INITIAL_CAPACITY;
            int numDoubles = 0;
            while (expectedCapacity <= bigSize) {
                expectedCapacity *= 2;
                numDoubles++;
            }

            // Calculates where the front of the queue will be
            int shift = bigSize;
            if (numDoubles > 0) {
                shift -= (ArrayQueue.INITIAL_CAPACITY - 1);
                for (int i = 0; i < numDoubles - 1; i++) {
                    shift -= (ArrayQueue.INITIAL_CAPACITY * (Math.pow(2, i)));
                }
            }

            // Fills the expected array
            String[] expected = new String[expectedCapacity];
            // Case if bigSize is even
            if (bigSize % 2 == 0) {
                expected[0] = "a" + (bigSize / 2);
                for (int i = 1, j = (bigSize / 2) + 1; i < bigSize - 1; i+=2, j++) {
                    expected[i] = "a" + j;
                    expected[i + 1] = "a" + j;
                }
                expected[bigSize - 1] = "a" + bigSize;
            // Case if bigSize is odd
            } else {
                for (int i = 0, j = (bigSize / 2) + 1; i < bigSize - 1; i+=2, j++) {
                    expected[i] = "a" + j;
                    expected[i + 1] = "a" + j;
                }
                expected[bigSize - 1] = "a" + bigSize;
            }

            // Shifts the front of the expected array
            String[] expectedWithShift = new String[expectedCapacity];
            for (int i = 0; i < expectedCapacity; i++) {
                if (i + shift < expectedCapacity) {
                    expectedWithShift[i + shift] = expected[i];
                } else {
                    expectedWithShift[(i + shift) % expectedCapacity] = expected[i];
                }
            }

            expected = expectedWithShift;

            assertArrayEquals(expected, arrayQ.getBackingArray());
        }

        /**
         * Enqueues an item, enqueues another item, and dequeues an item bigSize times
         * Tests that your array queue works correctly over multiple resizes and passes
         */
        @Test(timeout = TIMEOUT)
        public void bigLinkedQueue() {
            // Creates an array queue and fills
            int bigSize = 150;
            for (int i = 0; i < bigSize; i++) {
                linkedQ.enqueue("a" + i);
                linkedQ.enqueue("a" + (i+1));
                linkedQ.dequeue();
            }

            // Fills the expected array
            String[] expected = new String[bigSize];
            // Case if bigSize is even
            if (bigSize % 2 == 0) {
                expected[0] = "a" + (bigSize / 2);
                for (int i = 1, j = (bigSize / 2) + 1; i < bigSize - 1; i+=2, j++) {
                    expected[i] = "a" + j;
                    expected[i + 1] = "a" + j;
                }
                expected[bigSize - 1] = "a" + bigSize;
            // Case if bigSize is odd
            } else {
                for (int i = 0, j = (bigSize / 2) + 1; i < bigSize - 1; i+=2, j++) {
                    expected[i] = "a" + j;
                    expected[i + 1] = "a" + j;
                }
                expected[bigSize - 1] = "a" + bigSize;
            }

            assertArrayEquals(expected, linkedArrayQ());
        }
    }
}