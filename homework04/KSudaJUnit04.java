import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit04 {

    private static final int TIMEOUT = 200;

    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends BSTStudentTest {
    }

    /* Add other Student unit tests here, uncomment to activate
    public static class MyKSubramanian40JUnitTest extends KSubramanian40JUnitTest {
    }
     */

    public static class TestBSTNoArgConstructor {
        private BST<IntegerSecret> bst;

        @Before
        public void setup() {
            bst = new BST<>();
        }
        @Test(timeout = TIMEOUT)
        public void testConstructor() {
            assertEquals("size", 0, bst.size());
            assertNull("root", bst.getRoot());
        }
    }

    public static class TestBSTArrayConstructor {
        private BST<IntegerSecret> bst;

        @Test(timeout = TIMEOUT)
        public void testConstructorArray() {
            List<IntegerSecret> list = new ArrayList<>();
            bst = new BST<>(list);
            assertEquals("size", 0, bst.size());
            assertNull("root", bst.getRoot());
            list.add(new IntegerSecret(1));
            bst = new BST<>(list);
            assertEquals("size", 1, bst.size());
            assertEquals("root", list.get(0), bst.getRoot().getData());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testConstructorNullCollection() {
            bst = new BST<>(null);
        }
    }

    public static class TestBST {
        private BST<IntegerSecret> bst;

        /**
         * Checks empty BST values
         * @param bst BST to check
         */
        private static void assertEmptyBST(BST<IntegerSecret> bst) {
            assertEquals("size", 0, bst.size());
            assertNull("root", bst.getRoot());
            assertEquals("height", -1, bst.height());
            List<IntegerSecret> empty = new ArrayList<>();
            assertEquals("getMaxDataPerLevel", empty, bst.getMaxDataPerLevel());
            assertEquals("inorder", empty, bst.inorder());
            assertEquals("postorder", empty, bst.postorder());
            assertEquals("preorder", empty, bst.preorder());
            assertEquals("levelorder", empty, bst.levelorder());
            assertEquals("stringify", "0#", stringify(bst));
        }

        /**
         * Checks a BST for a single entry
         * @param bst BST to check
         * @param list List of nodes
         */
        private static void assertSingleBST(BST<IntegerSecret> bst, List<IntegerSecret> list) {
            assertEquals("size", 1, bst.size());
            BSTNode<IntegerSecret> root = bst.getRoot();
            assertNotNull("root", root);
            assertEquals("root.getData()", list.get(0), root.getData());
            assertNull("root.getRight()", root.getRight());
            assertNull("root.getLeft()", root.getLeft());
            assertEquals("height", 0, bst.height());
            assertEquals("getMaxDataPerLevel", list, bst.getMaxDataPerLevel());
            assertEquals("inorder", list, bst.inorder());
            assertEquals("postorder", list, bst.postorder());
            assertEquals("preorder", list, bst.preorder());
            assertEquals("levelorder", list, bst.levelorder());
            assertEquals("stringify", "1#" + list.get(0).data + "(,)", stringify(bst));
        }

        /**
         * Converts BST to a string with node relationship information
         * @param bst BST to stringify
         * @return String for the BST
         */
        private static String stringify(BST<IntegerSecret> bst) {
            StringBuilder sb = new StringBuilder();
            sb.append(bst.size());
            sb.append('#');
            stringify(bst.getRoot(), sb);
            return sb.toString();
        }

        /**
         * Converts a BSTNode with it's subtree to a string with node relationship information
         * @param node BSTNode to stringify
         * @param sb String for the BSTNode subtree
         */
        private static void stringify(BSTNode<IntegerSecret> node, StringBuilder sb) {
            if (node == null) {
                return;
            }
            sb.append(node.getData().data);
            sb.append('(');
            stringify(node.getLeft(), sb);
            sb.append(',');
            stringify(node.getRight(), sb);
            sb.append(')');
        }

        /**
         * Convert a List of IntegerSecrets to a string
         * @param list List to convert
         * @return List converted to a string
         */
        private static String stringify(List<IntegerSecret> list) {
            StringBuilder sb = new StringBuilder();
            for (IntegerSecret is:list) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(is.data);
            }
            return sb.toString();
        }

        /**
         * Checking all the values in one go
         * @param bst BST to check
         * @param size Size of BST
         * @param height Height of BST
         * @param getMaxDataPerLevel String representing list for max data per level
         * @param inorder String representing inorder list
         * @param postorder String representing postorder list
         * @param preorder String representing preorder list
         * @param levelorder String representing levelorder list
         * @param bstString Stringify of the BST
         */
        @SuppressWarnings("CheckStyle")
        private static void assertAll(BST<IntegerSecret> bst, int size, int height,
                                      String getMaxDataPerLevel, String inorder, String postorder,
                                      String preorder, String levelorder, String bstString) {
            assertEquals("size", size, bst.size());
            assertEquals("height", height, bst.height());
            assertEquals("getMaxDataPerLevel", getMaxDataPerLevel, stringify(bst.getMaxDataPerLevel()));
            assertEquals("inorder", inorder, stringify(bst.inorder()));
            assertEquals("postorder", postorder, stringify(bst.postorder()));
            assertEquals("preorder", preorder, stringify(bst.preorder()));
            assertEquals("levelorder", levelorder, stringify(bst.levelorder()));
            assertEquals("stringify", bstString, stringify(bst));
        }

        /**
         * Add all from list to BST
         * @param bst BST to add all to
         * @param list List to add to BST from
         */
        private static void addAll(BST<IntegerSecret> bst, List<IntegerSecret> list) {
            for (IntegerSecret is:list) {
                bst.add(is);
            }
        }

        /**
         * Get a list to create full, complete, balanced tree of size 3 with holes
         * @return List containing a the nodes in the right order to create a full, balanced, complete tree
         */
        private static List<IntegerSecret> get3EvenTreeList() {
            return getListFromArray(new int[] {4, 2, 6});
        }

        /**
         * Get a list to create full, complete, balanced tree of size 7
         * @return List containing a the nodes in the right order to create a full, balanced, complete tree
         */
        private static List<IntegerSecret> get7TreeList() {
            return getListFromArray(new int[] {4, 2, 6, 1, 3, 5, 7});
        }

        /**
         * Get a list to create full, complete, balanced tree of size 7
         * @return List containing a the nodes in the right order to create a full, balanced, complete tree
         */
        private static List<IntegerSecret> get7EvenTreeList() {
            return getListFromArray(new int[] {8, 4, 12, 2, 6, 10, 14});
        }

        /**
         * Create List of IntegerSecret from array of ints
         * @param array Array of ints
         * @return List of IntegerSecrets
         */
        private static List<IntegerSecret> getListFromArray(int[] array) {
            List<IntegerSecret> list = new ArrayList<>(array.length);
            for (int data:array) {
                list.add(new IntegerSecret(data));
            }
            return list;
        }

        @Before
        public void setup() {
            bst = new BST<>();
        }

        @Test(timeout = TIMEOUT)
        public void testAddSingle() {
            List<IntegerSecret> list = getListFromArray(new int[] {1});
            addAll(bst, list);
            assertSingleBST(bst, list);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddFirstNull() {
            bst.add(null);
        }

        @Test(timeout = TIMEOUT)
        public void testAddFirstNullCheckNoChanges() {
            try {
                bst.add(null);
            } catch (Exception ex) {
            }
            assertEmptyBST(bst);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddSecondNull() {
            List<IntegerSecret> list = new ArrayList<>();
            list.add(new IntegerSecret(1));
            bst.add(list.get(0));
            bst.add(null);
        }

        @Test(timeout = TIMEOUT)
        public void testAddSecondNullCheckNoChanges() {
            List<IntegerSecret> list = new ArrayList<>();
            list.add(new IntegerSecret(1));
            bst.add(list.get(0));
            try {
                bst.add(null);
            } catch (Exception ex) {
            }
            assertSingleBST(bst, list);
        }

        @Test(timeout = TIMEOUT)
        public void testAddSecondSmallerFirst() {
            List<IntegerSecret> list = getListFromArray(new int[] {1, 2});
            addAll(bst, list);
            assertAll(bst, 2, 1, "1,2", "1,2",
                "2,1", "1,2", "1,2", "2#1(,2(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testAddSecondLargerFirst() {
            List<IntegerSecret> list = getListFromArray(new int[] {2, 1});
            addAll(bst, list);
            assertAll(bst, 2, 1, "2,1", "1,2",
                "1,2", "2,1", "2,1", "2#2(1(,),)");
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            List<IntegerSecret> list = getListFromArray(new int[] {4, 2, 6, 1, 3, 5, 7});
            addAll(bst, list);
            assertAll(bst, 7, 2, "4,6,7", "1,2,3,4,5,6,7",
                "1,3,2,5,7,6,4", "4,2,1,3,6,5,7", "4,2,6,1,3,5,7",
                "7#4(2(1(,),3(,)),6(5(,),7(,)))");
            for (IntegerSecret is:list) {
                IntegerSecret actual = bst.get(is.getClone());
                assertEquals(is, actual);
                assertTrue(is.sameSecret(actual));
            }
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetEmpty() {
            bst.get(new IntegerSecret(1));
        }

        @Test(timeout = TIMEOUT)
        public void testGetEmptyCheckNoChanges() {
            try {
                bst.get(new IntegerSecret(1));
            } catch (Exception ex) {
            }
            assertEmptyBST(bst);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundLessOnlyRoot() {
            List<IntegerSecret> list = getListFromArray(new int[] {1});
            addAll(bst, list);
            bst.get(new IntegerSecret(0));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundGreaterOnlyRoot() {
            List<IntegerSecret> list = getListFromArray(new int[] {1});
            addAll(bst, list);
            bst.get(new IntegerSecret(2));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundLL() {
            addAll(bst, get3EvenTreeList());
            bst.get(new IntegerSecret(1));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundLR() {
            addAll(bst, get3EvenTreeList());
            bst.get(new IntegerSecret(3));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundRL() {
            addAll(bst, get3EvenTreeList());
            bst.get(new IntegerSecret(5));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundRR() {
            addAll(bst, get3EvenTreeList());
            bst.get(new IntegerSecret(7));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testGetNull() {
            bst.get(null);
        }

        @Test(timeout = TIMEOUT)
        public void testGetNullCheckNoChanges() {
            try {
                bst.get(null);
            } catch (Exception ex) {
            }
            assertEmptyBST(bst);
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            List<IntegerSecret> list = get7EvenTreeList();
            addAll(bst, list);
            for (IntegerSecret is:list) {
                assertTrue("" + is.data, bst.contains(is.getClone()));
            }
            for (int i = 1; i <= 9; i += 2) {
                assertFalse("not " + i, bst.contains(new IntegerSecret(i)));
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testContainsNull() {
            bst.contains(null);
        }

        @Test(timeout = TIMEOUT)
        public void testGetContainsCheckNoChanges() {
            try {
                bst.get(null);
            } catch (Exception ex) {
            }
            assertEmptyBST(bst);
        }

        @Test(timeout = TIMEOUT)
        public void testClearPopulated() {
            addAll(bst, get3EvenTreeList());
            bst.clear();
            assertEmptyBST(bst);
        }

        @Test(timeout = TIMEOUT)
        public void testClearEmpty() {
            bst.clear();
            assertEmptyBST(bst);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveInReverseOrder() {
            List<IntegerSecret> list = get7TreeList();
            List<String> stringifies = new ArrayList<String>();
            for (IntegerSecret is:list) {
                stringifies.add(stringify(bst));
                bst.add(is);
            }
            for (int i = list.size() - 1; i >= 0; --i) {
                IntegerSecret toRemove = list.get(i);
                IntegerSecret removed = bst.remove(toRemove.getClone());
                assertTrue("" + toRemove.data, toRemove.sameSecret(removed));
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveNull() {
            bst.remove(null);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveNullCheckNoChanges() {
            List<IntegerSecret> expected = getListFromArray(new int[] {1});
            bst.add(expected.get(0));
            try {
                bst.remove(null);
            } catch (Exception ex) {
            }
            assertSingleBST(bst, expected);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveNotFound() {
            addAll(bst, get7EvenTreeList());
            bst.remove(new IntegerSecret(1));
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveNotFoundCheckNoChanges() {
            addAll(bst, get3EvenTreeList());
            String expected = stringify(bst);
            try {
                bst.remove(new IntegerSecret(1));
            } catch (Exception ex) {
            }
            assertEquals(expected, stringify(bst));
        }

        /**
         * Helper function to test various scenarios with removing
         * @param addArray starting tree in add order
         * @param remove value to remove (searched for in addArray
         * @param stringifyResult Expected stringify(bst) after adding and removing element
         */
        private void testRemove(int[] addArray, int remove, String stringifyResult) {
            bst.clear();
            List<IntegerSecret> list = getListFromArray(addArray);
            IntegerSecret toRemove = list.get(list.indexOf(new IntegerSecret(remove)));
            addAll(bst, list);
            IntegerSecret removed = bst.remove(toRemove.getClone());
            assertTrue(toRemove.sameSecret(removed));
            assertEquals(stringifyResult, stringify(bst));
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveSingleChildLeft() {
            testRemove(new int[] {3, 2, 4, 1, 5}, 2, "4#3(1(,),4(,5(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveSingleChildRight() {
            testRemove(new int[] {3, 2, 4, 1, 5}, 4, "4#3(2(1(,),),5(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveSingleChildLeftInner() {
            testRemove(new int[] {3, 1, 5, 2, 4}, 2, "4#3(1(,),5(4(,),))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveSingleChildRightInner() {
            testRemove(new int[] {3, 1, 5, 2, 4}, 4, "4#3(1(,2(,)),5(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRootSingleChildLeft() {
            testRemove(new int[] {3, 1, 2}, 3, "2#1(,2(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRootSingleChildRight() {
            testRemove(new int[] {1, 3, 2}, 1, "2#3(2(,),)");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CLeft() {
            testRemove(new int[] {4, 2, 6, 1, 3, 5, 7}, 2, "6#4(3(1(,),),6(5(,),7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CRight() {
            testRemove(new int[] {4, 2, 6, 1, 3, 5, 7}, 6, "6#4(2(1(,),3(,)),7(5(,),))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CRoot() {
            testRemove(new int[] {4, 2, 6, 1, 3, 5, 7}, 4, "6#5(2(1(,),3(,)),6(,7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CDepthRight() {
            testRemove(new int[] {8, 9, 4, 2, 6, 1, 3, 5, 7}, 4, "8#8(5(2(1(,),3(,)),6(,7(,))),9(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CDepthLeft() {
            testRemove(new int[] {-1, -2, 4, 2, 6, 1, 3, 5, 7}, 4, "8#-1(-2(,),5(2(1(,),3(,)),6(,7(,))))");
        }
    }

    private static class IntegerSecret implements Comparable<IntegerSecret> {
        private static int seed = 0;
        private int data;
        private int secret;

        /**
         * Creates an object that will be unique by a secret value.
         * @param data Data represented by this object
         */
        public IntegerSecret(int data) {
            this.data = data;
            this.secret = seed++;
        }

        @Override
        public int compareTo(IntegerSecret o) {
            if (o == null) {
                return -1;
            }
            return data - o.data;
        }

        /**
         * Compares two IntegerSecret objects
         * @param o Other IntegerSecret object
         * @return true if both objects have the same data and secret value
         */
        public boolean sameSecret(IntegerSecret o) {
            return compareTo(o) == 0 && secret == o.secret;
        }

        /**
         * Clone the data, but give it a new secret value
         * @return Clone object with different secret
         */
        public IntegerSecret getClone() {
            return new IntegerSecret(data);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof IntegerSecret)) {
                return false;
            }
            return data == ((IntegerSecret) obj).data;
        }
    }
}
