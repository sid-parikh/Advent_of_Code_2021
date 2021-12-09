package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.Arrays;

public class Day09 extends Solution {
    public Day09() {
        super(9);
    }

    private int[][] map;

    @Override
    public String partOne() {
        map = new int[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }

        int answer = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // Up, Down, Left, Right
                boolean good = true;
                if (i > 0) {
                    good = good && map[i][j] < map[i - 1][j];
                }
                if (i + 1 < map.length) {
                    good = good && map[i][j] < map[i + 1][j];
                }
                if (j > 0) {
                    good = good && map[i][j] < map[i][j - 1];
                }
                if (j + 1 < map[0].length) {
                    good = good && map[i][j] < map[i][j + 1];
                }

                if (good) {
                    System.out.println(map[i][j]);
                    answer += 1 + map[i][j];
                }

            }
        }

        return String.valueOf(answer);
    }

    @Override
    public String partTwo() {
        return null;
    }
}
