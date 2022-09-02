/* Sid Parikh. Created on December 2, 2021, for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;

/**
 * Day 2: Dive!
 * <p>
 * We're trying to pilot the submarine. <br>
 * Star One: Take the directions and find the final depth and position. Return their product. <br>
 * Star Two: Up and down don't actually move us, just change our aim.
 */
public class Day02 extends Solution {

    public Day02() throws IOException {
        super(2);
    }

    /**
     * Calculates the final position using the given instructions. Up is down and down is up.
     *
     * @return the product of the final horizontal position and final depth (1714950)
     */
    @Override
    public String partOne() {
        int hPos = 0;
        int depth = 0;
        for (String command : input) {
            // The number (x) is one digit and at the end.
            int amount = Integer.parseInt(command.substring(command.length() - 1));
            if (command.startsWith("forward")) {
                hPos += amount;
            } else if (command.startsWith("down")) {
                depth += amount;
            } else if (command.startsWith("up")) {
                depth -= amount;
            }
        }
        return String.valueOf((long) hPos * depth);
    }

    /**
     * Calculates the final position using the new formula: up and down change the aim, and forward x moves forward x
     * and increases depth by x * aim.
     *
     * @return the product of the final horizontal position and final depth (1281977850)
     */
    @Override
    public String partTwo() {
        int hPos = 0;
        int depth = 0;
        int aim = 0;
        for (String command : input) {
            // The number (x) is one digit and at the end.
            int amount = Integer.parseInt(command.substring(command.length() - 1));
            if (command.startsWith("forward")) {
                hPos += amount;
                depth += aim * amount;
            } else if (command.startsWith("down")) {
                aim += amount;
            } else if (command.startsWith("up")) {
                aim -= amount;
            }
        }
        return String.valueOf((long) hPos * depth);
    }
}
