package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day09 extends Solution {
    public Day09() {
        super(9);
    }

    private int[][] map;
    List<Loc> lowPoints = new ArrayList<>();

    @Override
    public String partOne() {
        map = new int[input.size()][input.get(0).length()];


        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }

        int answer = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                final int n = map[i][j];
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
                    lowPoints.add(new Loc(i, j));
                    answer += 1 + map[i][j];
                }

            }
        }

        return String.valueOf(answer);
    }

    private record Loc(int x, int y){}

    private static int calcSize(int i, int j, int[][] map) {

        if (map[i][j] == 9) return 0;

        // We have a non-9
        // set to 9 to mark as counted
        map[i][j] = 9;
        // check adjacents (spread out)
        int a = 0;
        if (i > 0) {
            a += calcSize(i - 1, j, map);
        }
        if (i + 1 < map.length) {
            a += calcSize(i + 1, j, map);
        }
        if (j > 0) {
            a += calcSize(i, j - 1, map);
        }
        if (j + 1 < map[0].length) {
            a += calcSize(i, j + 1, map);
        }

        return 1 + a;

    }


    @Override
    public String partTwo() {


        List<Integer> basinSizes = new ArrayList<>();

        for (Loc l : lowPoints) {
            int a = calcSize(l.x(), l.y(), map);
            basinSizes.add(a);
        }

        basinSizes.sort(Collections.reverseOrder());

        long total = (long) basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);

        return String.valueOf(total);
    }
}
