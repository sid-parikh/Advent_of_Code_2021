/* Sid Parikh. Created on December 7, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.util.Arrays;

/**
 * Day 7: The Treachery of Whales
 * <p>
 * The horizontally moving crabs in submarines are going to save us from the scary whale. Let's help them line up! <br>
 * Star One: Using a linear model for fuel cost vs. distance, find the optimal place for the ships to go to. <br>
 * Star Two: The calculation for fuel cost for n units is actually (1 + 2 + ... + n).
 */
public class Day07 extends Solution {
    public Day07() throws IOException {
        super(7);
    }

    /**
     * Calculates fuel cost based on the Star Two formula: where the cost to move a ship n units is
     * (1 + 2 + ... + n) = n(n + 1)/2
     *
     * @param nums   a list of initial positions of ships
     * @param target the position to move all the ships to
     * @return the resultant fuel cost
     */
    private static long calculateFuelTwo(int[] nums, int target) {

        long sum = 0;
        for (int n : nums) {
            int diff = Math.abs(n - target);
            sum += (long) diff * (diff + 1) / 2;
        }

        return sum;
    }

    /**
     * Calculates the fuel cost to move all the ships to the median of their positions.
     *
     * @return the fuel cost (342641)
     */
    @Override
    public String partOne() {
        int[] nums = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        // Target is the median.
        Arrays.sort(nums);

        int target;
        if (nums.length % 2 == 0) {
            target = (nums[nums.length / 2] + nums[nums.length / 2 - 1]) / 2;
        } else {
            target = nums[nums.length / 2];
        }

        int totalFuel = Arrays.stream(nums).map((n) -> Math.abs(target - n)).sum();

        return String.valueOf(totalFuel);

    }

    /**
     * Calculates the lowest possible fuel cost if all ships are moved to any single point and the fuel cost to move a
     * ship n units is calculated by {@link #calculateFuelTwo(int[], int)}.
     *
     * @return the optimized fuel cost (93006301)
     */
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
            if (sum < smallestSum) {
                smallestSum = sum;
            }
        }

        return String.valueOf(smallestSum);
        // Fun fact it's not too inefficient to work!
    }
}
