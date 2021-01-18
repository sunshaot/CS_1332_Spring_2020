import org.junit.Before;
import org.junit.Test;

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
 * Other edge cases
 * Setup tests (toArray and isEmpty)
 *
 *
 * NOTE:
 * Many of my tests rely on list.toArray() and list.isEmpty()
 * Ensure the first set of tests are working correctly before troubleshooting further tests.
 *
 * I frequently use list.toArray() as it allows easy comparison of the entire list
 * and it allows you to more easily visualize any issues. While debugging, you will
 * be able to see the array values for expected and actual to see what part of your
 * list is wrong if a test fails.
 *
 *
 * @author Tyler Schott
 * @version 1.1
 */

public class SchottJUnit02 {
    private static final int TIMEOUT = 200;
    private CircularSinglyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new CircularSinglyLinkedList<>();
    }

// Test toArray and isEmpty / Setup Tests ---------------------------------------------------------------------------

    /**
     * NOTE: Many of my tests rely on list.toArray() and list.isEmpty()
     * Ensure these first tests are working correctly before troubleshooting further tests.
     *
     * I frequently use list.toArray() as it allows easy comparison of the entire list
     * and it allows you to more easily visualize any issues. While debugging, you will
     * be able to see the array values for expected and actual to see what part of your
     * list is wrong if a test fails.
     */
    @Test(timeout = TIMEOUT)
    public void testToArray_Setup1() {
        list.addToFront("a3");
        list.addToFront("a2");
        list.addToFront("a1");

        Object[] expected = {"a1", "a2", "a3"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testToArray_Setup2() {
        list.addToFront("a1");

        Object[] expected = {"a1"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testToArray_Setup3() {
        Object[] expected = {};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty1() {
        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty2() {
        list.addToFront("a1");
        list.removeFromFront();

        assertTrue(list.isEmpty());
    }

//Exception handling tests -------------------------------------------------------------------------------------

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtWrongIndexLow() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.addAtIndex(-1, "a0");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtWrongIndexHigh() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.addAtIndex(3, "a4");
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addNullDataFront() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addNullDataBack() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addNullDataAtIndex() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.addAtIndex(1, null);
    }


    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtEmptyIndex() {
        list.removeAtIndex(0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveAtEmptyBack() {
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveAtEmptyFront() {
        list.removeFromFront();
    }


    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getOutOfBoundsWhenEmpty() {
        list.get(0);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getOutOfBoundsLow() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.get(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getOutOfBoundsHigh() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        list.get(2);
    }


    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastNoSuchElementWhenEmpty() {
        list.removeLastOccurrence("a1");
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastNoSuchElement() {
        list.addToBack("a1"); // "a1"
        list.addToBack("a2"); // "a1", "a2"
        list.addToBack("a1"); // "a1", "a2", "a1"
        list.addToBack("a2"); // "a1", "a2", "a1", "a2"

        list.removeLastOccurrence("a3");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveLastNullWhenEmpty() {
        list.removeLastOccurrence(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveLastNull() {
        list.addToBack("a1"); // "a1"
        list.addToBack("a2"); // "a1", "a2"
        list.addToBack("a1"); // "a1", "a2", "a1"
        list.addToBack("a2"); // "a1", "a2", "a1", "a2"

        list.removeLastOccurrence(null);
    }

// Tests when size = 0 (that should not immediately throw an exception) --------------------------------------------

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex_WhenEmpty() {
        list.addAtIndex(0, "a1");

        assertEquals(list.get(0), "a1");
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront_WhenEmpty() {
        list.addToFront("a1");

        assertEquals(list.get(0), "a1");
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack_WhenEmpty() {
        list.addToBack("a1");

        assertEquals(list.get(0), "a1");
    }


    @Test(timeout = TIMEOUT)
    public void testGetHead_WhenEmpty() {
        assertEquals(list.getHead(), null);
    }


    @Test(timeout = TIMEOUT)
    public void testIsEmpty_WhenEmpty() {
        assertTrue(list.isEmpty());
    }


    @Test(timeout = TIMEOUT)
    public void testClear_WhenEmpty() {
        list.clear();
        assertTrue(list.isEmpty());
    }


    @Test(timeout = TIMEOUT)
    public void testToArray_WhenEmpty() {
        String[] expected = new String[0];
        assertArrayEquals(list.toArray(), expected);
    }

// Tests when size = 1 ------------------------------------------------------------------------------------------

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex0_WhenSize1() {
        list.addToFront("a2");

        list.addAtIndex(0, "a1");

        Object[] expected = {"a1", "a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex1_WhenSize1() {
        list.addToFront("a1");

        list.addAtIndex(1, "a2");

        Object[] expected = {"a1", "a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront_WhenSize1() {
        list.addToFront("a2");

        list.addToFront("a1");

        Object[] expected = {"a1", "a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack_WhenSize1() {
        list.addToFront("a1");

        list.addToBack("a2");

        Object[] expected = {"a1", "a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }


    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex_WhenSize1() {
        list.addToFront("a1");

        list.removeAtIndex(0);

        assertTrue(list.isEmpty());
        assertEquals(null, list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack_WhenSize1() {
        list.addToFront("a1");

        list.removeFromBack();

        assertTrue(list.isEmpty());
        assertEquals(null, list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront_WhenSize1() {
        list.addToFront("a1");

        list.removeFromFront();

        assertTrue(list.isEmpty());
        assertEquals(null, list.getHead());
    }


    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccur_WhenSize1() {
        list.addToFront("a1");

        list.removeLastOccurrence("a1");

        assertTrue(list.isEmpty());
        assertEquals(null, list.getHead());
    }

// Tests when size = 2 ------------------------------------------------------------------------------------------

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex0_WhenSize2() {
        list.addToFront("a3");
        list.addToFront("a2");

        list.addAtIndex(0, "a1");

        Object[] expected = {"a1", "a2", "a3"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex0_WhenSize2() {
        list.addToFront("a2");
        list.addToFront("a1");

        list.removeAtIndex(0);

        Object[] expected = {"a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        assertEquals(list.getHead(), list.getHead().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront_WhenSize2() {
        list.addToFront("a2");
        list.addToFront("a1");

        list.removeFromFront();

        Object[] expected = {"a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        assertEquals(list.getHead(), list.getHead().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack_WhenSize2() {
        list.addToFront("a2");
        list.addToFront("a1");

        list.removeFromBack();

        Object[] expected = {"a1"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        assertEquals(list.getHead(), list.getHead().getNext());
    }


    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurSame_WhenSize2() {
        list.addToFront("a1");
        list.addToFront("a1");

        list.removeLastOccurrence("a1");

        Object[] expected = {"a1"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        assertEquals(list.getHead(), list.getHead().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurDiff1_WhenSize2() {
        list.addToFront("a2");
        list.addToFront("a1");

        list.removeLastOccurrence("a1");

        Object[] expected = {"a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        assertEquals(list.getHead(), list.getHead().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurDiff2_WhenSize2() {
        list.addToFront("a2");
        list.addToFront("a1");

        list.removeLastOccurrence("a2");

        Object[] expected = {"a1"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        assertEquals(list.getHead(), list.getHead().getNext());
    }

// Tests for Add -------------------------------------------------------------------------------------------------

    @Test(timeout = TIMEOUT)
    public void addAtEdgeIndexLow() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        // Adds at lowest possible index
        list.addAtIndex(0, "a0");

        Object[] expected = {"a0", "a1", "a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
        // Tests for circular link
        assertEquals(list.getHead().getNext().getNext().getNext(), list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void addAtEdgeIndexHigh() {
        list.addToFront("a2"); // "a2"
        list.addToFront("a1"); // "a1", "a2"

        // Adds at highest possible index
        list.addAtIndex(2, "a3");

        Object[] expected = {"a1", "a2", "a3"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
        // Tests for circular link
        assertEquals(list.getHead().getNext().getNext().getNext(), list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testAllAdds() {
        list.addToFront("a2");
        list.addToBack("a4");
        list.addToBack("a5");
        list.addToFront("a1");
        list.addAtIndex(2, "a3");

        Object[] expected = {"a1", "a2", "a3", "a4", "a5"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

// Tests for Remove --------------------------------------------------------------------------------------------

    @Test(timeout = TIMEOUT)
    public void testAllRemoves() {
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a4");
        list.addToBack("a5");

        list.removeFromBack();
        Object[] expected = {"a1", "a2", "a3", "a4"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeAtIndex(1);
        expected = new Object[]{"a1", "a3", "a4"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeFromFront();
        expected = new Object[]{"a3", "a4"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeAtIndex(1);
        expected = new Object[]{"a3"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);
    }

// Tests for remove last occurrence -----------------------------------------------------------------------------

    @Test(timeout = TIMEOUT)
    public void testRemoveLast() {
        list.addToBack("a2");
        list.addToBack("a1");
        list.addToBack("a2");
        list.addToBack("a3");
        list.addToBack("a1");
        list.addToBack("a4");
        list.addToBack("a2");

        // Initial list {"a2", "a1", "a2", "a3", "a1", "a4", "a2"}

        list.removeLastOccurrence("a1");
        Object[] expected = {"a2", "a1", "a2", "a3", "a4", "a2"};
        Object[] actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeLastOccurrence("a2");
        expected = new Object[]{"a2", "a1", "a2", "a3", "a4"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeLastOccurrence("a3");
        expected = new Object[]{"a2", "a1", "a2", "a4"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeLastOccurrence("a4");
        expected = new Object[]{"a2", "a1", "a2"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeLastOccurrence("a1");
        expected = new Object[]{"a2", "a2"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeLastOccurrence("a2");
        expected = new Object[]{"a2"};
        actual = list.toArray();
        assertArrayEquals(expected, actual);

        list.removeLastOccurrence("a2");
        expected = new Object[0];
        actual = list.toArray();
        assertArrayEquals(expected, actual);
        assertTrue(list.isEmpty());
    }

}