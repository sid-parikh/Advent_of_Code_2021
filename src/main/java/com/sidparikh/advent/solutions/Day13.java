package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;
import com.sidparikh.advent.Utils.Loc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day13 extends Solution {

    public Day13() {
        super(13);
    }

    @Override
    public String partOne() {
        boolean[][] dots = new boolean[1500][1500];
        int i = 0;
        for (; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                break;
            }
            String[] coords = line.split(",");
            dots[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] = true;
        }
        String firstFold = input.get(i + 1);
        firstFold = firstFold.substring(11);
        boolean isX = firstFold.charAt(0) == 'x';
        int val = Integer.parseInt(firstFold.substring(2));

        int maxX = dots.length - 1;
        int maxY = dots[0].length - 1;

//        if (isX) {
//            for (i = 0; ((val + i < dots.length) && (val - i >= 0)); i++) {
//                for (int j = 0; j < dots[val].length; j++) {
//                    dots[val - i][j] |= dots[val + i][j];
//                }
//            }
//            maxX = val - 1;
//        }

        dots = foldX(dots, val);

        int count = 0;
        for (i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[0].length; j++) {
                count += dots[i][j] ? 1 : 0;
            }
        }

        return String.valueOf(count);

    }

    private static boolean[][] foldX(boolean[][] dots, int val) {

        boolean[][] answer;
        if (val > dots.length - val) {
            answer = new boolean[val][dots[0].length];
            for (int i = 1; ((val + i < dots.length) && (val - i >= 0)); i++) {
                for (int j = 0; j < dots[0].length; j++) {
                    answer[answer.length - i][j] = dots[val - i][j] || dots[val + i][j];
                }
            }
            for (int i = 0; i < val; i++) {
                for (int j = 0; j < dots[0].length; j++) {
                    answer[i][j] = dots[i][j];
                }
            }

            return answer;
        } else {
            answer = new boolean[dots.length - val][dots[0].length];

            for (int i = 1; ((val + i < dots.length) && (val - i >= 0)); i++) {
                for (int j = 0; j < dots[0].length; j++) {
                    answer[answer.length - i][j] = dots[val - i][j] || dots[val + i][j];
                }
            }
            for (int i = 0; i < answer.length - val - 1; i++) {
                for (int j = 0; j < dots[0].length; j++) {
                    answer[i][j] = dots[dots.length - 1 - i][j];
                }
            }

            return answer;
        }
    }



    private static boolean[][] foldY(boolean[][] dots, int val) {
        boolean[][] answer;
        if (val > dots[0].length - val) {
            answer = new boolean[dots.length][val];
            for (int i = 0; i < dots.length; i++) {
                for (int j = 1; ((val + j < dots[0].length) && (val - j >= 0)); j++) {
                    answer[i][answer[0].length - j] = dots[i][val - j] || dots[i][val + j];
                }
            }
            for (int i = 0; i < answer.length; i++) {
                for (int j = 0; j < val; j++) {
                    answer[i][j] = dots[i][j];
                }
            }

            return answer;
        } else {
            answer = new boolean[dots.length][dots[0].length - val];

            for (int i = 0; i < dots.length; i++) {
                for (int j = 1; ((val + j < dots[0].length) && (val - j >= 0)); j++) {
                    answer[i][answer[0].length - j] = dots[i][val - j] || dots[i][val + j];
                }
            }

            for (int i = 0; i < answer.length; i++) {
                for (int j = 0; j < answer[0].length - val - 1; j++) {
                    answer[i][j] = dots[i][dots[0].length - 1 - j];
                }
            }

            return answer;
        }
    }

    @Override
    public String partTwo() {
        boolean[][] dots = new boolean[1500][1500];
        int i = 0;
        for (; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                break;
            }
            String[] coords = line.split(",");
            dots[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] = true;
        }

        int maxX = dots.length - 1;
        int maxY = dots[0].length - 1;
        for (i = i + 1; i < input.size(); i++) {
            String intstructions = input.get(i);
            intstructions = intstructions.substring(11);
            boolean isX = intstructions.charAt(0) == 'x';
            int val = Integer.parseInt(intstructions.substring(2));

            if (isX) {
                dots = foldX(dots, val);
            } else {
                dots = foldY(dots, val);
            }

        }


        int count = 0;
//        for (i = 0; i <= maxX; i++) {
//            for (int j = 0; j <= maxY; j++) {
//                count += dots[i][j] ? 1 : 0;
//            }
//        }

        for (i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[0].length; j++) {
                System.out.print(dots[i][j] ? '#' : '.');
            }
            System.out.println();
        }

        return String.valueOf(count);
    }
}
