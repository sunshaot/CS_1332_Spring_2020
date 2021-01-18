import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayStackJUnitTest {
    private ArrayStack<Integer> arrayStack;

    @Before
    public void setup() {
        arrayStack = new ArrayStack<Integer>();
    }
    @Test
    public void testInitialization() {
        assertEquals(0, arrayStack.size());
        assertArrayEquals(new Integer[ArrayStack.INITIAL_CAPACITY],
                arrayStack.getBackingArray());
    }
    @Test(expected = IllegalArgumentException.class)
    public void pushNullData() {
        arrayStack.push(null);
    }
    @Test
    public void basicPushTest() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        assertEquals(5, arrayStack.size());
        Integer[] expected = {1,2,3,4,5, null, null, null, null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }
    @Test
    public void pushWithResize() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        assertEquals(10, arrayStack.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }
    @Test
    public void pushWithMultipleResizings() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        arrayStack.push(11);
        arrayStack.push(12);
        arrayStack.push(13);
        arrayStack.push(14);
        arrayStack.push(15);
        arrayStack.push(16);
        arrayStack.push(17);
        arrayStack.push(18);
        arrayStack.push(19); //resize will occur
        arrayStack.push(20); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19, 20]
        assertEquals(20, arrayStack.size());
        Integer[] expectedNum = new Integer[36];
        for (int i = 1; i <= 20; i++) {
            expectedNum[i-1] = i;
        }
        assertArrayEquals(expectedNum, arrayStack.getBackingArray());
    }
    @Test(expected = NoSuchElementException.class)
    public void popFromEmpty() {
        arrayStack.pop();
    }
    @Test
    public void basicPopTest() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        assertEquals(5, arrayStack.size());
        int expected = 5;
        assertEquals(expected, arrayStack.pop().intValue());
        assertEquals(4, arrayStack.size());
        Integer[] expectedList = {1,2,3,4,null,null,null,null,null};
        assertArrayEquals(expectedList, arrayStack.getBackingArray());
    }
    @Test
    public void popSizeOne() {
        arrayStack.push(47);
        assertEquals(1, arrayStack.size());
        assertEquals(47, arrayStack.pop().intValue());
        assertEquals(0, arrayStack.size());
    }
    @Test
    public void popTestWithResize() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        assertEquals(10, arrayStack.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
        int expectedNum = 0;
        for (int i = 10; i > 0; i--) {
            expectedNum = i;
            assertEquals(expectedNum, arrayStack.pop().intValue());
            assertEquals(i - 1, arrayStack.size());
        }
        assertEquals(0, arrayStack.size());
    }
    @Test
    public void popFromSizeOne() {
        arrayStack.push(36);
        assertEquals(36, arrayStack.pop().intValue());
        assertEquals(0, arrayStack.size());
    }
    @Test (expected = NoSuchElementException.class)
    public void peekFromEmpty() {
        arrayStack.peek();
    }
    @Test
    public void basicPeekTest() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        assertEquals(5, arrayStack.peek().intValue());
        assertEquals(5, arrayStack.size());
        Integer[] expected = {1,2,3,4,5,null,null,null,null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }
    @Test
    public void peekTestWithResize() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        assertEquals(10, arrayStack.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
        assertEquals(10, arrayStack.peek().intValue());
    }
    @Test
    public void getBackingArrayTest() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        assertEquals(10, arrayStack.size());
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }
    @Test
    public void getBackingArray() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        Integer[] expected = {1,2,3,4,5,null,null,null,null};
        assertArrayEquals(expected, arrayStack.getBackingArray());
    }
    @Test
    public void getEmptySize() {
        assertEquals(0, arrayStack.size());
    }
    @Test
    public void getSize() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        assertEquals(5, arrayStack.size());
    }
    @Test
    public void getSizeWithResize() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        assertEquals(10, arrayStack.size());
    }
    @Test
    public void peekAndPop() {
        arrayStack.push(1); //[1]
        arrayStack.push(2); //[1,2]
        arrayStack.push(3); //[1,2,3]
        arrayStack.push(4); //[1,2,3,4]
        arrayStack.push(5); //[1,2,3,4,5]
        arrayStack.push(6); //[1,2,3,4,5,6]
        arrayStack.push(7); //[1,2,3,4,5,6,7]
        arrayStack.push(8); //[1,2,3,4,5,6,7,8]
        arrayStack.push(9); //[1,2,3,4,5,6,7,8,9]
        arrayStack.push(10); //resize will occur
        assertEquals(10, arrayStack.size());
        for (int i = 10; i > 0; i--) {
            assertEquals(i, arrayStack.peek().intValue());
            arrayStack.pop();
        }
        assertEquals(0, arrayStack.size());
        assertArrayEquals(new Integer[18], arrayStack.getBackingArray());
    }
}
