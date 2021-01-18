import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * import java.util.ArrayList;
 * import java.util.Comparator;
 * import java.util.List;
 * import java.util.Random;
 * import java.util.PriorityQueue;
 */

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Shaotong Sun
 * @version 1.0
 * @userid ssun319
 * @GTID 903453285
 *
 * Collaborators: No
 *
 * Resources: Textbook
 */
public class Sorting {

    /**
     * swap help method
     *
     * @param <T>       data type to sort
     * @param arr       the array that need to be swap
     * @param x         index of value need to be swapped
     * @param y         index of value need to be swapped
     */
    private static <T> void swap(T[] arr, int x, int y) {
        T temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        for (int i = 1; i < arr.length; i++) {
            int n = i;
            while (n != 0 && (comparator.compare(arr[n], arr[n - 1]) < 0)) {
                swap(arr, n, n - 1);
                n--;
            }
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        int stopIndex = arr.length - 1;
        while (stopIndex != 0) {
            int i = 0;
            int lastSwap = 0;
            while (i < stopIndex) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    lastSwap = i;
                }
                i++;
            }
            stopIndex = lastSwap;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[length / 2];
        T[] right = (T[]) new Object[length - (length / 2)];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[i + midIndex];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int l = 0;
        int r = 0;
        while (l < midIndex && r < arr.length - midIndex) {
            if (comparator.compare(left[l], right[r]) <= 0) {
                arr[l + r] = left[l];
                l++;
            } else {
                arr[l + r] = right[r];
                r++;
            }
        }
        while (l < midIndex) {
            arr[l + r] = left[l];
            l++;
        }
        while (r < arr.length - midIndex) {
            arr[l + r] = right[r];
            r++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array cannot be null!");
        }
        if (arr.length == 1 || arr.length == 0) {
            return;
        }
        int size = 1;
        int max = arr[0];
        int temp;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                temp = arr[i] * (-1);
            } else {
                temp = arr[i];
            }
            if (temp > max) {
                max = temp;
            }
        }
        while (max >= 10) {
            size++;
            max /= 10;
        }
        List<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (int iteration = 1; iteration <= size; iteration++) {
            for (int i = 0; i < arr.length; i++) {
                int digit = digit(iteration, arr[i]);
                buckets[digit + 9].add(arr[i]);
            }
            int idx = 0;
            for (List<Integer> x: buckets) {
                for (int xx: x) {
                    arr[idx++] = xx;
                }
                x.clear();
            }
        }
    }

    /**
     * digit helper method
     *
     * @param iteration         which digit we want
     * @param num               number we want to use
     * @return digit
     */
    private static int digit(int iteration, int num) {
        for (int i = 1; i < iteration; i++) {
            num /= 10;
        }
        return num % 10;
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot sort null list!");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] result = new int[data.size()];
        int i = 0;
        while (!(heap.isEmpty())) {
            result[i++] = heap.peek();
            heap.remove();
        }
        return result;
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Error, k out of range!");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Error, rand cannot be null!");
        }
        return quickSelect(k, arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * quickSelect method
     *
     * @param <T> data type to sort
     * @param arr array we need
     * @param start start index
     * @param end end index
     * @param k the index to retrieve data from + 1
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object
     * @return the kth smallest element
     */
    private static <T> T quickSelect(int k, T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (start == end) {
            return arr[start];
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivotIndex];
        swap(arr, start, pivotIndex);
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotValue) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotValue) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        if (j == k - 1) {
            return arr[j];
        } else if (j > k - 1) {
            return quickSelect(k, arr, comparator, rand, start, j - 1);
        } else {
            return quickSelect(k, arr, comparator, rand, j + 1, end);
        }
    }
}
