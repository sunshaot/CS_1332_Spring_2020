import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class Karthik_AVL_JUnit {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avlTree;

    /**
     * Helper method
     * @return List
     */
    public List<Integer> levelorder() {
        Queue<AVLNode<Integer>> line = new LinkedList<AVLNode<Integer>>();
        List<Integer> levelOrderList = new ArrayList<Integer>();
        if (avlTree.getRoot() == null) {
            return levelOrderList;
        }
        line.add(avlTree.getRoot());
        while (!line.isEmpty()) {
            if (line.peek().getLeft() != null && line.peek().getRight() != null) {
                line.add(line.peek().getLeft());
                line.add(line.peek().getRight());
                levelOrderList.add(line.remove().getData());
            } else if (line.peek().getLeft() == null && line.peek().getRight() != null) {
                line.add(line.peek().getRight());
                levelOrderList.add(line.remove().getData());
            } else if (line.peek().getLeft() != null && line.peek().getRight() == null) {
                line.add(line.peek().getLeft());
                levelOrderList.add(line.remove().getData());
            } else {
                levelOrderList.add(line.remove().getData());
            }
        }
        return levelOrderList;
    }

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, avlTree.size());
        assertNull(avlTree.getRoot());
    }
    ////////////////////////////////////////////////
    //add tests
    @Test (expected = IllegalArgumentException.class)
    public void addErrorTest() {
        avlTree.add(null);
    }
    @Test
    public void addToEmptyTree() {
        avlTree.add(50);
        assertEquals(1, avlTree.size());
        assertNotNull(avlTree.getRoot());
        assertEquals(0, avlTree.getRoot().getHeight());
        assertEquals(0, avlTree.getRoot().getBalanceFactor());
    }

    /**
     *       100
     *      /   \
     *    80     150
     *     \
     *      85
     */
    @Test
    public void generalAdd() {
        avlTree.add(100);
        avlTree.add(150);
        avlTree.add(80);
        avlTree.add(85);
        assertEquals(4, avlTree.size());
        assertNotNull(avlTree.getRoot());
        assertEquals(100, avlTree.getRoot().getData().intValue());
        assertEquals(80, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(150, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(85, avlTree.getRoot().getLeft().getRight().getData().intValue());
        assertEquals(0, avlTree.getRoot().getLeft().getRight().getBalanceFactor());
        assertEquals(0, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals(-1, avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals(1, avlTree.getRoot().getBalanceFactor());
    }
    /**
     *        2
     *      /  \
     *    1     3
     *           \
     *            4
     */
    @Test
    public void addDegenerateCase() {
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(3);
        avlTree.add(4);
        assertEquals(4, avlTree.size());
        assertNotNull(avlTree.getRoot());
        assertEquals(2, avlTree.getRoot().getData().intValue());
        assertEquals(1, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(3, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(4, avlTree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(0, avlTree.getRoot().getRight().getRight().getBalanceFactor());
        assertEquals(0, avlTree.getRoot().getRight().getRight().getHeight());
        assertEquals(-1, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avlTree.getRoot().getRight().getHeight());
        assertEquals(2, avlTree.getRoot().getHeight());
        assertEquals(-1, avlTree.getRoot().getBalanceFactor());
        assertEquals(0, avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals(0, avlTree.getRoot().getLeft().getHeight());
    }
    /**
     *        100
     *      /  \
     *    85     150
     *   / \
     *  80  96
     */
    @Test
    public void addDuplicate() {
        avlTree.add(100);
        avlTree.add(150);
        avlTree.add(80);
        avlTree.add(85);
        avlTree.add(96);
        avlTree.add(80);
        assertNotNull(avlTree.getRoot());
        assertEquals(5, avlTree.size());
        assertEquals(100, avlTree.getRoot().getData().intValue());
        assertEquals(85, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(80, avlTree.getRoot().getLeft().getLeft().getData().intValue());
        assertEquals(96, avlTree.getRoot().getLeft().getRight().getData().intValue());
        assertEquals(150, avlTree.getRoot().getRight().getData().intValue());
    }
    /**
     *         5
     *        /  \
     *      2     8
     *      / \   /  \
     *     1   3 7    10
     *         \
     *          4
     */
    @Test
    public void addTest() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(4);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(2);
        expected.add(8);
        expected.add(1);
        expected.add(3);
        expected.add(7);
        expected.add(10);
        expected.add(4);
        assertEquals(8, avlTree.size());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertEquals(expected, levelorder());
        assertNotNull(avlTree.getRoot());
    }
    @Test
    public void twoNodeTest() {
        avlTree.add(5);
        avlTree.add(7);
        assertEquals(2, avlTree.size());
        assertEquals(1, avlTree.getRoot().getHeight());
        assertEquals(0, avlTree.getRoot().getRight().getHeight());
        assertEquals(-1, avlTree.getRoot().getBalanceFactor());
        assertNotNull(avlTree.getRoot().getRight());
        assertEquals(7, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(5, avlTree.getRoot().getData().intValue());
    }
    ////////////////////////////////////////////////////////////
    //remove tests
    @Test(expected = IllegalArgumentException.class)
    public void removeNull() {
        avlTree.remove(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyTree() {
        avlTree.remove(5);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeElementNotInTreeSizeOne() {
        avlTree.add(6);
        avlTree.remove(4);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeNotInTree() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        assertEquals(5, avlTree.size());
        assertNotNull(avlTree.getRoot());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        avlTree.remove(7);
    }
    @Test
    public void removeSizeOneTree() {
        avlTree.add(6);
        assertEquals(6, avlTree.remove(6).intValue());
        assertEquals(0, avlTree.size());
        assertNull(avlTree.getRoot());
    }

    /**
     *                  5
     *               /   \
     *             2       8
     *           /  \      /  \
     *          1     3   7    10
     */
    @Test
    public void removeWithPredecessor() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        assertEquals(7, avlTree.size());
        assertNotNull(avlTree.getRoot());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertEquals(2, avlTree.remove(2).intValue());
        assertEquals(6, avlTree.size());
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(5);
        expected.add(1);
        expected.add(8);
        expected.add(3);
        expected.add(7);
        expected.add(10);
        assertEquals(expected, levelorder());
        assertEquals(1, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(3, avlTree.getRoot().getLeft().getRight().getData().intValue());
    }
    @Test
    public void removeLeaf() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        assertEquals(7, avlTree.size());
        assertNotNull(avlTree.getRoot());
        assertEquals(10, avlTree.remove(10).intValue());
        assertEquals(6, avlTree.size());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertEquals(8, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(7, avlTree.getRoot().getRight().getLeft().getData().intValue());
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(5);
        expected.add(2);
        expected.add(8);
        expected.add(1);
        expected.add(3);
        expected.add(7);
        assertEquals(expected, levelorder());
        assertNull(avlTree.getRoot().getRight().getRight());
    }
    @Test
    public void removeLeafDegenerate() {
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(3);
        avlTree.add(4);
        assertEquals(4, avlTree.size());
        assertEquals(2, avlTree.getRoot().getData().intValue());
        assertEquals(1, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(3, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(4, avlTree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(4, avlTree.remove(4).intValue());
        assertEquals(3, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(3, avlTree.size());
        assertEquals(0, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals(0, avlTree.getRoot().getRight().getHeight());
        assertEquals(1, avlTree.getRoot().getHeight());
        assertEquals(0, avlTree.getRoot().getBalanceFactor());
    }

    /**
     *       5
     *      /   \
     *     3    10
     *    /     /
     *   1     7
     *   \     \
     *    2     8
     */
    @Test
    public void removeOneChild() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        assertEquals(4, avlTree.size());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertEquals(3, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(3, avlTree.remove(3).intValue());
        assertEquals(3, avlTree.size());
        assertEquals(1, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(10, avlTree.getRoot().getRight().getData().intValue());
    }
    @Test
    public void degenerateRemoveOneChild() {
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(3);
        avlTree.add(4);
        assertEquals(4,avlTree.size());
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, levelorder());
        assertEquals(3, avlTree.remove(3).intValue());
        ArrayList<Integer> expected2 = new ArrayList<>();
        expected2.add(2);
        expected2.add(1);
        expected2.add(4);
        assertEquals(expected2, levelorder());
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /  \
     *   1     7    12
     *   \     \    /
     *    2     8  11
     */
    @Test
    public void removeRoot() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        assertEquals(9, avlTree.size());
        assertEquals(5, avlTree.remove(5).intValue());
        assertEquals(3, avlTree.getRoot().getData().intValue());
        assertEquals(2, avlTree.getRoot().getLeft().getData().intValue());
        assertEquals(1, avlTree.getRoot().getLeft().getLeft().getData().intValue());
        assertEquals(8, avlTree.size());
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(2);
        expected.add(8);
        expected.add(1);
        expected.add(7);
        expected.add(11);
        expected.add(10);
        expected.add(12);
        assertEquals(expected, levelorder());
    }
    /**
     *       5
     *      /   \
     *     3      10
     *    / \    /  \
     *   1   4  7    12
     *   \      \    /
     *    2      8   11
     */
    @Test
    public void multipleRemoveTwoChildCases() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(4);
        assertEquals(10, avlTree.size());
        assertEquals(11, avlTree.remove(11).intValue());
        assertEquals(10, avlTree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(12, avlTree.getRoot().getRight().getRight().getRight().getData().intValue());
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(9, avlTree.size());
        expected.add(5);
        expected.add(2);
        expected.add(8);
        expected.add(1);
        expected.add(3);
        expected.add(7);
        expected.add(10);
        expected.add(4);
        expected.add(12);
        assertEquals(expected, levelorder());
        assertEquals(8, avlTree.remove(8).intValue());
        assertEquals(8, avlTree.size());
        ArrayList<Integer> expected2 = new ArrayList<>();
        expected2.add(5);
        expected2.add(2);
        expected2.add(10);
        expected2.add(1);
        expected2.add(3);
        expected2.add(7);
        expected2.add(12);
        expected2.add(4);
        assertEquals(expected2, levelorder());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertEquals(10, avlTree.getRoot().getRight().getData().intValue());
        assertEquals(7, avlTree.getRoot().getRight().getLeft().getData().intValue());
        assertEquals(12, avlTree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(0, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avlTree.getRoot().getRight().getHeight());
        assertEquals(3, avlTree.getRoot().getHeight());
    }
    /////////////////////////////////////////////////////////////////////////
    //Get tests
    @Test(expected = IllegalArgumentException.class)
    public void getNull() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        assertEquals(9, avlTree.size());
        avlTree.get(null);
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /  \
     *   1     7    12
     *   \     \    /
     *    2     8  11
     */
    @Test(expected = NoSuchElementException.class)
    public void cannotFindData() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.get(50);
    }
    @Test(expected = NoSuchElementException.class)
    public void getFromEmpty() {
        avlTree.get(60);
    }
    @Test(expected = NoSuchElementException.class)
    public void getFromSizeOneFail() {
        avlTree.add(70);
        assertEquals(1, avlTree.size());
        assertEquals(70, avlTree.getRoot().getData().intValue());
        avlTree.get(69);
    }
    @Test
    public void getFromSizeOneWorks() {
        avlTree.add(70);
        assertEquals(1, avlTree.size());
        assertEquals(70, avlTree.getRoot().getData().intValue());
        assertEquals(70, avlTree.get(70).intValue());
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /  \
     *   1     7    12
     *   \     \    /
     *    2     8  11
     */
    @Test
    public void genGetTest() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        assertEquals(9, avlTree.size());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertEquals(7, avlTree.get(7).intValue());
        assertEquals(11, avlTree.get(11).intValue());
    }
    ////////////////////////////////////////////////////////////////////////////////
    //contains tests
    @Test(expected = IllegalArgumentException.class)
    public void containsInEmpty() {
        avlTree.contains(null);
    }
    @Test
    public void falseContainsInSizeOne() {
        avlTree.add(60);
        assertFalse(avlTree.contains(70));
    }
    @Test
    public void trueContainsInSizeOne() {
        avlTree.add(50);
        assertTrue(avlTree.contains(50));
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /  \
     *   1     7    12
     *   \     \    /
     *    2     8  11
     */
    @Test
    public void genContains() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        assertEquals(9, avlTree.size());
        assertEquals(5, avlTree.getRoot().getData().intValue());
        assertTrue(avlTree.contains(10));
        assertFalse(avlTree.contains(13));
        assertTrue(avlTree.contains(1));
        assertTrue(avlTree.contains(2));
        assertTrue(avlTree.contains(8));
        assertTrue(avlTree.contains(11));
        assertTrue(avlTree.contains(12));
    }
    ///////////////////////////////////////////////
    //height tests
    @Test
    public void emptyHeight() {
        assertEquals(-1, avlTree.height());
    }
    @Test
    public void sizeOneHeight() {
        avlTree.add(70);
        assertEquals(1, avlTree.size());
        assertEquals(70, avlTree.getRoot().getData().intValue());
        assertEquals(0, avlTree.height());
    }
    @Test
    public void findHeightDegenerate() {
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(3);
        avlTree.add(4);
        assertEquals(2, avlTree.height());
        assertEquals(4, avlTree.size());
        assertEquals(2, avlTree.getRoot().getData().intValue());
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /  \
     *   1     7    12
     *   \    / \    / \
     *    2   6  8  11  15
     */
    @Test
    public void genFindHeight() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        assertEquals(11, avlTree.size());
        assertEquals(3, avlTree.height());
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /   \
     *   1     7      12
     *   /\    / \    / \
     *  0  2   6  8  11  15
     *                     \
     *                      20
     */
    @Test
    public void heightTestTwo() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(13, avlTree.size());
        assertEquals(4, avlTree.height());
    }
    ///////////////////////////////////////////////
    //cLear test
    @Test
    public void clearEmpty() {
        avlTree.clear();
        assertNull(avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }
    @Test
    public void clearSizeOne() {
        avlTree.add(60);
        avlTree.clear();
        assertNull(avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }
    @Test
    public void genClear() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(13, avlTree.size());
        avlTree.clear();
        assertEquals(0, avlTree.size());
        assertEquals(-1, avlTree.height());
    }
    ////////////////////////////////
    //elements within distance test
    @Test(expected =  IllegalArgumentException.class)
    public void distanceTest() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(13, avlTree.size());
        avlTree.elementsWithinDistance(null, 0);
    }
    @Test(expected =  IllegalArgumentException.class)
    public void negativeDistanceTest() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(13, avlTree.size());
        avlTree.elementsWithinDistance(1, -1);
    }
    @Test(expected = NoSuchElementException.class)
    public void distanceEmpty() {
        avlTree.elementsWithinDistance(0, 0);
    }
    @Test(expected = NoSuchElementException.class)
    public void sizeOneDistance() {
        avlTree.add(6);
        avlTree.elementsWithinDistance(2, 1);
    }
    @Test(expected = NoSuchElementException.class)
    public void distanceNoElement() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(13, avlTree.size());
        avlTree.elementsWithinDistance(25, 3);
    }
    @Test
    public void ewdSizeOne() {
        avlTree.add(25);
        HashSet<Integer> expected = new HashSet<>();
        expected.add(25);
        assertEquals(expected, avlTree.elementsWithinDistance(25, 0));
        assertEquals(expected, avlTree.elementsWithinDistance(25, 1));
    }
    @Test
    public void ewdIncludesAllElements() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(8, avlTree.getRoot().getData().intValue());
        assertEquals(13, avlTree.size());
        HashSet<Integer> expected = new HashSet<>();
        expected.add(5);
        expected.add(10);
        expected.add(3);
        expected.add(1);
        expected.add(2);
        expected.add(7);
        expected.add(8);
        expected.add(12);
        expected.add(11);
        expected.add(6);
        expected.add(15);
        expected.add(0);
        expected.add(20);
        assertEquals(expected, avlTree.elementsWithinDistance(0, avlTree.size()));
    }
    @Test
    public void normalEWDTest() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(8, avlTree.getRoot().getData().intValue());
        assertEquals(13, avlTree.size());
        HashSet<Integer> expected = new HashSet<>();
        expected.add(6);
        expected.add(7);
        expected.add(5);
        expected.add(2);
        expected.add(8);
        assertEquals(expected, avlTree.elementsWithinDistance(7, 2));
    }
    @Test
    public void normalEWDTestTwo() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(8, avlTree.getRoot().getData().intValue());
        assertEquals(13, avlTree.size());
        HashSet<Integer> expected = new HashSet<>();
        expected.add(8);
        expected.add(11);
        expected.add(5);
        expected.add(2);
        expected.add(7);
        expected.add(10);
        expected.add(1);
        expected.add(3);
        expected.add(6);
        expected.add(15);
        expected.add(12);
        expected.add(20);
        assertEquals(expected, avlTree.elementsWithinDistance(8, 3));
    }
    @Test
    public void distanceFromTwoChildNode() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(8, avlTree.getRoot().getData().intValue());
        assertEquals(13, avlTree.size());
        HashSet<Integer> expected = new HashSet<>();
        expected.add(11);
        expected.add(10);
        expected.add(15);
        expected.add(12);
        expected.add(20);
        expected.add(8);
        expected.add(5);
        assertEquals(expected, avlTree.elementsWithinDistance(11, 2));
    }
    @Test
    public void distanceFromLeaf() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(8, avlTree.getRoot().getData().intValue());
        assertEquals(13, avlTree.size());
        HashSet<Integer> expected = new HashSet<>();
        expected.add(3);
        expected.add(2);
        expected.add(1);
        expected.add(0);
        expected.add(5);
        expected.add(7);
        expected.add(8);
        assertEquals(expected, avlTree.elementsWithinDistance(3, 3));
    }
    @Test
    public void ewdInsanelyLargeTest() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        avlTree.add(20);
        assertEquals(8, avlTree.getRoot().getData().intValue());
        assertEquals(13, avlTree.size());
        HashSet<Integer> expected = new HashSet<>();
        expected.add(5);
        expected.add(10);
        expected.add(3);
        expected.add(1);
        expected.add(2);
        expected.add(7);
        expected.add(8);
        expected.add(12);
        expected.add(11);
        expected.add(6);
        expected.add(15);
        expected.add(0);
        expected.add(20);
        assertEquals(expected, avlTree.elementsWithinDistance(0, 10000000));
    }
    ////////////////////////////
    //One quick fun test to do
    @Test
    public void getAndRemove() {
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(3);
        avlTree.add(1);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(8);
        avlTree.add(12);
        avlTree.add(11);
        avlTree.add(6);
        avlTree.add(15);
        avlTree.add(0);
        assertEquals(3, avlTree.get(3).intValue());
        assertEquals(3, avlTree.remove(3).intValue());
        assertEquals(11, avlTree.size());
        assertEquals(8, avlTree.getRoot().getData().intValue());
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(5);
        expected.add(11);
        expected.add(1);
        expected.add(7);
        expected.add(10);
        expected.add(12);
        expected.add(0);
        expected.add(2);
        expected.add(6);
        expected.add(15);
        assertEquals(expected, levelorder());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), avlTree.remove(expected.get(i)).intValue());
            assertEquals(11 - i - 1, avlTree.size());
        }
        assertEquals(0, avlTree.size());
        assertNull(avlTree.getRoot());
    }
}
