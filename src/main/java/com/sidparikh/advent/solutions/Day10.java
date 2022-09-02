/* Sid Parikh. Created on December 10, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

/**
 * Day 10: Syntax Scoring
 * <p>
 * Our navigation system was evidently pretty badly designed. Syntax errors everywhere! <br>
 * Star One: Find the "corrupt" lines: those that have illegal closing characters. <br>
 * Star Two: Complete the incomplete lines.
 */
public class Day10 extends Solution {

    private int partOneAnswer;
    private long partTwoAnswer;

    public Day10() throws IOException {
        super(10);
        partOneAnswer = 0;
        partTwoAnswer = 0L;
    }

    /**
     * Checks if two symbols are a matching pair, e.g. ( and ).
     *
     * @param a one symbol
     * @param b another symbol
     * @return whether {@code a} and {@code b} match
     */
    private static boolean isMatchingPair(char a, char b) {
        switch (a) {
            case '(' -> {
                return b == ')';
            }
            case '[' -> {
                return b == ']';
            }
            case '{' -> {
                return b == '}';
            }
            case '<' -> {
                return b == '>';
            }
            case ')' -> {
                return b == '(';
            }
            case ']' -> {
                return b == '[';
            }
            case '}' -> {
                return b == '{';
            }
            case '>' -> {
                return b == '<';
            }
        }
        return false;
    }

    /**
     * Gets the score of an opening character. Used for Star Two
     *
     * @param c symbol
     * @return score corresponding with {code c}
     */
    private static int getScore(char c) {
        int a;
        switch (c) {
            case '(' -> a = 1;
            case '[' -> a = 2;
            case '{' -> a = 3;
            case '<' -> a = 4;
            default -> a = 0;
        }
        return a;
    }

    /**
     * Solves both star one
     */
    private void solve() {
        ArrayList<Long> scores = new ArrayList<>();
        for (String line : input) {
            Deque<Character> opens = new ArrayDeque<>();

            boolean corrupt = false;
            for (char c : line.toCharArray()) {

                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    opens.addLast(c);
                } else if (isMatchingPair(c, opens.peekLast())) {
                    opens.removeLast();
                } else {
                    switch (c) {
                        case ')' -> partOneAnswer += 3;
                        case ']' -> partOneAnswer += 57;
                        case '}' -> partOneAnswer += 1197;
                        case '>' -> partOneAnswer += 25137;
                    }
                    // System.out.printf("%c found, expected %c\n", c, opens.peekLast());
                    corrupt = true;
                    break;
                }

            }
            if (corrupt || opens.size() == 0) {
                continue;
            }
            long score = 0;
            while (opens.size() > 0) {
                score *= 5;
                score += getScore(opens.removeLast());
            }
            scores.add(score);
        }

        Collections.sort(scores);
        partTwoAnswer = scores.get(scores.size() / 2);


    }

    @Override
    public String partOne() {
        solve();
        return String.valueOf(partOneAnswer);
    }


    @Override
    public String partTwo() {
        return String.valueOf(partTwoAnswer);
    }
}
