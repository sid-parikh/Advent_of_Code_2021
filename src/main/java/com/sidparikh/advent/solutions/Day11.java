package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import com.sidparikh.advent.Utils.Loc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends Solution {
    private int[][] map;

    public Day11() {
        super(11);
    }

    private ArrayList<Loc> flashPoints = new ArrayList<>();

    @Override
    public String partOne() {
        map = new int[input.size()][input.get(0).length()];
        // Parse input for each row and save
        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }
        int answer = 0;
        for (int count = 0; count < 100; count++) {
            flashPoints = new ArrayList<>();

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    map[i][j] = (map[i][j] + 1) % 10;
                    if (map[i][j] == 0) {
                        flashPoints.add(new Loc(i, j));
                    }
                }
            }

            for (Loc loc : List.copyOf(flashPoints)) {
                answer += flash(map, loc.x(), loc.y());
            }

            for (Loc loc : flashPoints) {
                map[loc.x()][loc.y()] = 0;
            }
        }

        return String.valueOf(answer);
    }

    private int flash(int[][] map, int x, int y) {
        int a = 1;
        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, map.length - 1); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, map[0].length - 1); j++) {
                if (i != x || j != y) {
                    map[i][j] = (map[i][j] + 1) % 10;
                    if (map[i][j] == 0) {
                        flashPoints.add(new Loc(i, j));
                        a += flash(map, i, j);
                    }
                }
            }
        }
        return a;
    }

    @Override
    public String partTwo() {
        map = new int[input.size()][input.get(0).length()];
        // Parse input for each row and save
        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }
        int answer = 0;
        while (flashPoints.size() != 100) {
            answer++;
            flashPoints = new ArrayList<>();

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    map[i][j] = (map[i][j] + 1) % 10;
                    if (map[i][j] == 0) {
                        flashPoints.add(new Loc(i, j));
                    }
                }
            }

            for (Loc loc : List.copyOf(flashPoints)) {
                flash(map, loc.x(), loc.y());
            }

            for (Loc loc : flashPoints) {
                map[loc.x()][loc.y()] = 0;
            }
        }

        return String.valueOf(answer);
    }
}
