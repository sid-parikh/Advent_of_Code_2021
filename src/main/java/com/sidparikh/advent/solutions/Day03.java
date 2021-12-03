package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Day03 extends Solution {
    public Day03() {
        super(3);
    }

    @Override
    public String partOne() {
        boolean[][] binary = new boolean[input.size()][12];
        for (int i = 0; i < input.size(); i++) {
            char[] temp = input.get(i).toCharArray();
            for (int j = 0; j < temp.length; j++) {
                if (temp[j] == '0') binary[i][j] = false;
                else binary[i][j] = true;
            }
        }
        int[] countOnes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] epsilon = new int[12];
        for (int i = 0; i < binary.length; i++) {
            for (int j = 0; j < binary[0].length; j++) {
                if (binary[i][j]) countOnes[j]++;
            }
        }
        final int l = binary.length / 2;
        for (int i = 0; i < countOnes.length; i++) {
            if (countOnes[i] > l) {
                countOnes[i] = 1;
                epsilon[i] = 0;
            } else {
                countOnes[i] = 0;
                epsilon[i] = 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int c : countOnes) sb.append(c);

        StringBuilder sb2 = new StringBuilder();
        for (int c : epsilon) sb2.append(c);

        int delta = Integer.parseInt(sb.toString(), 2);
        int epsilonF = Integer.parseInt(sb2.toString(), 2);

        return String.valueOf((long) delta * epsilonF);

    }

    /**
     * Calculates the life support rating by multiplying the oxygen and carbon ratings.
     * @return
     */
    @Override
    public String partTwo() {
        int i;

        ArrayList<Integer> oxy = new ArrayList<>();
        ArrayList<Integer> co2 = new ArrayList<>();
        for (String s : input) {
            int n = Integer.parseInt(s, 2);
            oxy.add(n);
            co2.add(n);
        }

        i = 0;
        while (oxy.size() > 1 && i < 12) {
            int co = 0;
            for (int n : oxy) {
                if (((n >> 11 - i) & 1) == 1) co++;
            }
            final double l = (double) oxy.size() / 2;
            final int finalI = i;
            if (co >= l) {
                oxy.removeIf(n -> ((n >> 11 - finalI) & 1) == 0);
            } else {
                oxy.removeIf(n -> ((n >> 11 - finalI) & 1) == 1);
            }
            i++;
        }

        i = 0;
        while (co2.size() > 1 && i < 12) {
            int co = 0;
            for (int n : co2) {
                if (((n >> 11 - i) & 1) == 1) co++;
            }
            final double l = (double) co2.size() / 2;
            final int finalI = i;
            if (co < l) {
                co2.removeIf(n -> ((n >> 11 - finalI) & 1) == 0);
            } else {
                co2.removeIf(n -> ((n >> 11 - finalI) & 1) == 1);
            }
            i++;
        }

        System.out.printf("co2 = %d, oxy = %d", co2.get(0), oxy.get(0));
        return String.valueOf((long) co2.get(0) * oxy.get(0));


    }
}
