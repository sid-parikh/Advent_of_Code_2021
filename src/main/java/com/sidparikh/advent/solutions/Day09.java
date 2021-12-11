/* Sid Parikh. Created on December 9, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Day 9: Smoke Basin
 * <p>
 * The lava is giving off weird smoke, but at least we have a heightmap of the cave. <br>
 * Star One: Find low points, points that are lower than all adjacent points. <br>
 * Star Two: Measure the size of each basin, which start at low points and expand upwards to level-9 points.
 */
public class Day09 extends Solution {
    /**
     * A List of the low points found in {@link #partOne()}.
     */
    private final List<Loc> lowPoints = new ArrayList<>();
    /**
     * The heightmap (parsed puzzle input). Created in {@link #partOne()} and used again in {@link #partTwo()}.
     */
    private int[][] map;

    public Day09() {
        super(9);
    }

    /**
     * Recursively calculates the size of a basin in the map given one point within the basin. This method does
     * modify the original map to save the time of making a copy. Every counted square will be set to 9.
     *
     * @param i   the x/row value of the point
     * @param j   the y/column value of the point
     * @param map the map to search through
     * @return the number of points that are connected to the first one, bordered by 9s on all sides
     */
    private static int calcSize(int i, int j, int[][] map) {

        if (map[i][j] == 9) return 0;

        // We have a non-9
        // set to 9 to mark as counted
        map[i][j] = 9;
        // check adjacents (spread out)
        int a = 0;
        if (i > 0) {
            a += calcSize(i - 1, j, map);
        }
        if (i + 1 < map.length) {
            a += calcSize(i + 1, j, map);
        }
        if (j > 0) {
            a += calcSize(i, j - 1, map);
        }
        if (j + 1 < map[0].length) {
            a += calcSize(i, j + 1, map);
        }

        return 1 + a;

    }

    /**
     * Finds the locations of low points in the puzzle input.
     * @return the number of low points
     */
    @Override
    public String partOne() {
        // Create map
        map = new int[input.size()][input.get(0).length()];
        // Parse input for each row and save
        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }

        int answer = 0;
        // Loop through all points
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // Check all adjacent points:
                // Up, Down, Left, Right
                // Keep a boolean to make sure all conditions are met
                boolean good = true;
                if (i > 0) {
                    good = good & map[i][j] < map[i - 1][j];
                }
                if (i + 1 < map.length) {
                    good = good && map[i][j] < map[i + 1][j];
                }
                if (j > 0) {
                    good = good && map[i][j] < map[i][j - 1];
                }
                if (j + 1 < map[0].length) {
                    good = good && map[i][j] < map[i][j + 1];
                }

                // At this point, boolean good clearly represents if this point is a low one.
                if (good) {
                    // Save it so it can be used by part two
                    lowPoints.add(new Loc(i, j));
                    // Answer is the answer to part one
                    answer += 1 + map[i][j];
                }

            }
        }

        return String.valueOf(answer);
    }

    /**
     * Calculates the size of each basin.
     * @return the product of the sizes of the three largest basins.
     */
    @Override
    public String partTwo() {
        List<Integer> basinSizes = new ArrayList<>();

        // Each low point represents a different basin.
        for (Loc l : lowPoints) {
            int a = calcSize(l.x(), l.y(), map);
            basinSizes.add(a);
        }

        // Finding the largest three should be a one loop (O(n)) operation, but I'm lazy and this is one line.
        basinSizes.sort(Collections.reverseOrder());

        long total = (long) basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);

        return String.valueOf(total);
    }

    /**
     * Stores a pair of coordinates.
     */
    private record Loc(int x, int y) {
    }
}
