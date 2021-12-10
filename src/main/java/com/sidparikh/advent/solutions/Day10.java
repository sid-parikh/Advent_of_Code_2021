package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class Day10 extends Solution {
    public Day10() {
        super(10);
    }

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

    @Override
    public String partOne() {
        int answer = 0;
        for (String line : input) {
            Deque<Character> opens = new ArrayDeque<>();

            for (char c : line.toCharArray()) {

                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    opens.addLast(c);
                } else if (isMatchingPair(c, opens.peekLast())) {
                    opens.removeLast();
                } else {
                    // System.out.printf("%c found, expected %c\n", c, opens.peekLast());
                    switch (c) {
                        case ')' -> answer += 3;
                        case ']' -> answer += 57;
                        case '}' -> answer += 1197;
                        case '>' -> answer += 25137;
                    }
                    break;
                }

            }
        }
        return String.valueOf(answer);
    }

    @Override
    public String partTwo() {

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
                    // System.out.printf("%c found, expected %c\n", c, opens.peekLast());
                    corrupt = true;
                    break;
                }

            }
            if (corrupt || opens.size() == 0) continue;
            long score = 0;
            while (opens.size() > 0) {
                score *= 5;
                score += getScore(opens.removeLast());
            }
            scores.add(score);

            System.out.println(score);

        }

        Collections.sort(scores);
        long answer = scores.get(scores.size() / 2);

        return String.valueOf(answer);
    }
}
