import java.util.Arrays; // for toString and equals only

public class SolutionsWeek09 {

    /**
     * Given an integer array and two indices (left and right), recursively find and
     * return the maximum element in the subarray from arr[left] to arr[right]
     * inclusive.
     * 
     * @param arr   array to scan for max value within a given subarray
     * @param left  int left edge of subarray in arr
     * @param right int right edge of subarray in arr
     * @return int with max value in subarray array[left] through array[right]
     *         inclusive
     */
    public static int findMax(int[] arr, int left, int right) {
        // return value: assume if the first element of the subarray. This is the base
        // case when the length of the subarray is 1.
        int maxValue = arr[left];
        if (left != right) {
            // The subarray has more than one elements.
            // Compare the edges [left] and [right] of the current subarray, keep the
            // largest of the two and recurse.
            int newLeft = left;
            int newRight = right;
            if (arr[left] > arr[right]) {
                // Keep the left edge, move the right edge one position in
                newRight = right - 1;
            } else {
                // Keep the right edge, move the left edge on position out
                newLeft = left + 1;
            }
            // Recurse on the trimmed array
            maxValue = findMax(arr, newLeft, newRight);
        }
        // Done
        return maxValue;
    } // method findMax

    /**
     * A more classical approach to finding the max via recursion. The method
     * follows the patter of merge sort, ie, divide the array into smaller arrays
     * until each division has only one element. By definition, it will be the max
     * value in that length=1 array.
     * 
     * @param arr   array to scan for max value within a given subarray
     * @param left  int left edge of subarray in arr
     * @param right int right edge of subarray in arr
     * @return int with max value in subarray array[left] through array[right]
     *         inclusive
     */
    public static int findMax2(int[] arr, int left, int right) {
        // return value: assume if the first element of the subarray. This is the base
        // case when the length of the subarray is 1.
        int result = arr[left];
        if (left != right) {
            // The subarray has more than one elements. Split it into two halves and recurse
            // on each half to obtain its max value.
            int mid = left + ((right - left) / 2);
            int leftMax = findMax2(arr, left, mid);
            int rightMax = findMax2(arr, mid + 1, right);
            // Decide which of the two halves' max value is the greatest and keep it.
            result = (leftMax > rightMax) ? leftMax : rightMax;
        }
        // Done
        return result;
    } // method findMax2

    /**
     * Recursively reverse the elements of the array **in-place**, swapping elements
     * between `left` and `right`.
     * 
     * @param arr   int[] to reverse
     * @param left  int position of leftward element to swap with rightward element
     * @param right int position of rightward element to swap with leftward element.
     */
    public static void reverseArray(int[] arr, int left, int right) {
        // Recursion stops when left >= right
        if (left < right) {
            // Swap the leftward and rightward elements
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            // Recurse by advancing leftword and rightward by one position in their
            // respective directions.
            reverseArray(arr, left + 1, right - 1);
        }
    } // method reverseArray

    /**
     * Recursively count how many times `target` appears in `arr[index]` to the end
     * of the array.
     * 
     * @param arr    int[] array to process
     * @param target int value to count its occurences
     * @param index  int position of current leftmost boundary of array; the
     *               rightmost boundary is always the end of the array.
     * @return
     */
    public static int countOccurrences(int[] arr, int target, int index) {
        int count = 0;
        // Base case is when the index is out of array bounds
        if (index < arr.length) {
            // Within bounds. Recurse to the next smaller array by advancing index by 1
            count = countOccurrences(arr, target, index + 1);
            // If the current element matches the target value, increase counter
            if (arr[index] == target) {
                count += 1;
            }
        }
        // Done
        return count;
    } // method countOccurrences

    /**
     * Use the recursive binary search approach to find the index of `target` in a
     * **sorted** array. Return `-1` if the element is not found.
     * 
     * @param arr    int[] to process
     * @param target int value to search for
     * @param left   leftward boundary of search scope (initially first element of
     *               arr)
     * @param right  rightward boundary of search scope (initially last element of
     *               arr)
     * @return int with position of target value (will be in interval
     *         [0,arr.length-1]) or -1 if target not present in arr.
     */
    public static int binarySearch(int[] arr, int target, int left, int right) {
        // Expect that target will not be found
        int result = -1;
        // If the arr has more than 1 elements
        if (left <= right && right < arr.length) {
            // Find the middle element
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                // If the middle element is our search target, we are done
                result = mid;
            } else if (arr[mid] < target) {
                // If the search targer is expected above the mid element, focus the search on
                // that right half of the array, excluding the middle element
                result = binarySearch(arr, target, mid + 1, right);
            } else {
                // If the search targer is expected below the mid element, focus the search on
                // that left half of the array, excluding the middle element
                result = binarySearch(arr, target, left, mid - 1);
            }
        }
        // Done
        return result;
    } // method binarySearch

    /**
     * Simple testing. All results should return TRUE.
     */
    public static void main(String[] args) {
        int[] arr = { 3, 2, 100, 1 };
        System.out.println(100 == findMax(arr, 0, 3));
        System.out.println(findMax2(arr, 0, 3) == 100);
        reverseArray(arr, 0, arr.length - 1);
        int[] rra = { 1, 100, 2, 3 };
        System.out.println(Arrays.equals(arr, rra)); // expect 1,100,2,3
        System.out.println(0 == countOccurrences(arr, 5, 0));
        int[] sortedArr = { 1, 2, 3, 4, 5, 6, 7, 8 };
        System.out.println(1 == countOccurrences(sortedArr, 5, 0));
        System.out.println(-1 == binarySearch(sortedArr, 16, 0, arr.length - 1));
    }
}
