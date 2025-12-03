package simulation;

public class MergeSort {

    /**
     * Performs merge sort on an integer array.
     * Merge Sort is a "divide → sort → merge" algorithm.
     *
     * @param arr The unsorted array
     * @return    A new sorted array (descending or ascending? → ascending)
     */
    public static int[] sort(int[] arr) {

        // Base case:
        // If the array contains 0 or 1 element, it is already sorted.
        if (arr.length <= 1) return arr;

        // Find the middle index of the array.
        int mid = arr.length / 2;

        // Split array into two halves: left and right.
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        /**
         * Copy elements into the left and right subarrays.
         *
         * left  ← arr[0 ... mid-1]
         * right ← arr[mid ... end]
         */
        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        // Recursively sort each half.
        left = sort(left);
        right = sort(right);

        // Merge two sorted halves into one sorted array.
        return merge(left, right);
    }


    /**
     * Merges two sorted arrays (left and right) into a single sorted array.
     *
     * @param left  Sorted left half
     * @param right Sorted right half
     * @return      Fully merged and sorted array
     */
    private static int[] merge(int[] left, int[] right) {

        // The merged array will contain all elements from both inputs.
        int[] merged = new int[left.length + right.length];

        // Three pointer indexes:
        int i = 0; // pointer for left array
        int j = 0; // pointer for right array
        int k = 0; // pointer for merged array

        /**
         * Compare elements from left and right arrays,
         * pick the smaller one, and insert into merged[].
         */
        while (i < left.length && j < right.length) {

            // If left's value ≤ right's value, pick from left.
            if (left[i] <= right[j]) {
                merged[k++] = left[i++];
            } else {
                // Else pick from right.
                merged[k++] = right[j++];
            }
        }

        /**
         * Copy any remaining elements.
         *
         * Only one of these loops will run:
         * - if left still has leftover elements
         * - OR if right still has leftover elements
         */
        while (i < left.length) merged[k++] = left[i++];
        while (j < right.length) merged[k++] = right[j++];

        // Return the fully merged, sorted array.
        return merged;
    }
}
