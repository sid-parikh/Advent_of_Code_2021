/* Sid Parikh. Created on December 6, 2021, for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 6: Lanternfish
 * <p>
 * We need to model the exponentially growing school of lanternfish. <br>
 * Star One: Calculate the number of fish after 80 days. <br>
 * Star Two: Calculate the number of fish after 256 days.
 */
public class Day06 extends Solution {
    public Day06() throws IOException {
        super(6);
    }

    /**
     * Simulates a single day, given an array of longs where the index is the value (days remaining in cycle) of a fish
     * and the value is the number of fish with that value.
     * @param fishArray array where index represents days left and value represents number of fish
     * @return a new array representing the state of fish at the end of the day
     */
    private static long[] simulateDay(long[] fishArray) {
        long[] newFishies = new long[9];

        // Decrease indices by one by copying to a new array and shifting positions by one.
        System.arraycopy(fishArray, 1, newFishies, 0, 8);

        // Fish with a starting value of 0 were not included above, and have special behavior
        // These fish reset to 6 but also create an equal number of fish with an initial value of 8.
        newFishies[6] += fishArray[0];
        newFishies[8] += fishArray[0];

        // Return the new array.
        return newFishies;
    }

    /**
     * Parses the input (a list of values representing fish) and calls {@link #simulateDay(long[])} repeatedly.
     * @param numDays the number of days to simulate
     * @return the total number of fish at the end of {@code numDays} days.
     */
    private String simulateManyDays(int numDays) {
        // Setup: Convert to int[]
        int[] inputFish = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        long[] fishies = new long[9];

        // Index represents day in cycle (given) and value represents count (count how many; increment)
        for (int n : inputFish) {
            fishies[n]++;
        }

        // Simulate numDays days
        for (int n = 0; n < numDays; n++) {
            fishies = simulateDay(fishies);
        }

        // Sum the number of fish
        long total = Arrays.stream(fishies).sum();

        return String.valueOf(total);
    }

    /**
     * Simulates 80 days
     * @return the number of fish after 80 days (360610)
     */
    @Override
    public String partOne() {
        return simulateManyDays(80);
    }

    /**
     * Simulates 256 days
     * @return the number of fish after 256 days (1631629590423)
     */
    @Override
    public String partTwo() {
        // For some odd reason, part two always takes way less time than part one.
        // Maybe some kind of strange compiler optimization?
        return simulateManyDays(256);
    }
}
