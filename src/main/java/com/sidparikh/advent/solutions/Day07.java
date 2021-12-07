package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.Arrays;

public class Day07 extends Solution {
    public Day07() {
        super(7);
    }

    @Override
    public String partOne() {
        int[] nums = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        // I think the target is the median

        Arrays.sort(nums);

        int target;
        if (nums.length % 2 == 0) {
            target = (nums[nums.length / 2] + nums[nums.length / 2 - 1])/2;
        } else {
            target = nums[nums.length / 2];
        }

        int totalFuel = Arrays.stream(nums).map((n) -> Math.abs(target - n)).sum();

        return String.valueOf(totalFuel);

    }

    @Override
    public String partTwo() {
        int[] nums = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        // Trying to minimize the costs
        // Cost from a to b = 1 + 2 + ... + |a - b|
        // Nth partial sum of 1 + 2 + 3 + 4 + ... = n*(n+1)/2
        // So we need to find t that minimizes the sum of [|x - t| * (|x - t| + 1) / 2] for each x in nums

        // Simple solution would be to check every value between the min and max of nums
        // That would be pretty inefficient, I think
        // Let's try it anyway

        int max = nums[0];
        int min = nums[0];
        for (int n : nums) {
            max = Math.max(n, max);
            min = Math.min(n, max);
        }
        long smallestSum = calculateFuelTwo(nums, min);

        for (int n = min + 1; n <= max; n++) {
            long sum = calculateFuelTwo(nums, n);
            if (sum < smallestSum) smallestSum = sum;
        }

        return String.valueOf(smallestSum);
        // Fun fact it's not too inefficient to work!
    }

    private static long calculateFuelTwo(int[] nums, int target) {

        long sum = 0;
        for (int n : nums) {
            int diff = Math.abs(n - target);
            sum += (long) diff * (diff + 1) / 2;
        }

        return sum;
    }
}
