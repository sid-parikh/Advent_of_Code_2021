package com.sidparikh.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public abstract class Solution {


    private final int DAY;
    protected final List<String> input;

    protected Solution(int day) throws FileNotFoundException {
        this.DAY = day;
        input = getInput();
    }

    /**
     * Gets the input from the file's location on my PC.
     */
    private List<String> getInput() {
        String filepath = "inputs/day" + String.format("%02d", DAY) + ".txt";
        Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filepath)));

        List<String> input = new ArrayList<>();

        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }

        return input;
    }

    public String getSolution() {
        return "\n\n---Day " + String.format("%02d", DAY) + "---\nStar 1: " + partOne() + "\nStar 2: " + partTwo();
    }

    public abstract String partOne();

    public abstract String partTwo();
}
