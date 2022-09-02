/* Sid Parikh. Created on December 5, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;

/**
 * Day 5: Hydrothermal Venture
 * <p>
 * We need to avoid the chains of Hydrothermal vents. Let's find areas where more than one chain overlaps. <br>
 * Star One: Only check horizontal and vertical lines. <br>
 * Star Two: Include diagonal lines, too.
 */
public class Day05 extends Solution {
    public Day05() throws IOException {
        super(5);
    }

    /**
     * Counts the number of overlaps of horizontal and vertical lines.
     * @return the number of grid squares that two or more lines go through (7468)
     */
    @Override
    public String partOne() {
        // Coordinate plane Q1; integers only.
        // The value inside each cell represents the number of lines that have gone through that cell.
        int[][] grid = new int[1000][1000];

        for (String l : input) {
            // Convert to Line format
            Line line = Line.newLine(l);
            // Horizontal lines
            if (line.x1() == line.x2()) {
                // Can go in either direction; max and min simplifies the logic
                for (int y = Math.min(line.y1(), line.y2()); y <= Math.max(line.y1(), line.y2()); y++) {
                    grid[line.x1()][y]++;
                }
            // Vertical lines
            } else if (line.y1() == line.y2()) {
                // Can go in either direction; max and min simplifies the logic
                for (int x = Math.min(line.x1(), line.x2()); x <= Math.max(line.x1(), line.x2()); x++) {
                    grid[x][line.y1()]++;
                }
            }
        }

        // Just check the entire grid for overlaps.
        int result = 0;
        for (int[] row : grid) {
            for (int n : row) {
                if (n >= 2) {
                    result++;
                }
            }
        }

        return String.valueOf(result);
    }

    /**
     * Counts the number of overlaps of all lines.
     * @return the number of grid squares that two or more lines go through (22364)
     */
    @Override
    public String partTwo() {
        int[][] grid = new int[1000][1000];

        // Mostly the same as part one
        for (String l : input) {
            Line line = Line.newLine(l);
            if (line.x1() == line.x2()) {
                for (int y = Math.min(line.y1(), line.y2()); y <= Math.max(line.y1(), line.y2()); y++) {
                    grid[line.x1()][y]++;
                }
            } else if (line.y1() == line.y2()) {
                for (int x = Math.min(line.x1(), line.x2()); x <= Math.max(line.x1(), line.x2()); x++) {
                    grid[x][line.y1()]++;
                }
            } else {
                // We are given that lines are always 45 or -45 degrees.
                int x;
                int y;
                // It's very simple to go from left to right, so check x1 vs x2
                if (line.x2() > line.x1()) {
                    x = line.x1();
                    y = line.y1();
                    while (x <= line.x2()) {
                        grid[x][y]++;
                        x++;
                        // Check for positive or negative slope and increment accordingly
                        y += (line.y2() > line.y1()) ? 1 : -1;
                    }
                } else {
                    // Flip everything to go from left to right.
                    x = line.x2();
                    y = line.y2();
                    while (x <= line.x1()) {
                        grid[x][y]++;
                        x++;
                        // Check for positive or negative slope and increment accordingly
                        y += (line.y1() > line.y2()) ? 1 : -1;
                    }
                }
            }
        }

        int result = 0;
        for (int[] row : grid) {
            for (int n : row) {
                if (n >= 2) {
                    result++;
                }
            }
        }

        return String.valueOf(result);
    }

    /**
     * A helpful tool to turn four variables into one. I officially love records.
     */
    public record Line(int x1, int y1, int x2, int y2) {
        /**
         * Helper method that parses input in the form given into a Line
         * @param input a line in the form "x1, y1, -> x2, y2"
         * @return a Line with the corresponding values
         */
        public static Line newLine(String input) {
            String[] temp = input.split(" -> ");
            String[] firstPt = temp[0].split(",");
            String[] secondPt = temp[1].split(",");
            return new Line(Integer.parseInt(firstPt[0]), Integer.parseInt(firstPt[1]), Integer.parseInt(secondPt[0]), Integer.parseInt(secondPt[1]));
        }
    }
}
