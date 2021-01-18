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
import java.util.Set;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit07 {

    private static final int TIMEOUT = 200;

    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends AVLStudentTest {
    }

    /* Add other Student unit tests here, uncomment to activate
    public static class MyKarthikAVLJUnit extends KarthikAVLJUnit {
    }

    public static class MyPMaheshJUnit07 extends PMaheshJUnit07 {
    }

     */

    public static class TestBSTNoArgConstructor {
        private AVL<IntegerSecret> avl;

        @Before
        public void setup() {
            avl = new AVL<>();
        }
        @Test(timeout = TIMEOUT)
        public void testConstructor() {
            assertEquals("size", 0, avl.size());
            assertNull("root", avl.getRoot());
        }
    }

    public static class TestAVLArrayConstructor {
        private AVL<IntegerSecret> avl;

        @Test(timeout = TIMEOUT)
        public void testConstructorArray() {
            List<IntegerSecret> list = new ArrayList<>();
            avl = new AVL<>(list);
            assertEquals("size", 0, avl.size());
            assertNull("root", avl.getRoot());
            list.add(new IntegerSecret(1));
            avl = new AVL<>(list);
            assertEquals("size", 1, avl.size());
            assertEquals("root", list.get(0), avl.getRoot().getData());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testConstructorNullCollection() {
            avl = new AVL<>(null);
        }
    }

    public static class TestAVL {
        private AVL<IntegerSecret> avl;

        /**
         * Checks empty AVL values
         * @param avl AVL to check
         */
        private static void assertEmptyAVL(AVL<IntegerSecret> avl) {
            assertEquals("size", 0, avl.size());
            assertNull("root", avl.getRoot());
            assertEquals("height", -1, avl.height());
            List<IntegerSecret> empty = new ArrayList<>();
            assertEquals("stringify", "0#", stringify(avl));
        }

        /**
         * Checks a AVL for a single entry
         * @param avl AVL to check
         * @param list List of nodes
         */
        private static void assertSingleAVL(AVL<IntegerSecret> avl, List<IntegerSecret> list) {
            assertEquals("size", 1, avl.size());
            AVLNode<IntegerSecret> root = avl.getRoot();
            assertNotNull("root", root);
            assertEquals("root.getData()", list.get(0), root.getData());
            assertNull("root.getRight()", root.getRight());
            assertNull("root.getLeft()", root.getLeft());
            assertEquals("height", 0, avl.height());
            assertEquals("stringify", "1#" + list.get(0).data + "(,)", stringify(avl));
        }

        /**
         * Converts AVL to a string with node relationship information
         * @param avl AVL to stringify
         * @return String for the AVL
         */
        private static String stringify(AVL<IntegerSecret> avl) {
            StringBuilder sb = new StringBuilder();
            sb.append(avl.size());
            sb.append('#');
            stringify(avl.getRoot(), sb);
            return sb.toString();
        }

        /**
         * Converts a AVLNode with it's subtree to a string with node relationship information
         * @param node AVLNode to stringify
         * @param sb String for the AVLNode subtree
         */
        private static void stringify(AVLNode<IntegerSecret> node, StringBuilder sb) {
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
         * Checking all the values in one go
         * @param avl AVL to check
         * @param size Size of AVL
         * @param height Height of AVL
         * @param avlString Stringify of the AVL
         */
        @SuppressWarnings("CheckStyle")
        private static void assertAll(AVL<IntegerSecret> avl, int size, int height,
                                      String avlString) {
            assertEquals("size", size, avl.size());
            assertEquals("height", height, avl.height());
            assertEquals("stringify", avlString, stringify(avl));
        }

        /**
         * Add all from list to AVL
         * @param avl AVL to add all to
         * @param list List to add to AVL from
         */
        private static void addAll(AVL<IntegerSecret> avl, List<IntegerSecret> list) {
            for (IntegerSecret is:list) {
                avl.add(is);
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
            avl = new AVL<>();
        }

        @Test(timeout = TIMEOUT)
        public void testAddSingle() {
            List<IntegerSecret> list = getListFromArray(new int[] {1});
            addAll(avl, list);
            assertSingleAVL(avl, list);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddFirstNull() {
            avl.add(null);
        }

        @Test(timeout = TIMEOUT)
        public void testAddFirstNullCheckNoChanges() {
            try {
                avl.add(null);
            } catch (Exception ex) {
            }
            assertEmptyAVL(avl);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddSecondNull() {
            List<IntegerSecret> list = new ArrayList<>();
            list.add(new IntegerSecret(1));
            avl.add(list.get(0));
            avl.add(null);
        }

        @Test(timeout = TIMEOUT)
        public void testAddSecondNullCheckNoChanges() {
            List<IntegerSecret> list = new ArrayList<>();
            list.add(new IntegerSecret(1));
            avl.add(list.get(0));
            try {
                avl.add(null);
            } catch (Exception ex) {
            }
            assertSingleAVL(avl, list);
        }

        @Test(timeout = TIMEOUT)
        public void testAddSecondSmallerFirst() {
            List<IntegerSecret> list = getListFromArray(new int[] {1, 2});
            addAll(avl, list);
            assertAll(avl, 2, 1,
                "2#1(,2(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testAddSecondLargerFirst() {
            List<IntegerSecret> list = getListFromArray(new int[] {2, 1});
            addAll(avl, list);
            assertAll(avl, 2, 1,
                "2#2(1(,),)");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd123() {
            List<IntegerSecret> list = getListFromArray(new int[] {1, 2, 3});
            addAll(avl, list);
            assertAll(avl, 3, 1,
                "3#2(1(,),3(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd321() {
            List<IntegerSecret> list = getListFromArray(new int[] {3, 2, 1});
            addAll(avl, list);
            assertAll(avl, 3, 1,
                "3#2(1(,),3(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd132() {
            List<IntegerSecret> list = getListFromArray(new int[] {1, 3, 2});
            addAll(avl, list);
            assertAll(avl, 3, 1,
                "3#2(1(,),3(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd312() {
            List<IntegerSecret> list = getListFromArray(new int[] {3, 1, 2});
            addAll(avl, list);
            assertAll(avl, 3, 1,
                "3#2(1(,),3(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd13579r10() {
            List<IntegerSecret> list = getListFromArray(new int[] {3, 1, 7, 5, 9, 10});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#7(3(1(,),5(,)),9(,10(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd13579r8() {
            List<IntegerSecret> list = getListFromArray(new int[] {3, 1, 7, 5, 9, 8});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#7(3(1(,),5(,)),9(8(,),))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd13579r6() {
            List<IntegerSecret> list = getListFromArray(new int[] {3, 1, 7, 5, 9, 6});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#5(3(1(,),),7(6(,),9(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd73915r6() {
            List<IntegerSecret> list = getListFromArray(new int[] {7, 3, 9, 1, 5, 6});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#5(3(1(,),),7(6(,),9(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd73915r4() {
            List<IntegerSecret> list = getListFromArray(new int[] {7, 3, 9, 1, 5, 4});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#5(3(1(,),4(,)),7(,9(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd73915r2() {
            List<IntegerSecret> list = getListFromArray(new int[] {7, 3, 9, 1, 5, 2});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#3(1(,2(,)),7(5(,),9(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd73915r0() {
            List<IntegerSecret> list = getListFromArray(new int[] {7, 3, 9, 1, 5, 0});
            addAll(avl, list);
            assertAll(avl, 6, 2,
                "6#3(1(0(,),),7(5(,),9(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAdd4261357() {
            List<IntegerSecret> list = getListFromArray(new int[] {4, 2, 6, 1, 3, 5, 7});
            addAll(avl, list);
            assertAll(avl, 7, 2,
                "7#4(2(1(,),3(,)),6(5(,),7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testAddDuplicate() {
            IntegerSecret initial = new IntegerSecret(1);
            avl.add(initial);
            avl.add(initial.getClone());
            assertAll(avl, 1, 0,
                "1#1(,)");
            IntegerSecret result = avl.getRoot().getData();
            assertTrue(initial.sameSecret(result));
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            List<IntegerSecret> list = getListFromArray(new int[] {4, 2, 6, 1, 3, 5, 7});
            addAll(avl, list);
            assertAll(avl, 7, 2,
                "7#4(2(1(,),3(,)),6(5(,),7(,)))");
            for (IntegerSecret is:list) {
                IntegerSecret actual = avl.get(is.getClone());
                assertEquals(is, actual);
                assertTrue(is.sameSecret(actual));
            }
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetEmpty() {
            avl.get(new IntegerSecret(1));
        }

        @Test(timeout = TIMEOUT)
        public void testGetEmptyCheckNoChanges() {
            try {
                avl.get(new IntegerSecret(1));
            } catch (Exception ex) {
            }
            assertEmptyAVL(avl);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundLessOnlyRoot() {
            List<IntegerSecret> list = getListFromArray(new int[] {1});
            addAll(avl, list);
            avl.get(new IntegerSecret(0));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundGreaterOnlyRoot() {
            List<IntegerSecret> list = getListFromArray(new int[] {1});
            addAll(avl, list);
            avl.get(new IntegerSecret(2));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundLL() {
            addAll(avl, get3EvenTreeList());
            avl.get(new IntegerSecret(1));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundLR() {
            addAll(avl, get3EvenTreeList());
            avl.get(new IntegerSecret(3));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundRL() {
            addAll(avl, get3EvenTreeList());
            avl.get(new IntegerSecret(5));
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotFoundRR() {
            addAll(avl, get3EvenTreeList());
            avl.get(new IntegerSecret(7));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testGetNull() {
            avl.get(null);
        }

        @Test(timeout = TIMEOUT)
        public void testGetNullCheckNoChanges() {
            try {
                avl.get(null);
            } catch (Exception ex) {
            }
            assertEmptyAVL(avl);
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            List<IntegerSecret> list = get7EvenTreeList();
            addAll(avl, list);
            for (IntegerSecret is:list) {
                assertTrue("" + is.data, avl.contains(is.getClone()));
            }
            for (int i = 1; i <= 9; i += 2) {
                assertFalse("not " + i, avl.contains(new IntegerSecret(i)));
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testContainsNull() {
            avl.contains(null);
        }

        @Test(timeout = TIMEOUT)
        public void testGetContainsCheckNoChanges() {
            try {
                avl.get(null);
            } catch (Exception ex) {
            }
            assertEmptyAVL(avl);
        }

        @Test(timeout = TIMEOUT)
        public void testClearPopulated() {
            addAll(avl, get3EvenTreeList());
            avl.clear();
            assertEmptyAVL(avl);
        }

        @Test(timeout = TIMEOUT)
        public void testClearEmpty() {
            avl.clear();
            assertEmptyAVL(avl);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveInReverseOrder() {
            List<IntegerSecret> list = get7TreeList();
            List<String> stringifies = new ArrayList<String>();
            for (IntegerSecret is:list) {
                stringifies.add(stringify(avl));
                avl.add(is);
            }
            for (int i = list.size() - 1; i >= 0; --i) {
                IntegerSecret toRemove = list.get(i);
                IntegerSecret removed = avl.remove(toRemove.getClone());
                assertTrue("" + toRemove.data, toRemove.sameSecret(removed));
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveNull() {
            avl.remove(null);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveNullCheckNoChanges() {
            List<IntegerSecret> expected = getListFromArray(new int[] {1});
            avl.add(expected.get(0));
            try {
                avl.remove(null);
            } catch (Exception ex) {
            }
            assertSingleAVL(avl, expected);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveNotFound() {
            addAll(avl, get7EvenTreeList());
            avl.remove(new IntegerSecret(1));
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveNotFoundCheckNoChanges() {
            addAll(avl, get3EvenTreeList());
            String expected = stringify(avl);
            try {
                avl.remove(new IntegerSecret(1));
            } catch (Exception ex) {
            }
            assertEquals(expected, stringify(avl));
        }

        /**
         * Helper function to test various scenarios with removing
         * @param addArray starting tree in add order
         * @param remove value to remove (searched for in addArray
         * @param stringifyResult Expected stringify(avl) after adding and removing element
         */
        private void testRemove(int[] addArray, int remove, String stringifyResult) {
            avl.clear();
            List<IntegerSecret> list = getListFromArray(addArray);
            IntegerSecret toRemove = list.get(list.indexOf(new IntegerSecret(remove)));
            addAll(avl, list);
            IntegerSecret removed = avl.remove(toRemove.getClone());
            assertTrue(toRemove.sameSecret(removed));
            assertEquals(stringifyResult, stringify(avl));
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
            testRemove(new int[] {3, 1, 2}, 3, "2#2(1(,),)");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRootSingleChildRight() {
            testRemove(new int[] {1, 3, 2}, 1, "2#2(,3(,))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CLeft() {
            testRemove(new int[] {4, 2, 6, 1, 3, 5, 7}, 2, "6#4(1(,3(,)),6(5(,),7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CRight() {
            testRemove(new int[] {4, 2, 6, 1, 3, 5, 7}, 6, "6#4(2(1(,),3(,)),5(,7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CRoot() {
            testRemove(new int[] {4, 2, 6, 1, 3, 5, 7}, 4, "6#3(2(1(,),),6(5(,),7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CDepthRight() {
            testRemove(new int[] {8, 9, 4, 2, 6, 11, 1, 3, 5, 7}, 4, "9#8(3(2(1(,),),6(5(,),7(,))),9(,11(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2CDepthLeft() {
            testRemove(new int[] {-1, -2, 4, 2, 6, 1, 3, 5, 7}, 4, "8#2(-1(-2(,),1(,)),6(3(,5(,)),7(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveDeepRotate13() {
            testRemove(new int[] {8, 4, 12, 2, 6, 14, 1, 3}, 14, "7#4(2(1(,),3(,)),8(6(,),12(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveDeepRotate57() {
            testRemove(new int[] {8, 4, 12, 2, 6, 14, 5, 7}, 14, "7#6(4(2(,),5(,)),8(7(,),12(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveDeepRotate911() {
            testRemove(new int[] {8, 4, 12, 2, 10, 14, 9, 11}, 2, "7#10(8(4(,),9(,)),12(11(,),14(,)))");
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveDeepRotate1315() {
            testRemove(new int[] {8, 4, 12, 2, 10, 14, 13, 15}, 2, "7#12(8(4(,),10(,)),14(13(,),15(,)))");
        }

        /**
         * Tests elementsWithDistance give search value, distance, and expected results
         * @param value Value to search for
         * @param distance Distance from value to include
         * @param expected Expected set of ints
         */
        private void testElementsWithDistance(int value, int distance, int[] expected) {
            List<IntegerSecret> values = getListFromArray(new int[] {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15});
            addAll(avl, values);
            Set<IntegerSecret> actual = avl.elementsWithinDistance(new IntegerSecret(value), distance);
            assertEquals("size", expected.length, actual.size());
            for (int i:expected) {
                assertTrue("Contains " + i, actual.contains(new IntegerSecret(i)));
            }
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance10() {
            testElementsWithDistance(1, 0, new int[]{1});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance11() {
            testElementsWithDistance(1, 1, new int[]{1, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance12() {
            testElementsWithDistance(1, 2, new int[]{1, 2, 3, 4});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance13() {
            testElementsWithDistance(1, 3, new int[]{1, 2, 3, 4, 6, 8});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance14() {
            testElementsWithDistance(1, 4, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 12});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance20() {
            testElementsWithDistance(2, 0, new int[]{2});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance21() {
            testElementsWithDistance(2, 1, new int[]{1, 2, 3, 4});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance22() {
            testElementsWithDistance(2, 2, new int[]{1, 2, 3, 4, 6, 8});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance23() {
            testElementsWithDistance(2, 3, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 12});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance80() {
            testElementsWithDistance(8, 0, new int[]{8});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance81() {
            testElementsWithDistance(8, 1, new int[]{4, 8, 12});
        }

        @Test(timeout = TIMEOUT)
        public void testElementsWithinDistance82() {
            testElementsWithDistance(8, 2, new int[]{2, 4, 6, 8, 10, 12, 14});
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testElementsWithDistanceNullValue() {
            avl.elementsWithinDistance(null, 0);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testElementsWithDistanceNegativeDistance() {
            avl.elementsWithinDistance(new IntegerSecret(1), -1);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testElementsWithDistanceNotFound() {
            avl.add(new IntegerSecret(-1));
            avl.elementsWithinDistance(new IntegerSecret(1), 10);
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

        @Override
        public String toString() {
            return "IntegerSecret{data=" + data + ", secret=" + secret + '}';
        }

        @Override
        public int hashCode() {
            return data;
        }
    }
}

