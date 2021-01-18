import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Comprehensive JUnit Test for Circular Linked List
 * @author Karthik Subramanian
 * @version 1.0
 */
@SuppressWarnings("checkstyle:LineLength")
public class CircularSinglyLinkedListJUnitTest {
    private CircularSinglyLinkedList<Integer> list;
    @Before
    public void setUp() {
        list = new CircularSinglyLinkedList<>();
    }
    @Test
    public void testInitialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Add at index tests
     */
    @Test
    public void basicAddAtIndex() {
        list.addAtIndex(0, 1); //1
        list.addAtIndex(1, 2); //1,2
        list.addAtIndex(2, 3); //1,2,3
        list.addAtIndex(3, 4); //1,2,3,4
        list.addAtIndex(4, 5); //1,2,3,4,5
        assertEquals(5, list.size());
        CircularSinglyLinkedListNode<Integer> cur = list.getHead();
        assertNotNull(cur);
        assertEquals(1, cur.getData().intValue()); //1

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(2, cur.getData().intValue()); //2

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(3, cur.getData().intValue()); //3

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(4, cur.getData().intValue()); //4

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(5, cur.getData().intValue()); //5

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(list.getHead(), cur);
    }
    @Test
    public void complexAddAtIndex() {
        list.addAtIndex(0, 2);   // 2
        list.addAtIndex(0, 1);   // 1, 2
        list.addAtIndex(2, 4);   // 1, 2, 4
        list.addAtIndex(2, 3);   // 1, 2, 3, 4
        list.addAtIndex(0, 0);   // 0, 1, 2, 3, 4

        assertEquals(5, list.size());

        CircularSinglyLinkedListNode<Integer> cur = list.getHead();
        assertNotNull(cur);
        assertEquals(0, cur.getData().intValue());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(1, cur.getData().intValue());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(2, cur.getData().intValue());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(3, cur.getData().intValue());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(4, cur.getData().intValue());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(list.getHead(), cur);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void highIndexAddingTest() {
        list.addAtIndex(0, 1);
        list.addAtIndex(1, 2);
        list.addAtIndex(3, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void lowIndexAddingTest() {
        list.addAtIndex(0, 1);
        list.addAtIndex(1, 2);
        list.addAtIndex(-1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingNullData() {
        list.addAtIndex(0, 1);
        list.addAtIndex(1, 2);
        list.addAtIndex(2, null);
    }
    @Test
    public void addToEmptyList() {
        list.addToBack(1); //1
        assertFalse("false", list.isEmpty());
        assertNotEquals(0, list.size());
        assertEquals(1, list.getHead().getData().intValue());
        assertNotNull(list.getHead());
    }
    @Test
    public void addToEmptyListTwo() {
        list.addToFront(1); //1
        assertFalse("false", list.isEmpty());
        assertNotEquals(0, list.size());
        assertEquals(1, list.getHead().getData().intValue());
        assertNotNull(list.getHead());
    }
    @Test
    public void addToFrontTest() {
        list.addToFront(1); //[1]
        list.addToFront(2); //[2,1]
        list.addToFront(3); //[3,2,1]
        list.addToFront(4); //[4,3,2,1]
        list.addToFront(5); //[5,4,3,2,1]
        assertEquals(5, list.size());
        CircularSinglyLinkedListNode<Integer> current = list.getHead();
        Integer[] expected = {5, 4, 3, 2, 1};
        int count = 0;
        while (current.getNext() != list.getHead()) {
            assertEquals(current.getData(), expected[count]);
            count++;
            current = current.getNext();
        }
        assertEquals(5, list.size());
        assertEquals(5, list.getHead().getData().intValue());
    }
    @Test
    public void addToBackTest() {
        list.addToBack(5); //5
        list.addToBack(4); //5,4
        list.addToBack(2); //5,4,2
        list.addToBack(6); //5,4,2,6
        assertEquals(4, list.size());
        Integer[] expected = {5, 4, 2, 6};
        assertEquals(5, list.getHead().getData().intValue());
        CircularSinglyLinkedListNode<Integer> current = list.getHead();
        int count = 0;
        while (current.getNext() != list.getHead()) {
            assertEquals(current.getData(), expected[count]);
            count++;
            current = current.getNext();
        }
        assertEquals(4, list.size());
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNullToFront() {
        list.addToFront(1);
        list.addToBack(2);
        list.addToFront(3);
        list.addToFront(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addNullToBack() {
        list.addToBack(5);
        list.addToFront(4);
        list.addToBack(3);
        list.addToFront(2);
        list.addToBack(null);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Removing element tests
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeFromEmptyList() {
        list.removeAtIndex(0);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromFrontEmptyList() {
        list.removeFromFront();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromBackEmptyList() {
        list.removeFromBack();
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void removingTooHighIndex() {
        list.addToFront(1);
        list.addToBack(20);
        list.addAtIndex(1, 3);
        list.addAtIndex(2, 60);
        list.addAtIndex(3, 4);
        assertEquals(5, list.size());
        list.removeAtIndex(list.size()); //IndexOutOfBoundsException
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void removingNegativeIndex() {
        list.addToFront(1);
        list.addToBack(20);
        list.addAtIndex(1, 3);
        list.addAtIndex(2, 60);
        list.addAtIndex(3, 4);
        assertEquals(5, list.size());
        list.removeAtIndex(-1); //IndexOutOfBoundsException
    }
    @Test
    public void removeAtIndex() {
        list.addToFront(1); //1
        list.addToBack(20); //1,20
        list.addAtIndex(1, 3); //1,3,20
        list.addAtIndex(2, 60); //1,3,60,20
        list.addAtIndex(3, 4); //1,3,60,4,20
        int returnedData = list.removeAtIndex(3).intValue();
        assertEquals(4, returnedData);
        Integer[] expected = {1, 3, 60, 20};
        assertArrayEquals(expected, list.toArray());
        assertEquals(4, list.size());
    }
    @Test
    public void removeFromSizeOneList() {
        list.addToFront(5);
        assertEquals(1, list.size());
        int returnedData = list.removeAtIndex(list.size() - 1).intValue();
        assertEquals(5, returnedData);
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }
    @Test
    public void removeFromFront() {
        list.addToFront(1); //1
        list.addToBack(20); //1,20
        list.addAtIndex(1, 3); //1,3,20
        list.addAtIndex(2, 60); //1,3,60,20
        list.addAtIndex(3, 4); //1,3,60,4,20
        int returnedData = list.removeFromFront().intValue();
        assertEquals(1, returnedData);
        Integer[] expected = {3, 60, 4, 20};
        assertArrayEquals(expected, list.toArray());
        assertEquals(4, list.size());
        assertNotNull(list.getHead());
        assertEquals(3, list.getHead().getData().intValue());
        CircularSinglyLinkedListNode<Integer> cur = list.getHead();
        while (cur.getNext() != list.getHead()) {
            cur = cur.getNext();
        }
        assertEquals(3, cur.getNext().getData().intValue()); //make sure the last node is pointing to the correct head
    }

    @Test
    public void removeFromBack() {
        list.addToFront(1); //1
        list.addToBack(20); //1,20
        list.addAtIndex(1, 3); //1,3,20
        list.addAtIndex(2, 60); //1,3,60,20
        list.addAtIndex(3, 4); //1,3,60,4,20
        int returnedData = list.removeFromBack().intValue();
        assertEquals(20, returnedData);
        Integer[] expected = {1, 3, 60, 4};
        assertArrayEquals(expected, list.toArray());
        assertNotNull(list.getHead());
        assertEquals(4, list.size());
        assertEquals(1, list.getHead().getData().intValue());
        CircularSinglyLinkedListNode<Integer> cur = list.getHead();
        while (cur.getNext() != list.getHead()) {
            cur = cur.getNext();
        }
        assertEquals(1, cur.getNext().getData().intValue()); //make sure last node is pointing to correct head
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Retrieval of Element Tests
     */
    @Test
    public void get() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addAtIndex(1, 3); //1,3,2
        list.addAtIndex(2, 7); //1,3,7,2
        list.addToFront(8); //8,1,3,7,2
        Integer[] expected = {8, 1, 3, 7, 2};
        assertArrayEquals(expected, list.toArray());
        assertNotNull(list.getHead());
        assertEquals(5, list.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].intValue(), list.get(i).intValue());
        }
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void getFromEmptyList() {
        list.get(1);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void getFromNegativeIndex() {
        list.addToFront(1);
        list.addToBack(2);
        list.addToFront(3);
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getFromHighIndex() {
        list.addToFront(1);
        list.addToBack(2);
        list.addToFront(3);
        list.get(list.size());
    }
    @Test
    public void getFromHead() {
        list.addToBack(1); //1
        list.addToFront(2); //2,1
        list.addAtIndex(1, 3); //2,3,1
        list.addToBack(4); //2,3,1,4
        assertEquals(2, list.get(0).intValue());
        assertEquals(2, list.getHead().getData().intValue());
    }
    @Test
    public void getFromTail() {
        list.addToBack(1); //1
        list.addToFront(2); //2,1
        list.addAtIndex(1, 3); //2,3,1
        list.addToBack(4); //2,3,1,4
        assertEquals(4, list.get(list.size() - 1).intValue());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Empty List cases
     */
    @Test
    public void isEmpty() {
        list.addToFront(1);
        list.addToBack(2);
        list.addAtIndex(0, 3);
        list.addAtIndex(2, 5);
        assertFalse("false", list.isEmpty());
        assertNotEquals(0, list.size());

    }
    @Test
    public void emptyTestOnEmptyArray() {
        assertEquals(0, list.size());
        assertTrue("true", list.isEmpty());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Clearing out a list tests
     */
    @Test
    public void clear() {
        list.addToFront(1);
        list.addToBack(2);
        list.addAtIndex(0, 3);
        list.addAtIndex(2, 5);
        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Tests on getting the head and size
     */
    @Test
    public void retrieveHeadTest() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        assertNotNull(list.getHead());
        assertEquals(3, list.getHead().getData().intValue());
    }
    @Test
    public void retrieveNullHead() {
        assertNull(list.getHead());
    }
    @Test
    public void retrieveSizeTest() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        assertNotNull(list.getHead());
        assertEquals(4, list.size());
    }
    @Test
    public void zeroSizeTest() {
        assertEquals(0, list.size());
    }
    //////////////////////////////////////////////////////////////////////////////
    /**
     * Get and remove
     */
    @Test
    public void getAndRemove() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        list.addToFront(5); //5,3,1,2,4
        list.addToBack(6); //5,3,1,2,4,6
        list.addToFront(7); //7,5,3,1,2,4,6
        list.addToBack(8); //7,5,3,1,2,4,6,8
        list.addToFront(9); //9,7,5,3,1,2,4,6,8
        list.addToFront(10); //10,9,7,5,3,1,2,4,6,8
        list.addAtIndex(3, 11); //10,9,7,11,5,3,1,2,4,6,8
        int expected = 11;
        int expected2 = 3;
        assertEquals(expected, list.get(3).intValue());
        assertEquals(expected2, list.get(5).intValue());
        int returnedData1 = list.removeAtIndex(3);
        assertEquals(expected, returnedData1);
        Integer[] expectedList1 = {10, 9, 7, 5, 3, 1, 2, 4, 6, 8};
        assertArrayEquals(expectedList1, list.toArray());
        int returnedData2 = list.removeAtIndex(4);
        assertEquals(expected2, returnedData2);
        Object[] expectedList2 = {10, 9, 7, 5, 1, 2, 4, 6, 8};
        assertArrayEquals(expectedList2, list.toArray());
        assertNotNull(list.getHead());
        assertEquals(10, list.getHead().getData().intValue());
    }
    //////////////////////////////////////////////////////////////////////////////
    /**
     * Converting from linked list to array
     */
    @Test
    public void toArrayTest() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        list.addToFront(5); //5,3,1,2,4
        list.addToBack(6); //5,3,1,2,4,6
        list.addToFront(7); //7,5,3,1,2,4,6
        list.addToBack(8); //7,5,3,1,2,4,6,8
        list.addToFront(9); //9,7,5,3,1,2,4,6,8
        list.addToFront(10); //10,9,7,5,3,1,2,4,6,8
        list.addAtIndex(3, 11); //10,9,7,11,5,3,1,2,4,6,8
        Integer[] expected = {10, 9, 7, 11, 5, 3, 1, 2, 4, 6, 8};
        assertArrayEquals(expected, list.toArray());
    }
    //////////////////////////////////////////////////////////////////////////////
    /**
     * Removing last occurrence test
     */
    @Test(expected = IllegalArgumentException.class)
    public void lastOccurrenceOfNullElement() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        list.addToFront(5); //5,3,1,2,4
        list.addToBack(6); //5,3,1,2,4,6
        list.addToFront(7); //7,5,3,1,2,4,6
        list.addToBack(8); //7,5,3,1,2,4,6,8
        list.addToFront(9); //9,7,5,3,1,2,4,6,8
        list.addToFront(10); //10,9,7,5,3,1,2,4,6,8
        list.addAtIndex(3, 11); //10,9,7,11,5,3,1,2,4,6,8
        list.removeLastOccurrence(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void cannotFindLastOccurrenceOfElement() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        list.addToFront(5); //5,3,1,2,4
        list.addToBack(6); //5,3,1,2,4,6
        list.addToFront(7); //7,5,3,1,2,4,6
        list.addToBack(8); //7,5,3,1,2,4,6,8
        list.addToFront(9); //9,7,5,3,1,2,4,6,8
        list.addToFront(10); //10,9,7,5,3,1,2,4,6,8
        list.addAtIndex(3, 11); //10,9,7,11,5,3,1,2,4,6,8
        list.removeLastOccurrence(12);
    }
    @Test(expected = NoSuchElementException.class)
    public void cannotFindLastOccurrenceOfElement2() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        list.addToFront(5); //5,3,1,2,4
        list.addToBack(6); //5,3,1,2,4,6
        list.addToFront(7); //7,5,3,1,2,4,6
        list.addToBack(8); //7,5,3,1,2,4,6,8
        list.addToFront(9); //9,7,5,3,1,2,4,6,8
        list.addToFront(10); //10,9,7,5,3,1,2,4,6,8
        list.addAtIndex(3, 11); //10,9,7,11,5,3,1,2,4,6,8
        list.removeLastOccurrence(-1);
    }
    @Test
    public void removeLastOccurrence() {
        list.addToFront(1); //1
        list.addToBack(2); //1,2
        list.addToFront(3); //3,1,2
        list.addToBack(4); //3,1,2,4
        list.addToFront(5); //5,3,1,2,4
        list.addToBack(6); //5,3,1,2,4,6
        list.addToFront(7); //7,5,3,1,2,4,6
        list.addToBack(8); //7,5,3,1,2,4,6,8
        list.addToFront(9); //9,7,5,3,1,2,4,6,8
        list.addToFront(10); //10,9,7,5,3,1,2,4,6,8
        list.addAtIndex(3, 11); //10,9,7,11,5,3,1,2,4,6,8
        assertEquals(11, list.removeLastOccurrence(11).intValue());
        Integer[] expected = {10, 9, 7, 5, 3, 1, 2, 4, 6, 8};
        assertArrayEquals(expected, list.toArray());
        assertEquals(10, list.size());
        assertNotNull(list.getHead());
        assertEquals(10, list.getHead().getData().intValue());
    }
    @Test(expected =  NoSuchElementException.class)
    public void removeLastOccurrenceSizeOneListCannotFind() {
        list.addAtIndex(0, 5);
        list.removeLastOccurrence(6);
    }
    @Test
    public void removeLastOccurrenceSizeOneList() {
        list.addAtIndex(0, 6);
        list.removeLastOccurrence(6);
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }
    @Test (expected = NoSuchElementException.class)
    public void removeLastOccurrenceCannotFindSizeTwo() {
        list.addAtIndex(0, 5);
        list.addToBack(21);
        list.removeLastOccurrence(7);
    }
    @Test
    public void removeLastOccurrenceSizeTwo() {
        list.addToBack(1);
        list.addAtIndex(1, 23);
        list.removeLastOccurrence(23);
        assertEquals(1, list.size());
        assertEquals(1, list.get(list.size() - 1).intValue());
        assertEquals(1, list.getHead().getData().intValue());
    }
    @Test
    public void removeLastOccurrenceWhenLastOccurrenceBack() {
        list.addToBack(1); //1
        list.addAtIndex(1, 23); //1,23
        list.addToFront(2); //2,1,23
        list.addToFront(3); //3,2,1,23
        Integer returnInt = list.removeLastOccurrence(23);
        assertEquals(23, returnInt.intValue());
        Integer[] expected = {3, 2, 1};
        assertArrayEquals(expected, list.toArray());
        assertEquals(3, list.size());
        assertEquals(3, list.getHead().getData().intValue());
    }
    @Test(expected = NoSuchElementException.class)
    public void removeLastOccurrenceEmptyList() {
        list.removeLastOccurrence(5);
    }
}
