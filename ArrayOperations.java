/**
 * @author Palmer
 * @version 1.0
 * This class implements several methods to operate Arrays
 */
import java.util.Random;
public class ArrayOperations {
    public static void main(String[] args) {
        Random rand = new Random();
    //    boolean works = true;
        Integer[] binaryArr = new Integer[10];
        for(int i = 0; i < 10; i++) {
            Integer[] numArr = new Integer[100];
            for(int j = 0; j < numArr.length; j++) {
                numArr[j] = rand.nextInt(100);
            }
            insertionSort(numArr);
            binaryArr[i] = binarySearch(numArr, 13);
            /* 
            if (binarySearch(numArr, 13) == -1) {
                int answer = linearSearch(numArr, 13);
                if (answer != -1) {
                    works = false;
                    break;
                }
            }
            */
        }
        print(binaryArr);
        /* 
        Integer[] numbers = new Integer[100];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(100);
        }
        selectionSort(numbers); 
        print(numbers);
        System.out.println(binarySearch(numbers, 7));
      //  System.out.println(binarySearch(numbers, 3));

        
        int[] numArray = new int[100];
        for(int i = 0; i < 100; i++) {
            for (int j = 0; j < numbers.length; j++) {
                numbers[j] = rand.nextInt(100);
            }
            long start = System.nanoTime();
            selectionSort(numbers);
            long end = System.nanoTime();
            long start2 = System.nanoTime();
            insertionSort(numbers);
            long end2 = System.nanoTime();
            int num = (int)((double)((end - start) - (end2 - start2)) / (end - start) * 100);
            numArray[i] = num;
        }
        print(numArray);
        */
    }

    /**
     * This method prints int arrays
     * @param arr an int array
     */
    public static void print(Integer[] arr) {
        System.out.print("{");
        for(int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println(arr[arr.length-1] + "}");
    }

    /**
     * This method sorts an int array using selection sort, in O(n^2) time
     * @param arr an int array
     */
    public static void selectionSort(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    /**
     * This method sorts an int array using insertion sort in O(n^2) time
     * @param arr an int array
     */
    public static void insertionSort(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int comparePointer = i;
            do {comparePointer--;
            } while (comparePointer > 0 && arr[i] < arr[comparePointer]);
            int temp = arr[i];
            for (int j = i; j > comparePointer + 1; j--) {
                arr[j] = arr[j - 1];
            }
            arr[comparePointer] = temp;
        }
    }

    /**
     * This method searches an int array in O(n) time
     * @param arr an int array
     * @return an int
     */
    public static int linearSearch(Integer[] arr, int target) {
        for(int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method implements a binary search in O(log(n)) time
     * @param arr an int array
     * @return an int
     */
    public static int binarySearch(Integer[] arr, int target) {
        return recursiveBinarySearch(arr, target, 0, arr.length-1);
    }
    /**
     * This private method is used to binary search an array
     * @param arr an int array
     * @param target an int
     * @param start an int
     * @param end an int
     * @return an int
     */
    private static int recursiveBinarySearch(Integer[] arr, int target, int start, int end) {
        int mid = ((end - start) / 2) + start;
        if (target == arr[mid]) {
            return mid;
        } else if (end - start == 1) {
            return -1;
        } else if (target < arr[mid]) {
            return recursiveBinarySearch(arr, target, start, mid - 1);
        } else {
            return recursiveBinarySearch(arr, target, mid + 1, end);
        }
    }
}
