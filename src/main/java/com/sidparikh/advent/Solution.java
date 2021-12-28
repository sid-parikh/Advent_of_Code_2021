package com.sidparikh.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is a generic Solution class that all daily solutions will extend. This class provides basic methods to get the
 * day's input and to print the output to sysout.
 */
public abstract class Solution {

    /**
     * The puzzle input represented as a list of strings (rows). Subclasses should just reference this list instead of
     * any files.
     */
    protected final List<String> input;
    /**
     * The numerical value of the day that is being solved. It is used to generate the filename to pull input from.
     */
    private final int DAY;

    public Solution(int day) {
        this.DAY = day;
        input = getInput();
    }

    /**
     * Gets the input from the file's location. Now with relative filepaths and a real resources folder.
     *
     * @return the puzzle input as a list of rows
     * @throws NullPointerException from {@link Objects#requireNonNull(Object)} if input file is not found
     */
    private List<String> getInput() throws NullPointerException {
        // For some reason, even though there are provided methods to get a resource as a file, it would never find
        // the file when I used those. Only getResourceAsStream worked. Unfortunately, getResourceAsStream hides the
        // FileNotFoundException and reverts it to an NPE, which is far less useful. I had to throw and catch it to add
        // an error message explaining it is probably because the input file is not found.
        String filepath = "inputs/day" + String.format("%02d", DAY) + ".txt";
        Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filepath),
                "Input File not Found!"));

        List<String> input = new ArrayList<>();

        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }

        return input;
    }

    /**
     * Returns a String, formatted for sysout, that contains the results of the solution for that day.
     */
    public String getSolution() {
        // Temps
        double start;
        double elapsed;

        // Run and time part one
        start = System.nanoTime();
        String resOne = partOne();
        elapsed = System.nanoTime() - start;
        resOne += String.format(" (%.4f ms)", elapsed / 1_000_000.0);

        // Run and time part two
        start = System.nanoTime();
        String resTwo = partTwo();
        elapsed = System.nanoTime() - start;
        resTwo += String.format(" (%.4f ms)", elapsed / 1_000_000.0);

        return String.format("\n\n---Day %02d---\nStar 1: %s\nStar 2: %s", DAY, resOne, resTwo);
    }

    /**
     * Solves part one of the day's puzzle.
     *
     * @return the answer as a String (so it doesn't have to be either int or long)
     */
    public abstract String partOne();

    /**
     * Solves part two of the day's puzzle.
     *
     * @return the answer as a String (so it doesn't have to be either int or long)
     */
    public abstract String partTwo();
}
