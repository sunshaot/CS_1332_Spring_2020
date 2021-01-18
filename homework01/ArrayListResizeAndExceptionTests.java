import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListResizeAndExceptionTests {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBounds() {
        list.addAtIndex(-1, "0a");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullFront() {
        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullBack() {
        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullAtIndex() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT)
    public void testSize() {
        list.addToBack("1");
        list.addToFront("2");
        list.addToFront("3");
        list.addToBack("4");
        list.addAtIndex(3, "5");
        list.addToFront("6");
        assertEquals(6, list.size());

        list.removeAtIndex(4);
        assertEquals(5, list.size());
        list.removeFromFront();
        assertEquals(4, list.size());
        list.removeFromBack();
        assertEquals(3, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        for (int i = 0; i < 9; i++) {
            list.addToBack(Integer.toString(i));
        }

        Object[] backingArray = list.getBackingArray();
        assertEquals(9, backingArray.length);

        list.addToBack("9");
        backingArray = list.getBackingArray();
        assertEquals(18, backingArray.length);

        list.removeFromFront();
        backingArray = list.getBackingArray();
        assertEquals(18, backingArray.length);

    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyAddAndRemoveToFront() {
        for (int i = 0; i < 10; i++) {
            list.addToFront(Integer.toString(i));
        }
        assertFalse(list.isEmpty());
        for (int i = 0; i < 10; i++) {
            list.removeFromFront();
        }
        assertTrue(list.isEmpty());

    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyAddAndRemoveToBack() {
        for (int i = 0; i < 10; i++) {
            list.addToBack(Integer.toString(i));
        }
        assertFalse(list.isEmpty());
        for (int i = 0; i < 10; i++) {
            list.removeFromBack();
        }
        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyAddAndRemoveAtIndex() {
        for (int i = 0; i < 10; i++) {
            list.addAtIndex(i, Integer.toString(i));
        }
        assertFalse(list.isEmpty());
        for (int i = 0; i < 10; i++) {
            list.removeAtIndex(0);
        }
        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeAddToFront() {
        Object[] expected = new Object[18];
        for (int i = 0; i < 10; i++) {
            list.addToFront(Integer.toString(i));
            expected[9 - i] = Integer.toString(i);
        }

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeAddToBack() {
        Object[] expected = new Object[18];
        for (int i = 0; i < 10; i++) {
            list.addToBack(Integer.toString(i));
            expected[i] = Integer.toString(i);
        }

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeAddAtIndex() {
        Object[] expected = new Object[18];
        for (int i = 0; i < 10; i++) {
            list.addAtIndex(0, Integer.toString(i));
            expected[9 - i] = Integer.toString(i);
        }

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFrontEmpty() {
        list.removeFromFront();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveBackEmpty() {
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexEmpty() {
        list.removeAtIndex(0);
    }
}