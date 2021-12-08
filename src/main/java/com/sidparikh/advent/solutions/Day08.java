package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 extends Solution {
    private final List<NotebookEntry> entryList = new ArrayList<>();

    public Day08() {
        super(8);
    }

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
            } else if (digit.contains(String.valueOf(key.get('d')))){
                return 6;
            } else {
                return 0;
            }

        }

        return -1;

    }

    @Override
    public String partOne() {
        for (String s : input) {
            String[] line = s.split("\\|");
            NotebookEntry e = new NotebookEntry(line[0].trim().split(" "), line[1].trim().split(" "));
            entryList.add(e);
        }

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

    @Override
    public String partTwo() {

        int answer = 0;
        for (NotebookEntry e : entryList) {

            HashMap<Character, Integer> letterFrequencies = new HashMap<>();
            letterFrequencies.put('a', 0);
            letterFrequencies.put('b', 0);
            letterFrequencies.put('c', 0);
            letterFrequencies.put('d', 0);
            letterFrequencies.put('e', 0);
            letterFrequencies.put('f', 0);
            letterFrequencies.put('g', 0);

            String one = "";
            String four = "";
            String seven = "";

            HashMap<Character, Character> key = new HashMap<>();

            for (String s : e.signalPatterns()) {

                if (s.length() == 2) {
                    one = s;
                } else if (s.length() == 4) {
                    four = s;
                } else if (s.length() == 3) {
                    seven = s;
                }

                for (char c : s.toCharArray()) {
                    letterFrequencies.put(c, letterFrequencies.get(c) + 1);
                }

            }

            // System.out.println(letterFrequencies);


            for (int i = 0; i < seven.length(); i++) {
                if (!one.contains(seven.substring(i, i + 1))) {
                    key.put('a', seven.charAt(i));
                    letterFrequencies.replace(seven.charAt(i), 0);
                    break;
                }
            }

            for (Map.Entry<Character, Integer> pair : letterFrequencies.entrySet()) {

                if (pair.getValue() == 6) {
                    key.put('b', pair.getKey());
                    pair.setValue(0);

                    for (int i = 0; i < four.length(); i++) {
                        if (!seven.contains(four.substring(i, i + 1)) && four.charAt(i) != pair.getKey()) {
                            key.put('d', four.charAt(i));
                            letterFrequencies.replace(four.charAt(i), 0);
                            break;
                        }
                    }

                } else if (pair.getValue() == 4) {
                    key.put('e', pair.getKey());
                    pair.setValue(0);
                } else if (pair.getValue() == 9) {
                    key.put('f', pair.getKey());
                    pair.setValue(0);
                } else if (pair.getValue() == 8) {
                    key.put('c', pair.getKey());
                    pair.setValue(0);
                }

            }

            for (Map.Entry<Character, Integer> pair : letterFrequencies.entrySet()) {
                if (pair.getValue() == 7) {
                    key.put('g', pair.getKey());
                    pair.setValue(0);
                    break;
                }
            }

            StringBuilder sb = new StringBuilder();
            for (String outputDigit : e.outputValue()) {

                sb.append(evaluateDigit(outputDigit, key));

            }
            answer  += Integer.parseInt(sb.toString());

        }

        return String.valueOf(answer);

    }


    private record NotebookEntry(String[] signalPatterns, String[] outputValue) {
    }
}
