import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * This is a basic set of unit tests for Sorting.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class Karthik_Sorting_JUnit {

    private static final int TIMEOUT = 200;
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alex
                index 1: Ivan
                index 2: Miguel
                index 3: Paige
                index 4: Brooke
                index 5: Sanjana
                index 6: Yotam
                index 7: Nick
                index 8: Reece
                index 9: Destini
         */

        /*
            Sorted Names:
                index 0: Alex
                index 1: Brooke
                index 2: Destini
                index 3: Ivan
                index 4: Miguel
                index 5: Nick
                index 6: Paige
                index 7: Reece
                index 8: Sanjana
                index 9: Yotam
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel");
        tas[3] = new TeachingAssistant("Paige");
        tas[4] = new TeachingAssistant("Brooke");
        tas[5] = new TeachingAssistant("Sanjana");
        tas[6] = new TeachingAssistant("Yotam");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Reece");
        tas[9] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[4];
        tasByName[2] = tas[9];
        tasByName[3] = tas[1];
        tasByName[4] = tas[2];
        tasByName[5] = tas[7];
        tasByName[6] = tas[3];
        tasByName[7] = tas[8];
        tasByName[8] = tas[5];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
    }
    ///////////////////////////////////////////////////////
    //insertion sort tests
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void insertionSortNull() {
        Sorting.insertionSort(null, comp);
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void insertionSortWithNullComparator() {
        Sorting.insertionSort(tas, null);
    }
    //Testing for the adaptive condition of insertion sort
    @Test(timeout = TIMEOUT)
    public void insertionSortOnSortedListTest() {
        TeachingAssistant[] sortedTas = new TeachingAssistant[10];
        sortedTas[0] = new TeachingAssistant("Alex");
        sortedTas[1] = new TeachingAssistant("Brooke");
        sortedTas[2] = new TeachingAssistant("Destini");
        sortedTas[3] = new TeachingAssistant("Ivan");
        sortedTas[4] = new TeachingAssistant("Miguel");
        sortedTas[5] = new TeachingAssistant("Nick");
        sortedTas[6] = new TeachingAssistant("Paige");
        sortedTas[7] = new TeachingAssistant("Reece");
        sortedTas[8] = new TeachingAssistant("Sanjana");
        sortedTas[9] = new TeachingAssistant("Yotam");
        Sorting.insertionSort(sortedTas, comp);
        assertArrayEquals(tasByName,sortedTas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 24 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void insertionSortReversedOrder() {
        TeachingAssistant[] reversed_tas = new TeachingAssistant[10];
        reversed_tas[9] = new TeachingAssistant("Alex");
        reversed_tas[8] = new TeachingAssistant("Brooke");
        reversed_tas[7] = new TeachingAssistant("Destini");
        reversed_tas[6] = new TeachingAssistant("Ivan");
        reversed_tas[5] = new TeachingAssistant("Miguel");
        reversed_tas[4] = new TeachingAssistant("Nick");
        reversed_tas[3] = new TeachingAssistant("Paige");
        reversed_tas[2] = new TeachingAssistant("Reece");
        reversed_tas[1] = new TeachingAssistant("Sanjana");
        reversed_tas[0] = new TeachingAssistant("Yotam");
        Sorting.insertionSort(reversed_tas, comp);
        assertArrayEquals(tasByName, reversed_tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 45 && comp.getCount() != 0);
    }
    ////tests for in-place condition
    @Test(timeout = TIMEOUT)
    public void insertionSortWithDuplicates() {
        tas = new TeachingAssistant[11];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel1");
        tas[3] = new TeachingAssistant("Miguel2");
        tas[4] = new TeachingAssistant("Paige");
        tas[5] = new TeachingAssistant("Brooke");
        tas[6] = new TeachingAssistant("Sanjana");
        tas[7] = new TeachingAssistant("Yotam");
        tas[8] = new TeachingAssistant("Nick");
        tas[9] = new TeachingAssistant("Reece");
        tas[10] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[11];
        tasByName[0] = new TeachingAssistant("Alex");
        tasByName[1] = new TeachingAssistant("Brooke");
        tasByName[2] = new TeachingAssistant("Destini");
        tasByName[3] = new TeachingAssistant("Ivan");
        tasByName[4] = new TeachingAssistant("Miguel1");
        tasByName[5] = new TeachingAssistant("Miguel2");
        tasByName[6] = new TeachingAssistant("Nick");
        tasByName[7] = new TeachingAssistant("Paige");
        tasByName[8] = new TeachingAssistant("Reece");
        tasByName[9] = new TeachingAssistant("Sanjana");
        tasByName[10] = new TeachingAssistant("Yotam");
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 27 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);
    }
    //////////////////////////////////////////////////////
    // bubble-sort tests
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void bubbleSortNull() {
        Sorting.bubbleSort(null, comp);
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void bubbleSortWithNullComparator() {
        Sorting.bubbleSort(tas, null);
    }
    @Test(timeout = TIMEOUT)
    public void testBubbleSort() {
        Sorting.bubbleSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 44 && comp.getCount() != 0);
    }
    //Testing for the adaptive condition of insertion sort
    @Test(timeout = TIMEOUT)
    public void bubbleSortOnSortedListTest() {
        TeachingAssistant[] sortedTas = new TeachingAssistant[10];
        sortedTas[0] = new TeachingAssistant("Alex");
        sortedTas[1] = new TeachingAssistant("Brooke");
        sortedTas[2] = new TeachingAssistant("Destini");
        sortedTas[3] = new TeachingAssistant("Ivan");
        sortedTas[4] = new TeachingAssistant("Miguel");
        sortedTas[5] = new TeachingAssistant("Nick");
        sortedTas[6] = new TeachingAssistant("Paige");
        sortedTas[7] = new TeachingAssistant("Reece");
        sortedTas[8] = new TeachingAssistant("Sanjana");
        sortedTas[9] = new TeachingAssistant("Yotam");
        Sorting.bubbleSort(sortedTas, comp);
        assertArrayEquals(tasByName,sortedTas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 24 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void bubbleSortReversedOrder() {
        TeachingAssistant[] reversed_tas = new TeachingAssistant[10];
        reversed_tas[9] = new TeachingAssistant("Alex");
        reversed_tas[8] = new TeachingAssistant("Brooke");
        reversed_tas[7] = new TeachingAssistant("Destini");
        reversed_tas[6] = new TeachingAssistant("Ivan");
        reversed_tas[5] = new TeachingAssistant("Miguel");
        reversed_tas[4] = new TeachingAssistant("Nick");
        reversed_tas[3] = new TeachingAssistant("Paige");
        reversed_tas[2] = new TeachingAssistant("Reece");
        reversed_tas[1] = new TeachingAssistant("Sanjana");
        reversed_tas[0] = new TeachingAssistant("Yotam");
        Sorting.bubbleSort(reversed_tas, comp);
        assertArrayEquals(tasByName, reversed_tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 45 && comp.getCount() != 0);
    }
    ////tests for in-place condition
    @Test(timeout = TIMEOUT)
    public void bubbleSortWithDuplicates() {
        tas = new TeachingAssistant[11];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel1");
        tas[3] = new TeachingAssistant("Miguel2");
        tas[4] = new TeachingAssistant("Paige");
        tas[5] = new TeachingAssistant("Brooke");
        tas[6] = new TeachingAssistant("Sanjana");
        tas[7] = new TeachingAssistant("Yotam");
        tas[8] = new TeachingAssistant("Nick");
        tas[9] = new TeachingAssistant("Reece");
        tas[10] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[11];
        tasByName[0] = new TeachingAssistant("Alex");
        tasByName[1] = new TeachingAssistant("Brooke");
        tasByName[2] = new TeachingAssistant("Destini");
        tasByName[3] = new TeachingAssistant("Ivan");
        tasByName[4] = new TeachingAssistant("Miguel1");
        tasByName[5] = new TeachingAssistant("Miguel2");
        tasByName[6] = new TeachingAssistant("Nick");
        tasByName[7] = new TeachingAssistant("Paige");
        tasByName[8] = new TeachingAssistant("Reece");
        tasByName[9] = new TeachingAssistant("Sanjana");
        tasByName[10] = new TeachingAssistant("Yotam");
        Sorting.bubbleSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 54 && comp.getCount() != 0);
    }
    ///////////////////////////////////////////////////////
    //merge sort tests
    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 21 && comp.getCount() != 0);
    }
    @Test(expected = IllegalArgumentException.class, timeout =  TIMEOUT)
    public void mergeSortNull() {
        Sorting.mergeSort(null, comp);
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void mergeSortWithNullComparator() {
        Sorting.mergeSort(tas, null);
    }
    @Test(timeout = TIMEOUT)
    public void mergeSortSizeOneArray() {
        TeachingAssistant[] taList = new TeachingAssistant[1];
        taList[0] = new TeachingAssistant("Jacob");
        TeachingAssistant[] sorted = new TeachingAssistant[1];
        sorted[0] = new TeachingAssistant("Jacob");
        Sorting.mergeSort(taList, comp);
        assertArrayEquals(sorted, taList);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() == 0);
    }
    @Test(timeout = TIMEOUT)
    public void mergeSortSizeTwoArray() {
        TeachingAssistant[] taList = new TeachingAssistant[2];
        taList[0] = new TeachingAssistant("Jacob");
        taList[1] = new TeachingAssistant("Alex");
        TeachingAssistant[] sorted = new TeachingAssistant[2];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Jacob");
        Sorting.mergeSort(taList, comp);
        assertArrayEquals(sorted, taList);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 1 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void mergeSortSizeTwoSorted() {
        TeachingAssistant[] taList = new TeachingAssistant[2];
        taList[0] = new TeachingAssistant("Alex");
        taList[1] = new TeachingAssistant("Jacob");
        TeachingAssistant[] sorted = new TeachingAssistant[2];
        sorted[0] = new TeachingAssistant("Alex");
        sorted[1] = new TeachingAssistant("Jacob");
        Sorting.mergeSort(taList, comp);
        assertArrayEquals(sorted, taList);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 1);
    }
    @Test(timeout = TIMEOUT)
    public void mergeSortAlreadySortedArrayEvenLength() {
        TeachingAssistant[] sortedTas = new TeachingAssistant[10];
        sortedTas[0] = new TeachingAssistant("Alex");
        sortedTas[1] = new TeachingAssistant("Brooke");
        sortedTas[2] = new TeachingAssistant("Destini");
        sortedTas[3] = new TeachingAssistant("Ivan");
        sortedTas[4] = new TeachingAssistant("Miguel");
        sortedTas[5] = new TeachingAssistant("Nick");
        sortedTas[6] = new TeachingAssistant("Paige");
        sortedTas[7] = new TeachingAssistant("Reece");
        sortedTas[8] = new TeachingAssistant("Sanjana");
        sortedTas[9] = new TeachingAssistant("Yotam");
        Sorting.mergeSort(sortedTas, comp);
        assertArrayEquals(tasByName,sortedTas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 15 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void mergeSortAlreadySortedOddLength() {
        TeachingAssistant[] sortedTas = new TeachingAssistant[9];
        sortedTas[0] = new TeachingAssistant("Alex");
        sortedTas[1] = new TeachingAssistant("Brooke");
        sortedTas[2] = new TeachingAssistant("Destini");
        sortedTas[3] = new TeachingAssistant("Ivan");
        sortedTas[4] = new TeachingAssistant("Miguel");
        sortedTas[5] = new TeachingAssistant("Nick");
        sortedTas[6] = new TeachingAssistant("Paige");
        sortedTas[7] = new TeachingAssistant("Sanjana");
        sortedTas[8] = new TeachingAssistant("Yotam");
        TeachingAssistant[] expectedSort = new TeachingAssistant[9];
        expectedSort[0] = new TeachingAssistant("Alex");
        expectedSort[1] = new TeachingAssistant("Brooke");
        expectedSort[2] = new TeachingAssistant("Destini");
        expectedSort[3] = new TeachingAssistant("Ivan");
        expectedSort[4] = new TeachingAssistant("Miguel");
        expectedSort[5] = new TeachingAssistant("Nick");
        expectedSort[6] = new TeachingAssistant("Paige");
        expectedSort[7] = new TeachingAssistant("Sanjana");
        expectedSort[8] = new TeachingAssistant("Yotam");
        Sorting.mergeSort(sortedTas, comp);
        assertArrayEquals(expectedSort, sortedTas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 13 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void reversedOrderMergeSort() {
        TeachingAssistant[] reversed_tas = new TeachingAssistant[10];
        reversed_tas[9] = new TeachingAssistant("Alex");
        reversed_tas[8] = new TeachingAssistant("Brooke");
        reversed_tas[7] = new TeachingAssistant("Destini");
        reversed_tas[6] = new TeachingAssistant("Ivan");
        reversed_tas[5] = new TeachingAssistant("Miguel");
        reversed_tas[4] = new TeachingAssistant("Nick");
        reversed_tas[3] = new TeachingAssistant("Paige");
        reversed_tas[2] = new TeachingAssistant("Reece");
        reversed_tas[1] = new TeachingAssistant("Sanjana");
        reversed_tas[0] = new TeachingAssistant("Yotam");
        Sorting.mergeSort(reversed_tas, comp);
        assertArrayEquals(tasByName, reversed_tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void allSameElementMergeSort() {
        TeachingAssistant[] sameNameList = new TeachingAssistant[10];
        sameNameList[0] = new TeachingAssistant("Brooke");
        sameNameList[1] = new TeachingAssistant("Brooke");
        sameNameList[2] = new TeachingAssistant("Brooke");
        sameNameList[3] = new TeachingAssistant("Brooke");
        sameNameList[4] = new TeachingAssistant("Brooke");
        sameNameList[5] = new TeachingAssistant("Brooke");
        sameNameList[6] = new TeachingAssistant("Brooke");
        sameNameList[7] = new TeachingAssistant("Brooke");
        sameNameList[8] = new TeachingAssistant("Brooke");
        sameNameList[9] = new TeachingAssistant("Brooke");
        TeachingAssistant[] sortedList = new TeachingAssistant[10];
        sortedList[0] = new TeachingAssistant("Brooke");
        sortedList[1] = new TeachingAssistant("Brooke");
        sortedList[2] = new TeachingAssistant("Brooke");
        sortedList[3] = new TeachingAssistant("Brooke");
        sortedList[4] = new TeachingAssistant("Brooke");
        sortedList[5] = new TeachingAssistant("Brooke");
        sortedList[6] = new TeachingAssistant("Brooke");
        sortedList[7] = new TeachingAssistant("Brooke");
        sortedList[8] = new TeachingAssistant("Brooke");
        sortedList[9] = new TeachingAssistant("Brooke");
        Sorting.mergeSort(sameNameList, comp);
        assertArrayEquals(sortedList, sameNameList);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 15 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void mergeSortTestWithDuplicates() {
        tas = new TeachingAssistant[11];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Ivan");
        tas[2] = new TeachingAssistant("Miguel1");
        tas[3] = new TeachingAssistant("Miguel2");
        tas[4] = new TeachingAssistant("Paige");
        tas[5] = new TeachingAssistant("Brooke");
        tas[6] = new TeachingAssistant("Sanjana");
        tas[7] = new TeachingAssistant("Yotam");
        tas[8] = new TeachingAssistant("Nick");
        tas[9] = new TeachingAssistant("Reece");
        tas[10] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[11];
        tasByName[0] = new TeachingAssistant("Alex");
        tasByName[1] = new TeachingAssistant("Brooke");
        tasByName[2] = new TeachingAssistant("Destini");
        tasByName[3] = new TeachingAssistant("Ivan");
        tasByName[4] = new TeachingAssistant("Miguel1");
        tasByName[5] = new TeachingAssistant("Miguel2");
        tasByName[6] = new TeachingAssistant("Nick");
        tasByName[7] = new TeachingAssistant("Paige");
        tasByName[8] = new TeachingAssistant("Reece");
        tasByName[9] = new TeachingAssistant("Sanjana");
        tasByName[10] = new TeachingAssistant("Yotam");
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 22 && comp.getCount() != 0);
    }
    ///////////////////////////////////////////////////////////////////////
    //lsd radix sort tests
    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }
    @Test(timeout = TIMEOUT)
    public void lsdRadixEmptyList() {
        int[] list = new int[0];
        int[] sorted = new int[0];
        Sorting.lsdRadixSort(list);
        assertArrayEquals(sorted, list);
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void lsdRadixNullList() {
        Sorting.lsdRadixSort(null);
    }
    @Test(timeout = TIMEOUT)
    public void lsdRadixAlreadySorted() {
        int[] unsortedArray = new int[] {1,2,3,4,5,6,7,8,9,109};
        int[] sortedArray = new int[] {1,2,3,4,5,6,7,8,9,109};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }
    @Test(timeout = TIMEOUT)
    public void lsdRadixReversedOrder() {
        int[] unsortedArray = new int[] {109,99,89,79,69,59,49,39,29,19,9};
        int[] sortedArray = new int[] {9,19,29,39,49,59,69,79,89,99,109};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }
    @Test(timeout = TIMEOUT)
    public void lsdRadixSuperLargeNumberOfDigits() {
        int[] unsortedArray = new int[] {99,59,69,1,3,5,6,7,10000000,Integer.MAX_VALUE,4};
        int[] sortedArray = new int[] {1,3,4,5,6,7,59,69,99,10000000,Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }
    @Test(timeout = TIMEOUT)
    public void lsdRadixWithMinValue() {
        int[] unsortedArray = new int[] {393239,12324,Integer.MAX_VALUE, 3467,2354,5675867,Integer.MIN_VALUE, 123123};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, 2354, 3467, 12324, 123123, 393239, 5675867, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }
    /////////////////////////////////////////////////////////////////
    //Heap Sort Tests
    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void heapSortNullData() {
        Sorting.heapSort(null);
    }
    @Test(timeout = TIMEOUT)
    public void heapSortOnSorted() {
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : sortedArray) {
            unsortedList.add(i);
        }
        int[] expectedsortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }
    @Test(timeout = TIMEOUT)
    public void heapSortReversedOrder() {
        int[] reversedOrder = new int[] {122, 84, 58, 54, 28, 20, 3, -85};
        List<Integer> unsorted = new ArrayList<>();
        for (int i: reversedOrder) {
            unsorted.add(i);
        }
        int[] sortedList = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsorted);
        assertArrayEquals(sortedList, actualArray);
    }
    @Test(timeout = TIMEOUT)
    public void heapSortWithDuplicates() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 20, 122, -85, 3};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-85, 3, 20, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }
    ///////////////////////////////////////////////////////
    //kth select tests
    @Test(timeout = TIMEOUT)
    public void testKthSelect() {
        assertEquals(new TeachingAssistant("Destini"), Sorting.kthSelect(3,
            tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 17 && comp.getCount() != 0);
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void kthSelectNullList() {
        Sorting.kthSelect(3, null, comp, new Random(234));
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void kthSelectBadK() {
        Sorting.kthSelect(-1, tas, comp, new Random(234));
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void kthSelectBadKAboveLength() {
        Sorting.kthSelect(tas.length + 1, tas, comp, new Random(234));
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void kthSelectNullRand() {
        Sorting.kthSelect(3, tas, comp, null);
    }
    @Test(timeout = TIMEOUT)
    public void kthSelectSmallest() {
        assertEquals(new TeachingAssistant("Alex"), Sorting.kthSelect(1, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void kthSelectLargest() {
        assertEquals(new TeachingAssistant("Yotam"), Sorting.kthSelect(tas.length, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
    }
    @Test(timeout = TIMEOUT)
    public void kthSelectAll() {
        assertEquals(new TeachingAssistant("Alex"), Sorting.kthSelect(1, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Brooke"), Sorting.kthSelect(2, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 32 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Destini"), Sorting.kthSelect(3, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 47 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Ivan"), Sorting.kthSelect(4, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 65 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Miguel"), Sorting.kthSelect(5, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 83 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Nick"), Sorting.kthSelect(6, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 93 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Paige"), Sorting.kthSelect(7, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 109 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Reece"), Sorting.kthSelect(8, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 125 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Sanjana"), Sorting.kthSelect(9, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 141 && comp.getCount() != 0);
        assertEquals(new TeachingAssistant("Yotam"), Sorting.kthSelect(10, tas, comp, new Random(234)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 154 && comp.getCount() != 0);
    }
    /// Add a test for bad pivots (ones always chosen from the ends)
    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                && ((TeachingAssistant) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}