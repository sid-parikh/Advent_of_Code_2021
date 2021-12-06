/* Sid Parikh. Created on December 6, 2021, for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 extends Solution {
    public Day06() {
        super(6);
    }

    @Override
    public String partOne() {
        ArrayList<Integer> fish = new ArrayList<>((Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList()));

        for (int i = 0; i < 80; i++) {
            simulateDay(fish);
        }

        return String.valueOf(fish.size());
    }

    private static void simulateDay(List<Integer> fishList) {
        for (int i = 0; i < fishList.size(); i++) {
            int f = fishList.get(i);
            if (f == 0) {
                fishList.add(9);
                fishList.set(i, 6);
            } else {
                fishList.set(i, f - 1);
            }

        }
    }

    @Override
    public String partTwo() {
        // It seems doing it manually does not work. Java ran out of heap space or something.
        // I don't even know what that means other than BAD.

//        ArrayList<Integer> fish = new ArrayList<Integer>((Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList()));
//
//        for (int i = 0; i < 256; i++) {
//            simulateDay(fish);
//        }
//
//        return String.valueOf(fish.size());

        // The trick is that fish with the same value are the same, so just keep a count of the number of fish which each value.
        // Haha, now my part 2 runs in 5% of the time that my part one does. I'll update that one in the morning
        int[] inputFish = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        long[] fishies = new long[9];

        for (int n : inputFish) {
            fishies[n]++;
        }

        for (long n : fishies) {
            System.out.println(n);
        }

        for (int i = 0; i < 256; i++) {
            long[] newFishies = new long[9];
            newFishies[6] = fishies[0];
            newFishies[8] = fishies[0];
            newFishies[0] = fishies[1];
            newFishies[1] = fishies[2];
            newFishies[2] = fishies[3];
            newFishies[3] = fishies[4];
            newFishies[4] = fishies[5];
            newFishies[5] = fishies[6];
            newFishies[6] += fishies[7];
            newFishies[7] = fishies[8];
            fishies = newFishies;
        }

        long total = 0;
        for (int i = 0; i <= 8; i++) {
            total += fishies[i];
        }

        return String.valueOf(total);
    }
}
