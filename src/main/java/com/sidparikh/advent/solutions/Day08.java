/* Sid Parikh. Created on December 8, 2021 for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 8: Seven Segment Search
 * <p>
 * We're still navigating through the cave, but first we need to fix our seven-segment displays. <br>
 * Star One: Count the number of 1s, 4s, 7s, and 8s in the output section of our notes. <br>
 * Star Two: Figure out which wires are connected to which segments and then find the total of the outputs.
 */
public class Day08 extends Solution {
    private final List<NotebookEntry> entryList = new ArrayList<>();

    public Day08() {
        super(8);
    }

    /**
     * Converts a letter-string that identifies which segments are powered into a number, given a map that converts the
     * letters in the string to the standard letters. <br>
     * Standard Lettering: <br>
     * <pre>
     *     aaaa
     *    b    c
     *    b    c
     *     dddd
     *    e    f
     *    e    f
     *     gggg
     * </pre>
     *
     * @param digit letter-string representing segments, e.g. "acdfg" in Standard lettering (order doesn't matter)
     * @param key   should be in the format "<Standard Letter, Corresponding letter in String>"
     * @return number depicted by the segment, e.g. 3
     */
    // There's no serious need for this to be its own method, but there's no harm either.
    private static int evaluateDigit(String digit, Map<Character, Character> key) {

        final int l = digit.length();

        if (l == 2) return 1;
        if (l == 4) return 4;
        if (l == 3) return 7;
        if (l == 7) return 8;

        if (l == 5) {

            if (digit.contains(String.valueOf(key.get('g')))) {
                final boolean c = digit.contains(String.valueOf(key.get('c')));
                final boolean f = digit.contains(String.valueOf(key.get('f')));
                if (c && f) return 3;
                if (c) return 2;
                if (f) return 5;
            }

        }

        if (l == 6) {

            if (!digit.contains(String.valueOf(key.get('e')))) {
                return 9;
            } else if (digit.contains(String.valueOf(key.get('d')))) {
                return 6;
            } else {
                return 0;
            }

        }

        return -1;

    }

    /**
     * Parses the input; saves it into {@link #entryList} so {@link #partTwo()} can use it too. Counts the number of
     * 1s, 4s, 7s, and 8s, in the output column, because these four numbers are identifiable by the length of their
     * strings alone.
     *
     * @return the total number of 1s, 4s, and 8s in the output column of our notes (352)
     */
    @Override
    public String partOne() {

        // Convert each row into a POJO just to make it a little easier to keep track of.
        for (String s : input) {
            String[] line = s.split("\\|");
            NotebookEntry e = new NotebookEntry(line[0].trim().split(" "), line[1].trim().split(" "));
            entryList.add(e);
        }

        // Count the number of 1s, 4s, 7s, and 8s, which have string lengths 2, 4, 3, and 7, respectively.
        int result = 0;
        for (NotebookEntry e : entryList) {
            for (String digit : e.outputValue()) {
                final int l = digit.length();
                if (l == 2 || l == 3 || l == 4 || l == 7) {
                    result++;
                }
            }
        }

        return String.valueOf(result);
    }

    /**
     * Decodes each entry (identifies which wires are connected to which segments) and calculates each output.
     *
     * @return the sum of all of the outputs (936117)
     */
    @Override
    public String partTwo() {

        int answer = 0;

        // We will work row by row to add up the outputs
        for (NotebookEntry e : entryList) {

            // A usefel tool to map the wires to the segments is to count the frequency of each letter, similar to the
            // process of frequency analysis in cryptanalysis.
            HashMap<Character, Integer> letterFrequencies = new HashMap<>();
            letterFrequencies.put('a', 0);
            letterFrequencies.put('b', 0);
            letterFrequencies.put('c', 0);
            letterFrequencies.put('d', 0);
            letterFrequencies.put('e', 0);
            letterFrequencies.put('f', 0);
            letterFrequencies.put('g', 0);

            // 1, 4, 7, and 8 can be identified by their lengths alone, but 8 isn't useful for decoding because it just
            // contains every letter
            String one = "";
            String four = "";
            String seven = "";

            // This will be where we save the final key, in a map of pairs in the form
            // "<Standard Letter, Corresponding Letter>", where S.L. is the letter representing the segment based on the
            // pattern laid out above, and C.L. is the corresponding letter for *this specific entry*
            HashMap<Character, Character> key = new HashMap<>();

            for (String s : e.signalPatterns()) {

                // Identify by length alone
                if (s.length() == 2) {
                    one = s;
                } else if (s.length() == 4) {
                    four = s;
                } else if (s.length() == 3) {
                    seven = s;
                }

                // Count the number of each character for frequency analysis
                for (char c : s.toCharArray()) {
                    letterFrequencies.put(c, letterFrequencies.get(c) + 1);
                }

            }

            // In standard lettering, 7 is acf and 1 is cf. Thus, the letter in 7 but not in 1 can be identified as a.
            for (int i = 0; i < seven.length(); i++) {
                if (!one.contains(seven.substring(i, i + 1))) {
                    key.put('a', seven.charAt(i));
                    // Change its frequency to 0 in order to mark it as found. Could alternatively remove it, but would
                    // run into ConcurrentModification problems later. This is just easier.
                    letterFrequencies.replace(seven.charAt(i), 0);
                    break;
                }
            }

            // Frequency analysis time
            for (Map.Entry<Character, Integer> pair : letterFrequencies.entrySet()) {

                // B is the only segment that is in exactly 6 of the 10 numbers.
                if (pair.getValue() == 6) {
                    key.put('b', pair.getKey());
                    pair.setValue(0);

                    // 4 is bcdf. Once we've eliminated b, we can find d by comparing 4 to 7 (acf).
                    for (int i = 0; i < four.length(); i++) {
                        if (!seven.contains(four.substring(i, i + 1)) && four.charAt(i) != pair.getKey()) {
                            key.put('d', four.charAt(i));
                            letterFrequencies.replace(four.charAt(i), 0);
                            break;
                        }
                    }
                    // E is the only segment that is in exactly 4 of the 10 numbers.
                } else if (pair.getValue() == 4) {
                    key.put('e', pair.getKey());
                    pair.setValue(0);
                    // F occurs 9 times
                } else if (pair.getValue() == 9) {
                    key.put('f', pair.getKey());
                    pair.setValue(0);
                    // C occurs 8 times
                } else if (pair.getValue() == 8) {
                    key.put('c', pair.getKey());
                    pair.setValue(0);
                }

            }

            // G is the only remaining letter and can be easily identified by having a frequency of 7.
            for (Map.Entry<Character, Integer> pair : letterFrequencies.entrySet()) {
                if (pair.getValue() == 7) {
                    key.put('g', pair.getKey());
                    pair.setValue(0);
                    break;
                }
            }

            /* At this point, the key has been build. Use evaluateDigit to evaluate each digit. */

            StringBuilder sb = new StringBuilder();
            for (String outputDigit : e.outputValue()) {
                // We're given four digits that are represent four place values. Easy solution is to string them
                // together and then convert back into an int
                sb.append(evaluateDigit(outputDigit, key));
            }

            // This is the output for this entry; add to answer and start again
            answer += Integer.parseInt(sb.toString());
        }

        return String.valueOf(answer);

    }


    /**
     * A simple record to keep represent a single entry/row in our notes/input.
     */
    private record NotebookEntry(String[] signalPatterns, String[] outputValue) {
    }
}
