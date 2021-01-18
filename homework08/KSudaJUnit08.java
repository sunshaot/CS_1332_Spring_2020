import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit08 {

    private static final int TIMEOUT = 200;

    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends SortingStudentTest {
    }

    /* Add other Student unit tests here, uncomment to activate
    public static class MyKarthikSortingJUnit extends KarthikSortingJUnit {
    }

     */

    public static class TestSorting {
        @Test(timeout = 300000)
        public void massTestSorts() {
            IntegerSecret[] base = new IntegerSecret[]{
                new IntegerSecret(Integer.MIN_VALUE),
                new IntegerSecret(Integer.MIN_VALUE),
                new IntegerSecret(-1),
                new IntegerSecret(-1),
                new IntegerSecret(0),
                new IntegerSecret(0),
                new IntegerSecret(1),
                new IntegerSecret(1),
                new IntegerSecret(Integer.MAX_VALUE),
                new IntegerSecret(Integer.MAX_VALUE)
            };
            recursiveMassTestSorts(base, new IntegerSecret[0], new boolean[base.length],
                new MyComparator(), new MyRevComparator());
        }

        /**
         * Recursively tests the sorts given the starting points
         * @param base Full set to eventually test
         * @param list List to test currently
         * @param used Used items from base
         * @param comparator Comparator in ascending order
         * @param revComparator Comparator in descending order
         */
        private void recursiveMassTestSorts(IntegerSecret[] base, IntegerSecret[] list,
                                            boolean[] used, Comparator<IntegerSecret> comparator,
                                            Comparator<IntegerSecret> revComparator) {
            IntegerSecret[] start = Arrays.copyOf(list, list.length);
            IntegerSecret[] expected = Arrays.copyOf(list, list.length);
            Arrays.sort(expected, comparator);
            Sorting.insertionSort(start, comparator);
            assertArrayEquals("insertionSort", expected, start);
            start = Arrays.copyOf(list, list.length);
            Sorting.bubbleSort(start, comparator);
            assertArrayEquals("bubbleSort", expected, start);
            start = Arrays.copyOf(list, list.length);
            Sorting.mergeSort(start, comparator);
            assertArrayEquals("mergeSort", expected, start);
            int[] intStart = new int[list.length];
            int[] intExpected = new int[list.length];
            for (int i = 0; i < list.length; ++i) {
                intStart[i] = start[i].data;
                intExpected[i] = expected[i].data;
            }
            Sorting.lsdRadixSort(intStart);
            assertArrayEquals("lsdRadixSort", intExpected, intStart);
            List<Integer> heapStart = new ArrayList<>();
            for (int i = 0; i < list.length; ++i) {
                heapStart.add(start[i].data);
            }
            int[] heapResult = Sorting.heapSort(heapStart);
            assertArrayEquals("heapSort", intExpected, heapResult);
            for (int i = 0; i < list.length; ++i) {
                start = Arrays.copyOf(list, list.length);
                // test data because quick might change order
                 assertEquals("" + (i + 1) + "th select", expected[i].data, Sorting.kthSelect(i + 1,
                    start, (o1, o2) -> o1.compareTo(o2), new Random(1)).data);
            }
            expected = Arrays.copyOf(list, list.length);
            Arrays.sort(expected, revComparator);
            start = Arrays.copyOf(list, list.length);
            Sorting.insertionSort(start, revComparator);
            assertArrayEquals("reverse insertionSort", expected, start);
            start = Arrays.copyOf(list, list.length);
            Sorting.bubbleSort(start, revComparator);
            assertArrayEquals("reverse bubbleSort", expected, start);
            start = Arrays.copyOf(list, list.length);
            Sorting.mergeSort(start, revComparator);
            assertArrayEquals("reverse mergeSort", expected, start);
            if (list.length == base.length) {
                return;
            }
            IntegerSecret[] childList = Arrays.copyOf(list, list.length + 1);
            for (int i = 0; i < base.length; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    childList[list.length] = base[i];
                    recursiveMassTestSorts(base, childList, used, comparator, revComparator);
                    used[i] = false;
                }
            }
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testInsertionSortNullArray() {
            Sorting.insertionSort(null, new MyComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testInsertionSortNullComparator() {
            Sorting.insertionSort(new Integer[0], null);
        }

        @Test(timeout = TIMEOUT)
        public void testInsertionSortOrderCompare() {
            Integer[] arr = new Integer[] {1, 2, 6, 5, 3, 4, 7, 8, 9};
            Integer[][] order = new Integer[][] {{2, 1}, {6, 2}, {5, 6}, {5, 2},
                {3, 6}, {3, 5}, {3, 2}, {4, 6}, {4, 5}, {4, 3}, {7, 6}, {8, 7},
                {9, 8}};
            Sorting.insertionSort(arr, new MyOrderingComparator(order));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBubbleSortNullArray() {
            Sorting.bubbleSort(null, new MyComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBubbleSortNullComparator() {
            Sorting.bubbleSort(new Integer[0], null);
        }

        @Test(timeout = TIMEOUT)
        public void testBubbleSortOrderCompare() {
            Integer[] arr = new Integer[] {1, 2, 6, 5, 3, 4, 7, 8, 9};
            Integer[][] order = new Integer[][] {{1, 2}, {2, 6}, {6, 5}, {6, 3},
                {6, 4}, {6, 7}, {7, 8}, {8, 9}, {1, 2}, {2, 5}, {5, 3}, {5, 4},
                {1, 2}, {2, 3}, {3, 4}};
            Sorting.bubbleSort(arr, new MyOrderingComparator(order));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testMergeSortNullArray() {
            Sorting.mergeSort(null, new MyComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testMergeSortNullComparator() {
            Sorting.mergeSort(new Integer[0], null);
        }

        @Test(timeout = TIMEOUT)
        public void testMergeSortOrderCompare() {
            Integer[] arr = new Integer[] {1, 2, 6, 5, 3, 4, 7, 8, 9};
            Integer[][] order = new Integer[][] {{1, 2}, {6, 5}, {1, 5}, {2, 5},
                {3, 4}, {8, 9}, {7, 8}, {3, 7}, {4, 7}, {1, 3}, {2, 3}, {5, 3},
                {5, 4}, {5, 7}, {6, 7}};
            Sorting.mergeSort(arr, new MyOrderingComparator(order));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testLSDRadixSortNullArray() {
            Sorting.lsdRadixSort(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testHeapSortNullArray() {
            Sorting.heapSort(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKthSelectNullArray() {
            Sorting.kthSelect(1, null, (o1, o2) -> o1 == o2 ? 0 : 1, new Random(1));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKthSelectNullComparator() {
            Sorting.kthSelect(1, new Integer[1], null, new Random(1));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKthSelectNullRandom() {
            Sorting.kthSelect(1, new Integer[1], (o1, o2) -> o1.compareTo(o2), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKthSelectZeroK() {
            Sorting.kthSelect(0, new Integer[1], (o1, o2) -> o1.compareTo(o2), new Random(1));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKthSelectPastEndK() {
            Sorting.kthSelect(2, new Integer[1], (o1, o2) -> o1.compareTo(o2), new Random(1));
        }

        @Test(timeout = TIMEOUT)
        public void testkthSelectOrderCompare() {
            Integer[] arr = new Integer[] {1, 2, 6, 5, 3, 4, 7, 8, 9};
            Integer[][] order = new Integer[][] {{2, 1}, {9, 1}, {8, 1}, {7, 1},
                {4, 1}, {3, 1}, {5, 1}, {6, 1}, {2, 1}, {6, 2}, {9, 2}, {8, 2},
                {7, 2}, {4, 2}, {3, 2}, {5, 2}, {6, 2}, {5, 6}, {3, 6}, {4, 6},
                {7, 6}, {9, 6}, {8, 6}, {7, 6}, {5, 4}, {3, 4}};
            Sorting.kthSelect(5, arr, new MyOrderingComparator(order), new MyNotRandom());
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
            if (this == o) {
                return 0;
            }
            if (data == o.data) {
                return Integer.compare(secret, o.secret);
            }
            return Integer.compare(data, o.data);
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
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IntegerSecret)) {
                return false;
            }
            IntegerSecret o = (IntegerSecret) obj;
            return data == o.data && secret == o.secret;
        }

        @Override
        public String toString() {
            return "IntegerSecret{data=" + data + ", secret=" + secret + '}';
        }

        @Override
        public int hashCode() {
            return data * 101 + secret;
        }
    }

    private static class MyComparator implements Comparator<IntegerSecret> {

        @Override
        public int compare(IntegerSecret a, IntegerSecret b) {
            if (a.data < 0 && b.data > 0) {
                return -2;
            }
            if (a.data > 0 && b.data < 0) {
                return 2;
            }
            if (a.data < b.data) {
                return -1;
            }
            if (a.data > b.data) {
                return 1;
            }
            return 0;
        }
    }

    private static class MyRevComparator implements Comparator<IntegerSecret> {

        @Override
        public int compare(IntegerSecret a, IntegerSecret b) {
            if (a.data < 0 && b.data > 0) {
                return 2;
            }
            if (a.data > 0 && b.data < 0) {
                return -2;
            }
            if (a.data < b.data) {
                return 1;
            }
            if (a.data > b.data) {
                return -1;
            }
            return 0;
        }
    }

    private static class MyOrderingComparator implements Comparator<Integer> {
        private Integer[][] pairs;
        private int offset;

        /**
         * Comparator that takes in an expected order to the comparisons
         * @param pairs paris of comparisons
         */
        private MyOrderingComparator(Integer[][] pairs) {
            this.pairs = pairs;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            //System.err.println("{" + o1.intValue() + ", " + o2.intValue() + "},");
            assertThat("comparison#" + offset, o1, anyOf(equalTo(pairs[offset][0]), equalTo(pairs[offset][1])));
            assertThat("comparison#" + offset, o2, anyOf(equalTo(pairs[offset][0]), equalTo(pairs[offset][1])));
            ++offset;
            return Integer.compare(o1, o2);
        }
    }

    private static class MyNotRandom extends Random {
        @Override
        public int nextInt(int bound) {
            return 0;
        }
    }
}

