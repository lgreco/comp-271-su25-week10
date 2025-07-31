import java.util.Arrays; // for use of .toString() only

public class Merge {

    /**
     * Method accepts two arrays that are already sorted in ascending order and
     * merges them in an so sorted output array.
     * 
     * @param A int[] sorted array
     * @param B int[] sorted array
     * @return int[] that contains the elements from A and B also sorted.
     */
    static int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        // Initialize points to active leftmost elements for the three arrays
        int i = 0; // index for input array A
        int j = 0; // index for input array B
        int k = 0; // index for output array C
        /*
         * While both arrays have active leftmost elements to consider, select the
         * smallest of the two to add to the output array and advance the corresponding
         * input array's leftmost index by 1.
         */
        while (i < A.length && j < B.length) {
            if (A[i] < B[j]) {
                C[k++] = A[i++];
            } else {
                C[k++] = B[j++];
            }
        }
        // In case array A has elements to process
        while (i < A.length) {
            C[k++] = A[i++];
        }
        // In case array B has elements to process
        while (j < B.length) {
            C[k++] = B[j++];
        }
        return C;
    } // method merge

    /**
     * Recursively sorts a given array by decomposing it to single element arrays,
     * then passing them to the merge method. 
     * 
     * @param array int[] array to sort
     * @returns sorted int[]
     */
    static int[] mergeSort(int[] array) {
        // Initialize the output
        int[] result;
        if (array.length == 1) {
            // Base case - input array has one element. By definition
            // it's already sorted.
            result = new int[1];
            result[0] = array[0];
        } else {
            // Split input array into two halves
            int mid = array.length / 2;
            int[] left = new int[mid];
            int[] right = new int[mid];
            // populate left half
            for (int i = 0; i < mid; i++) {
                left[i] = array[i];
            }
            // populate right half ... index i-mid for 0, 1, ... for right array
            for (int i = mid; i < array.length; i++) {
                right[i - mid] = array[i];
            }
            // Feed the halves to this method in case they need to be halved again
            int[] sortedLeft = mergeSort(left);
            int[] sortedRight = mergeSort(right);
            // Recursion ultimately returns control here and we can begin 
            // merging the sorted halves. Note that the first sorted halves
            // we'll see here will have length 1.
            result = merge(sortedLeft, sortedRight);
        }
        // Done
        return result;
    } // method mergeSort

    /** Demo code */
    public static void main(String[] args) {
        int[] test = { 4, 3, 2, 6 };
        int[] sortedTest = mergeSort(test);
        System.out.printf("\n%s\n\n",Arrays.toString(sortedTest));
    }
}