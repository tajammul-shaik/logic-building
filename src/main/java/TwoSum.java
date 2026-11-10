//• nums: [2, 7, 11, 15]
//• target: 9

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TwoSum {

    private static final int MIN_LEN = 2;
    private static final int MAX_LEN = 100_000;
    private static final int MIN_VAL = -1_000_000_000;
    private static final int MAX_VAL =  1_000_000_000;

    static int[] twoSumBruteForce(int[] data, int target) {
        // Start j at i + 1 to prevent using the same element twice
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] + data[j] == target) {
                    return new int[]{i, j}; // Return immediately when found
                }
            }
        }
        return new int[]{}; // Return empty array if no pair found
    }

    static int[] twoSumOptimal(int[] data, int target){
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < data.length; i++){
            int complement = target - data[i];

            if (map.containsKey(complement)){
                return new int[]{map.get(complement), i};
            }

            map.put(data[i], i);

        }

        return new int[]{};
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Validated Array Length Input
        int arraySize;
        while (true) {
            System.out.print("Enter Array Size (2 to 10^5): ");
            arraySize = sc.nextInt();
            if (arraySize >= MIN_LEN && arraySize <= MAX_LEN) {
                break;
            }
            System.out.println("Invalid size! Must satisfy: 2 <= size <= 100,000.");
        }

        // 2. Validated Element Inputs
        int[] arr = new int[arraySize];
        for (int i = 0; i < arr.length; i++) {
            while (true) {
                System.out.print("Insert value at index " + i + " (-10^9 to 10^9): ");
                int val = sc.nextInt();
                if (val >= MIN_VAL && val <= MAX_VAL) {
                    arr[i] = val;
                    break;
                }
                System.out.println("Invalid element! Must satisfy: -10^9 <= value <= 10^9.");
            }
        }

        // 3. Validated Target Input
        int target;
        while (true) {
            System.out.print("Provide a Target (-10^9 to 10^9): ");
            target = sc.nextInt();
            if (target >= MIN_VAL && target <= MAX_VAL) {
                break;
            }
            System.out.println("Invalid target! Must satisfy: -10^9 <= target <= 10^9.");
        }

        System.out.println("\nAll inputs validated successfully!");
        System.out.println("Array : " + Arrays.toString(arr));
        System.out.println("Target: " + target);

        int[] resultOfTwoSumBruteForce = twoSumBruteForce(arr, target);
        int[] resultOfTwoSumOptimal = twoSumOptimal(arr, target);


        System.out.println("Optimal Approach Result : "+Arrays.toString(resultOfTwoSumOptimal));

        if (resultOfTwoSumBruteForce.length == 2) {
            System.out.println("Result of Index Brute Force Approach : " + Arrays.toString(resultOfTwoSumBruteForce));
        } else {
            System.out.println("No two sum solution found.");
        }

        sc.close();
    }

}
