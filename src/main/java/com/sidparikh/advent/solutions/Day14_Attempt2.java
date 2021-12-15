package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.HashMap;
import java.util.Map;


public class Day14_Attempt2 extends Solution {
    public Day14_Attempt2() {
        super(14);
    }

    private record CharPair(Character first, Character second) {
        @Override
        public String toString() {
            return String.format("%c%c", first, second);
        }
    }

    @Override
    public String partOne() {
        return null;
    }

    @Override
    public String partTwo() {
        HashMap<CharPair, Character> rules = new HashMap<>();
        HashMap<CharPair, Long> pairFreqs = new HashMap<>();
        HashMap<Character, Long> charFreqs = new HashMap<>();


        for (int in = 2; in < input.size(); in++) {
            String line = input.get(in);
            char[] pair = line.substring(0, 2).toCharArray();
            char insert = line.charAt(6);

            rules.put(new CharPair(pair[0], pair[1]), insert);
        }

        char[] start = input.get(0).toCharArray();
        for (int i = 0; i + 1 < start.length; i++) {
            charFreqs.put(start[i], 1 + charFreqs.getOrDefault(start[i], 0L));
            CharPair cp = new CharPair(start[i], start[i + 1]);
            pairFreqs.put(cp, 1 + pairFreqs.getOrDefault(cp, 0L));
        }

        charFreqs.put(start[start.length - 1], 1 + charFreqs.getOrDefault(start[start.length - 1], 0L));

        for (int i = 0; i < 40; i++) {
            Map<CharPair, Long> toBeAdded = new HashMap<>();
            for (Map.Entry<CharPair, Character> rule : rules.entrySet()) {
                final CharPair pair = rule.getKey();
                final Character insert = rule.getValue();
                if (pairFreqs.containsKey(pair)) {

                    // We've deleted all instances this pair by inserting a character in between each one.
                    final long freq = pairFreqs.get(pair);
                    pairFreqs.put(pair, 0L);

                    // However, we've created two new pairs
                    final CharPair np1 = new CharPair(pair.first(), insert);
                    final CharPair np2 = new CharPair(insert, pair.second());

                    // The new pairs exist freq more times than they used to
                    toBeAdded.put(np1, freq + toBeAdded.getOrDefault(np1, 0L));
                    toBeAdded.put(np2, freq + toBeAdded.getOrDefault(np2, 0L));

                    // We've also added freq as many [insert] chars.
                    charFreqs.put(rule.getValue(), freq + charFreqs.getOrDefault(insert, 0L));

                    System.out.printf("rule %s created %d %cs\n", rule.getKey(), freq, insert);
                }
            }
            // So as not to concurrently mess with things, we saved all the additions we needed to make. Now lets do it.
            for (Map.Entry<CharPair, Long> addition : toBeAdded.entrySet()) {
                pairFreqs.put(addition.getKey(), addition.getValue() + pairFreqs.getOrDefault(addition.getKey(), 0L));
            }
            System.out.println();

        }

        long highest = 0;
        long lowest = Long.MAX_VALUE;

        for (Map.Entry<Character, Long> e: charFreqs.entrySet()) {
            // System.out.println(e);
            highest = Math.max(highest, e.getValue());
            lowest = Math.min(lowest, e.getValue());
        }

        return String.valueOf(highest - lowest);
    }
}
