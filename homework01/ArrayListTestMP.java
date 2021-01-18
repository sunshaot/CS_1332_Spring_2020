import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ArrayListTestMP {

    private static final int TIMEOUT = 2000;
    private ArrayList<Integer> data;

    @Before
    public void setUp() throws Exception {
        data = new ArrayList();
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackWhileExpanding() {
        for (int i = 0; i < 15; i++) {
            data.addToBack(i);
        }
        Integer[] expected = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, null, null, null};
        assertArrayEquals(expected, data.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontWhileExpanding() {
        for (int i = 14; i >= 0; i--) {
            data.addToFront(i);
        }
        Integer[] expected = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, null, null, null};
        assertArrayEquals(expected, data.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWhileExpanding() {
        data.addToBack(100);
        data.addToBack(100);
        for (int i = 0; i < 10; i++) {
            data.addAtIndex(1, i);
        }
        Integer[] expected = new Integer[] {100, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 100, null, null, null, null, null, null};
        assertArrayEquals(expected, data.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyBeforeAndAfterClear() {
        for (int i = 0; i < 5; i++) {
            data.addToBack(i);
        }
        assertFalse(data.isEmpty());
        data.clear();
        assertTrue(data.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyAfterManualEmptying() {
        for (int i = 0; i < 10; i++) {
            data.addToBack(i);
        }
        for (int i = 0; i < 10; i++) {
            data.removeFromBack();
        }
        assertTrue(data.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAllAddsAndRemoves() {
        for (int i = 0; i < 5; i++) {
            data.addToBack(i);
        }
        assertEquals(5, data.size());
        for (int i = 0; i < 2; i++) {
            data.removeAtIndex(1);
        }
        assertEquals(3, data.size());
        for (int i = 0; i < 4; i++) {
            data.addToFront(i);
        }
        assertEquals(7, data.size());
        for (int i = 0; i < 3; i++) {
            data.removeFromBack();
        }
        assertEquals(4, data.size());
        for (int i = 0; i < 4; i++) {
            data.addAtIndex(2, i);
        }
        assertEquals(8, data.size());
        for (int i = 0; i < 2; i++) {
            data.removeFromFront();
        }
        assertEquals(6, data.size());

        assertArrayEquals(new Integer[] {3, 2, 1, 0, 1, 0, null, null, null}, data.getBackingArray());
    }
}