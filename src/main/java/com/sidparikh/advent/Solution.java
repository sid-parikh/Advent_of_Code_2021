package com.sidparikh.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is a generic Solution class that all daily solutions will extend. This class provides basic methods to get the
 * day's input and to print the output to sysout.
 */
public abstract class Solution {

    private final int DAY;
    protected final List<String> input;

    public Solution(int day) {
        this.DAY = day;
        try {
            input = getInput();
        } catch (NullPointerException e) {
            // An expensive way to add an error message, but this should really never happen,
            // and it's a puzzle, RAM isn't really a concern.
            System.err.println("Input File not Found!\n\n");
            throw e;
        }
    }

    /**
     * Gets the input from the file's location. Now with relative filepaths and a real resources folder.
     * @throws NullPointerException from Objects#requireNonNull if input file is not found
     */
    private List<String> getInput() throws NullPointerException {
        // For some reason, even though there are provided methods to get a resource as a file, it would never find
        // the file when I used those. Only getResourceAsStream worked. Unfortunately, getResourceAsStream hides the
        // FileNotFoundException and reverts it to an NPE, which is far less useful. I had to throw and catch it to add
        // an error message explaining it is probably because the input file is not found.
        String filepath = "inputs/day" + String.format("%02d", DAY) + ".txt";
        Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filepath)));

        List<String> input = new ArrayList<>();

        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }

        return input;
    }

    /**
     * Returns a String, formatted for Sysout, that contains the results of the solution for that day.
     */
    public String getSolution() {
        // Temps
        long start;
        long elapsed;

        // Run and time part one
        start = System.currentTimeMillis();
        String resOne = partOne();
        elapsed = System.currentTimeMillis() - start;
        resOne += String.format(" (%d ms)", elapsed);

        // Run and time part two
        start = System.currentTimeMillis();
        String resTwo = partTwo();
        elapsed = System.currentTimeMillis() - start;
        resTwo += String.format(" (%d ms)", elapsed);

        return String.format("\n\n---Day %02d---\nStar 1: %s\nStar 2: %s", DAY, resOne, resTwo);
    }

    public abstract String partOne();

    public abstract String partTwo();
}
