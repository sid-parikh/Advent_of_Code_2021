/* Sid Parikh. Created December 1, 2021 for Advent of Code */
package com.sidparikh.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 1: Sonar Sweep
 * It's a new year! This one wasn't too hard, but it took some time to get back in the groove.
 */

public class Day01 {

    /**
     * Counts the number of times a depth measurement represents an increase from the previous one.
     */
    public static int partOne(List<String> input) {
        int res = 0;
        // Loop through, convert to ints, and compare.
        // I tried directly using String.compareTo but my result was off by one for some strange reason.
        for (int i = 1; i < input.size(); i++) {
            if (Integer.parseInt(input.get(i)) > Integer.parseInt(input.get(i - 1))) {
                res++;
            }
        }
        return res;
    }

    public static int partTwo(List<String> input) {
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

        return res;
    }

    /**
     * Gets the input from the file's location on my PC. At some point, I'll make an interface or class that handles
     * these daily tasks. And maybe I'll find a way to use a relative path, so it's not so long and ugly.
     */
    public static List<String> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("C:\\Users\\sidparikh\\Documents\\Advent of Code\\Advent of Code 2021\\src\\com\\sidparikh\\advent\\inputs\\day01.txt"));
        List<String> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }
        return input;
    }

    /**
     * Solves the puzzle.
     * Another method that needs to be extracted into some interface or class at some point.
     */
    public static void main(String[] args) throws FileNotFoundException {
        List<String> puzzleInput = getInput();

        int resOne = partOne(puzzleInput);
        int resTwo = partTwo(puzzleInput);

        System.out.printf("Day 01\nPart 1: %d\nPart 2: %d", resOne, resTwo);
    }
}
