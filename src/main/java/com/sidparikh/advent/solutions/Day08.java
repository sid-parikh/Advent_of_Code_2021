package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day08 extends Solution {
    public Day08() {
        super(8);
    }

    private record Entry(String[] signalPatterns, String[] outputValue) {}

    private final List<Entry> entryList = new ArrayList<>();

    @Override
    public String partOne() {
        for (String s : input) {
            String[] line = s.split("\\|");
            Entry e = new Entry(line[0].split(" "), line[1].split(" "));
            entryList.add(e);
        }

        int result = 0;
        for (Entry e : entryList) {
            for (String digit : e.outputValue()) {
                final int l = digit.length();
                if (l == 2 || l == 3 || l == 4 || l == 7) {
                    result++;
                }
            }
        }

        return String.valueOf(result);
    }

    @Override
    public String partTwo() {

        for (Entry e : entryList) {
            String[] numberRepresentations = new String[10];

            // Ordered a to g in the original format
            String[] decodedPositions = new String[6];

            for (String digit : e.signalPatterns()) {
                final int l = digit.length();

                if (l == 2) {
                    numberRepresentations[1] = digit;
                } else if (l == 3) {
                    numberRepresentations[7] = digit;
                } else if (l == 4) {
                    numberRepresentations[4] = digit;
                }

            }

            for (int i = 0; i < numberRepresentations[7].length(); i++) {
                if (!numberRepresentations[1].contains(numberRepresentations[7].substring(i, i+1))) {
                    decodedPositions[0] = numberRepresentations[7].substring(i, i + 1);
                }
            }


        }


    }
}
