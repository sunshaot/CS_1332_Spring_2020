import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

public class ArrayListJUnitTest {
    private ArrayList<Integer> numList;

    @Before
    public void setUp() throws Exception {
        numList = new ArrayList<>();
    }
    @Test
    public void testInitialization() {
        assertEquals(0, numList.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY], numList.getBackingArray());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Adding Element Tests
     */
    @Test
    public void simpleAddAtIndex() {
        numList.addAtIndex(0,  1);
        numList.addAtIndex(1, 2);
        numList.addAtIndex(2, 3);
        numList.addAtIndex(3, 4);
        numList.addAtIndex(4, 5); //[1,2,3,4,5]
        assertEquals(5, numList.size());
        Object[] expectedArr = new Object[ArrayList.INITIAL_CAPACITY];
        expectedArr[0] = 1;
        expectedArr[1] = 2;
        expectedArr[2] = 3;
        expectedArr[3] = 4;
        expectedArr[4] = 5;
        assertArrayEquals(expectedArr, numList.getBackingArray());
    }

    @Test
    public void addAtIndexWithShift() {
        numList.addAtIndex(0, 1); //[1]
        numList.addAtIndex(1, 2); //[1,2]
        numList.addAtIndex(0, 3); //[3,1,2]
        numList.addAtIndex(1, 4); //[3,4,1,2]
        numList.addAtIndex(2, 5); //[3,4,5,1,2]
        assertEquals(5, numList.size());
        Object[] expectedArr = new Object[ArrayList.INITIAL_CAPACITY];
        expectedArr[0] = 3;
        expectedArr[1] = 4;
        expectedArr[2] = 5;
        expectedArr[3] = 1;
        expectedArr[4] = 2;
        assertArrayEquals(expectedArr, numList.getBackingArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void highIndexAddingTest() {
        numList.addAtIndex(0, 1);
        numList.addAtIndex(1, 2);
        numList.addAtIndex(3, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void lowIndexAddingTest() {
        numList.addAtIndex(0, 1);
        numList.addAtIndex(1, 2);
        numList.addAtIndex(-1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingNullData() {
        numList.addAtIndex(0, 1);
        numList.addAtIndex(1, 2);
        numList.addAtIndex(2, null);
    }

    @Test
    public void addToEmptyList() {
        numList.addToBack(1);
        assertFalse("false", numList.isEmpty());
        assertNotEquals(0, numList.size());
    }
    @Test
    public void addToFrontTest() {
        numList.addToFront(1); //[1]
        numList.addToFront(2); //[2,1]
        numList.addToFront(3); //[3,2,1]
        numList.addToFront(4); //[4,3,2,1]
        numList.addToFront(5); //[5,4,3,2,1]
        assertEquals(5, numList.size());
        Object[] expectedArr = new Object[ArrayList.INITIAL_CAPACITY];
        expectedArr[0] = 5;
        expectedArr[1] = 4;
        expectedArr[2] = 3;
        expectedArr[3] = 2;
        expectedArr[4] = 1;
        assertArrayEquals(expectedArr, numList.getBackingArray());
    }
    @Test
    public void addToBackTest() {
        numList.addToBack(5);
        numList.addToBack(4);
        numList.addToBack(2);
        numList.addToBack(6);
        assertEquals(4, numList.size());
        Object[] expectedArr = new Object[ArrayList.INITIAL_CAPACITY];
        expectedArr[0] = 5;
        expectedArr[1] = 4;
        expectedArr[2] = 2;
        expectedArr[3] = 6;
        assertArrayEquals(expectedArr, numList.getBackingArray());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Removing element tests
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeFromEmptyList() {
        numList.removeAtIndex(0);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromFrontEmptyList() {
        numList.removeFromFront();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromBackEmptyList() {
        numList.removeFromBack();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removingTooHighIndex() {
        numList.addToFront(1);
        numList.addToBack(20);
        numList.addAtIndex(1, 3);
        numList.addAtIndex(2, 60);
        numList.addAtIndex(3, 4);
        assertEquals(5, numList.size());
        numList.removeAtIndex(numList.size()); //IndexOutOfBoundsException
    }
    @Test
    public void removeAtIndex() {
        numList.addToFront(1); //[1]
        numList.addToBack(20); //[1,20]
        numList.addAtIndex(1, 3); //[1,3,20]
        numList.addAtIndex(2, 60); //[1,3,60,20]
        numList.addAtIndex(3, 4); //[1,3,60,4,20]
        int returnedData = numList.removeAtIndex(3);
        assertEquals(4, returnedData);
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = 1;
        expected[1] = 3;
        expected[2] = 60;
        expected[3] = 20;
        assertArrayEquals(expected, numList.getBackingArray());
    }

    @Test
    public void removeFromFront() {
        numList.addToFront(1); //[1]
        numList.addToBack(20); //[1,20]
        numList.addAtIndex(1, 3); //[1,3,20]
        numList.addAtIndex(2, 60); //[1,3,60,20]
        numList.addAtIndex(3, 4); //[1,3,60,4,20]
        int returnedData = numList.removeFromFront();
        assertEquals(1, returnedData);
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = 3;
        expected[1] = 60;
        expected[2] = 4;
        expected[3] = 20;
        assertArrayEquals(expected, numList.getBackingArray());
    }

    @Test
    public void removeFromBack() {
        numList.addToFront(1); //[1]
        numList.addToBack(20); //[1,20]
        numList.addAtIndex(1, 3); //[1,3,20]
        numList.addAtIndex(2, 60); //[1,3,60,20]
        numList.addAtIndex(3, 4); //[1,3,60,4,20]
        int returnedData = numList.removeFromBack();
        assertEquals(20, returnedData);
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = 1;
        expected[1] = 3;
        expected[2] = 60;
        expected[3] = 4;
        assertArrayEquals(expected, numList.getBackingArray());

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Retrieval of Element Tests
     */
    @Test
    public void get() {
        numList.addToFront(1); //[1]
        numList.addToBack(2); //[1,2]
        numList.addAtIndex(1, 3); //[1,3,2]
        numList.addAtIndex(2, 7); //[1,3,7,2]
        numList.addToFront(8); //[8,1,3,7,2]
        Object[] expected = {8, 1, 3, 7, 2};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], numList.get(i));
        }

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getFromEmptyList() {
        numList.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getFromNegativeIndex() {
        numList.addToFront(1);
        numList.addToBack(2);
        numList.addToFront(3);
        numList.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getFromHighIndex() {
        numList.addToFront(1);
        numList.addToBack(2);
        numList.addToFront(3);
        numList.get(numList.size());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Empty List cases
     */
    @Test
    public void isEmpty() {
        numList.addToFront(1);
        numList.addToBack(2);
        numList.addAtIndex(0, 3);
        numList.addAtIndex(2, 5);
        assertFalse("false", numList.isEmpty());

    }

    @Test
    public void emptyTestOnEmptyArray() {
        assertEquals(0, numList.size());
        assertTrue("true", numList.isEmpty());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Clearing out a list tests
     */
    @Test
    public void clear() {
        numList.addToFront(1);
        numList.addToBack(2);
        numList.addAtIndex(0, 3);
        numList.addAtIndex(2, 5);
        numList.clear();
        assertEquals(0, numList.size());
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        assertArrayEquals(expected, numList.getBackingArray());
    }

    @Test
    public void clearWithResizedList() {
        numList.addToFront(1); //[1]
        numList.addToBack(2); //[1,2]
        numList.addToFront(3); //[3,1,2]
        numList.addToBack(4); //[3,1,2,4]
        numList.addToFront(5); //[5,3,1,2,4]
        numList.addToBack(6); //[5,3,1,2,4,6]
        numList.addToFront(7); //[7,5,3,1,2,4,6]
        numList.addToBack(8); //[7,5,3,1,2,4,6,8]
        numList.addToFront(9); //[9,7,5,3,1,2,4,6,8]
        numList.addToFront(10); //part where the resizing will occur
        numList.clear();
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        assertArrayEquals(expected, numList.getBackingArray());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Tests on getting the backing array and the size
     */
    @Test
    public void getBackingArray() {
        numList.addToFront(1); //[1]
        numList.addToBack(2); //[1,2]
        numList.addToFront(3); //[3,1,2]
        numList.addToBack(4); //[3,1,2,4]
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = 3;
        expected[1] = 1;
        expected[2] = 2;
        expected[3] = 4;
        assertEquals(4, numList.size());
        assertArrayEquals(expected, numList.getBackingArray());
    }

    @Test
    public void size() {
        numList.addToFront(1); //[1]
        numList.addToBack(2); //[1,2]
        numList.addToFront(3); //[3,1,2]
        numList.addToBack(4); //[3,1,2,4]
        assertEquals(4, numList.size());
        numList.clear();
        assertEquals(0, numList.size());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Resize test
     */
    @Test
    public void resizeTest() {
        numList.addToFront(1); //[1]
        numList.addToBack(2); //[1,2]
        numList.addToFront(3); //[3,1,2]
        numList.addToBack(4); //[3,1,2,4]
        numList.addToFront(5); //[5,3,1,2,4]
        numList.addToBack(6); //[5,3,1,2,4,6]
        numList.addToFront(7); //[7,5,3,1,2,4,6]
        numList.addToBack(8); //[7,5,3,1,2,4,6,8]
        numList.addToFront(9); //[9,7,5,3,1,2,4,6,8]
        numList.addToFront(10); //part where the resizing will occur
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected[0] = 10;
        expected[1] = 9;
        expected[2] = 7;
        expected[3] = 5;
        expected[4] = 3;
        expected[5] = 1;
        expected[6] = 2;
        expected[7] = 4;
        expected[8] = 6;
        expected[9] = 8;
        assertArrayEquals(expected, numList.getBackingArray());
    }
    @Test
    public void multipleResize() {
        int num = 36;
        int arraySize = ArrayList.INITIAL_CAPACITY;
        while (arraySize < num) {
            arraySize *= 2;
        }
        Integer[] expected = new Integer[arraySize];
        for (int i = 1; i <= num; i++) {
            numList.addToBack(i);
            expected[i - 1] = i;
            assertEquals(i, numList.size());
            assertEquals(expected[i - 1], numList.get(i - 1));
        }
        assertEquals(36, numList.size());
        assertArrayEquals(expected, numList.getBackingArray());
    }
    //////////////////////////////////////////////////////////////////////////////
    /**
     * Get and remove
     */
    @Test
    public void getAndRemove() {
        numList.addToFront(1); //[1]
        numList.addToBack(2); //[1,2]
        numList.addToFront(3); //[3,1,2]
        numList.addToBack(4); //[3,1,2,4]
        numList.addToFront(5); //[5,3,1,2,4]
        numList.addToBack(6); //[5,3,1,2,4,6]
        numList.addToFront(7); //[7,5,3,1,2,4,6]
        numList.addToBack(8); //[7,5,3,1,2,4,6,8]
        numList.addToFront(9); //[9,7,5,3,1,2,4,6,8]
        numList.addToFront(10); //part where the resizing will occur. [10,9,7,5,3,1,2,4,6,8]
        numList.addAtIndex(3, 11); //[10,9,7,11,5,3,1,2,4,6,8]
        int expected = 11;
        int expected2 = 3;
        assertEquals(expected, numList.get(3).intValue());
        assertEquals(expected2, numList.get(5).intValue());
        int returnedData1 = numList.removeAtIndex(3);
        assertEquals(expected, returnedData1);
        Object[] expectedList1 = {10, 9, 7, 5, 3, 1, 2, 4, 6, 8, null, null, null, null, null, null, null, null};
        assertArrayEquals(expectedList1, numList.getBackingArray());
        int returnedData2 = numList.removeAtIndex(4);
        assertEquals(expected2, returnedData2);
        Object[] expectedList2 = {10, 9, 7, 5, 1, 2, 4, 6, 8, null, null, null, null, null, null, null, null, null};
        assertArrayEquals(expectedList2, numList.getBackingArray());
    }
}
