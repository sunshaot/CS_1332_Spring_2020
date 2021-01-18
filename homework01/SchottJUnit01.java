import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * This set of JUnits assumes you have already passed all tests in the ArrayListStudentTest
 * provided tests.
 *
 * If any tests do not work as expected, please let me know! Email me at tschott6@gatech.edu or
 * reply to my Piazza post.
 *
 * TESTS INCLUDED:
 * Correct resizing and clearing (Adding at front and back) for any size array
 * Exception handling for get, add, and remove
 * Exception handling and functionality when array size = 0
 *
 * @author Tyler Schott
 * @version 1.5
 */

public class SchottJUnit01 {
    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    /*
     * Resizing test(s)
     */

    /**
     * Comprehensive resizing test for custom size list
     *
     * Tests array resizing for any size > 3 (numElements) Array List for:
     * addToFront(), addToBack(), and addAtIndex()
     *
     * Also tests correct clearing
     *
     * If tests fail, it may be easier to refer to other, more simple tests below to debug.
     */
    @Test(timeout = TIMEOUT)
    public void testMultiResize() {
        // Set array list size
        int numElements = 39;
        // Finds correct backing array size
        int arraySize = ArrayList.INITIAL_CAPACITY;
        while (arraySize < numElements) {
            arraySize *= 2;
        }

        // Sets up expected array
        // {"a1", "a2", ... , "an"}
        String[] expected = new String[arraySize];
        for (int i = 0, j = 1; i < numElements; i++, j++) {
            expected[i] = "a" + j;
        }

        // Tests addToBack and compares to expected array
        // Checks that backing array reaches form {"a1", "a2", ... , "an"} with correct array size
        for (int i = 0, j = 1; i < numElements; i++, j++) {
            list.addToBack("a" + j);
        }
        assertArrayEquals(expected, list.getBackingArray());

        // Tests addToFront and compares to expected array
        // Checks that backing array reaches form {"a1", "a2", ... , "an"} with correct array size
        list.clear();
        for (int i = 0, j = numElements; i < numElements; i++, j--) {
            list.addToFront("a" + j);
        }
        assertArrayEquals(expected, list.getBackingArray());

        // Tests addAtIndex in the middle and compares to expected array
        // Checks that backing array reaches form {"a1", "a2", ... , "an"} with correct array size
        list.clear();
        list.addToFront("a1");
        list.addToBack("a" + numElements);
        for (int i = 1, j = 2; i < numElements - 1; i++, j++) {
            list.addAtIndex(i, "a" + j);
        }
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Assumes INITIAL_CAPACITY is within [6, 11]
     */
    @Test(timeout = TIMEOUT)
    public void testResize() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.addToBack("a7");
        list.addToBack("a8");
        list.addToBack("a9");
        list.addToBack("a10");
        list.addToBack("a11");
        list.addToBack("a12");

        assertEquals(12, list.size());
        assertEquals(ArrayList.INITIAL_CAPACITY * 2, Array.getLength(list.getBackingArray()));

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected[0] = "a1";
        expected[1] = "a2";
        expected[2] = "a3";
        expected[3] = "a4";
        expected[4] = "a5";
        expected[5] = "a6";
        expected[6] = "a7";
        expected[7] = "a8";
        expected[8] = "a9";
        expected[9] = "a10";
        expected[10] = "a11";
        expected[11] = "a12";

        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Assumes INITIAL_CAPACITY is within [6, 11]
     */
    @Test(timeout = TIMEOUT)
    public void testResizeAddFront() {
        list.addToBack("a5");
        list.addToBack("a6");
        list.addToBack("a7");
        list.addToBack("a8");
        list.addToBack("a9");
        list.addToBack("a10");
        list.addToBack("a11");
        list.addToFront("a4");
        list.addToFront("a3");
        list.addToFront("a2");
        list.addToFront("a1");
        list.addToBack("a12");

        assertEquals(12, list.size());
        assertEquals(ArrayList.INITIAL_CAPACITY * 2, Array.getLength(list.getBackingArray()));

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected[0] = "a1";
        expected[1] = "a2";
        expected[2] = "a3";
        expected[3] = "a4";
        expected[4] = "a5";
        expected[5] = "a6";
        expected[6] = "a7";
        expected[7] = "a8";
        expected[8] = "a9";
        expected[9] = "a10";
        expected[10] = "a11";
        expected[11] = "a12";

        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Assumes INITIAL_CAPACITY is within [6, 11]
     */
    @Test(timeout = TIMEOUT)
    public void testResizeAndClear() {
        list.addToBack("a5");
        list.addToBack("a6");
        list.addToBack("a7");
        list.addToBack("a8");
        list.addToBack("a9");
        list.addToBack("a10");
        list.addToBack("a11");
        list.addToFront("a4");
        list.addToFront("a3");
        list.addToFront("a2");
        list.addToFront("a1");
        list.addToBack("a12");

        list.clear();

        assertEquals(0, list.size());
        assertEquals(ArrayList.INITIAL_CAPACITY, Array.getLength(list.getBackingArray()));
    }

    /*
     * Exception handling test(s)
     */

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddIndexTooHigh() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.addAtIndex(7, "a8");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddIndexTooLow() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.addAtIndex(-1, "a0");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastIndex() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.removeAtIndex(5);
    }

    /**
     * Trys to remove the last index twice. First time should pass, second should throw Out of Bounds
     */
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexTooHigh() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.removeAtIndex(5);

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "a1";
        expected[1] = "a2";
        expected[2] = "a3";
        expected[3] = "a4";
        expected[4] = "a5";
        assertArrayEquals(expected, list.getBackingArray());

        list.removeAtIndex(5);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexTooLow() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetIndexTooHigh() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.get(7);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetIndexTooLow() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToBack("a6");
        list.get(-1);
    }

    /*
     * Empty array test(s)
     */

    @Test(timeout = TIMEOUT)
    public void testRemoveFromZero() {
        int exceptionCount = 0;

        //Tests removeAtIndex
        try {
            list.removeAtIndex(0);
        } catch (IndexOutOfBoundsException e) {
            exceptionCount++;
        }
        assertEquals(1, exceptionCount);

        //Tests removeFromFront
        try {
            list.removeFromFront();
        } catch (NoSuchElementException e) {
            exceptionCount++;
        }
        assertEquals(2, exceptionCount);

        //Tests removeFromBack
        try {
            list.removeFromBack();
        } catch (NoSuchElementException e) {
            exceptionCount++;
        }
        assertEquals(3, exceptionCount);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetAtEmpty() {
        list.get(0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddWhenEmpty() {
        String[] expected = new String[ArrayList.INITIAL_CAPACITY];

        list.addToFront("a1");
        list.removeFromFront();
        assertArrayEquals(expected, list.getBackingArray());

        list.addToBack("a1");
        list.removeFromBack();
        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(0, "a1");
        list.removeAtIndex(0);
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtWrongIndexWhenEmpty() {
        list.addAtIndex(1, "Should not work");
    }
}