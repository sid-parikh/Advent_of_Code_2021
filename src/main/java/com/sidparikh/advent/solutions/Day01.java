/* Sid Parikh. Created December 1, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Day 1: Sonar Sweep <br> <br>
 *
 * We're getting some depth measurements from the sonar on our ship. <br>
 * Star One: Find out how the depth is changing (how often it increases). <br>
 * Star Two: To eliminate some noise in the data, use a the sum of a sliding three-measurement window instead. <br> <br>
 *
 * It's a new year! This one wasn't too hard, but it took some time to get back in the groove.
 */

public class Day01 extends Solution {

    public Day01() throws FileNotFoundException {
        super(1);
    }

    /**
     * Counts the number of times a depth measurement represents an increase from the previous one.
     */
    public String partOne() {
        int res = 0;
        // Loop through, convert to ints, and compare.
        // I tried directly using String.compareTo but my result was off by one for some strange reason.
        for (int i = 1; i < input.size(); i++) {
            if (Integer.parseInt(input.get(i)) > Integer.parseInt(input.get(i - 1))) {
                res++;
            }
        }
        return String.valueOf(res);
    }

    /**
     * Counts the number of times a sum of three consecutive measurements represents an increase from the previous sum.
     */
    public String partTwo() {
        // This time just convert to ints first to make our life easier
        int[] nums = input.stream().mapToInt(Integer::parseInt).toArray();
        // Complete groups of three means the final two nums will not start new groups.
        int[] sums = new int[nums.length - 2];

        // Add em up!
        for (int i = 0; i < sums.length; i++) {
            sums[i] = nums[i] + nums[i + 1] + nums[i + 2];
        }

        // Count em!
        int res = 0;
        for (int i = 1; i < sums.length; i++) {
            if (sums[i] > sums[i - 1]) {
                res++;
            }
        }

        return String.valueOf(res);
    }


}
