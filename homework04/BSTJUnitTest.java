import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class BSTJUnitTest {
    private BST<Integer> tree;
    @Before
    public void setup() {
        tree = new BST<>();
    }

    @Test
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }
    /////////////////////////////////////////////////////////
    //Add to tree tests
    @Test (expected = IllegalArgumentException.class)
    public void addErrorTest() {
        tree.add(null);
    }
    @Test
    public void addToEmptyTree() {
        tree.add(50);
        assertEquals(1, tree.size());
        assertNotNull(tree.getRoot());
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
        tree.add(100);
        tree.add(150);
        tree.add(80);
        tree.add(85);
        assertEquals(4, tree.size());
        assertNotNull(tree.getRoot());
        assertEquals(100, tree.getRoot().getData().intValue());
        assertEquals(80, tree.getRoot().getLeft().getData().intValue());
        assertEquals(150, tree.getRoot().getRight().getData().intValue());
        assertEquals(85, tree.getRoot().getLeft().getRight().getData().intValue());
    }
    @Test
    public void addDegenerateCase() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        assertEquals(4, tree.size());
        assertNotNull(tree.getRoot());
        assertEquals(1, tree.getRoot().getData().intValue());
        assertEquals(2, tree.getRoot().getRight().getData().intValue());
        assertEquals(3, tree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(4, tree.getRoot().getRight().getRight().getRight().getData().intValue());
    }
    @Test
    public void addDuplicate() {
        tree.add(100);
        tree.add(150);
        tree.add(80);
        tree.add(85);
        tree.add(96);
        tree.add(80);
        assertNotNull(tree.getRoot());
        assertEquals(5, tree.size());
        assertEquals(100, tree.getRoot().getData().intValue());
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
    public void addTest() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(4);
        assertEquals(8, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        assertNotNull(tree.getRoot());
    }
    /////////////////////////////////////////////////////
    //Remove Tests
    @Test(expected = IllegalArgumentException.class)
    public void removeNull() {
        tree.remove(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyTree() {
        tree.remove(5);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeElementNotInTreeSizeOne() {
        tree.add(6);
        tree.remove(4);
    }
    @Test(expected = NoSuchElementException.class)
    public void removeNotInTree() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        assertEquals(5, tree.size());
        assertNotNull(tree.getRoot());
        tree.remove(7);
    }
    @Test
    public void removeSizeOneTree() {
        tree.add(6);
        assertEquals(6, tree.remove(6).intValue());
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }
    @Test
    public void removeLeaf() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        assertEquals(7, tree.size());
        assertNotNull(tree.getRoot());
        assertEquals(2, tree.remove(2).intValue());
        assertEquals(6, tree.size());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals(1, tree.getRoot().getLeft().getLeft().getData().intValue());
    }
    @Test
    public void removeLeafTwo() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        assertEquals(7, tree.size());
        assertNotNull(tree.getRoot());
        assertEquals(8, tree.remove(8).intValue());
        assertEquals(6, tree.size());
        assertEquals(7, tree.getRoot().getRight().getLeft().getData().intValue());
        assertEquals(2, tree.remove(2).intValue());
        assertEquals(5, tree.size());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertEquals(1, tree.getRoot().getLeft().getLeft().getData().intValue());
    }
    @Test
    public void removeLeafDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        assertEquals(4, tree.size());
        assertEquals(4, tree.remove(4).intValue());
        assertNull(tree.getRoot().getRight().getRight().getRight());
        assertEquals(3, tree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(3, tree.size());
        assertEquals(1, tree.getRoot().getData().intValue());
        assertEquals(3, tree.remove(3).intValue());
        assertEquals(2, tree.size());
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        assertEquals(7, tree.size());
        assertEquals(1, tree.remove(1).intValue());
        assertEquals(6, tree.size());
        assertEquals(2, tree.getRoot().getLeft().getLeft().getData().intValue());
        assertEquals(7, tree.remove(7).intValue());
        assertEquals(5, tree.size());
        assertEquals(8, tree.getRoot().getRight().getLeft().getData().intValue());
        assertNotNull(tree.getRoot());
        assertEquals(5, tree.getRoot().getData().intValue());
    }
    @Test
    public void degenerateRemoveOneChild() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        assertEquals(4,tree.size());
        assertEquals(1, tree.getRoot().getData().intValue());
        assertEquals(3, tree.remove(3).intValue());
        assertEquals(4, tree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(3, tree.size());
        assertEquals(1, tree.remove(1).intValue());
        assertEquals(2, tree.size());
        assertEquals(2, tree.getRoot().getData().intValue());
        assertEquals(4, tree.getRoot().getRight().getData().intValue());
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
    public void removeTwoChild() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        assertEquals(9, tree.size());
        assertEquals(10, tree.remove(10).intValue());
        assertEquals(11, tree.getRoot().getRight().getData().intValue());
        assertEquals(12, tree.getRoot().getRight().getRight().getData().intValue());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertEquals(8, tree.size());
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(4);
        assertEquals(10, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        assertEquals(10, tree.remove(10).intValue());
        assertEquals(9, tree.size());
        assertEquals(11, tree.getRoot().getRight().getData().intValue());
        assertEquals(12, tree.getRoot().getRight().getRight().getData().intValue());
        assertEquals(3, tree.remove(3).intValue());
        assertEquals(8, tree.size());
        assertEquals(4, tree.getRoot().getLeft().getData().intValue());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(1, tree.getRoot().getLeft().getLeft().getData().intValue());
        assertEquals(2, tree.getRoot().getLeft().getLeft().getRight().getData().intValue());
        assertEquals(5, tree.remove(5).intValue());
        assertEquals(7, tree.size());
        assertEquals(7, tree.getRoot().getData().intValue());
        assertEquals(8, tree.getRoot().getRight().getLeft().getData().intValue());
    }
    /////////////////////////////////////////////////////////////////////////
    //Get tests
    @Test(expected = IllegalArgumentException.class)
    public void getNull() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        assertEquals(9, tree.size());
        tree.get(null);
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.get(50);
    }
    @Test(expected = NoSuchElementException.class)
    public void getFromEmpty() {
        tree.get(60);
    }
    @Test(expected = NoSuchElementException.class)
    public void getFromSizeOneFail() {
        tree.add(70);
        assertEquals(1, tree.size());
        assertEquals(70, tree.getRoot().getData().intValue());
        tree.get(69);
    }
    @Test
    public void getFromSizeOneWorks() {
        tree.add(70);
        assertEquals(1, tree.size());
        assertEquals(70, tree.getRoot().getData().intValue());
        assertEquals(70, tree.get(70).intValue());
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        assertEquals(9, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        assertEquals(7, tree.get(7).intValue());
        assertEquals(11, tree.get(11).intValue());
    }
    ////////////////////////////////////////////////////////////////////////////////
    //contains tests
    @Test(expected = IllegalArgumentException.class)
    public void containsInEmpty() {
        tree.contains(null);
    }
    @Test
    public void falseContainsInSizeOne() {
        tree.add(60);
        assertFalse(tree.contains(70));
    }
    @Test
    public void trueContainsInSizeOne() {
        tree.add(50);
        assertTrue(tree.contains(50));
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        assertEquals(9, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        assertTrue(tree.contains(10));
        assertFalse(tree.contains(13));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(8));
        assertTrue(tree.contains(11));
        assertTrue(tree.contains(12));
    }
    /////////////////////////////////////////////
    //Pre-order traversal test
    @Test
    public void preOrderEmpty() {
        assertEquals(new ArrayList<Integer>(), tree.preorder());
        assertEquals(0, tree.preorder().size());
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void preOrderSizeOne() {
        tree.add(34);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(34);
        assertEquals(expected, tree.preorder());
        assertEquals(1, tree.preorder().size());
        assertEquals(34, tree.getRoot().getData().intValue());
    }
    @Test
    public void preOrderDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, tree.preorder());
        assertEquals(5, tree.preorder().size());
        assertEquals(1, tree.getRoot().getData().intValue());
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
    public void genPreorder() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        assertEquals(11, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        //Expected = [5,3,1,2,10,7,6,8,12,11,15]
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(3);
        expected.add(1);
        expected.add(2);
        expected.add(10);
        expected.add(7);
        expected.add(6);
        expected.add(8);
        expected.add(12);
        expected.add(11);
        expected.add(15);
        assertEquals(expected, tree.preorder());
    }
    /////////////////////////////////////////
    //in-order traversal tests
    @Test
    public void inOrderEmpty() {
        assertEquals(new ArrayList<Integer>(), tree.inorder());
        assertEquals(0, tree.inorder().size());
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void inOrderSizeOne() {
        tree.add(34);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(34);
        assertEquals(expected, tree.inorder());
        assertEquals(1, tree.inorder().size());
        assertEquals(34, tree.getRoot().getData().intValue());
    }
    @Test
    public void inOrderDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, tree.inorder());
        assertEquals(5, tree.inorder().size());
        assertEquals(1, tree.getRoot().getData().intValue());
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
    public void genInorder() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        assertEquals(11, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        //Expected = [1,2,3,5,6,7,8,10,11,12,15]
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(5);
        expected.add(6);
        expected.add(7);
        expected.add(8);
        expected.add(10);
        expected.add(11);
        expected.add(12);
        expected.add(15);
        assertEquals(expected, tree.inorder());
    }
    ////////////////////////////////////////////////////
    //post-order
    @Test
    public void postOrderEmpty() {
        assertEquals(new ArrayList<Integer>(), tree.postorder());
        assertEquals(0, tree.postorder().size());
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void postOrderSizeOne() {
        tree.add(34);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(34);
        assertEquals(expected, tree.postorder());
        assertEquals(1, tree.postorder().size());
        assertEquals(34, tree.getRoot().getData().intValue());
    }
    @Test
    public void postOrderDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        assertEquals(expected, tree.postorder());
        assertEquals(5, tree.postorder().size());
        assertEquals(1, tree.getRoot().getData().intValue());
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
    public void genPostorder() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        assertEquals(11, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        //Expected = [2,1,3,6,8,7,11,15,12,10,5]
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        expected.add(3);
        expected.add(6);
        expected.add(8);
        expected.add(7);
        expected.add(11);
        expected.add(15);
        expected.add(12);
        expected.add(10);
        expected.add(5);
        assertEquals(expected, tree.postorder());
    }
    //////////////////////////////////////////
    //level order traversal tests
    @Test
    public void levelOrderEmpty() {
        ArrayList<Integer> expected = new ArrayList<Integer>();
        assertEquals(expected, tree.levelorder());
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void levelOrderSizeOne() {
        tree.add(34);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(34);
        assertEquals(expected, tree.levelorder());
        assertEquals(1, tree.levelorder().size());
        assertEquals(34, tree.getRoot().getData().intValue());
    }
    @Test
    public void levelOrderDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, tree.levelorder());
        assertEquals(5, tree.levelorder().size());
        assertEquals(1, tree.getRoot().getData().intValue());
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
    public void genLevelorder() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        assertEquals(11, tree.size());
        assertEquals(5, tree.getRoot().getData().intValue());
        //Expected = [5,3,10,1,7,12,2,6,8,11,15]
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(3);
        expected.add(10);
        expected.add(1);
        expected.add(7);
        expected.add(12);
        expected.add(2);
        expected.add(6);
        expected.add(8);
        expected.add(11);
        expected.add(15);
        assertEquals(expected, tree.levelorder());
    }
    ///////////////////////////////////////////////
    //height tests
    @Test
    public void emptyHeight() {
        assertEquals(-1, tree.height());
    }
    @Test
    public void sizeOneHeight() {
        tree.add(70);
        assertEquals(1, tree.size());
        assertEquals(70, tree.getRoot().getData().intValue());
        assertEquals(0, tree.height());
    }
    @Test
    public void findHeightDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        assertEquals(3, tree.height());
        assertEquals(4, tree.size());
        assertEquals(1, tree.getRoot().getData().intValue());
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        assertEquals(11, tree.size());
        assertEquals(3, tree.height());
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
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(20);
        assertEquals(13, tree.size());
        assertEquals(4, tree.height());
    }
    ///////////////////////////////////////////////
    //cLear test
    @Test
    public void clearEmpty() {
        tree.clear();
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void clearSizeOne() {
        tree.add(60);
        tree.clear();
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void genClear() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(20);
        assertEquals(13, tree.size());
        tree.clear();
        assertEquals(0, tree.size());
        assertEquals(-1, tree.height());
    }
    //////////////////////////////////////////////////////
    //Get Max Data Per Level Tests
    @Test
    public void maxDataEmpty() {
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
        assertEquals(expected, tree.getMaxDataPerLevel());
    }
    @Test
    public void maxDataSizeOne() {
        tree.add(60);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(60);
        assertEquals(1, tree.size());
        assertNotNull(tree.getRoot());
        assertEquals(expected, tree.getMaxDataPerLevel());
        assertEquals(1, tree.getMaxDataPerLevel().size());
    }
    @Test
    public void getMaxDataDegenerate() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, tree.getMaxDataPerLevel());
        assertEquals(1, tree.getRoot().getData().intValue());
        assertEquals(4, tree.getMaxDataPerLevel().size());
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
    public void genGetMaxData() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        //expected = [5,10,12,15]
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(10);
        expected.add(12);
        expected.add(15);
        assertEquals(expected, tree.getMaxDataPerLevel());
        assertEquals(4, tree.getMaxDataPerLevel().size());
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
    public void genGetMaxDataTwo() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(20);
        assertEquals(13, tree.size());
        ArrayList<Integer> expected = new ArrayList<>();
        //expected = [5,10,12,15,20]
        expected.add(5);
        expected.add(10);
        expected.add(12);
        expected.add(15);
        expected.add(20);
        assertEquals(expected, tree.getMaxDataPerLevel());
        assertEquals(5, tree.getMaxDataPerLevel().size());
    }
    /**
     *       5
     *      /   \
     *     3    10
     *    /     /   \
     *   1     7      12
     *   /\    / \    / \
     *  0  2   6  8  11  15
     *  /
     * -1
     */
    @Test
    public void genMaxTestThree() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(-1);
        ArrayList<Integer> expected = new ArrayList<>();
        //expected = [5,10,12,15,-1]
        expected.add(5);
        expected.add(10);
        expected.add(12);
        expected.add(15);
        expected.add(-1);
        assertEquals(expected, tree.getMaxDataPerLevel());
        assertEquals(5, tree.getMaxDataPerLevel().size());
    }
    ///////////////////////////////////////////////////////////////
    //get root tests
    @Test
    public void getNullRoot() {
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
    @Test
    public void getRootSizeOne() {
        tree.add(15);
        assertEquals(15, tree.getRoot().getData().intValue());
        assertEquals(1, tree.size());
    }
    @Test
    public void genGetRoot() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(-1);
        assertEquals(5, tree.getRoot().getData().intValue());
    }
    ////////////////////////////////////////////////////////////////
    //get size tests
    @Test
    public void getSizeEmpty() {
        assertEquals(0, tree.size());
    }
    @Test
    public void getSizeOne() {
        tree.add(5);
        assertEquals(1, tree.size());
    }
    @Test
    public void genGetSize() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(-1);
        assertEquals(13, tree.size());
    }
    /////////////////////////////////////////////////////
    //One quick fun test to do

    public void getAndRemove() {
        tree.add(5);
        tree.add(10);
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(7);
        tree.add(8);
        tree.add(12);
        tree.add(11);
        tree.add(6);
        tree.add(15);
        tree.add(0);
        tree.add(-1);
        Integer[] dataList = {5,10,3,1,2,7,8,12,11,6,15,0,-1};
        assertEquals(13, tree.size());
        for (int i = 0; i < dataList.length; i++) {
            assertEquals(dataList[i], tree.remove(dataList[i]));
            assertEquals(dataList.length - i - 1, tree.size());
        }
        assertNull(tree.getRoot());
        assertEquals(0, tree.size());
    }
}
