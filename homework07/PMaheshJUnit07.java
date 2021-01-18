import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Pranav Mahesh's JUnit tests for HW7 - AVL.
 * Comprehensive tests on all the methods on the HW
 * Let me know if you have questions or if anything doesn't work by replying to my Piazza post!
 * If you're getting errors, use https://csvistool.com/AVL and put the elements in
 * the order that I add/remove in to see the tree!
 *
 * @author Pranav Mahesh
 * @version 1.0 (updated 3/5/20)
 */
public class PMaheshJUnit07 {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avl;

    @Before
    public void setup() {
        avl = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, avl.size());
        assertNull(avl.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testCollectionConstructor() {
        Collection<Integer> collection = new ArrayList<>();
        collection.add(3);
        collection.add(4);
        collection.add(2);

        AVL<Integer> avl2 = new AVL<>(collection);
        assertEquals(avl2.getRoot().getHeight(), 1);
        assertEquals(avl2.getRoot().getBalanceFactor(), 0);
        assertEquals(avl2.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl2.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl2.getRoot().getRight().getHeight(), 0);
        assertEquals(avl2.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullCollectionConstructor() {
        Collection<Integer> collection = null;

        AVL<Integer> avl2 = new AVL<>(collection);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullDataCollectionConstructor() {
        Collection<Integer> collection = new ArrayList<>();
        collection.add(2);
        collection.add(3);
        collection.add(null);
        collection.add(4);

        AVL<Integer> avl2 = new AVL<>(collection);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNull() {
        avl.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddOne() {
        avl.add(3);
        assertEquals(avl.size(), 1);
        assertEquals(avl.getRoot().getData(), (Integer) 3);

        assertEquals(avl.getRoot().getHeight(), 0);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddTwo() {
        avl.add(3);
        avl.add(4);
        assertEquals(avl.size(), 2);
        assertEquals(avl.getRoot().getData(), (Integer) 3);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 4);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddThree() {
        avl.add(3);
        avl.add(4);
        avl.add(2);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 3);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 2);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 4);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotation() {
        avl.add(3);
        avl.add(4);
        avl.add(5);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 4);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 3);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 5);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);

    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotation() {
        avl.add(3);
        avl.add(2);
        avl.add(1);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 2);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 1);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 3);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotation() {
        avl.add(4);
        avl.add(2);
        avl.add(3);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 3);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 2);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 4);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);

    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotation() {
        avl.add(4);
        avl.add(6);
        avl.add(5);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 5);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 4);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 6);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddGeneral() {
        avl.add(40);
        avl.add(50);
        avl.add(30);
        avl.add(20);
        assertEquals(avl.size(), 4);
        assertEquals(avl.getRoot().getData(), (Integer) 40);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 30);
        assertEquals(avl.getRoot().getLeft().getLeft().getData(), (Integer) 20);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 50);

        assertEquals(avl.getRoot().getHeight(), 2);
        assertEquals(avl.getRoot().getBalanceFactor(), 1);
        assertEquals(avl.getRoot().getLeft().getHeight(), 1);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 1);
        assertEquals(avl.getRoot().getLeft().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddGeneral2() {
        avl.add(40);
        avl.add(50);
        avl.add(30);
        avl.add(60);
        assertEquals(avl.size(), 4);
        assertEquals(avl.getRoot().getData(), (Integer) 40);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 30);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 50);
        assertEquals(avl.getRoot().getRight().getRight().getData(), (Integer) 60);

        assertEquals(avl.getRoot().getHeight(), 2);
        assertEquals(avl.getRoot().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 1);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getRight().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddDuplicate() {
        avl.add(40);
        avl.add(50);
        avl.add(30);
        avl.add(60);
        avl.add(60);

        assertEquals(avl.size(), 4);
        assertEquals(avl.getRoot().getData(), (Integer) 40);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 30);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 50);
        assertEquals(avl.getRoot().getRight().getRight().getData(), (Integer) 60);

        assertEquals(avl.getRoot().getHeight(), 2);
        assertEquals(avl.getRoot().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 1);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getRight().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddComplex() {
        // Use visualization tool if you're having trouble
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        assertEquals(avl.size(), 9);
        assertEquals(avl.getRoot().getData(), (Integer) 50);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 40);
        assertEquals(avl.getRoot().getLeft().getLeft().getData(), (Integer) 16);
        assertEquals(avl.getRoot().getLeft().getRight().getData(), (Integer) 43);
        assertEquals(avl.getRoot().getLeft().getLeft().getLeft().getData(), (Integer) 15);
        assertEquals(avl.getRoot().getLeft().getLeft().getRight().getData(), (Integer) 20);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 59);
        assertEquals(avl.getRoot().getRight().getLeft().getData(), (Integer) 57);
        assertEquals(avl.getRoot().getRight().getRight().getData(), (Integer) 60);

        assertEquals(avl.getRoot().getHeight(), 3);
        assertEquals(avl.getRoot().getBalanceFactor(), 1);
        assertEquals(avl.getRoot().getLeft().getHeight(), 2);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 1);
        assertEquals(avl.getRoot().getLeft().getLeft().getHeight(), 1);
        assertEquals(avl.getRoot().getLeft().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getRight().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getLeft().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getLeft().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getLeft().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getLeft().getRight().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 1);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getRight().getBalanceFactor(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        avl.add(3);
        avl.remove(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveNotInTree() {
        avl.add(3);
        avl.remove(5);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOne() {
        avl.add(3);
        avl.remove(3);
        assertEquals(0, avl.size());
        assertNull(avl.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwo() {
        avl.add(3);
        avl.add(4);
        avl.remove(4);
        assertEquals(1, avl.size());
        assertEquals(avl.getRoot().getData(), (Integer) 3);
        assertNull(avl.getRoot().getRight());

        assertEquals(avl.getRoot().getHeight(), 0);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveThree() {
        avl.add(3);
        avl.add(4);
        avl.add(2);
        avl.remove(2);
        assertEquals(avl.size(), 2);
        assertEquals(avl.getRoot().getData(), (Integer) 3);
        assertNull(avl.getRoot().getLeft());
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 4);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOneChild() {
        avl.add(4);
        avl.add(5);
        avl.add(3);
        avl.add(6);
        avl.remove(5);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 4);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 3);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 6);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChild() {
        avl.add(5);
        avl.add(6);
        avl.add(3);
        avl.add(4);
        avl.remove(5);
        assertEquals(avl.size(), 3);
        assertEquals(avl.getRoot().getData(), (Integer) 4);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 3);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 6);

        assertEquals(avl.getRoot().getHeight(), 1);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveComplex() {
        // Use visualization tool if you're having trouble
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        avl.remove(50);
        avl.remove(59);
        avl.remove(16);
        avl.remove(20);
        assertEquals(avl.size(), 5);

        assertEquals(avl.getRoot().getData(), (Integer) 43);
        assertEquals(avl.getRoot().getLeft().getData(), (Integer) 15);
        assertEquals(avl.getRoot().getLeft().getRight().getData(), (Integer) 40);
        assertEquals(avl.getRoot().getRight().getData(), (Integer) 57);
        assertEquals(avl.getRoot().getRight().getRight().getData(), (Integer) 60);

        assertEquals(avl.getRoot().getHeight(), 2);
        assertEquals(avl.getRoot().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getLeft().getHeight(), 1);
        assertEquals(avl.getRoot().getLeft().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getLeft().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getLeft().getRight().getBalanceFactor(), 0);
        assertEquals(avl.getRoot().getRight().getHeight(), 1);
        assertEquals(avl.getRoot().getRight().getBalanceFactor(), -1);
        assertEquals(avl.getRoot().getRight().getRight().getHeight(), 0);
        assertEquals(avl.getRoot().getRight().getRight().getBalanceFactor(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetNull() {
        avl.add(3);
        avl.get(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetNotInTree() {
        avl.add(3);
        avl.get(5);
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        // Use visualization tool if you're having trouble
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        avl.remove(50);
        avl.remove(59);
        avl.remove(16);
        avl.remove(20);
        assertEquals(avl.size(), 5);

        assertEquals((Integer) 40, avl.get(40));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testContainsNull() {
        avl.add(3);
        avl.contains(null);
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        // Use visualization tool if you're having trouble
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        avl.remove(50);
        avl.remove(59);
        avl.remove(16);
        avl.remove(20);
        assertEquals(avl.size(), 5);

        assertTrue(avl.contains(15));
        assertFalse(avl.contains(16));
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        assertEquals(-1, avl.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        avl.add(3);
        avl.add(4);
        avl.add(5);
        avl.clear();
        assertNull(avl.getRoot());
        assertEquals(0, avl.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEWDNull() {
        avl.add(3);
        avl.elementsWithinDistance(null, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEWDNegative() {
        avl.add(3);
        avl.elementsWithinDistance(3, -1);
    }

    @Test (expected = NoSuchElementException.class)
    public void testEWDNotInTree() {
        avl.add(3);
        avl.elementsWithinDistance(5, 1);
    }

    @Test(timeout = TIMEOUT)
    public void testEWDSimple() {
        Set<Integer> set = new HashSet<>();
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        avl.remove(50);
        avl.remove(59);
        avl.remove(16);
        avl.remove(20);

        set.add(15);
        set.add(40);
        set.add(43);
        set.add(57);

        assertEquals(set, avl.elementsWithinDistance(15, 2));
    }

    @Test(timeout = TIMEOUT)
    public void testEWDComplex() {
        Set<Integer> set = new HashSet<>();
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        set.add(59);
        set.add(57);
        set.add(60);
        set.add(50);
        set.add(40);
        set.add(16);
        set.add(43);

        assertEquals(set, avl.elementsWithinDistance(59, 3));
    }

    @Test(timeout = TIMEOUT)
    public void testEWDAll() {
        Set<Integer> set = new HashSet<>();
        avl.add(40);
        avl.add(50);
        avl.add(60);
        avl.add(20);
        avl.add(57);
        avl.add(43);
        avl.add(16);
        avl.add(15);
        avl.add(59);

        set.add(40);
        set.add(50);
        set.add(60);
        set.add(20);
        set.add(57);
        set.add(43);
        set.add(16);
        set.add(15);
        set.add(59);

        assertEquals(set, avl.elementsWithinDistance(59, avl.size()));
    }
}