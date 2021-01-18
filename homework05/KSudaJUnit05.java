import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit05 {

    private static final int TIMEOUT = 200;

    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends MaxHeapStudentTest {
    }

    /* Add other Student unit tests here, uncomment to activate
    public static class MyKarthinkJunit05 extends KarthinkJunit05 {
    }
     */

    public static class TestMaxHeapNoArgConstructor {
        private MaxHeap<IntegerSecret> mh;

        @Before
        public void setup() {
            mh = new MaxHeap<>();
        }
        @Test(timeout = TIMEOUT)
        public void testConstructor() {
            assertEquals("size", 0, mh.size());
            IntegerSecret[] expected = new IntegerSecret[MaxHeap.INITIAL_CAPACITY];
            assertArrayEquals("backingArray", expected, mh.getBackingArray());
        }
    }

    public static class TestMaxHeapArrayConstructor {
        private MaxHeap<IntegerSecret> mh;

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayEmpty() {
            testListAndExpected(new int[0], new int[0]);
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayOne() {
            testListAndExpected(new int[] {1}, new int[] {0});
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testConstructorNullCollection() {
            mh = new MaxHeap<>(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testConstructorNullData() {
            ArrayList<IntegerSecret> list = new ArrayList<>();
            list.add(null);
            mh = new MaxHeap<>(list);
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayOneTwo() {
            testListAndExpected(new int[] {1, 2}, new int[] {1, 0});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayTwoOne() {
            testListAndExpected(new int[] {2, 1}, new int[] {0, 1});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayOneTwoThree() {
            testListAndExpected(new int[] {1, 2, 3}, new int[] {2, 1, 0});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayTwoOneThree() {
            testListAndExpected(new int[] {2, 1, 3}, new int[] {2, 1, 0});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayOneThreeTwo() {
            testListAndExpected(new int[] {1, 3, 2}, new int[] {1, 0, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayTwoThreeOne() {
            testListAndExpected(new int[] {2, 3, 1}, new int[] {1, 0, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayThreeOneTwo() {
            testListAndExpected(new int[] {3, 1, 2}, new int[] {0, 1, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testConstructorArrayThreeTwoOne() {
            testListAndExpected(new int[] {3, 2, 1}, new int[] {0, 1, 2});
        }

        /**
         * Tests the list for heap population using constructor and expected resulting backing array
         * @param ints list of ints to create IntegerSecrets with in order to be passed to constructor
         * @param order Order from ints to put into expected
         */
        private void testListAndExpected(int[] ints, int[] order) {
            ArrayList<IntegerSecret> list = new ArrayList<>();
            for (int i:ints) {
                list.add(new IntegerSecret(i));
            }
            IntegerSecret[] expected = new IntegerSecret[ints.length * 2 + 1];
            for (int i = 0; i < order.length; ++i) {
                expected[i + 1] = list.get(order[i]);
            }
            mh = new MaxHeap<>(list);
            assertEquals("size", ints.length, mh.size());
            assertArrayEquals("backingArray", expected, mh.getBackingArray());
        }
    }

    public static class TestMaxHeap {
        private MaxHeap<IntegerSecret> mh;

        @Test(timeout = TIMEOUT)
        public void testAddOne() {
            testAddAndExpected(new int[] {1}, new int[] {0});
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddNull() {
            mh = new MaxHeap<>();
            mh.add(null);
        }

        @Test(timeout = TIMEOUT)
        public void testAddNullCheckNoChanges() {
            mh = new MaxHeap<>();
            try {
                mh.add(null);
            } catch (Exception ex) {
            }
            testEmpty(MaxHeap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testAddGrow() {
            int[] ints = new int[MaxHeap.INITIAL_CAPACITY];
            int[] order = new int[MaxHeap.INITIAL_CAPACITY];
            for (int i = 0; i < MaxHeap.INITIAL_CAPACITY; ++i) {
                // add in reverse order so no swapping occurs
                ints[i] = MaxHeap.INITIAL_CAPACITY - i;
                // expected order should be same order added
                order[i] = i;
            }
            testAddAndExpected(ints, order);
        }

        @Test(timeout = TIMEOUT)
        public void testAddDoubleGrow() {
            int[] ints = new int[MaxHeap.INITIAL_CAPACITY * 2];
            int[] order = new int[MaxHeap.INITIAL_CAPACITY * 2];
            for (int i = 0; i < MaxHeap.INITIAL_CAPACITY * 2; ++i) {
                // add in reverse order so no swapping occurs
                ints[i] = MaxHeap.INITIAL_CAPACITY * 2 - i;
                // expected order should be same order added
                order[i] = i;
            }
            testAddAndExpected(ints, order);
        }

        @Test(timeout = TIMEOUT)
        public void testAddOneTwo() {
            testAddAndExpected(new int[] {1, 2}, new int[] {1, 0});
        }

        @Test(timeout = TIMEOUT)
        public void testAddTwoOne() {
            testAddAndExpected(new int[] {2, 1}, new int[] {0, 1});
        }

        @Test(timeout = TIMEOUT)
        public void testAddOneTwoThree() {
            testAddAndExpected(new int[] {1, 2, 3}, new int[] {2, 0, 1});
        }

        @Test(timeout = TIMEOUT)
        public void testAddTwoOneThree() {
            testAddAndExpected(new int[] {2, 1, 3}, new int[] {2, 1, 0});
        }

        @Test(timeout = TIMEOUT)
        public void testAddOneThreeTwo() {
            testAddAndExpected(new int[] {1, 3, 2}, new int[] {1, 0, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testAddTwoThreeOne() {
            testAddAndExpected(new int[] {2, 3, 1}, new int[] {1, 0, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testAddThreeOneTwo() {
            testAddAndExpected(new int[] {3, 1, 2}, new int[] {0, 1, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testAddThreeTwoOne() {
            testAddAndExpected(new int[] {3, 2, 1}, new int[] {0, 1, 2});
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveEmpty() {
            mh = new MaxHeap<>();
            mh.remove();
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveEmptyCheckNoChanges() {
            mh = new MaxHeap<>();
            try {
                mh.remove();
            } catch (Exception ex) {
            }
            testEmpty(MaxHeap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveOne() {
            testRemove(new int[] {1}, new int[] {});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveTwoOne() {
            testRemove(new int[] {2, 1}, new int[] {1});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveThreeTwoOne() {
            testRemove(new int[] {3, 2, 1}, new int[] {1, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveThreeOneTwo() {
            testRemove(new int[] {3, 1, 2}, new int[] {2, 1});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveFourThreeTwoOne() {
            testRemove(new int[] {4, 3, 2, 1}, new int[] {1, 3, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveFourThreeOneTwo() {
            testRemove(new int[] {4, 3, 1, 2}, new int[] {1, 3, 2});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveFourTwoThreeOne() {
            testRemove(new int[] {4, 2, 3, 1}, new int[] {2, 1, 3});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveFiveFourThreeTwoOne() {
            testRemove(new int[] {5, 4, 3, 2, 1}, new int[] {1, 3, 2, 4});
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveFiveThreeFourTwoOne() {
            testRemove(new int[] {5, 3, 4, 2, 1}, new int[] {2, 1, 4, 3});
        }

        @Test(timeout = TIMEOUT)
        public void testGetMax() {
            IntegerSecret[] expected = new IntegerSecret[MaxHeap.INITIAL_CAPACITY];
            expected[1] = new IntegerSecret(1);
            mh = new MaxHeap<>();
            mh.add(expected[1]);
            assertEquals(expected[1], mh.getMax());
            assertEquals(1, mh.size());
            assertArrayEquals(expected, mh.getBackingArray());
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetMaxEmpty() {
            mh = new MaxHeap<>();
            mh.getMax();
        }

        @Test(timeout = TIMEOUT)
        public void testGetMaxEmptyCheckNoChanges() {
            mh = new MaxHeap<>();
            try {
                mh.getMax();
            } catch (Exception ex) {
            }
            testEmpty(MaxHeap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testIsEmptyEmpty() {
            mh = new MaxHeap<>();
            assertTrue(mh.isEmpty());
        }

        @Test(timeout = TIMEOUT)
        public void testIsEmptyOne() {
            mh = new MaxHeap<>();
            mh.add(new IntegerSecret(1));
            assertFalse(mh.isEmpty());
        }

        @Test(timeout = TIMEOUT)
        public void testClearEmpty() {
            mh = new MaxHeap<>();
            mh.clear();
            testEmpty(MaxHeap.INITIAL_CAPACITY);
        }

        @Test(timeout = TIMEOUT)
        public void testClearChangeBackingArray() {
            mh = new MaxHeap<>();
            Object o = mh.getBackingArray();
            mh.clear();
            assertFalse("backingArray change", o == mh.getBackingArray());
        }

        @Test(timeout = TIMEOUT)
        public void testClearOne() {
            ArrayList<IntegerSecret> list = new ArrayList<>();
            list.add(new IntegerSecret(1));
            mh = new MaxHeap<>(list);
            mh.clear();
            testEmpty(MaxHeap.INITIAL_CAPACITY);
        }

        /**
         * Tests the list for heap population using add and expected resulting backing array
         * @param ints list of ints to create IntegerSecrets with in order to be passed to constructor
         * @param order Order from ints to put into expected
         */
        private void testAddAndExpected(int[] ints, int[] order) {
            IntegerSecret[] list = buildList(ints);
            assertEquals("size", ints.length, mh.size());
            testExpected(list, order);
        }

        /**
         * Test Remove by first adding the provided ints, then verify the resulting order
         * @param ints ints used to build the list via calls to add
         * @param order Resulting order of ints after removing the max element
         */
        private void testRemove(int[] ints, int[] order) {
            IntegerSecret[] list = buildList(ints);
            assertEquals(list[0], mh.remove());
            testExpected(list, order);
        }

        /**
         * Builds an array of IntegerSecrets for the given array of ints
         * @param ints Ints to build an IntegerSecret array for
         * @return IntegerSecret array in the same order as the provided ints
         */
        private IntegerSecret[] buildList(int[] ints) {
            IntegerSecret[] list = new IntegerSecret[ints.length];
            mh = new MaxHeap<>();
            for (int i = 0; i < ints.length; ++i) {
                list[i] = new IntegerSecret(ints[i]);
                mh.add(list[i]);
            }
            return list;
        }

        /**
         * Test for the expected IntegerSecret array using the provided list and order indexes
         * @param list Base list used to get the same IntegerSecrets as was added
         * @param order Indexes into the IntegerSecrets for the new order
         */
        private void testExpected(IntegerSecret[] list, int[] order) {
            int size = MaxHeap.INITIAL_CAPACITY;
            while (size <= list.length) {
                size *= 2;
            }
            IntegerSecret[] expected = new IntegerSecret[size];
            for (int i = 0; i < order.length; ++i) {
                expected[i + 1] = list[order[i]];
            }
            assertArrayEquals("backingArray", expected, mh.getBackingArray());
        }

        /**
         * Checks that the MaxHeap is empty with a capacity
         * @param capacity Expected capacity of the MaxHeap
         */
        private void testEmpty(int capacity) {
            assertEquals("size", 0, mh.size());
            assertArrayEquals("backingArray", new IntegerSecret[capacity], mh.getBackingArray());
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
            return "" + data + "(" + secret + ")";
        }
    }
}

