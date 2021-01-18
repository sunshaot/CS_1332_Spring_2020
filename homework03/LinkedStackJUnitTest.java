import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class LinkedStackJUnitTest {
    private LinkedStack<Integer> linkedStack;
    @Before
    public void setup() {
        linkedStack = new LinkedStack<>();
    }
    @Test
    public void testInitialization() {
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());
    }
    @Test(expected = IllegalArgumentException.class)
    public void pushNullData() {
        linkedStack.push(null);
    }
    @Test
    public void pushFirstNode() {
        linkedStack.push(5);
        assertEquals(5, linkedStack.getHead().getData().intValue());
        assertEquals(1, linkedStack.size());
        assertNull(linkedStack.getHead().getNext());
    }
    @Test
    public void pushTest() {
        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.push(3);
        linkedStack.push(4);
        linkedStack.push(5);
        assertEquals(5, linkedStack.getHead().getData().intValue());
        assertEquals(5, linkedStack.size());
        Integer[] expected = {5,4,3,2,1};
        LinkedNode<Integer> current = linkedStack.getHead();
        for (int i = 0; i < 5; i++) {
            assertEquals(5 - i, current.getData().intValue());
            current = current.getNext();
        }
    }
    @Test(expected = NoSuchElementException.class)
    public void popFromEmpty() {
        linkedStack.pop();
    }
    @Test
    public void popTest() {
        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.push(3);
        linkedStack.push(4);
        linkedStack.push(5);
        assertEquals(5, linkedStack.getHead().getData().intValue());
        assertEquals(5, linkedStack.size());
        assertEquals(5, linkedStack.pop().intValue());
        assertEquals(4, linkedStack.size());
        assertEquals(4, linkedStack.getHead().getData().intValue());
        assertEquals(3, linkedStack.getHead().getNext().getData().intValue());
    }
    @Test (expected = NoSuchElementException.class)
    public void peekFromEmptyTest() {
        linkedStack.peek();
    }
    @Test
    public void peekTest() {
        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.push(3);
        linkedStack.push(4);
        linkedStack.push(5);
        assertEquals(5, linkedStack.getHead().getData().intValue());
        assertEquals(5, linkedStack.size());
        assertEquals(5, linkedStack.peek().intValue());
    }
    @Test
    public void getHeadFromEmpty() {
        assertNull(linkedStack.getHead());
    }
    @Test
    public void getHeadFromNonEmpty() {
        linkedStack.push(5);
        linkedStack.push(4);
        linkedStack.push(3);
        linkedStack.push(2);
        linkedStack.push(1);
        assertEquals(1, linkedStack.getHead().getData().intValue());
        assertNotNull(linkedStack.getHead().getNext());
        assertEquals(5, linkedStack.size());
    }
    @Test
    public void getSizeFromEmpty() {
        assertEquals(0, linkedStack.size());
    }
    @Test
    public void getSizeTest() {
        linkedStack.push(5);
        linkedStack.push(4);
        linkedStack.push(3);
        linkedStack.push(2);
        linkedStack.push(1);
        assertEquals(5, linkedStack.size());
    }
    @Test
    public void peekAndRemoveTest() {
        linkedStack.push(5);
        linkedStack.push(4);
        linkedStack.push(3);
        linkedStack.push(2);
        linkedStack.push(1);
        assertEquals(5, linkedStack.size());
        Integer[] expected = {1,2,3,4,5};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].intValue(), linkedStack.peek().intValue());
            linkedStack.pop();
        }
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());
    }

}
