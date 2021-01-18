import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a MaxHeap.
 *
 * @author Shaotong Sun
 * @version 1.0
 * @userid ssun319
 * @GTID 903453285
 *
 * Collaborators: No
 *
 * Resources: Only from textbook
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot but maxheap with list!");
        } else {
            size = data.size();
            backingArray = (T[]) (new Comparable[2 * size + 1]);
            for (int i = 1; i <= data.size(); i++) {
                if (data.get(i - 1) == null) {
                    throw new IllegalArgumentException("Error, cannot but maxheap with list that has null in it!");
                } else {
                    backingArray[i] = data.get(i - 1);
                }
            }
            for (int i = (size / 2); i > 0; i--) {
                heapify(i);
            }
        }
    }

    /**
     * The helper for maxheaps heapify
     * @param i the currect location index
     */
    private void heapify(int i) {
        int max = i;
        int l = 2 * i + 1;
        int r = 2 * i;
        if (l <= size && backingArray[l].compareTo(backingArray[max]) > 0) {
            max = l;
        }
        if (r <= size && backingArray[r].compareTo(backingArray[max]) > 0) {
            max = r;
        }
        if (max != i) {
            T temp = backingArray[i];
            backingArray[i] = backingArray[max];
            backingArray[max] = temp;
            heapify(max);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot add null to heap!");
        } else {
            if (size + 2 == backingArray.length) {
                T[] temp = (T[]) (new Comparable[backingArray.length * 2]);
                for (int i = 1; i <= size; i++) {
                    temp[i] = backingArray[i];
                }
                backingArray = temp;
            }
            backingArray[size + 1] = data;
            size++;
            for (int i = (size / 2); i > 0; i /= 2) {
                heapify(i);
            }
            heapify(1);
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Error, heap is empty!");
        } else {
            T data = backingArray[1];
            if (size == 1) {
                backingArray[1] = null;
                size--;
                return data;
            } else {
                backingArray[1] = backingArray[size];
                backingArray[size] = null;
                size--;
                heapify(1);
                return data;
            }
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Error, the heap is empty!");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
